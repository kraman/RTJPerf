/*-------------------------------------------------------------------------*
 * $Id: PeriodicTimerTest.java,v 1.2 2002/03/19 07:14:48 corsaro Exp $
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
        int millis = Integer.parseInt(args[1]); // time in msec
        int nanos = Integer.parseInt(args[2]);


        final PerformanceReport report =
            new PerformanceReport("PeriodicTimerTest" + millis + "." + nanos);
        final HighResTimer timer = new HighResTimer();
        
        final String dataPath = args[3];
        final EventVariable event = new EventVariable();

        Runnable logic = new Runnable() {
                public void run() {
                    timer.stop();
                    report.addMeasuredVariable(TIMEOUT_TIME, timer.getElapsedTime());
                    timer.reset();
                    fireCount++;
                    timer.start();
                    // System.out.println("FireCount: " + fireCount);
                    if (fireCount == count) {
                        // System.out.println("Signaling event variable");
                        event.signal();
                    }
                }
            };

        TimeoutHandler handler = new TimeoutHandler(null, null, null, null, null, false,
                                                    logic);
        
        PeriodicTimer ptimer =
            new PeriodicTimer(new RelativeTime(0, 0), // start
                              new RelativeTime(millis, nanos), // period
                             handler);
        
        ptimer.enable();
        timer.start();
        ptimer.start();

        try {
            System.out.println("Waiting on event variable...");
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
