#!/bin/bash
export IMMORTAL_SIZE=6000000

$RTJ_HOME/bin/tjvm -Xverify:none -Xms128M -Xbootclasspath:$RTJ_HOME/lib/foundation.jar -Djava.library.path=$JTOOLS_HOME/lib -Djava.class.path=$RTJP_HOME/classes:$JTOOLS_HOME/classes -Dproperties.load.path=$JTOOLS_HOME/bin edu.uci.ece.doc.rtjperf.mem.timing.ScopedMemoryTimingTest $*

