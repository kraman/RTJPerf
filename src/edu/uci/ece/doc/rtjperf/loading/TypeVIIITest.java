/*-------------------------------------------------------------------------*
 * $Id: TypeVIIITest.java,v 1.2 2002/04/17 00:00:55 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.loading;

import edu.uci.ece.ac.time.HighResTimer;

public class TypeVIIITest {
    public static void main(String[] args) {
        HighResTimer timer = new HighResTimer();
        timer.start();
        timer.stop();
        
        timer.start();
        new TypeVIII();
        timer.stop();
        System.out.println(timer.getElapsedTime());
    }
}
