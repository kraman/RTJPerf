/*-------------------------------------------------------------------------*
 * $Id: EagerTypeFactoryTest.java,v 1.2 2002/04/17 00:00:55 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.loading;

import edu.uci.ece.ac.time.HighResTimer;

public class EagerTypeFactoryTest {

    public static void main(String[] args) {
        
        EagerTypeFactory factory = EagerTypeFactory.instance();
        for (int round = 0; round < 3; ++round) {
            for (int i = 1; i <= 10; i++) 
                factory.createType(i);
            System.out.println("Round: "+ round);
        }
        
    }
}
