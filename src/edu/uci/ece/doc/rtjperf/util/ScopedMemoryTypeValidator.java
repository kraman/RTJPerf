// ************************************************************************
//    $Id: ScopedMemoryTypeValidator.java,v 1.1 2002/04/17 00:00:55 corsaro Exp $
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
package edu.uci.ece.doc.rtjperf.util;

// -- jTools Imports --
import edu.uci.ece.ac.jargo.Validator;
import edu.uci.ece.ac.jargo.InvalidArgumentValueException;

/**
 * Describe class <code>MemoryTypeArgumentValidator</code> here.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class ScopedMemoryTypeValidator implements Validator {

    private static final String[] SCOPED_MEM_AREA_TYPE = {"LTMemory", "VTMemory", "CTMemory"};
    
    public Object validate(String argValue) throws InvalidArgumentValueException {
        int index = -1;
        for (int i = 0; i < SCOPED_MEM_AREA_TYPE.length; ++i) {
            if (argValue.equals(SCOPED_MEM_AREA_TYPE[i])) {
                index = i;
                break;
            }
        }
        
        if (index == -1)
            throw new InvalidArgumentValueException(argValue + " Is not a valid scoped memory type!");

        return new Integer(index);
    }
}
