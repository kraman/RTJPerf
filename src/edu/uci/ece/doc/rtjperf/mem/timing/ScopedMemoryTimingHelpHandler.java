// ************************************************************************
//    $Id: ScopedMemoryTimingHelpHandler.java,v 1.1 2002/11/30 23:33:52 corsaro Exp $
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

// -- jTools Import --
import edu.uci.ece.ac.jargo.HelpHandler;


public class ScopedMemoryTimingHelpHandler extends HelpHandler {
    
    static String msg = "\nUSAGE: The following option are needed for this test: \n\n\n" +
        
        "--count <alloc-count>          Sets the number of times the scoped memory \n" +
        "                               will be created/entered/exited.\n\n" + 

        "--scopedMemoryType <mem-type>  Sets the scoped memory area type to\n" +
        "                               be used; available option are\n" +
        "                               \"LTMemory\", \"VTMemory\" and \"CTMemory\"\n\n" +

        "--memSize <size>               Sets the size of the scoped memory in bytes\n\n" +
        
        "--outDir <dir-path>            Sets directory under which the result\n" +
        "                               of the test have to be saved.\n\n";
    
    public ScopedMemoryTimingHelpHandler() {
        super(msg);
    }
}
