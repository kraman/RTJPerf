/*-------------------------------------------------------------------------*
 * $Id: DispatchDelayTestLauncher.java,v 1.1 2002/01/09 03:15:21 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.asynch.timing;

// -- DOC Util Import --
import edu.uci.ece.doc.util.ArgParser;
import edu.uci.ece.doc.util.CommandLineArgument;
import edu.uci.ece.doc.util.MalformedCommandLineArgumentException;
    
// -- RTJPerf Import --
import edu.uci.ece.doc.rtjperf.sys.PerformanceTestCase;
import edu.uci.ece.doc.rtjperf.sys.TestLauncher;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import edu.uci.ece.doc.rtjperf.util.SingletonMemoryAreaAccessor;
    
// -- RTJava Import --
import javax.realtime.AsyncEventHandler;
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

// -- DOC Utils Import --
import edu.uci.ece.doc.util.ArgParser;
import edu.uci.ece.doc.util.CommandLineArgument;

/**
 * This class takes care of acquiring and setting up all the
 * parameters needed to run the test.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class DispatchDelayTestLauncher extends TestLauncher {

    private AsyncEventHandler eventHandler;
    private EventHandlerLogic logic;
    
    private int fireCount;
    private int handlerPriority = PriorityScheduler.MAX_PRIORITY;
    private boolean noHeap;
    private boolean threadBound;
    private String outPath;
    private boolean memProfiling = false;
    private int profileStep;
    private SchedulingParameters schedParams;
    private ReleaseParameters releaseParams;
    private MemoryParameters memoryParams;
    private MemoryArea memoryArea;
    private ProcessingGroupParameters procGroupParams;
        

    private static String THREAD_BOUND_OPT = "--threadBoundHandler";
    private static String NO_HEAP_OPT = "--noHeap";
    private static String HANDLER_PRIORITY_OPT = "--handlerPriority";
    private static String FIRE_COUNT_OPT = "--fireCount";
    private static String MEMORY_AREA_OPT = "--memoryArea";
    private static String OUT_DIR_OPT = "--outDir";
    private static String HELP_OPT = "--help";
    private static String MEM_PARAMS_OPTION = "--memParams";
    private static String MEM_PROFILE_OPT = "--memProfiling";
    
    
    static {
        String msg = "Usage: The following option are available for this test (option marked \n" +
            "with a (*) are compulsory: \n\n" +
            "    --threadBoundHandler          If this option is present the BoundAsyncEventHandler\n" +
            "                                  is used.\n" +
            "    --noHeap                      If this option is present no heap memory is used.\n" +
            "    --handlerPriority <priority>  Defines the priority at which the handler is\n" +
            "                                  run (the default is PriorityScheduler.MAX_PRIORITY.\n" +
            "(*) --fireCount <fire-count>      Sets the number of times that the event has to be\n" +
            "                                  fired during the test\n" +
            "    --memoryArea <mem-area-type>  Sets the memory area type to be used; available option\n" +
            "                                  are \"immortal\" and \"heap\"\n" +
            "    --memProfiling <profile-step>   Sets the memory profile mode.\n" +
            "(*) --outDir <dir-path>           Sets directory under which the result of the test have\n" +
            "                                  to be saved.\n";
        
        TestLauncher.setUsageMessage(msg);
    }
    
    public DispatchDelayTestLauncher(final String[] args) throws MalformedCommandLineArgumentException {
        super(args);
        this.init();
    }


    private void init() throws MalformedCommandLineArgumentException {
        CommandLineArgument cla; 
        if (this.argParser.isCommandLineArgumentDefined(THREAD_BOUND_OPT))
            this.threadBound = true;
        else
            this.threadBound = false;

        if (this.argParser.isCommandLineArgumentDefined(NO_HEAP_OPT))
            this.noHeap = true;
        else
            this.noHeap = false;
        
        if (this.argParser.isCommandLineArgumentDefined(HANDLER_PRIORITY_OPT)) {
            cla = this.argParser.getCommandLineArgument(HANDLER_PRIORITY_OPT);
            if (cla.getArgValueNum() < 1)
                throw new MalformedCommandLineArgumentException("Missing priority value" +
                                                                "for argument --handlerPriority");
            else {
                this.handlerPriority = Integer.parseInt(cla.getValue());
                cla = null;
            }
        }

        this.schedParams = new PriorityParameters(this.handlerPriority);

        cla = this.argParser.getCommandLineArgument(FIRE_COUNT_OPT);
        if (cla == null)
            throw new MalformedCommandLineArgumentException("Missing command line arg --fireCount");
        
        if (cla.getArgValueNum() < 1)
            throw new MalformedCommandLineArgumentException("Missing fire count for argument --fireCount");
        
        this.fireCount = Integer.parseInt(cla.getValue());
        cla = null;
        
        if (this.argParser.isCommandLineArgumentDefined(MEMORY_AREA_OPT)) {
            cla = this.argParser.getCommandLineArgument(MEMORY_AREA_OPT);

            if (cla.getArgValueNum() < 1)
                throw new MalformedCommandLineArgumentException("Missing argument for --mememoryArea");

            this.memoryArea =SingletonMemoryAreaAccessor.instance(cla.getValue());

            if (memoryArea == null)
                throw new MalformedCommandLineArgumentException("Unknown value for --mememoryArea was provided");
        }
        else 
            memoryArea =
                SingletonMemoryAreaAccessor.instance(SingletonMemoryAreaAccessor.HEAP_MEMORY);
        

        cla = this.argParser.getCommandLineArgument(MEM_PROFILE_OPT);
        if (cla != null) {
            if ( cla.getArgValueNum() < 1) 
                throw new MalformedCommandLineArgumentException("No value for --memProfiling was provided");

            else if (cla != null) {
                this.memProfiling = true;
                this.profileStep = Integer.parseInt(cla.getValue());
            }
        }
        // Set up remaining parameters...

        this.memoryParams = null;
        this.procGroupParams = null;
        this.releaseParams = new AperiodicParameters(new RelativeTime(10, 0), // cost
                                                     null,                    // deadline
                                                     null,                    // overrun handler
                                                     null);                   // miss handler
        
        this.logic = new EventHandlerLogic(this.memProfiling);
        if (this.threadBound)
            this.eventHandler = new ThreadBoundAsynchHandler(this.schedParams,
                                                             this.releaseParams,
                                                             this.memoryParams,
                                                             this.memoryArea,
                                                             this.procGroupParams,
                                                             this.noHeap,
                                                             this.logic);
        else
            this.eventHandler = new AsyncEventHandler(this.schedParams,
                                                      this.releaseParams,
                                                      this.memoryParams,
                                                      this.memoryArea,
                                                      this.procGroupParams,
                                                      this.noHeap,
                                                      this.logic);

        int testThreadPriority;
         if (this.handlerPriority > PriorityScheduler.MIN_PRIORITY)
             testThreadPriority = this.handlerPriority - 1;
         else
             testThreadPriority = PriorityScheduler.MIN_PRIORITY;
            
        this.testCase = new AsyncEventHandlerDispatchDelayTest(this.eventHandler,
                                                               this.logic,
                                                               this.fireCount,
                                                               new PriorityParameters(testThreadPriority),
                                                               this.noHeap,
                                                               this.memoryArea,
                                                               this.memProfiling,
                                                               this.profileStep);
    }

    public MemoryArea getMemoryArea() {
        return this.memoryArea;
    }

    public static void main(String[] args)  throws Exception {
        
        final String[] fargs = args;
        ArgParser argParser = new ArgParser(args);
        String memType = "heap";
        
        CommandLineArgument cla = argParser.getCommandLineArgument(MEMORY_AREA_OPT);

        if (cla != null && cla.getArgValueNum() >= 1)
            memType = cla.getValue();
        
        final MemoryArea memoryArea = SingletonMemoryAreaAccessor.instance(memType);
        

        // This thread is used to guarantee that all the memory
        // allocated during the test is allocated out of the type of
        // memory specified by the command line argument --memoryArea
        //        RealtimeThread rtThread = new RealtimeThread() {
        final Runnable logic = new Runnable() {
                public void run() {
                    System.out.println("--- Test Started ---");
                    try {
                        final DispatchDelayTestLauncher testLauncher =
                            new DispatchDelayTestLauncher(fargs);
                        testLauncher.launchTest();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            };

        // MemoryArea.enter can only be called by a RealtimeThread
        RealtimeThread rtThread = new RealtimeThread() {
                public void run() {
                    memoryArea.enter(logic);
                }
            };

        rtThread.start();
        rtThread.join();
        System.out.println("--- Test Completed ---");
    }
}


    
