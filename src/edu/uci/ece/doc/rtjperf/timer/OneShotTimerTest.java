/*-------------------------------------------------------------------------*
 * $Id: OneShotTimerTest.java,v 1.2 2002/03/19 07:14:48 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.timer;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import edu.uci.ece.doc.util.concurrent.EventVariable;
import javax.realtime.*;


public class OneShotTimerTest {

    static final String TIMEOUT_TIME = "TimeOutTime";
    
    public static void main(String[] args) {
        final int count = Integer.parseInt(args[0]);
        int millis = Integer.parseInt(args[1]); // time in msec
        int nanos = Integer.parseInt(args[2]);

        final PerformanceReport report =
            new PerformanceReport("OneShotTimerTest" + millis + "." + nanos);
        final HighResTimer timer = new HighResTimer();
        
        final String dataPath = args[3];
        final EventVariable event = new EventVariable();
        
        Runnable logic = new Runnable() {
                public void run() {
                    timer.stop();
                    report.addMeasuredVariable(TIMEOUT_TIME, timer.getElapsedTime());
                    timer.reset();
                    event.signal();
                }
            };

        PriorityParameters prioParams =
            new PriorityParameters(PriorityScheduler.MAX_PRIORITY);
        TimeoutHandler handler = new TimeoutHandler(prioParams, null, null, null, null, false,
                                                    logic);
        
        OneShotTimer ostimer =
            new OneShotTimer(new RelativeTime(millis, nanos),
                             handler);
        
        ostimer.enable();
        for (int i = 0; i < count; i++) {
            ostimer.start();
            timer.start();
            if (i == count - 1)
                break;
            try {
                event.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        try {
            report.generateDataFile(dataPath + "/");
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
}