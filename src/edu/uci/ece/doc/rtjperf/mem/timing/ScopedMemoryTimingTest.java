// ************************************************************************
//    $Id: ScopedMemoryTimingTest.java,v 1.5 2002/12/07 03:15:33 corsaro Exp $
// ************************************************************************
//
//                               RTJPerf
//
//               Copyright (C) 2001-2002 by Angelo Corsaro.
//                         <corsaro@ece.uci.edu>
//                          All Rights Reserved.
//
//   Permission to use, copy, modify, and distribute this software and
//   its  documentation for any purpose is hereby  granted without fee,
//   provided that the above copyright notice appear in all copies and
//   that both that copyright notice and this permission notice appear
//   in  supporting  documentation. I don't make  any  representations
//   about the  suitability  of this  software for any  purpose. It is
//   provided "as is" without express or implied warranty.
//
//
//
// *************************************************************************
//  
// *************************************************************************
package edu.uci.ece.doc.rtjperf.mem.timing;

// -- RTJava Import --
import javax.realtime.MemoryArea;
import javax.realtime.RealtimeThread;
import javax.realtime.PriorityParameters;
import javax.realtime.ImmortalMemory;

// -- jTools Import --
import edu.uci.ece.ac.time.HighResTimer;
import edu.uci.ece.ac.time.HighResClock;
import edu.uci.ece.ac.time.PerformanceReport;
import edu.uci.ece.ac.jargo.*;

// -- RTJPerf Import --
import edu.uci.ece.doc.rtjperf.util.RTJPerfArgs;
import edu.uci.ece.doc.rtjperf.mem.MemoryAreaFactory;

/**
 * This test performs measures the time taken to create/enter/exit a
 * ScopedMemory.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class ScopedMemoryTimingTest {

    final static String CREATION_TIME = "CreationTime";
    final static String ENTER_TIME = "EnterTime";
    final static String EXIT_TIME = "ExitTime";
    final static String EXEC_TIME = "ExecTime";
    
    static class Item {
        private double field0;
        private double field1;
        private double field2;
        private double field3;
        private double field4;
        
        //      protected void finalize() {
        // We don't do anything but, to check if Scoped memory are
        // finilizing the object allocated we can print something
        // from here.
        // System.out.print("o");
        //      }
    }

    static class AllocatorLogic implements Runnable {

        private final int SLACK = 1024;
        private MemoryArea scopedMemory;
        private HighResTimer timerEnter;
        private HighResTimer timerExit;
        private HighResTimer timerExec;
      
        AllocatorLogic(HighResTimer timerEnter,
                       HighResTimer timerExit,
                       HighResTimer timerExec) {
            this.timerEnter = timerEnter;
            this.timerExit = timerExit;
            this.timerExec = timerExec;
        }

        void setScopedMemory(MemoryArea scopedMemory) {
            this.scopedMemory = scopedMemory;
        }

        public void run() {
            timerEnter.stop();
            timerExec.start();
            while (this.scopedMemory.memoryRemaining() > SLACK) {
                new Item();
            }
            timerExec.stop();
            timerExit.start();
        }
    }                                              
    
    static class TestLogic implements Runnable {

        private int count;
        private long memSize;
        private int memType;
        private String outDir;
        
        public TestLogic(int count, long memSize, int memType, String outDir) {
            this.count = count;
            this.memSize = memSize;
            this.memType = memType;
            this.outDir = outDir;
        }

        public void run() {
            //            System.out.println("---------------------> Test Started <------------------ ");
            final HighResTimer timerEnter = new HighResTimer();
            final HighResTimer timerExit = new HighResTimer();
            final HighResTimer timerExec = new HighResTimer();
            final HighResTimer timer = new HighResTimer();
            PerformanceReport report =
                new PerformanceReport("ScopedMemoryTiming" + this.memType);
      
            timerEnter.start();
            timerEnter.stop();
            timerEnter.reset();
            
            timerExit.start();
            timerExit.stop();
            timerExit.reset();

            timer.start();
            timer.stop();
            timer.reset();

            // This memory area is used as a bootstram memory area within
            // which is created the Big Memory area that runs the test. This
            // guarantees that once we exit the bootstrap memory area (i.e. 
            // at each iteration of the test the big Memory Area is
            // automatically cleaned up)
            final CTMemory bootstrapMA = new CTMemory(8 * 1024 ); // 8KB

            final Runnable bootstrapLogic = new Runnable() {
                    public void run() {
                        AllocatorLogic allocatorLogic = new AllocatorLogic(timerEnter, timerExit, timerExec);

                        MemoryArea memoryArea =
                            MemoryAreaFactory.createMemoryArea(memSize, memSize, memType, timer);
                        
                        allocatorLogic.setScopedMemory(memoryArea);
                        
                        timerEnter.start();
                        memoryArea.enter(allocatorLogic);
                        timerExit.stop();
                  
                    }
                };
            
            for (int i = 0; i < this.count; i++) {
                bootstrapMA.enter(bootstrapLogic);
                report.addMeasuredVariable(CREATION_TIME + memSize, timer.getElapsedTime());
                timer.reset();
              
              
                report.addMeasuredVariable(ENTER_TIME + memSize, timerEnter.getElapsedTime());
                timerEnter.reset();
              
                report.addMeasuredVariable(EXIT_TIME + memSize, timerExit.getElapsedTime());
                timerExit.reset();
              
                report.addMeasuredVariable(EXEC_TIME + memSize, timerExec.getElapsedTime());
                timerExec.reset();
            }
              
            
            try {
                report.generateDataFile(this.outDir);
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
            }
            //            System.out.println("\n---------------------> Test Completed <------------------ ");
        }
    }

    static ArgParser parseArgs(String[] args) throws Exception {
        
        CommandLineSpec cls = new CommandLineSpec();
        cls.addRequiredArg(RTJPerfArgs.COUNT_OPT);
        cls.addRequiredArg(RTJPerfArgs.SCOPED_MEMORY_TYPE_OPT);
        cls.addRequiredArg(RTJPerfArgs.MEM_SIZE_OPT);
        cls.addRequiredArg(RTJPerfArgs.OUT_DIR_OPT);
        
        ArgParser argParser = new ArgParser(cls, new ScopedMemoryTimingHelpHandler());
        argParser.parse(args);
        return argParser;
    }
    
    public static void main(String[] args) throws Exception {
        ArgParser argParser = parseArgs(args);

        Integer value = (Integer)argParser.getArg(RTJPerfArgs.COUNT_OPT).getValue();
        final int count = value.intValue();

        value = (Integer)argParser.getArg(RTJPerfArgs.SCOPED_MEMORY_TYPE_OPT).getValue();
        final int memType = value.intValue();

        value = (Integer)argParser.getArg(RTJPerfArgs.MEM_SIZE_OPT).getValue();
        long memSize = value.intValue();

        String outDir = (String)argParser.getArg(RTJPerfArgs.OUT_DIR_OPT).getValue();

        TestLogic testLogic = new TestLogic(count,
                                            memSize,
                                            memType,
                                            outDir);
        
        RealtimeThread rtThread =
            new RealtimeThread(new PriorityParameters(30),
                               null,
                               null,
                               ImmortalMemory.instance(),
                               null,
                               testLogic);
        

        // -- Start the test --
        rtThread.start();

        // Join the thread
        rtThread.join();
    }
}            
    
