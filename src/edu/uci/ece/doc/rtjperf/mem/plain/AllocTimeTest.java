package edu.uci.ece.doc.rtjperf.mem.plain;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;

public class AllocTimeTest {

    public static String ALLOC_TIME = "AllocTime";
    public static void main(String args[]) {
        final int count = Integer.parseInt(args[0]);
        final int chunckSize = Integer.parseInt(args[1]);

        HighResTimer timer = new HighResTimer();
        byte[] vec;
        PerformanceReport report = new PerformanceReport("AllocTime");

        for (int i = 0; i < count; i++) {
            timer.start();
            vec = new byte[chunckSize];
            timer.stop();
            vec = null;
            report.addMeasuredVariable(ALLOC_TIME + chunckSize, timer.getElapsedTime());
        }

        try {
            report.generateDataFile("/home/angelo/Devel/RTJPerf/JDKData");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
