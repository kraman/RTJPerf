###
# This file generates the octave script that shows the result for the Scoped Memory
# Timing test.
###

import os
import sys
import string


def genParseDataCode(name, octaveScript):
    for id in range(0, 2):
        currSize = 4096
        matrix = '%s%d = [ \\\n' % (name, id)
        octaveScript.write(matrix)
        while currSize <= pow(2, 20):
            target = '\tloadMatrix("ScopedMemoryTiming%d/%s%d\", %s' % (id, name, currSize, '\"%f\")\'; \\\n')
            octaveScript.write(target)
            currSize = currSize * 2
        octaveScript.write('];\n\n')


def genPlotCode(matrix, octaveScript):
    
    octaveScript.write('for i = 1:size(%s)(1)\n' % ( matrix))
    octaveScript.write('\tplot(%s(i, 5:(size(%s)(2))));\n' % (matrix, matrix))
    octaveScript.write('endfor\n');
            

def genReadKey(msg, octaveScript):
    octaveScript.write('printf(\"%s\")\n' % (msg))
    octaveScript.write('scanf(\"%s\", 1)\n')
    

def genCTTimingAnalizer():

    octaveScript = open('analizeSMTiming.sh', 'w')
    octaveScript.write('#!/usr/bin/octave -q \n\n')

    genParseDataCode('EnterTime', octaveScript)
    genParseDataCode('ExitTime', octaveScript)
    genParseDataCode('CreationTime', octaveScript)
    genParseDataCode('ExecTime', octaveScript)

    octaveScript.write('\nhold on \n\n')
    
    genPlotCode('EnterTime0', octaveScript)
    genReadKey("Press a key to see CT Exit Time...", octaveScript)
    genPlotCode('ExitTime0', octaveScript) 


    genReadKey("Press a key to see LT Enter Time...", octaveScript)
    genPlotCode('EnterTime1', octaveScript)
    genReadKey("Press a key to see LT Exit Time...", octaveScript)            
    genPlotCode('ExitTime1', octaveScript)
    
    genReadKey("Press a key to exit...", octaveScript)            

    octaveScript.close()

        
if __name__ == '__main__':
    genCTTimingAnalizer()
