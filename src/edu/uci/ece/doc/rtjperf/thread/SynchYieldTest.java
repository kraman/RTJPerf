/*-------------------------------------------------------------------------*
 * $Id: SynchYieldTest.java,v 1.1 2002/03/08 00:42:45 corsaro Exp $
 *-------------------------------------------------------------------------*/

package edu.uci.ece.doc.rtjperf.thread;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import edu.uci.ece.doc.util.concurrent.EventVariable;
import javax.realtime.*;

public class SynchYieldTest {

    static final String SYNCH_YIELD_TIME = "SynchYieldTimeTest";
    
    static class Synchornizer {
        
        private HighResTimer timer;
        private PerformanceReport report; 
        private EventVariable exitEvent;
        private EventVariable enterEvent;
        private boolean firstTime = true;
        
        public Synchornizer(EventVariable exitEvent,EventVariable enterEvent, PerformanceReport report) {
            this.exitEvent = exitEvent;
            this.enterEvent = enterEvent;
            this.report = report;
            timer = new HighResTimer();
        }

        public synchronized void enterLow() {
            if (!firstTime)
                enterEvent.signal();
            else
                firstTime = false;
            try {
                exitEvent.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.start();
        }

        public synchronized void enterHigh() {
            timer.stop();
            report.addMeasuredVariable(SYNCH_YIELD_TIME, timer.getElapsedTime());
            timer.reset();
        }
        
    }
    public static void main(String args[]) throws Exception {

        final int count = Integer.parseInt(args[0]);
        final EventVariable exitEvent = new EventVariable();
        final EventVariable enterEvent = new EventVariable();
        final PerformanceReport report =
            new PerformanceReport(SYNCH_YIELD_TIME + "Test");

        final String dataPath = args[1];
        final Synchornizer synch = new Synchornizer(exitEvent, enterEvent, report);

        Runnable lowPrioLogic = new Runnable() {
                public void run() {
                    for (int i = 0; i < count; i++) {
                        synch.enterLow();
                    }
                }
            };

        Runnable highPrioLogic = new Runnable() {
                public void run() {
                    for (int i = 0; i < count; i++) {
                        exitEvent.signal();
                        synch.enterHigh();
                        try {
                            enterEvent.wait();
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
                
            };

        RealtimeThread lowPrio =
            new RealtimeThread(new PriorityParameters(PriorityScheduler.MAX_PRIORITY - 5),
                               null,
                               null,
                               null,
                               null,
                               lowPrioLogic);

        RealtimeThread highPrio =
            new RealtimeThread(new PriorityParameters(PriorityScheduler.MAX_PRIORITY),
                               null,
                               null,
                               null,
                               null,
                               highPrioLogic);

        lowPrio.start();
        highPrio.start();
    }
}
