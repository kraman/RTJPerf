/*-------------------------------------------------------------------------*
 * $Id: TypeIV.java,v 1.1 2002/01/15 17:06:28 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.loading;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;

public class TypeIV extends TypeIII {

    public void doSomething() {  }

        public static void main(String[] args) {
        HighResTimer timer = new HighResTimer();
        timer.start();
        timer.stop();

        timer.start();
        TypeIV t = new TypeIV();
        timer.stop();
    }
    
}
