/*-------------------------------------------------------------------------*
 * $Id: MonitorTest.java,v 1.1 2002/03/08 02:26:52 corsaro Exp $
 *-------------------------------------------------------------------------*/

package edu.uci.ece.doc.rtjperf.synch;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import edu.uci.ece.doc.util.concurrent.EventVariable;
import javax.realtime.*;

public class MonitorTest {

    static final String MONITOR_ENTER_TIME =  "MonitorEnterTime";
    static final String MONITOR_EXIT_TIME =  "MonitorExitTime";
    
    static class Monitor {
        private HighResTimer timer;
        private PerformanceReport report;
        
        public Monitor(HighResTimer timer, PerformanceReport report) {
            this.timer = timer;
            this.report = report;
        }

        public synchronized void enter() {
            timer.stop();
            report.addMeasuredVariable(MONITOR_ENTER_TIME, timer.getElapsedTime());
            timer.reset();

            timer.start();
        }
    }

    public static void main(String[] args) {
        final int count = Integer.parseInt(args[0]);
        final PerformanceReport report =
            new PerformanceReport("MonitorTest");
        
        final String dataPath = args[1];
        
        Runnable logic = new Runnable() {
                public void run() {
                    final HighResTimer timer = new HighResTimer();
                    Monitor m = new Monitor(timer, report);

                    for (int i = 0; i < count; ++i) {
                        timer.start();
                        m.enter();
                        timer.stop();
                        report.addMeasuredVariable(MONITOR_EXIT_TIME, timer.getElapsedTime());
                        timer.reset();
                    }
                    try {
                        report.generateDataFile(dataPath + "/");
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            };

        RealtimeThread rtThread =
            new RealtimeThread(new PriorityParameters(PriorityScheduler.MAX_PRIORITY),
                               null,
                               null,
                               null,
                               null,
                               logic);
        
        rtThread.start();
    }
}
