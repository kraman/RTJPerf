/*-------------------------------------------------------------------------*
 * $Id: CreationLatencyTest.java,v 1.1 2002/03/07 04:44:07 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.thread;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import javax.realtime.*;

public class CreationLatencyTest  {
    final static String CREATION_LATENCY = "CreationLatency";
    final static String STARTUP_LATENCY = "StartupLatency";
    public static void main(String[] args) throws Exception {

        int count = Integer.parseInt(args[0]);
        String path = args[1];
        
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
        SchedulingParameters schedParam = new PriorityParameters(PriorityScheduler.MAX_PRIORITY);
        for (int i = 0; i < count; i++) {
            timer.start();
            rtThread = new RealtimeThread(null, null, null, null, null, logic);
            timer.stop();
            report.addMeasuredVariable(CREATION_LATENCY, timer.getElapsedTime());
            timer.reset();
            timer.start();
            rtThread.start();
            rtThread.join();
        }

        report.generateDataFile(args[1] + "/" + CREATION_LATENCY);
    }
    
}
