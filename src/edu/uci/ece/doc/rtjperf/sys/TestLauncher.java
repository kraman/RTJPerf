package edu.uci.ece.doc.rtjperf.sys;

import edu.uci.ece.doc.util.ArgParser;
import edu.uci.ece.doc.util.CommandLineArgument;
import edu.uci.ece.doc.util.MalformedCommandLineArgumentException;

public abstract class TestLauncher {

    protected final static String HELP_OPT = "--help";
    protected final static String OUT_DIR_OPT = "--outDir";

    protected ArgParser argParser;

    protected String outDir;

    protected static String usageMessage = "";

    protected PerformanceTestCase testCase;
    
    public TestLauncher(String[] args) throws MalformedCommandLineArgumentException {
        this.parseArgs(args); 
    }

    private final void parseArgs(String[] args) throws MalformedCommandLineArgumentException {
        this.argParser = new ArgParser(args);

        if (argParser.isCommandLineArgumentDefined(HELP_OPT)) {
            System.out.println(TestLauncher.usageMessage);
            System.exit(0);
        }

        CommandLineArgument cla;
        try {
            cla = argParser.getCommandLineArgument(OUT_DIR_OPT);
            this.outDir = cla.getValue();
        }
        catch (Exception e) {
            throw new MalformedCommandLineArgumentException("Missing or Malformed --outDir option");
        }
    }
    
    public void launchTest() throws Exception {
        this.testCase.run();
        PerformanceReport report = testCase.getPerformanceReport();
        report.generateDataFile(this.outDir);
    }

    public static final void setUsageMessage(String str) {
        usageMessage = str;
        MalformedCommandLineArgumentException.setUsageMessage(usageMessage);
    }
}
