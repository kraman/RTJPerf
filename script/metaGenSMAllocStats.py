import os
import sys
import string

PATH = '/home/angelo/Devel/RTJPerf/WS'
STATS_PATH = '/home/angelo/Devel/RTJPerf/WS/SMAllocTimingStats.ows'
OFFSET = 5
X = '[32 64 128 256 512 1024 2048 4096 8192 16384]\''
M = ('CT', 'LT', 'VT')

def loadData(os):
    i = 0
    os.write('X = %s;\n' % (X))
    
    while i < 3:
        size = 32
        os.write('\n\n%s = [\\\n' % (M[i]));
        while size <= 16384:
            os.write('\tloadMatrix(\"%s%d/%s%d\", %s)\';\\ \n' % ('AllocTime', i, 'AllocTime', size, '\"%f\"'));
            size = size*2

        os.write('];\n');
        i += 1
        
def analizeData(os):

    for i in range(0, 3):
        size = 32
        j = 1;
        while size <= 16384:
            os.write('%s%d = 1000 * %s(%d, %d:1:size(%s)(2));\n' % (M[i], size, M[i], j, OFFSET, M[i]))
            j += 1
            size = 2*size

    for i in range(0, 3):
        os.write('\n\n')
        os.write('[A S M m N] = asmm(1000 * %s(1, %d:(size(%s)(2))));\n' % (M[i], OFFSET, M[i]))
        os.write('%sStats = [A S M m N];\n' % (M[i]))
        os.write('for i = 2:size(%s)(1)\n' % (M[i]))
        os.write('\t[A S M m N] = asmm(1000 * %s(i, %d:(size(%s)(2))));\n' % (M[i], OFFSET, M[i]))
        os.write('\t%sStats = [%sStats; [A S M m N]];\n' % (M[i],M[i]))
        os.write('endfor\n\n')


    for i in range(0, 3):
        size = 32
        j = 1;
        while size <= 16384:
            os.write('%s%dpd = GenDistr(%s%d);\n' % (M[i], size, M[i], size))
            j += 1
            size = 2*size
            

        for i in range(0, 3):
            os.write('%sAvg = [X  %sStats(1:1:size(%sStats)(1), 1)];\n' % (M[i], M[i], M[i]))
            os.write('%sStd = [X %sStats(1:1:size(%sStats)(1), 2)];\n' % (M[i], M[i], M[i]))
            os.write('%sMax = [X %sStats(1:1:size(%sStats)(1), 3)];\n' % (M[i], M[i], M[i]))
            os.write('%sMin = [X %sStats(1:1:size(%sStats)(1), 4)];\n' % (M[i], M[i], M[i]))
            os.write('%sNN  = [X %sStats(1:1:size(%sStats)(1), 5)];\n' % (M[i], M[i], M[i]))

    
def writeData(os):

    os.write('save -ascii %s\n;' % (STATS_PATH))
    
    for i in range(0, 3):
        os.write('save -ascii %s/%sAvg.dat %sAvg;\n' % (PATH, M[i], M[i]))
        os.write('save -ascii %s/%sStd.dat %sStd;\n' % (PATH, M[i], M[i]))
        os.write('save -ascii %s/%sMax.dat %sMax;\n' % (PATH, M[i], M[i]))
        os.write('save -ascii %s/%sMin.dat %sMin;\n' % (PATH, M[i], M[i]))
        os.write('save -ascii %s/%sNN.dat  %sNN;\n'  % (PATH, M[i], M[i]))

    for i in range(0, 3):
        size = 32
        j = 1;
        while size <= 16384:
            os.write('save -ascii %s/%s%dpd.dat %s%dpd;\n' % (PATH, M[i], size, M[i], size))
            j += 1
            size = 2*size

        
def metaGenSMAllocStats():
    octaveScript = open('genSMAllocStats.sh', 'w')
    octaveScript.write('#!/usr/bin/octave -q \n\n')

    loadData(octaveScript)
    analizeData(octaveScript)
    writeData(octaveScript)
    octaveScript.flush();
    octaveScript.close();
    

if __name__ == '__main__':
    metaGenSMAllocStats()
