import rtjperf

def runTest():

    iteration = 1000
    memSize = 100000 # bytes
    allocSize = 32   # bytes
    while allocSize <= pow(2, 14):
        # create a test for the CTMemory case
        ctmatt = rtjperf.MemAllocTimeTest('AllocTimeTest',
                                          iteration, 
                                          allocSize, 
                                          'CTMemory',
                                          memSize,  
                                          'Data');
        ctmatt.setComment('AllocTimeTest(' +  str(iteration) + ' iter, '
                          + str(allocSize) + ' bytes, '
                          + str(memSize) + ' bytes, ' + 'CTMemory)')

        # create a test for the LTMemory case
        ltmatt = rtjperf.MemAllocTimeTest('AllocTimeTest',
                                          iteration, 
                                          allocSize, 
                                          'LTMemory',
                                          memSize,  
                                          'Data');
        ltmatt.setComment('AllocTimeTest(' +  str(iteration) + ' iter, '
                          + str(allocSize) + ' bytes, '
                          + str(memSize) + ' bytes, ' + 'LTMemory)')

        # run the tests
        ctmatt.run();
        ltmatt.run()

        # update the parameters
        memSize = memSize * 2;
        allocSize = allocSize * 2;
    

if __name__ == '__main__':
    runTest()
