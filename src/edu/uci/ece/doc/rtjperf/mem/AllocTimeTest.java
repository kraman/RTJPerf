/*-------------------------------------------------------------------------*
 * $Id: AllocTimeTest.java,v 1.3 2002/02/25 21:28:08 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.mem;

// -- RTJava Import --
import javax.realtime.MemoryArea;
//import javax.realtime.LTMemory;
//import javax.realtime.VTMemory;
import javax.realtime.RealtimeThread;
import javax.realtime.CTMemoryArea;
import javax.realtime.PriorityParameters;

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

        public static String ALLOC_TIME;// = "AllocTime";

        private int count;
        private int allocRate;
        private String reportName;
        private MemoryArea memArea;
        
        public MemAllocatorLogic(int count, int allocRate,
                                 MemoryArea memArea,
                                 String reportName,
                                 String varName) {
 
            this.count = count;
            this.allocRate = allocRate;
            this.reportName = reportName;
            this.memArea = memArea;
            this.ALLOC_TIME = varName;
        }
           
        public void run() {
            System.out.println("---------------------> Starting <------------------ ");
            //            String ALLOC_TIME = new String("AllocTime");
            HighResTimer timer = new HighResTimer();
            byte[] vec;
            PerformanceReport report = new PerformanceReport(reportName);
            for (int i = 0; i < this.count; ++i) {
                //                System.out.println(this.memArea.memoryConsumed());
                
                timer.start();
                vec = new byte[allocRate];
                timer.stop();
                vec = null;
                report.addMeasuredVariable(ALLOC_TIME, timer.getElapsedTime());
                //                System.out.println(timer.getElapsedTime());
                //                System.out.print(i + ", ");
            }
            try {
                //                System.out.println("Report Name: " + reportName);
                
                report.generateDataFile("/home/angelo/Devel/RTJPerf/Data");
                //                System.out.println("Variable Name: " + ALLOC_TIME);
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
            }
            System.out.println("---------------------> Done <------------------ ");
        }
    }


    public static void main(String[] args) throws Exception {
        final int count = Integer.parseInt(args[0]);
        final int allocRate = Integer.parseInt(args[1]);
        //        final int size = Integer.parseInt(args[2]);
        //         final LTMemory ltMem = new LTMemory(2 * count * count * allocRate, 2 * count * count * allocRate);
//         RealtimeThread rt1 = new RealtimeThread() {
                
//                 public void run() {
//                     Runnable logic = new MemAllocatorLogic(count, allocRate, "LTAllTime");
//                     ltMem.enter(logic);
//                 }
//             };
//         rt1.start();
//         rt1.join();
        
//         final VTMemory vtMem = new VTMemory(2 * count * count * allocRate, 2 * count * count * allocRate);
//         RealtimeThread rt2 = new RealtimeThread() {

//                 public void run() {
//                     Runnable logic = new MemAllocatorLogic(count, allocRate, "VTAllTime");
//                     vtMem.enter(logic);
//                 }
//             };
        
//         rt2.start();
//         rt2.join();
        
        final CTMemoryArea ctMem = new CTMemoryArea(100 * allocRate * count + count * 500000, true);
        RealtimeThread rt3 = new RealtimeThread() {

                public void run() {
                    Runnable logic = new MemAllocatorLogic(count,
                                                           allocRate,
                                                           ctMem,
                                                           "CTAllTime",
                                                           "Time" + allocRate);
                    ctMem.enter(logic);
                }
            };

        rt3.setSchedulingParameters(new PriorityParameters(90));
        rt3.start();
    }
}            
    
