##########################################################################
# $Id: Rules.GNU,v 1.1 2002/11/17 03:31:50 corsaro Exp $
##########################################################################

OBJS := $(patsubst %.java,%.o,$(SRCS)) 
LT_OBJS := $(patsubst %.java,%.lo,$(SRCS)) 

all: $(OBJS)
	$(MAKETARGET)


%.o: %.java
	$(COMPILE) -o $@  $<

clean:
	$(RM) $(OBJS) $(LT_OBJS) $(TARGET)
