// ************************************************************************
//    $Id: MemoryAreaFactory.java,v 1.5 2002/12/05 22:22:16 corsaro Exp $
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
package edu.uci.ece.doc.rtjperf.mem;

import javax.realtime.MemoryArea;
import javax.realtime.LTMemory;
import javax.realtime.VTMemory;
import javax.realtime.CTMemory;

import edu.uci.ece.ac.time.HighResTimer;

public class MemoryAreaFactory {

    public static final int CT = 0;
    public static final int LT = 1;
    public static final int VT = 2;
    
    public static MemoryArea createMemoryArea(long minSize, long maxSize, int type) {
        switch (type) {
        case CT:
            return new CTMemory(minSize, maxSize);
        case LT:
            return new LTMemory(minSize, maxSize);
        case VT:
            return new VTMemory(minSize, maxSize);
        default:
            return null;
        }
    }

    public static MemoryArea createMemoryArea(long minSize, long maxSize, int type, HighResTimer timer) {
        MemoryArea ma = null;
        switch (type) {
        case CT:
            timer.start();
            ma = new CTMemory(minSize, maxSize);
            timer.stop();
            return ma;
        case LT:
            timer.start();
            ma = new LTMemory(minSize, maxSize);
            timer.stop();
            return ma;
        case VT:
            timer.start();
            ma = new VTMemory(minSize, maxSize);
            timer.stop();
            return ma;
        default:
            return ma;
        }
    }

}
