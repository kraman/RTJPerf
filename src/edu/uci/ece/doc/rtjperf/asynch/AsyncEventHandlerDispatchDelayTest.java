/*-------------------------------------------------------------------------*
 * $Id: AsyncEventHandlerDispatchDelayTest.java,v 1.4 2002/03/07 04:44:07 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.asynch.timing;

// -- RTJPerf Import --
import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.PerformanceTestCase;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;

// -- RTJava Import --
import javax.realtime.AsyncEvent;
import javax.realtime.AsyncEventHandler;
import javax.realtime.SchedulingParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.NoHeapRealtimeThread;
import javax.realtime.MemoryArea;

// -- DOC Utils Import --
import edu.uci.ece.doc.util.concurrent.EventVariable;

/**
 * This test tryes to identify the delay, and jitter incurred in
 * firing <code> AsynchEventHandler </code> under different
 * circumstances.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class AsyncEventHandlerDispatchDelayTest extends PerformanceTestCase {

    private final HighResTimer timer;
    private final AsyncEvent event;
    private  EventVariable eventVar;
    private final int fireCount;
    private RealtimeThread thread;
    private final MemoryArea memoryArea;

    private boolean memProfiling = false;
    private int profilingStep;
    
    public AsyncEventHandlerDispatchDelayTest(AsyncEventHandler eventHandler,
                                              AsyncEvent event,
                                              EventHandlerLogic logic,
                                              int fireCount,
                                              SchedulingParameters schedParams,
                                              boolean noHeap,
                                              MemoryArea memoryArea,
                                              boolean memProfiling,
                                              int profilingStep)
    {
        super("AsynchEventHandlerDelayTest",
              "This test measure the delay incurred in the notification of a AsynchEventHandler");
        this.timer = new HighResTimer();
        this.event = event;
        this.eventVar = new EventVariable();
        this.fireCount = fireCount;
        this.memoryArea = memoryArea;
        this.memProfiling = memProfiling;
        this.profilingStep = profilingStep;
        
        logic.init(this.performanceReport, this.timer, this.eventVar);
        // this.performanceReport.preallocateMeasuredVariableStorage(EventHandlerLogic.DISPATCH_DELAY, fireCount);
        this.event.addHandler(eventHandler);
        Runnable runnable = new Runnable() {
                public void run() {
                    runImpl();
                }
            };
        if (noHeap)
            this.thread = new NoHeapRealtimeThread(schedParams, null, null, memoryArea, null, runnable);
        else
            this.thread = new RealtimeThread(schedParams, null, null, memoryArea, null, runnable);
    }

    protected void runLogic() {
        this.timer.start();
        event.fire();
        try {
            eventVar.await();
            // The following wait is necessary to force the
            // termination of the thread that was used to run the
            // handler, when the test is run on plain Linux instead
            // of Linux RT.
            eventVar.await(1);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    protected void runImpl() {
        Runtime runtime = Runtime.getRuntime();
        super.runTime.start();
        int j = 0;
        long ubgc, abgc;
        System.out.println("Used Memory (BGC)  Available Memory (BGC) Used Memory (AGC)  Available Memory (AGC)");
        for (int i = 0; i < this.fireCount; ++i) {
            this.runLogic();
            if (this.memProfiling && ++j >= this.profilingStep) {
                ubgc = this.memoryArea.memoryConsumed();
                abgc = this.memoryArea.memoryRemaining();
                System.runFinalization();
                System.gc();
                System.runFinalization();
                System.gc();
                System.out.println(ubgc +  "   " + abgc + "   "
                                   +  this.memoryArea.memoryConsumed() +
                                   "   "  + this.memoryArea.memoryRemaining());
                j = 0;
            }
        }
                
        super.runTime.stop();
        super.setTestExecutionTime();
    }

    public void run() {
        this.thread.start();
        try {
            this.thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
