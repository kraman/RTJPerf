package edu.uci.ece.doc.rtjperf.mem.plain;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;

public class AllocTimeTest {

    public static String ALLOC_TIME = "AllocTime";
    public static void main(String args[]) {
        final int count = Integer.parseInt(args[0]);
        final int chunckSize = Integer.parseInt(args[1]);
        String path = args[2];
        HighResTimer timer = new HighResTimer();
        byte[] vec;
        PerformanceReport report = new PerformanceReport("AllocTime");
        long start, stop;
        for (int i = 0; i < count; i++) {
            timer.start();
            vec = new byte[chunckSize];
            vec = null;
            timer.stop();
            report.addMeasuredVariable(ALLOC_TIME + chunckSize, timer.getElapsedTime());
            timer.reset();
        }

        try {
            report.generateDataFile(path);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}
