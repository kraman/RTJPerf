#!/usr/bin/octave -q

jDD = [\
    1000 * loadMatrix("LPAEH.xjRate.1/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.2/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.4/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.8/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.16/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.32/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.64/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.128/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.256/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.512/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xjRate.1024/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
];

rDD = [\
    1000 * loadMatrix("LPAEH.xRI.1/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.2/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.4/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.8/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.16/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.32/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.64/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.128/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.256/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.512/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
    1000 * loadMatrix("LPAEH.xRI.1024/AsynchEventHandlerDelayTest/DispatchDelay", "%f")(5:1:1000)'; \
];

X = [0 1 2 4 8 16 32 64 128 256 512 1024]';

load -ascii jRate-TBAE-DD.dat
jDD0 = tbD;
jDDStats = [];
[A S M m N] = asmm(jDD0);

jDDStats = [A S M m N];
for i = 1:11
    [A S M m N] = asmm(jDD(i, 1:1:size(jDD)(2)));
    jDDStats = [jDDStats; [A S M m N]];
endfor
jDDStats

load -ascii RI-TBAE-DD.dat
rDD0 = rD ;
rDDStats = [];
[A S M m N] = asmm(rDD0);

rDDStats = [A S M m N];
for i = 1:11
    [A S M m N] = asmm(rDD(i, 1:1:size(rDD)(2)));
    rDDStats = [rDDStats; [A S M m N]];
endfor

rDDStats
jDDAvg = jDDStats(1:1:size(jDDStats)(1), 1);
save -ascii /home/angelo/Devel/RTJPerf/WS/jDDAvg.dat jDDAvg

jDDSTD = jDDStats(1:1:size(jDDStats)(1), 2);
save -ascii /home/angelo/Devel/RTJPerf/WS/jDDSTD.dat jDDSTD

jDDMax = jDDStats(1:1:size(jDDStats)(1), 3);
save -ascii /home/angelo/Devel/RTJPerf/WS/jDDMax.dat jDDMax


jDDMin = jDDStats(1:1:size(jDDStats)(1), 4);
save -ascii /home/angelo/Devel/RTJPerf/WS/jDDMin.dat jDDMin

jDDNN = jDDStats(1:1:size(jDDStats)(1), 5);
save -ascii /home/angelo/Devel/RTJPerf/WS/jDDNN.dat jDDNN



rDDAvg = rDDStats(1:1:size(rDDStats)(1), 1);
save -ascii /home/angelo/Devel/RTJPerf/WS/rDDAvg.dat rDDAvg

rDDSTD = rDDStats(1:1:size(rDDStats)(1), 2);
save -ascii /home/angelo/Devel/RTJPerf/WS/rDDSTD.dat rDDSTD

rDDMax = rDDStats(1:1:size(rDDStats)(1), 3);
save -ascii /home/angelo/Devel/RTJPerf/WS/rDDMax.dat rDDMax


rDDMin = rDDStats(1:1:size(rDDStats)(1), 4);
save -ascii /home/angelo/Devel/RTJPerf/WS/rDDMin.dat rDDMin

rDDNN = rDDStats(1:1:size(rDDStats)(1), 5);
save -ascii /home/angelo/Devel/RTJPerf/WS/rDDNN.dat rDDNN


save -ascii /home/angelo/Devel/RTJPerf/WS/LPAEHStats.ows 
