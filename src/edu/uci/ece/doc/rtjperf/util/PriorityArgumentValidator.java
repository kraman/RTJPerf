/*-------------------------------------------------------------------------*
 * $Id: PriorityArgumentValidator.java,v 1.1 2002/01/11 05:02:54 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.util;

// -- DOC Util Imports --
import edu.uci.ece.doc.util.CommandLineArgumentValidator;
import edu.uci.ece.doc.util.InvalidArgumentValueException;

// -- RTJava Import --
import javax.realtime.PriorityScheduler;

/**
 * Describe class <code>MemoryTypeArgumentValidator</code> here.
 *
 * @author <a href="mailto:corsaro@doc.ece.uci.edu">Angelo Corsaro</a>
 * @version 1.0
 */
public class PriorityArgumentValidator implements  CommandLineArgumentValidator {
    
    public void validate(String argValue) throws InvalidArgumentValueException {
        int prioValue = -1;
        try {
            prioValue = Integer.parseInt(argValue);
        }
        catch (Exception e) {
            throw new InvalidArgumentValueException(argValue + " is not an Integer!");
        }

        if ((prioValue < PriorityScheduler.MIN_PRIORITY) ||
            (prioValue > PriorityScheduler.MAX_PRIORITY))
            throw new InvalidArgumentValueException(argValue + " is not a valid priority. It should be in the range" +
                                                    PriorityScheduler.MIN_PRIORITY + " - " + PriorityScheduler.MAX_PRIORITY);
    }
}
