OUTFILE = $(RTJP_HOME)/bin/AllocTimeTest

all:
	gcj -O3  -I$(JRATE_HOME)/src -L$(JTOOLS_HOME)/lib -L$(JRATE_HOME)/lib   -lHRTime -ljTools -ljRate -ljRateRT  $(RTJP_HOME)/src/edu/uci/ece/doc/rtjperf/util/*.java *.java -o $(OUTFILE)  --main=edu.uci.ece.doc.rtjperf.mem.AllocTimeTest

clean:
	rm $(AllocTimeTest)
