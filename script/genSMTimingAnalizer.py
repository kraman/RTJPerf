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
    octaveScript.write('\thold on\n')
    octaveScript.write('endfor\n');

def genLogPlotCode(matrix, octaveScript):
    
    octaveScript.write('for i = 1:size(%s)(1)\n' % ( matrix))
    octaveScript.write('\tsemilogy(%s(i, 5:(size(%s)(2))));\n' % (matrix, matrix))
    octaveScript.write('\thold on\n')
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

    octaveScript.write('printf(\"Plotting CTMemory Enter Time\\n\")\n\n')
    genLogPlotCode('EnterTime0', octaveScript)
    genReadKey("Press a key to see CTMEmory Exit Time", octaveScript)
    genLogPlotCode('ExitTime0', octaveScript) 


    genReadKey("Press a key to see LT Enter Time", octaveScript)
    genLogPlotCode('EnterTime1', octaveScript)
    genReadKey("Press a key to see LT Exit Time", octaveScript)            
    genLogPlotCode('ExitTime1', octaveScript)

    octaveScript.write('\nhold off \n\n')
    genReadKey("Press a key to continue...", octaveScript)
    octaveScript.write('printf(\"Plotting Creation Times\\n\")\n\n')
    genPlotCode('CreationTime0', octaveScript)
    genReadKey("...", octaveScript)
    genPlotCode('CreationTime1', octaveScript)

    octaveScript.write('\nhold off \n\n')
    genReadKey("Press a key to continue...", octaveScript)
    octaveScript.write('printf(\"Plotting Execution Times\\n\")\n\n')
    genLogPlotCode('ExecTime0', octaveScript)
    genReadKey("...", octaveScript)
    genLogPlotCode('ExecTime1', octaveScript)

    genReadKey("Press a key to exit...", octaveScript)            
    
    octaveScript.close()

        
if __name__ == '__main__':
    genCTTimingAnalizer()
