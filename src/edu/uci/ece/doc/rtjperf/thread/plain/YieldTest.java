/*-------------------------------------------------------------------------*
 * $Id: YieldTest.java,v 1.1 2002/03/07 04:44:07 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.thread.plain;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import edu.uci.ece.doc.util.concurrent.EventVariable;

public class YieldTest {

    final static String YIELD_LATENCY = "YieldLatency";
    static int activeThread = 0;

    static class YieldLogic implements Runnable {

        private int count;
        private int yieldCount = 0;
        private HighResTimer timer;
        private PerformanceReport report;
        private EventVariable event;
        
        YieldLogic(HighResTimer timer, int count, PerformanceReport report, EventVariable event) {
            this.timer = timer;
            this.count = count;
            this.report = report;
            this.event = event;
        }

        int getYieldCount() {
            return this.yieldCount;
        }
        
        public void run() {
            String name = Thread.currentThread().getName();
            //            event.signal();
            try {
                event.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (this.yieldCount < this.count) {
                this.timer.stop();
                this.yieldCount++;
                
                //                System.out.println(name + "  Executing\t" + yieldCount);
                //                System.out.flush();
                if (this.yieldCount >= 10)
                    report.addMeasuredVariable(YIELD_LATENCY, timer.getElapsedTime());
                
                //                System.out.println(name + "  Yielding\t" + yieldCount);
                //                System.out.flush();
                Thread.yield();
            }
        }
    }

    
    static class MainYieldLogic implements  Runnable {

        private PerformanceReport report;
        private int count;
        private int priority;
        private String reportPath;
        
        MainYieldLogic(int count, int priority, String reportPath) {
            report = new PerformanceReport(YIELD_LATENCY);
            this.count = count;
            this.priority = priority;
            this.reportPath = reportPath;
        }
        
        public void run() {
            try {
                HighResTimer timer = new HighResTimer();
                EventVariable event = new EventVariable();
                YieldLogic yieldLogic = new YieldLogic(timer, count, report, event);             
                Thread rt = new Thread(yieldLogic);
                rt.setPriority(priority);
                rt.start();
                String name = Thread.currentThread().getName();
                int safenessCount = 0;
                event.signal();
                while ( yieldLogic.getYieldCount() < count && safenessCount < 10000) {
                    safenessCount++;
                    //                    System.out.println(name + "  Executing Main");
                    //                    System.out.flush();
                    timer.reset();
                    timer.start();
                    //                    System.out.println(name + "  Yielding Main");
                    Thread.yield();
                }
                report.generateDataFile(reportPath + "/");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
                        
    }

    
    public static void main(String[] args) throws Exception {
        final int count = Integer.parseInt(args[0]) + 10;
        final String path = args[1];
        MainYieldLogic logic = new MainYieldLogic(count, Thread.MAX_PRIORITY, path);
        Thread runner = new Thread(logic);
        runner.setPriority(Thread.MAX_PRIORITY);
        runner.start();
    }
}
