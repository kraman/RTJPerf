/*-------------------------------------------------------------------------*
 * $Id: ThreadBoundAsynchHandler.java,v 1.1 2002/01/09 03:15:21 corsaro Exp $
 *-------------------------------------------------------------------------*/
package edu.uci.ece.doc.rtjperf.asynch.timing;


// -- RTJava Import --
import javax.realtime.BoundAsyncEventHandler;
import javax.realtime.SchedulingParameters;
import javax.realtime.ReleaseParameters;
import javax.realtime.MemoryParameters;
import javax.realtime.MemoryArea;
import javax.realtime.ProcessingGroupParameters;


// Angelo 31 dec 2001> This class has to be created because the
// BoundAsyncEventHandler is defined as an abstract class, and cannot
// be instantiated.
class ThreadBoundAsynchHandler extends  BoundAsyncEventHandler {

    ThreadBoundAsynchHandler(SchedulingParameters scheduling,
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
