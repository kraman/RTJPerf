/*-------------------------------------------------------------------------*
 * $Id: TimeoutHandler.java,v 1.1 2002/03/08 03:38:35 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.timer;

import edu.uci.ece.doc.rtjperf.sys.HighResTimer;
import edu.uci.ece.doc.rtjperf.sys.HighResTime;
import edu.uci.ece.doc.rtjperf.sys.PerformanceReport;
import edu.uci.ece.doc.util.concurrent.EventVariable;
import javax.realtime.*;


public class TimeoutHandler extends javax.realtime.BoundAsyncEventHandler {

    TimeoutHandler(SchedulingParameters scheduling,
                   ReleaseParameters release,
                   MemoryParameters memory,
                   MemoryArea area,
                   ProcessingGroupParameters group,
                   boolean nonheap,
                   Runnable logic)
    {
        super(scheduling,
              release,
              memory,
              area,
              group,
              nonheap,
              logic);
    }
}


//     private Runnable logic;

//     TimeoutHandler(Runnable logic) {
//         super(null,
//               null,
//               null,
//               null,
//               null,
//               false,
//               null);
//         // the logic in the parent class is private!
//         this.logic = logic;
//     }

//     public void handle() {
//         this.logic.run();
//     }
