/*-------------------------------------------------------------------------*
 * $Id: AllocTimeTest.java,v 1.6 2002/03/26 18:46:09 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.mem;

// -- RTJava Import --
import javax.realtime.MemoryArea;
import javax.realtime.LTMemory;
import javax.realtime.VTMemory;
import javax.realtime.RealtimeThread;
import javax.realtime.CTMemoryArea;
import javax.realtime.PriorityParameters;

// -- RTJPerf Import --
import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResClock;
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

        public static String ALLOC_TIME;

        private int count;
        private int allocSize;
        private String reportName;
        private MemoryArea memArea;
        private int memType;
        
        public MemAllocatorLogic(int count, int allocSize,
                                 MemoryArea memArea,
                                 String reportName,
                                 String varName,
                                 int memType) {
 
            this.count = count;
            this.allocSize = allocSize;
            this.reportName = reportName;
            this.memArea = memArea;
            this.ALLOC_TIME = varName;
            this.memType = memType;
        }
           
        public void run() {
            System.out.println("---------------------> Test Started <------------------ ");
            HighResTimer timer = new HighResTimer();
            byte[] vec;
            long start, stop;
            timer.start();
            timer.stop();
            timer.reset();
            PerformanceReport report = new PerformanceReport("AllocTime");
            for (int i = 0; i < this.count; ++i) {
                timer.start();
                vec = new byte[allocSize];
                timer.stop();
                vec = null;
                report.addMeasuredVariable(ALLOC_TIME, timer.getElapsedTime());
            }
            try {
                report.generateDataFile("/home/angelo/Devel/RTJPerf/AllocTime" + this.memType);
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
            }
            System.out.println("---------------------> Test Completed <------------------ ");
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        final int count = Integer.parseInt(args[0]);
        final int allocSize = Integer.parseInt(args[1]);
        final int memType = Integer.parseInt(args[2]);
        long memSize = (allocSize * 4 * count) + (18 * count * 4096); // + count * 500000;
        System.out.println("Allocating: " + memSize);
        final MemoryArea memArea = MemoryAreaFactory.createMemoryArea(memSize, memSize, memType);

        RealtimeThread rt = new RealtimeThread() {
                public void run() {
                    Runnable logic = new MemAllocatorLogic(count,
                                                           allocSize,
                                                           memArea,
                                                           "AllocTime",
                                                           "AllocTime" + allocSize,
                                                            memType);
                    memArea.enter(logic);
                }
            };
        rt.setSchedulingParameters(new PriorityParameters(30));
        rt.start();
    }
}            
    
