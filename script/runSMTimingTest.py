import rtjperf
import sys

def runSCTimingTest(target):
    print 'Target: %s' % (target)

    iteration = 100
    memSize = 4096 # bytes
    while memSize <= pow(2, 20):
        # create a test for the CTMemory case
        ctTiming = rtjperf.ScopedMemoryTimingTest(target,
                                            iteration, 
                                            'CTMemory',
                                            memSize,  
                                            'SMTiming');
        ctTiming.setComment('AllocTimeTest(' +  str(iteration) + ' iter, ' 
                            + str(memSize) + ' bytes, ' + 'CTMemory)')

        # create a test for the LTMemory case
        ltTiming = rtjperf.ScopedMemoryTimingTest(target,
                                            iteration, 
                                            'LTMemory',
                                            memSize,  
                                            'SMTiming');
        ltTiming.setComment('ScopedMemoryTiming(' +  str(iteration) + ' iter, '
                          + str(memSize) + ' bytes, ' + 'LTMemory)')

        # run the tests
        ctTiming.run();
        ltTiming.run()

        # update the parameters
        memSize = memSize * 2;

if __name__ == '__main__':

    RITarget ='ScopedMemoryTiming.sh'
    jRateTarget = 'ScopedMemoryTiming'

    if (sys.argv[1] == 'jRate'):
        runSCTimingTest(jRateTarget)
    else:
        runSCTimingTest(RITarget)
        
