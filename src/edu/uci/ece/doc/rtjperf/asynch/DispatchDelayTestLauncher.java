/*-------------------------------------------------------------------------*
 * $Id: DispatchDelayTestLauncher.java,v 1.4 2002/03/07 04:44:07 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.asynch.timing;

// -- DOC Util Import --
import edu.uci.ece.doc.util.ArgParser;
import edu.uci.ece.doc.util.CommandLineArgument;
import edu.uci.ece.doc.util.MalformedCommandLineArgumentException;
    
// -- RTJPerf Import --
import edu.uci.ece.doc.rtjperf.sys.PerformanceTestCase;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import edu.uci.ece.doc.rtjperf.util.SingletonMemoryAreaAccessor;
import edu.uci.ece.doc.rtjperf.util.RTJPerfArgs;

// -- RTJava Import --
import javax.realtime.AsyncEventHandler;
import javax.realtime.AsyncEvent;
import javax.realtime.BoundAsyncEventHandler;
import javax.realtime.SchedulingParameters;
import javax.realtime.ReleaseParameters;
import javax.realtime.MemoryParameters;
import javax.realtime.MemoryArea;
import javax.realtime.ImmortalMemory;
import javax.realtime.ProcessingGroupParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RelativeTime;
import javax.realtime.HeapMemory;
import javax.realtime.AperiodicParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.ThreadedAsyncEventHandler;
import javax.realtime.CTMemoryArea;
import javax.realtime.ScopedMemory;

// -- DOC Utils Import --
import edu.uci.ece.doc.util.ArgParser;
import edu.uci.ece.doc.util.CommandLineArgument;
import edu.uci.ece.doc.util.MalformedCommandLineArgumentException;


/**
 * This class takes care of acquiring and setting up all the
 * parameters needed to run the test.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class DispatchDelayTestLauncher {

    private AsyncEventHandler eventHandler;
    private EventHandlerLogic logic;
    
    private int fireCount;
    private int handlerPriority = PriorityScheduler.MAX_PRIORITY;
    private boolean noHeap;
    private boolean threadBound;
    private String outDir;
    private boolean memProfiling = false;
    private int profileStep;
    private SchedulingParameters schedParams;
    private ReleaseParameters releaseParams;
    private MemoryParameters memoryParams;
    private MemoryArea memoryArea;
    private ProcessingGroupParameters procGroupParams;
        
    private ArgParser argParser;
    protected PerformanceTestCase testCase;
    

    public DispatchDelayTestLauncher(final String[] args)
        throws MalformedCommandLineArgumentException
    {
        int requiredArgs = RTJPerfArgs.FIRE_COUNT_OPT_CODE
            | RTJPerfArgs.OUT_DIR_OPT_CODE;
        this.argParser = new ArgParser(args, requiredArgs);
        this.init();
    }

    public void launchTest() throws Exception {
        this.testCase.run();
        PerformanceReport report = testCase.getPerformanceReport();
        report.generateDataFile(this.outDir);
    }


    private void init() throws MalformedCommandLineArgumentException {
        CommandLineArgument cla; 

        cla = argParser.getCommandLineArgument(RTJPerfArgs.OUT_DIR_OPT);
        this.outDir = cla.getValue();
        
        if (this.argParser.isCommandLineArgumentDefined(RTJPerfArgs.THREAD_BOUND_OPT))
            this.threadBound = true;
        else
            this.threadBound = false;

        if (this.argParser.isCommandLineArgumentDefined(RTJPerfArgs.NO_HEAP_OPT))
            this.noHeap = true;
        else
            this.noHeap = false;
        
        if (this.argParser.isCommandLineArgumentDefined(RTJPerfArgs.HANDLER_PRIORITY_OPT)) {
            cla = this.argParser.getCommandLineArgument(RTJPerfArgs.HANDLER_PRIORITY_OPT);
            this.handlerPriority = Integer.parseInt(cla.getValue());
            cla = null;
        }

        this.schedParams = new PriorityParameters(this.handlerPriority);

        cla = this.argParser.getCommandLineArgument(RTJPerfArgs.FIRE_COUNT_OPT);
        this.fireCount = Integer.parseInt(cla.getValue());
        cla = null;
        
//         if (this.argParser.isCommandLineArgumentDefined(RTJPerfArgs.MEMORY_AREA_OPT)) {
//             cla = this.argParser.getCommandLineArgument(RTJPerfArgs.MEMORY_AREA_OPT);
//             this.memoryArea =SingletonMemoryAreaAccessor.instance(cla.getValue());
//         }
//         else 
//             memoryArea =
//                 SingletonMemoryAreaAccessor.instance(SingletonMemoryAreaAccessor.HEAP_MEMORY);
        

        cla = this.argParser.getCommandLineArgument(RTJPerfArgs.MEM_PROFILE_OPT);
        if (cla != null) {
            this.memProfiling = true;
            this.profileStep = Integer.parseInt(cla.getValue());
        }
        // Set up remaining parameters...

        this.memoryParams = null;
        this.procGroupParams = null;
        this.releaseParams = new AperiodicParameters(new RelativeTime(10, 0), // cost
                                                     null,                    // deadline
                                                     null,                    // overrun handler
                                                     null);                   // miss handler
        
        this.logic = new EventHandlerLogic(this.memProfiling);
        if (this.threadBound) {
            this.eventHandler = new ThreadBoundAsynchHandler(this.schedParams,
                                                             this.releaseParams,
                                                             this.memoryParams,
                                                             this.memoryArea,
                                                             this.procGroupParams,
                                                             this.noHeap,
                                                             this.logic);
            // ((ThreadBoundAsynchHandler)this.eventHandler).activate();
        }
        else {
            System.out.println(">> Creating ThreadedAsyncEventHandler!");
            this.eventHandler = new ThreadedAsyncEventHandler(this.schedParams,
                                                              this.releaseParams,
                                                              this.memoryParams,
                                                              this.memoryArea,
                                                              this.procGroupParams,
                                                              this.noHeap,
                                                              this.logic);
        }

        int testThreadPriority;
        if (this.handlerPriority > PriorityScheduler.MIN_PRIORITY + 1)
            testThreadPriority = this.handlerPriority - 1;
        else
            testThreadPriority = PriorityScheduler.MIN_PRIORITY;

        AsyncEvent event = new AsyncEvent();
        cla = this.argParser.getCommandLineArgument(RTJPerfArgs.LP_ASYNC_HANDLER_NUMBER_OPT);
        
        if (cla != null) {
            int handlerNum = Integer.parseInt(cla.getValue());
            cla = cla = this.argParser.getCommandLineArgument(RTJPerfArgs.LP_ASYNC_HANDLER_PRIORITY_OPT);
            int handlerPrio;
            if (cla != null) {
                handlerPrio = Integer.parseInt(cla.getValue());
                cla = null;
            }
            else
                handlerPrio = testThreadPriority - 1;

            PriorityParameters pp = new PriorityParameters(handlerPrio);
            Runnable noOpLogic = new Runnable() {
                    public void run() {}
                };
            ThreadBoundAsynchHandler handler = new ThreadBoundAsynchHandler(pp,
                                                                            this.releaseParams,
                                                                            this.memoryParams,
                                                                            this.memoryArea,
                                                                            this.procGroupParams,
                                                                            this.noHeap,
                                                                            noOpLogic);

            for (int i = 0; i < handlerNum; ++i)
                event.addHandler(handler);
            
        }
        
        this.testCase = new AsyncEventHandlerDispatchDelayTest(this.eventHandler,
                                                               event,
                                                               this.logic,
                                                               this.fireCount,
                                                               new PriorityParameters(testThreadPriority),
                                                               this.noHeap,
                                                               this.memoryArea,
                                                               this.memProfiling,
                                                               this.profileStep);

        this.argParser = null;
    }

    public MemoryArea getMemoryArea() {
        return this.memoryArea;
    }

    public static void main(String[] args)  throws Exception {
        final String[] fargs = args;
        ArgParser.setArgProperty(RTJPerfArgs.HELP_OPT + ".handler",
                                 "edu.uci.ece.doc.rtjperf.asynch.timing.TestHelpHandler");
        ArgParser argParser = new ArgParser(args);
        String memType = "heap";
        CommandLineArgument cla = argParser.getCommandLineArgument(RTJPerfArgs.MEMORY_AREA_OPT);
        
        if (cla != null && cla.getArgValueNum() >= 1)
            memType = cla.getValue();

        //final MemoryArea memoryArea = SingletonMemoryAreaAccessor.instance(memType);
        final ScopedMemory memoryArea = new CTMemoryArea(50000000);
        argParser = null;
        
        // This thread is used to guarantee that all the memory
        // allocated during the test is allocated out of the type of
        // memory specified by the command line argument --memoryArea
        //        RealtimeThread rtThread = new RealtimeThread() {
        final Runnable logic = new Runnable() {
                public void run() {
                    try {
                        final DispatchDelayTestLauncher testLauncher =
                            new DispatchDelayTestLauncher(fargs);
                        System.out.println("--- Test Started ---");
                        testLauncher.launchTest();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            };
        
        Runnable rtlogic = new Runnable() {
                public void run() {
                    memoryArea.enter(logic);
                }
            };
        
        // MemoryArea.enter can only be called by a RealtimeThread
        PriorityParameters pp = new PriorityParameters((PriorityScheduler.MAX_PRIORITY - PriorityScheduler.MIN_PRIORITY)/2);
        RealtimeThread rtThread = new RealtimeThread(pp, null, null, null, null, rtlogic);
        
        rtThread.start();
        rtThread.join();
        System.out.println("--- Test Completed ---");
    }
}


    
