/*-------------------------------------------------------------------------*
 * $Id: PeriodicTimerTest.java,v 1.1 2002/03/08 03:46:06 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.timer;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import edu.uci.ece.doc.util.concurrent.EventVariable;
import javax.realtime.*;


public class PeriodicTimerTest {

    static final String TIMEOUT_TIME = "TimeOutTime";
    static int fireCount = 0;

    public static void main(String[] args) {
        final int count = Integer.parseInt(args[0]);
        final int time = Integer.parseInt(args[1]); // time in msec

        final PerformanceReport report =
            new PerformanceReport("PeriodicTimer");
        final HighResTimer timer = new HighResTimer();
        
        final String dataPath = args[2];
        final EventVariable event = new EventVariable();

        Runnable logic = new Runnable() {
                public void run() {
                    timer.stop();
                    report.addMeasuredVariable(TIMEOUT_TIME, timer.getElapsedTime());
                    timer.reset();
                    fireCount++;
                    timer.start();
                    if (fireCount == count)
                        event.signal();
                }
            };

        TimeoutHandler handler = new TimeoutHandler(null, null, null, null, null, false,
                                                    logic);
        
        PeriodicTimer ptimer =
            new PeriodicTimer(new RelativeTime(0, 0), // start
                              new RelativeTime(time, 0), // period
                             handler);
        
        ptimer.enable();
        timer.start();
        ptimer.start();

        try {
            event.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        try {
            report.generateDataFile(dataPath + "/");
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
}
