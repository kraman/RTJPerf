/*-------------------------------------------------------------------------*
 * $Id: PeriodicThreadTest.java,v 1.1 2002/03/07 23:46:08 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.thread;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import javax.realtime.*;

public class PeriodicThreadTest {

    static final String THREAD_PERIOD = "ThreadPeriod";
    public static void main(String[] args) {
        
        final int count = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        final String path = args[2];
        final PerformanceReport report = new PerformanceReport(THREAD_PERIOD + "Test");
        final HighResTimer timer = new HighResTimer();

        ////////////////////////////////////////////////////////////
        // Runnable Logic Anonymous Class
        Runnable periodicThreadLogic = new Runnable() {
                public void run() {
                    RealtimeThread rtThread = null;
                    try {
                        rtThread = RealtimeThread.currentRealtimeThread();

                        for (int i = 0; i < count; ++i) {
                            timer.start();
                            rtThread.waitForNextPeriod();
                            timer.stop();
                            report.addMeasuredVariable(THREAD_PERIOD, timer.getElapsedTime());
                        }
                        report.generateDataFile(path + "/");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                    
            };
        //
        ////////////////////////////////////////////////////////////

        RelativeTime period = new RelativeTime(T, 0); // T msec Period
        
        PeriodicParameters periodicParams =
            new PeriodicParameters(new RelativeTime(0, 0), // Start Time
                                  period,
                                  null,   // cost estimate
                                  period, // deadline == period
                                  null,   // overrun handler
                                  null);  // miss handler

        PriorityParameters prioParams =
            new PriorityParameters(PriorityScheduler.MAX_PRIORITY);

        RealtimeThread rtThread =
            new RealtimeThread(prioParams, // Sched Params
                               periodicParams, //Release Params
                               null, // mem params
                               null, // mem area
                               null, // processing group
                               periodicThreadLogic); // logic

        rtThread.start();
    }
    
}
