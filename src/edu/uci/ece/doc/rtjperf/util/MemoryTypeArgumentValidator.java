/*-------------------------------------------------------------------------*
 * $Id: MemoryTypeArgumentValidator.java,v 1.1 2002/01/11 05:02:54 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.util;

// -- DOC Util Imports --
import edu.uci.ece.doc.util.CommandLineArgumentValidator;
import edu.uci.ece.doc.util.InvalidArgumentValueException;

/**
 * Describe class <code>MemoryTypeArgumentValidator</code> here.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class MemoryTypeArgumentValidator implements CommandLineArgumentValidator {

    private static final String[] MEM_AREA_TYPE = {"immortal", "heap", "scoped"};
    
    public void validate(String argValue) throws InvalidArgumentValueException {
        boolean notValid = true;
        
        for (int i = 0; i < MEM_AREA_TYPE.length; ++i) {
            if (argValue.equals(MEM_AREA_TYPE[i])) {
                notValid = false;
                break;
            }
        }
        
        if (notValid)
            throw new InvalidArgumentValueException(argValue + " Is not a valid memory type!");
        
    }
}
