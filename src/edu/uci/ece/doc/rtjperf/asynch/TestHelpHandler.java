package edu.uci.ece.doc.rtjperf.asynch.timing;

import edu.uci.ece.doc.util.HelpHandler;

public class TestHelpHandler extends HelpHandler {
    
    static String msg = "\nUSAGE: The following option are available for this test (option marked \n" +
        "with a (*) are compulsory: \n\n\n" +
        "    --threadBoundHandler          If this option is present the BoundAsyncEventHandler\n" +
        "                                  is used.\n\n" +
        "    --noHeap                      If this option is present no heap memory is used.\n\n" +
        
        "    --handlerPriority <priority>  Defines the priority at which the handler is\n" +
        "                                  run (the default is PriorityScheduler.MAX_PRIORITY.\n\n" +
        
        "(*) --fireCount <fire-count>      Sets the number of times that the event has to be\n" +
        "                                  fired during the test\n\n" +
        
        "    --memoryArea <mem-area-type>  Sets the memory area type to be used; available option\n" +
        "                                  are \"immortal\" and \"heap\"\n\n" +
        
        "    --memProfiling <profile-step>   Sets the memory profile mode.\n\n" +
        
        "(*) --outDir <dir-path>           Sets directory under which the result of the test have\n" +
        "                                  to be saved.\n\n";
    
    public void handleHelp() {
        System.out.println(msg);
        System.exit(0);
    }
}
