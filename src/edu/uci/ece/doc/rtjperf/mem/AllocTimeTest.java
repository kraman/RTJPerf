/*-------------------------------------------------------------------------*
 * $Id: AllocTimeTest.java,v 1.2 2002/01/11 05:02:54 corsaro Exp $
 *-------------------------------------------------------------------------*/
//package edu.uci.ece.doc.rtjperf.mem;

// -- RTJava Import --
import javax.realtime.MemoryArea;
import javax.realtime.LTMemory;
import javax.realtime.VTMemory;
import javax.realtime.RealtimeThread;

// -- RTJPerf Import --
import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;

/**
 * This test takes care of measuring the time necessary to allocated
 * different sizes of object.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class AllocTimeTest {

    static class MemAllocatorLogic implements Runnable {

        static final String ALLOC_TIME = "AllocTime";

        private int count;
        private int allocRate;
        private String reportName;
        
        public MemAllocatorLogic(int count, int allocRate, String reportName) {
 
            this.count = count;
            this.allocRate = allocRate;
            this.reportName = reportName;
        }
           
        public void run() {
            
            HighResTimer timer = new HighResTimer();
            byte[] vec;
            PerformanceReport report = new PerformanceReport(reportName);
            
            for (int i = 0; i < this.count; ++i) {
                //                        System.out.println("Before Start");
                timer.start();
                //                        System.out.println("After Start");
                vec = new byte[this.allocRate * i];
                timer.stop();
                vec = null;
                report.addMeasuredVariable(ALLOC_TIME, timer.getElapsedTime());
            }
            try {
                report.generateDataFile("/home/zeus/Devel/RTJPerf/src/edu/uci/ece/doc/rtjperf/mem");
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {

        final int count = Integer.parseInt(args[0]);
        final int allocRate = Integer.parseInt(args[1]);
        

        final LTMemory ltMem = new LTMemory(2 * count * count * allocRate, 2 * count * count * allocRate);
        RealtimeThread rt1 = new RealtimeThread() {
                
                public void run() {
                    Runnable logic = new MemAllocatorLogic(count, allocRate, "LTAllTime");
                    ltMem.enter(logic);
                }
            };
        rt1.start();
        rt1.join();
        
        final VTMemory vtMem = new VTMemory(2 * count * count * allocRate, 2 * count * count * allocRate);
        RealtimeThread rt2 = new RealtimeThread() {

                public void run() {
                    Runnable logic = new MemAllocatorLogic(count, allocRate, "VTAllTime");
                    vtMem.enter(logic);
                }
            };
        
        rt2.start();
    }
}            
    
