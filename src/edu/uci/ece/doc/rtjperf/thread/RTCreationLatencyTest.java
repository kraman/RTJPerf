/*-------------------------------------------------------------------------*
 * $Id: RTCreationLatencyTest.java,v 1.1 2002/04/13 19:22:25 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.thread;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import javax.realtime.*;

public class RTCreationLatencyTest  {
    final static String CREATION_LATENCY = "RTCreationLatency";
    final static String STARTUP_LATENCY = "RTStartupLatency";

    public static void main(String[] args) throws Exception {

        final int count = Integer.parseInt(args[0]);
        final String path = args[1];

        // Plain linux has 90 different prio at most
        final int MAX_PRIORITY = 90;
        
        Runnable testLogic = new Runnable() {
                public void run() {
                    final HighResTimer timer = new HighResTimer();
                    final PerformanceReport report = new PerformanceReport(CREATION_LATENCY);
                    
                    final Runnable logic = new Runnable() {
                            public void run() {
                                timer.stop();
                                report.addMeasuredVariable(STARTUP_LATENCY, timer.getElapsedTime());
                                timer.reset();
                            }
                        };
                    
                    RealtimeThread rtThread = null;
                    SchedulingParameters schedParam = new PriorityParameters(MAX_PRIORITY);

                    for (int i = 0; i < count; i++) {
                        timer.start();
                        rtThread = new RealtimeThread(schedParam, null, null, null, null, logic);
                        timer.stop();
                        report.addMeasuredVariable(CREATION_LATENCY, timer.getElapsedTime());
                        timer.reset();
                        timer.start();
                        rtThread.start();
                        
                        try {
                            rtThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            rtThread = null;
                        }
                    }
                    
                    try {
                        report.generateDataFile(path + "/" + CREATION_LATENCY);
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            };
        
        RealtimeThread testThread = new RealtimeThread(new PriorityParameters(MAX_PRIORITY - 10),
                                                       null, null, null, null, testLogic);
        testThread.start();
    }
}
