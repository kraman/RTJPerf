/*-------------------------------------------------------------------------*
 * $Id: TypeXTest.java,v 1.2 2002/02/12 20:57:54 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.loading;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;

public class TypeXTest {
    public static void main(String[] args) {
        HighResTimer timer = new HighResTimer();
        ClassLoader classLoader = timer.getClass().getClassLoader();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);
        System.out.println(systemClassLoader);
        System.out.println(systemClassLoader.getClass().getClassLoader());

        timer.start();
        timer.stop();
        
        timer.start();
        new TypeX();
        timer.stop();
        System.out.println(timer.getElapsedTime());
        Float f = new Float("1.0F");
        System.out.println(f.getClass().getClassLoader());
    }
}
