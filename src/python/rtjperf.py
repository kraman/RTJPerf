#
# $Id: rtjperf.py,v 1.3 2002/11/30 23:33:52 corsaro Exp $
#
# This module contains the definition for the classes and function used
# by RTJPerf to run its tests
#

import os
import time

class RTSPTest(object):

    def __init__(self):
        self.retVal = 0
        self.testComment = nil
    
    def run(self):
        start = time.time();
        print '\nrtjpy:>> Test Started on', time.ctime(start)
        print '\nrtjpy:>>', self.getComment()
        print 'rtjpy:>> Running...'

        # call template method
        self.runImpl()

        end = time.time() 
        print 'rtjpy:>> Test Completed on', time.ctime(end)
        print 'rtjpy:>> Execution Time (secs):', (end - start)
        print 'rtjpy:>> Return Value:', self.retVal 
        print 'rtjpy:>> Test Completed\n'

    def runImpl(self):
        self.retVal = -1

    def setComment(self, str):
        self.testComment = str

    def getComment(self):
        return self.testComment
        
#
# This class defines the Memory Allocation Test
#
class MemAllocTimeTest(RTSPTest): 
    
    def __init__(self, fileName, iteration, allocSize, memType, memSize, outDir):
        self.fileName = fileName
        self.iteration = iteration
        self.allocSize = allocSize
        self.memType = memType
        self.memSize = memSize
        self.outDir = outDir

        self.argList = [self.fileName,
                        '--count', str(self.iteration),
                        '--allocSize', str(self.allocSize),
                        '--scopedMemoryType', self.memType,
                        '--memSize', str(self.memSize),
                        '--outDir', self.outDir]

    def runImpl(self):
        self.retVal = os.spawnlp(os.P_WAIT, self.fileName, self.fileName,
                                 '--count', str(self.iteration),
                                 '--allocSize', str(self.allocSize),
                                 '--scopedMemoryType', self.memType,
                                 '--memSize', str(self.memSize),
                                 '--outDir', self.outDir)
        


#
# This class defines the ScopedMemory Timing Test
#
class MemAllocTimeTest(RTSPTest): 
    def __init__(self, fileName, iteration, memType, memSize, outDir):
        self.fileName = fileName
        self.iteration = iteration
        self.memType = memType
        self.memSize = memSize
        self.outDir = outDir

        self.argList = [self.fileName,
                        '--count', str(self.iteration),
                        '--scopedMemoryType', self.memType,
                        '--memSize', str(self.memSize),
                        '--outDir', self.outDir]

    def runImpl(self):
        self.retVal = os.spawnlp(os.P_WAIT, self.fileName, self.fileName,
                                 '--count', str(self.iteration),
                                 '--scopedMemoryType', self.memType,
                                 '--memSize', str(self.memSize),
                                 '--outDir', self.outDir)
        
