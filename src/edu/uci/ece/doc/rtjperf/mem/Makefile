#
# $Id: Makefile,v 1.4 2002/03/07 04:44:07 corsaro Exp $
#

RTJP_CLASSES = ${RTJP_HOME}/classes
RTJP_SRC = ${RTJP_HOME}/src
RTJP_LIB = ${RTJP_HOME}/lib
RTJP_BIN = ${RTJP_HOME}/bin

GCJ_INCLUDE=$(GCC_HOME)/include
GCJ_SRC_INCLUDE=$(GCC_SRC_HOME)/libjava/include
BOEHM_SRC_INCLUDE=$(GCC_HOME)/boehm-gc
INCLUDE_DIRS = -I$(GCJ_INCLUDE)  -I$(GCJ_SRC_INCLUDE) -I$(BOEHM_SRC_INCLUDE) -I$(RTJAVA_HOME)/src/librtj/include -I$(RTJAVA_HOME)/src/librtj/src 

# -I$(RTJAVA_HOME)/classes
all:
	gcj -static  -I$(RTJAVA_HOME)/src -I$(RTJP_HOME)/src $(RTJAVA_HOME)/src/javax/realtime/*.java --main=edu.uci.ece.doc.rtjperf.mem.AllocTimeTest -o $(RTJP_BIN)/allocTimeTest  ${RTJP_SRC}/edu/uci/ece/doc/rtjperf/mem/MemoryAreaFactory.java ${RTJP_SRC}/edu/uci/ece/doc/rtjperf/mem/AllocTimeTest.java  ${RTJP_SRC}/edu/uci/ece/doc/rtjperf/sys/*.java ${RTJP_SRC}/edu/uci/ece/doc/rtjperf/sys/HighResClock.cc $(INCLUDE_DIRS) $(RTJAVA_HOME)/src/librtj/src/*.cc  -lgcj -lgcjgc

#$(RTJAVA_HOME)/src/librtj/src/malloc.o


# ${RTJP_SRC}/edu/uci/ece/doc/util/*.java  ${RTJP_SRC}/edu/uci/ece/doc/util/concurrent/*.java   -I$(RTJAVA_HOME)/src $(RTJAVA_HOME)/src/javax/realtime/*.java -L$(RTJAVA_HOME)/src/librtj/lib  -lrtjni -L${RTJP_LIB} -lHRTime-cni

#	gcj  -O3   $(RTJAVA_HOME)/src/javax/realtime/*.java -L$(RTJAVA_HOME)/src/librtj/lib -lrtj -lrtjni -L${RTJP_LIB} -lHRTime-cni ${RTJP_SRC}/edu/uci/ece/doc/rtjperf/sys/*.java ${RTJP_SRC}/edu/uci/ece/doc/util/*.java  ${RTJP_SRC}/edu/uci/ece/doc/util/concurrent/*.java ${RTJP_SRC}/edu/uci/ece/doc/rtjperf/util/*.java  ${RTJP_SRC}/edu/uci/ece/doc/rtjperf/mem/AllocTimeTest.java --main=edu.uci.ece.doc.rtjperf.mem.AllocTimeTest -o $(RTJP_BIN)/allocTimeTest



java:
	javac -classpath /home/zeus/Tools/RTJava/lib/foundation.jar:/home/zeus/Devel/RTJPerf/classes/  AllocTimeTest.java

run-ri:
	/home/zeus/Tools/RTJava/bin/tjvm  -Xbootclasspath=/home/zeus/Tools/RTJava/lib/foundation.jar -Djava.library.path=/home/zeus/Devel/RTJPerf/lib -Djava.class.path=./:/home/zeus/Devel/RTJPerf/classes -Dproperties.load.path=$RTJP_HOME/bin AllocTimeTest 10000 1
