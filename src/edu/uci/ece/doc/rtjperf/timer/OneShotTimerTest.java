/*-------------------------------------------------------------------------*
 * $Id: OneShotTimerTest.java,v 1.1 2002/03/08 03:38:35 corsaro Exp $
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
        final int time = Integer.parseInt(args[1]); // time in msec

        final PerformanceReport report =
            new PerformanceReport("OneShotTimer");
        final HighResTimer timer = new HighResTimer();
        
        final String dataPath = args[2];
        final EventVariable event = new EventVariable();
        
        Runnable logic = new Runnable() {
                public void run() {
                    timer.stop();
                    report.addMeasuredVariable(TIMEOUT_TIME, timer.getElapsedTime());
                    timer.reset();
                    event.signal();
                }
            };

        TimeoutHandler handler = new TimeoutHandler(null, null, null, null, null, false,
                                                    logic);
        
        OneShotTimer ostimer =
            new OneShotTimer(new RelativeTime(time, 0),
                             handler);
        
        ostimer.enable();
        for (int i = 0; i < count; i++) {
            ostimer.start();
            timer.start();
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
