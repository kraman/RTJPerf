##########################################################################
# $Id: Macros.GNU,v 1.1 2002/11/17 03:31:50 corsaro Exp $
##########################################################################

SRC_DIR := $(RTJP_HOME)/src/edu/uci/ece/doc/rtjperf
TARGET =
MAINCLASS =
SRCS =
OBJS =
LT_OBJS =

JXXFLAGS =
LDT_FLAGS = 
LDLIBS = -ljTools -ljRateRT -ljRate -lrt
LD_DIR = -L$(JTOOLS_HOME)/lib -L$(JRATE_HOME)/lib
JXXINCLUDE = -I$(JRATE_HOME)/src -I$(JTOOLS_HOME)/src -I$(RTJP_HOME)/src

JXX = gcj
CXX = g++
CP = cp
GCJH = gcjh
LIBTOOL = libtool


ifeq ($(static),1)
   LDT_FLAGS = -all-static
endif #static

ifeq ($(debug),1)
   JXXFLAGS += -g
endif #debug

ifeq (,$(optimize))
   JXXFLAGS += -O2
else
   JXXFLAGS += -O$(optimize)
endif # optimize


COMPILE = $(LIBTOOL) --mode=compile  $(JXX) -prefer-non-pic $(JXXINCLUDE)  $(JXXFLAGS) -c

MAKETARGET = $(LIBTOOL) --mode=link gcj $(LDT_FLAGS) $(JXXFLAGS) $(INCLUDE_DIRS) $(LIB_DIRS)\
          --main=$(MAINCLASS)  $(OBJS) -o $(TARGET) $(LDLIBS)

MAKECLEAN = $(RM) $(TARGET)

