#
# $Id: rtjperf.py,v 1.1 2002/10/17 01:01:13 corsaro Exp $
#
# This module contains the definition for the classes and function used
# by RTJPerf to run its tests
#

import os

#
# This class defines the Memory Allocation Test
#
class MemAllocTimeTest:

    def __init__(self, fileName, iteration, allocSize, memType, memSize, outDir):
        self.fileName = fileName
        self.iteration = iteration
        self.allocSize = allocSize
        self.memType = memType
        self.memSize = memSize
        self.outDir = outDir

#         self.argList = [self.fileName, '--count', self.iteration,
#                         '--allocSize', self.allocSize,
#                         '--scopedMemoryType', self.memType,
#                         '--memSize', self.memSize,
#                         '--outDir', self.outDir]

    def run(self):
        print '---------------- Starting Test ----------------'
        print 'Command Line:', self.argList
        retVal = os.spawnlp(os.P_WAIT, self.fileName, self.fileName,
                            '--count', self.iteration,
                            '--allocSize', self.allocSize,
                            '--scopedMemoryType', self.memType,
                            '--memSize', self.memSize,
                            '--outDir', self.outDir)
                            
        
        print retVal
        print '---------------- Test Completed ----------------'
      

    
