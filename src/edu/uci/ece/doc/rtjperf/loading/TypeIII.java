/*-------------------------------------------------------------------------*
 * $Id: TypeIII.java,v 1.1 2002/01/15 17:06:28 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.loading;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;

public class TypeIII extends TypeII {

    public void doSomething() {  }

    public static void main(String[] args) {
        HighResTimer timer = new HighResTimer();
        timer.start();
        timer.stop();

        timer.start();
        TypeIII t = new TypeIII();
        timer.stop();
    }

}
