/*-------------------------------------------------------------------------*
 * $Id: TypeIXTest.java,v 1.1 2002/01/15 17:06:28 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.loading;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;

public class TypeIXTest {
    public static void main(String[] args) {
        HighResTimer timer = new HighResTimer();
        timer.start();
        timer.stop();
        
        timer.start();
        new TypeIX();
        timer.stop();
        System.out.println(timer.getElapsedTime());
    }
}