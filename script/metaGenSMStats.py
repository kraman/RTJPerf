import os
import sys
import string

PATH = '/home/angelo/Devel/RTJPerf/WS'
WS_PATH = '/home/angelo/Devel/RTJPerf/WS/SMTiming.ows'
STATS_PATH = '/home/angelo/Devel/RTJPerf/WS/SMTimingStats.ows'
OFFSET = 5
X = '[4096 8192 16384 32768 65536 131072 262144 524288 1048576]'

def genEnterTimeStats(name, os):

    for i in range(0, 3):
        os.write('\n\n')
        os.write('[A S M m N] = asmm(%s%d(1, %d:(size(%s%d)(2))));\n' % (name, i, OFFSET, name, i))
        os.write('%sStats%d = [A S M m N];\n' % (name, i))
        os.write('for i = 2:size(%s%d)(1)\n' % (name, i))
        os.write('\t[A S M m N] = asmm(%s%d(i, %d:(size(%s%d)(2))));\n' % (name, i, OFFSET, name, i))
        os.write('\t%sStats%d = [%sStats%d; [A S M m N]];\n' % (name, i, name, i))
        os.write('endfor\n\n')


def genAggregateMeasures(name, os):

    for i in range(0, 3):
        os.write('\n\n')
        os.write('%sAvg%d = [];\n' % (name, i))
        os.write('%sSTD%d = [];\n' % (name, i))
        os.write('%sMax%d = [];\n' % (name, i))
        os.write('%sMin%d = [];\n' % (name, i))
        os.write('%sNN%d = [];\n' % (name, i))
        os.write('for i = 1:size(%sStats%d)(1)\n' % (name, i))
        os.write('\t%sAvg%d(i) = %sStats%d(i,1);\n' % (name, i, name, i))
        os.write('\t%sSTD%d(i) = %sStats%d(i,2);\n' % (name, i, name, i))
        os.write('\t%sMax%d(i) = %sStats%d(i,3);\n' % (name, i, name, i))
        os.write('\t%sMin%d(i) = %sStats%d(i,4);\n' % (name, i, name, i))
        os.write('\t%sNN%d(i) = %sStats%d(i,5);\n' % (name, i, name, i))
        os.write('endfor\n')

def saveAggregate(name, X, os):

    for i in range(0, 3):
        os.write('X = %s\';\n' % (X))
        os.write('tmp = [X 1000 * %sAvg%d];\n' % (name, i))
        os.write('save -ascii  %s/%sAvg%d.dat tmp;\n' % (PATH, name, i))

        os.write('tmp = [X 1000 * %sSTD%d];\n' % (name, i))
        os.write('save -ascii  %s/%sSTD%d.dat tmp\n' % (PATH, name, i))

        os.write('tmp = [X 1000 * %sMax%d];\n' % (name, i))
        os.write('save -ascii  %s/%sMax%d.dat tmp\n' % (PATH, name, i))

        os.write('tmp = [X 1000 * %sMin%d];\n' % (name, i))
        os.write('save -ascii  %s/%sMin%d.dat tmp\n' % (PATH, name, i))
        
        os.write('tmp = [X 1000 * %sNN%d];\n' % (name, i))
        os.write('save -ascii  %s/%sNN%d.dat tmp\n' % (PATH, name, i))
        
    
def metaGenSMStats():
    octaveScript = open('genSMStats.sh', 'w')
    octaveScript.write('#!/usr/bin/octave -q \n\n')
        
    octaveScript.write('load -ascii %s;\n' % (WS_PATH))
    octaveScript.write('offset = %s;\n' %(OFFSET))
    
    genEnterTimeStats('EnterTime', octaveScript)
    genEnterTimeStats('ExitTime', octaveScript)
    genEnterTimeStats('CreationTime', octaveScript)
    genEnterTimeStats('ExecTime', octaveScript)

    genAggregateMeasures('EnterTime', octaveScript)
    genAggregateMeasures('ExitTime', octaveScript)
    genAggregateMeasures('CreationTime', octaveScript)
    genAggregateMeasures('ExecTime', octaveScript)

    saveAggregate('EnterTime', X, octaveScript)
    saveAggregate('ExitTime', X, octaveScript)
    saveAggregate('CreationTime', X, octaveScript)
    saveAggregate('ExecTime', X, octaveScript)

    octaveScript.write('save -ascii %s\n' %(STATS_PATH))
    octaveScript.flush();
    octaveScript.close();

    

if __name__ == '__main__':
    metaGenSMStats()
