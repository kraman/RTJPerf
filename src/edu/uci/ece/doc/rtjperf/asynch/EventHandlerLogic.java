/*-------------------------------------------------------------------------*
 * $Id: EventHandlerLogic.java,v 1.1 2002/01/09 03:15:21 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.asynch.timing;

// -- RTJPerf Import --
import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;

// -- DOC Utils Import --
import edu.uci.ece.doc.util.concurrent.EventVariable;


/**
 * This class encapsulates the event handling logic.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class EventHandlerLogic implements Runnable {

    private PerformanceReport performanceReport;
    private HighResTimer timer;
    private EventVariable eventVar;
    private boolean memProfiling;
    
    static final String DISPATCH_DELAY = "DispatchDelay";
    
    public EventHandlerLogic(boolean memProfiling) {
        this.memProfiling = memProfiling;
    }
    
    public EventHandlerLogic(PerformanceReport performanceReport,
                             HighResTimer timer,
                             EventVariable eventVar,
                             boolean memProfiling) {
        this.performanceReport = performanceReport;
        this.timer = timer;
        this.eventVar = eventVar;
        this.memProfiling = memProfiling;
    }

    public final void init(PerformanceReport performanceReport, HighResTimer timer, EventVariable eventVar) {
        this.performanceReport = performanceReport;
        this.timer = timer;
        this.eventVar = eventVar;
    }

    public final void run() {
        timer.stop();
        if (!this.memProfiling) 
            performanceReport.addMeasuredVariable(DISPATCH_DELAY, timer.getElapsedTime());
        
        timer.reset();
        eventVar.signal();
    }
}

