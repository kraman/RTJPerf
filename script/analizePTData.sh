#!/usr/bin/octave -q

jPT = [\
    loadMatrix("PTTestjRate/ThreadPeriodTest1.0/ThreadPeriod", "%f")(10:1:1000)';\
    loadMatrix("PTTestjRate/ThreadPeriodTest5.0/ThreadPeriod", "%f")(10:1:1000)';\
    loadMatrix("PTTestjRate/ThreadPeriodTest10.0/ThreadPeriod", "%f")(10:1:1000)';\
    loadMatrix("PTTestjRate/ThreadPeriodTest50.0/ThreadPeriod", "%f")(10:1:1000)';\
    loadMatrix("PTTestjRate/ThreadPeriodTest100.0/ThreadPeriod", "%f")(10:1:1000)';\
    loadMatrix("PTTestjRate/ThreadPeriodTest500.0/ThreadPeriod", "%f")(10:1:1000)';\
];    

rPT = [\
    loadMatrix("PTTestRI/ThreadPeriodTest1.0/ThreadPeriod", "%f")(40:1:1000)';\
    loadMatrix("PTTestRI/ThreadPeriodTest5.0/ThreadPeriod", "%f")(40:1:1000)';\
    loadMatrix("PTTestRI/ThreadPeriodTest10.0/ThreadPeriod", "%f")(40:1:1000)';\
    loadMatrix("PTTestRI/ThreadPeriodTest50.0/ThreadPeriod", "%f")(40:1:1000)';\
    loadMatrix("PTTestRI/ThreadPeriodTest100.0/ThreadPeriod", "%f")(40:1:1000)';\
    loadMatrix("PTTestRI/ThreadPeriodTest500.0/ThreadPeriod", "%f")(40:1:1000)';\
];    

len = size(jPT)(2);

[A S M m N] = asmm(jPT(1, 1:1:len));
jPTStats = [A S M m N];

for i=2:6
    [A S M m N] = asmm(jPT(i, 1:1:len));
    jPTStats = [jPTStats; [A S M m N]];
endfor

len = size(rPT)(2);
[A S M m N] = asmm(rPT(1, 1:1:len));
rPTStats = [A S M m N];

for i=2:6
    [A S M m N] = asmm(rPT(i, 1:1:len));
    rPTStats = [rPTStats; [A S M m N]];
endfor

X = [1 5 10 50 100 500]';

rows = size(jPTStats)(1);
jPTAvg = [X jPTStats(1:1:rows, 1)];
jPTSTD = [X jPTStats(1:1:rows, 2)];
jPTMax = [X jPTStats(1:1:rows, 3)];
jPTNN  = [X jPTStats(1:1:rows, 5)];

rows = size(rPTStats)(1);
rPTAvg = [X rPTStats(1:1:rows, 1)];
rPTSTD = [X rPTStats(1:1:rows, 2)];
rPTMax = [X rPTStats(1:1:rows, 3)];
rPTNN  = [X rPTStats(1:1:rows, 5)];

save -ascii /home/angelo/Devel/RTJPerf/WS/jPTAvg.dat jPTAvg
save -ascii /home/angelo/Devel/RTJPerf/WS/jPTSTD.dat jPTSTD
save -ascii /home/angelo/Devel/RTJPerf/WS/jPTMax.dat jPTMax
save -ascii /home/angelo/Devel/RTJPerf/WS/jPTNN.dat jPTNN

save -ascii /home/angelo/Devel/RTJPerf/WS/rPTAvg.dat rPTAvg
save -ascii /home/angelo/Devel/RTJPerf/WS/rPTSTD.dat rPTSTD
save -ascii /home/angelo/Devel/RTJPerf/WS/rPTMax.dat rPTMax
save -ascii /home/angelo/Devel/RTJPerf/WS/rPTNN.dat rPTNN

save -ascii /home/angelo/Devel/RTJPerf/WS/PTStats.ows
