/*-------------------------------------------------------------------------*
 * $Id: MemoryAreaFactory.java,v 1.1 2002/03/07 04:44:07 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.mem;

import javax.realtime.MemoryArea;
import javax.realtime.LTMemory;
import javax.realtime.VTMemory;
import javax.realtime.CTMemoryArea;

public class MemoryAreaFactory {

    public static final int CT = 0;
    public static final int LT = 1;
    public static final int VT = 2;
    
    public static MemoryArea createMemoryArea(long minSize, long maxSize, int type) {
        switch (type) {
        case CT:
            return new CTMemoryArea(maxSize);
        case LT:
            return new LTMemory(minSize, maxSize);
        case VT:
            return new VTMemory(minSize, maxSize);
        default:
            return null;
        }
    }
}
