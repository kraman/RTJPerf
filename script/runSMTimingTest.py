import rtjperf

def runSCTimingTest():

    iteration = 100
    memSize = 4096 # bytes
    while memSize <= pow(2, 20):
        # create a test for the CTMemory case
        ctTiming = rtjperf.MemAllocTimeTest('ScopedMemoryTiming',
                                          iteration, 
                                          'CTMemory',
                                          memSize,  
                                          'SMTiming');
        ctTiming.setComment('AllocTimeTest(' +  str(iteration) + ' iter, ' 
                            + str(memSize) + ' bytes, ' + 'CTMemory)')

        # create a test for the LTMemory case
        ltTiming = rtjperf.MemAllocTimeTest('ScopedMemoryTiming',
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
    runSCTimingTest()
