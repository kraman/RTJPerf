#!/usr/bin/octave -q 

load -ascii /home/angelo/Devel/RTJPerf/WS/SMTiming.ows;
offset = 5;


[A S M m N] = asmm(EnterTime0(1, 5:(size(EnterTime0)(2))));
EnterTimeStats0 = [A S M m N];
for i = 2:size(EnterTime0)(1)
	[A S M m N] = asmm(EnterTime0(i, 5:(size(EnterTime0)(2))));
	EnterTimeStats0 = [EnterTimeStats0; [A S M m N]];
endfor



[A S M m N] = asmm(EnterTime1(1, 5:(size(EnterTime1)(2))));
EnterTimeStats1 = [A S M m N];
for i = 2:size(EnterTime1)(1)
	[A S M m N] = asmm(EnterTime1(i, 5:(size(EnterTime1)(2))));
	EnterTimeStats1 = [EnterTimeStats1; [A S M m N]];
endfor



[A S M m N] = asmm(EnterTime2(1, 5:(size(EnterTime2)(2))));
EnterTimeStats2 = [A S M m N];
for i = 2:size(EnterTime2)(1)
	[A S M m N] = asmm(EnterTime2(i, 5:(size(EnterTime2)(2))));
	EnterTimeStats2 = [EnterTimeStats2; [A S M m N]];
endfor



[A S M m N] = asmm(ExitTime0(1, 5:(size(ExitTime0)(2))));
ExitTimeStats0 = [A S M m N];
for i = 2:size(ExitTime0)(1)
	[A S M m N] = asmm(ExitTime0(i, 5:(size(ExitTime0)(2))));
	ExitTimeStats0 = [ExitTimeStats0; [A S M m N]];
endfor



[A S M m N] = asmm(ExitTime1(1, 5:(size(ExitTime1)(2))));
ExitTimeStats1 = [A S M m N];
for i = 2:size(ExitTime1)(1)
	[A S M m N] = asmm(ExitTime1(i, 5:(size(ExitTime1)(2))));
	ExitTimeStats1 = [ExitTimeStats1; [A S M m N]];
endfor



[A S M m N] = asmm(ExitTime2(1, 5:(size(ExitTime2)(2))));
ExitTimeStats2 = [A S M m N];
for i = 2:size(ExitTime2)(1)
	[A S M m N] = asmm(ExitTime2(i, 5:(size(ExitTime2)(2))));
	ExitTimeStats2 = [ExitTimeStats2; [A S M m N]];
endfor



[A S M m N] = asmm(CreationTime0(1, 5:(size(CreationTime0)(2))));
CreationTimeStats0 = [A S M m N];
for i = 2:size(CreationTime0)(1)
	[A S M m N] = asmm(CreationTime0(i, 5:(size(CreationTime0)(2))));
	CreationTimeStats0 = [CreationTimeStats0; [A S M m N]];
endfor



[A S M m N] = asmm(CreationTime1(1, 5:(size(CreationTime1)(2))));
CreationTimeStats1 = [A S M m N];
for i = 2:size(CreationTime1)(1)
	[A S M m N] = asmm(CreationTime1(i, 5:(size(CreationTime1)(2))));
	CreationTimeStats1 = [CreationTimeStats1; [A S M m N]];
endfor



[A S M m N] = asmm(CreationTime2(1, 5:(size(CreationTime2)(2))));
CreationTimeStats2 = [A S M m N];
for i = 2:size(CreationTime2)(1)
	[A S M m N] = asmm(CreationTime2(i, 5:(size(CreationTime2)(2))));
	CreationTimeStats2 = [CreationTimeStats2; [A S M m N]];
endfor



[A S M m N] = asmm(ExecTime0(1, 5:(size(ExecTime0)(2))));
ExecTimeStats0 = [A S M m N];
for i = 2:size(ExecTime0)(1)
	[A S M m N] = asmm(ExecTime0(i, 5:(size(ExecTime0)(2))));
	ExecTimeStats0 = [ExecTimeStats0; [A S M m N]];
endfor



[A S M m N] = asmm(ExecTime1(1, 5:(size(ExecTime1)(2))));
ExecTimeStats1 = [A S M m N];
for i = 2:size(ExecTime1)(1)
	[A S M m N] = asmm(ExecTime1(i, 5:(size(ExecTime1)(2))));
	ExecTimeStats1 = [ExecTimeStats1; [A S M m N]];
endfor



[A S M m N] = asmm(ExecTime2(1, 5:(size(ExecTime2)(2))));
ExecTimeStats2 = [A S M m N];
for i = 2:size(ExecTime2)(1)
	[A S M m N] = asmm(ExecTime2(i, 5:(size(ExecTime2)(2))));
	ExecTimeStats2 = [ExecTimeStats2; [A S M m N]];
endfor



EnterTimeAvg0 = [];
EnterTimeSTD0 = [];
EnterTimeMax0 = [];
EnterTimeMin0 = [];
EnterTimeNN0 = [];
for i = 1:size(EnterTimeStats0)(1)
	EnterTimeAvg0(i) = EnterTimeStats0(i,1);
	EnterTimeSTD0(i) = EnterTimeStats0(i,2);
	EnterTimeMax0(i) = EnterTimeStats0(i,3);
	EnterTimeMin0(i) = EnterTimeStats0(i,4);
	EnterTimeNN0(i) = EnterTimeStats0(i,5);
endfor


EnterTimeAvg1 = [];
EnterTimeSTD1 = [];
EnterTimeMax1 = [];
EnterTimeMin1 = [];
EnterTimeNN1 = [];
for i = 1:size(EnterTimeStats1)(1)
	EnterTimeAvg1(i) = EnterTimeStats1(i,1);
	EnterTimeSTD1(i) = EnterTimeStats1(i,2);
	EnterTimeMax1(i) = EnterTimeStats1(i,3);
	EnterTimeMin1(i) = EnterTimeStats1(i,4);
	EnterTimeNN1(i) = EnterTimeStats1(i,5);
endfor


EnterTimeAvg2 = [];
EnterTimeSTD2 = [];
EnterTimeMax2 = [];
EnterTimeMin2 = [];
EnterTimeNN2 = [];
for i = 1:size(EnterTimeStats2)(1)
	EnterTimeAvg2(i) = EnterTimeStats2(i,1);
	EnterTimeSTD2(i) = EnterTimeStats2(i,2);
	EnterTimeMax2(i) = EnterTimeStats2(i,3);
	EnterTimeMin2(i) = EnterTimeStats2(i,4);
	EnterTimeNN2(i) = EnterTimeStats2(i,5);
endfor


ExitTimeAvg0 = [];
ExitTimeSTD0 = [];
ExitTimeMax0 = [];
ExitTimeMin0 = [];
ExitTimeNN0 = [];
for i = 1:size(ExitTimeStats0)(1)
	ExitTimeAvg0(i) = ExitTimeStats0(i,1);
	ExitTimeSTD0(i) = ExitTimeStats0(i,2);
	ExitTimeMax0(i) = ExitTimeStats0(i,3);
	ExitTimeMin0(i) = ExitTimeStats0(i,4);
	ExitTimeNN0(i) = ExitTimeStats0(i,5);
endfor


ExitTimeAvg1 = [];
ExitTimeSTD1 = [];
ExitTimeMax1 = [];
ExitTimeMin1 = [];
ExitTimeNN1 = [];
for i = 1:size(ExitTimeStats1)(1)
	ExitTimeAvg1(i) = ExitTimeStats1(i,1);
	ExitTimeSTD1(i) = ExitTimeStats1(i,2);
	ExitTimeMax1(i) = ExitTimeStats1(i,3);
	ExitTimeMin1(i) = ExitTimeStats1(i,4);
	ExitTimeNN1(i) = ExitTimeStats1(i,5);
endfor


ExitTimeAvg2 = [];
ExitTimeSTD2 = [];
ExitTimeMax2 = [];
ExitTimeMin2 = [];
ExitTimeNN2 = [];
for i = 1:size(ExitTimeStats2)(1)
	ExitTimeAvg2(i) = ExitTimeStats2(i,1);
	ExitTimeSTD2(i) = ExitTimeStats2(i,2);
	ExitTimeMax2(i) = ExitTimeStats2(i,3);
	ExitTimeMin2(i) = ExitTimeStats2(i,4);
	ExitTimeNN2(i) = ExitTimeStats2(i,5);
endfor


CreationTimeAvg0 = [];
CreationTimeSTD0 = [];
CreationTimeMax0 = [];
CreationTimeMin0 = [];
CreationTimeNN0 = [];
for i = 1:size(CreationTimeStats0)(1)
	CreationTimeAvg0(i) = CreationTimeStats0(i,1);
	CreationTimeSTD0(i) = CreationTimeStats0(i,2);
	CreationTimeMax0(i) = CreationTimeStats0(i,3);
	CreationTimeMin0(i) = CreationTimeStats0(i,4);
	CreationTimeNN0(i) = CreationTimeStats0(i,5);
endfor


CreationTimeAvg1 = [];
CreationTimeSTD1 = [];
CreationTimeMax1 = [];
CreationTimeMin1 = [];
CreationTimeNN1 = [];
for i = 1:size(CreationTimeStats1)(1)
	CreationTimeAvg1(i) = CreationTimeStats1(i,1);
	CreationTimeSTD1(i) = CreationTimeStats1(i,2);
	CreationTimeMax1(i) = CreationTimeStats1(i,3);
	CreationTimeMin1(i) = CreationTimeStats1(i,4);
	CreationTimeNN1(i) = CreationTimeStats1(i,5);
endfor


CreationTimeAvg2 = [];
CreationTimeSTD2 = [];
CreationTimeMax2 = [];
CreationTimeMin2 = [];
CreationTimeNN2 = [];
for i = 1:size(CreationTimeStats2)(1)
	CreationTimeAvg2(i) = CreationTimeStats2(i,1);
	CreationTimeSTD2(i) = CreationTimeStats2(i,2);
	CreationTimeMax2(i) = CreationTimeStats2(i,3);
	CreationTimeMin2(i) = CreationTimeStats2(i,4);
	CreationTimeNN2(i) = CreationTimeStats2(i,5);
endfor


ExecTimeAvg0 = [];
ExecTimeSTD0 = [];
ExecTimeMax0 = [];
ExecTimeMin0 = [];
ExecTimeNN0 = [];
for i = 1:size(ExecTimeStats0)(1)
	ExecTimeAvg0(i) = ExecTimeStats0(i,1);
	ExecTimeSTD0(i) = ExecTimeStats0(i,2);
	ExecTimeMax0(i) = ExecTimeStats0(i,3);
	ExecTimeMin0(i) = ExecTimeStats0(i,4);
	ExecTimeNN0(i) = ExecTimeStats0(i,5);
endfor


ExecTimeAvg1 = [];
ExecTimeSTD1 = [];
ExecTimeMax1 = [];
ExecTimeMin1 = [];
ExecTimeNN1 = [];
for i = 1:size(ExecTimeStats1)(1)
	ExecTimeAvg1(i) = ExecTimeStats1(i,1);
	ExecTimeSTD1(i) = ExecTimeStats1(i,2);
	ExecTimeMax1(i) = ExecTimeStats1(i,3);
	ExecTimeMin1(i) = ExecTimeStats1(i,4);
	ExecTimeNN1(i) = ExecTimeStats1(i,5);
endfor


ExecTimeAvg2 = [];
ExecTimeSTD2 = [];
ExecTimeMax2 = [];
ExecTimeMin2 = [];
ExecTimeNN2 = [];
for i = 1:size(ExecTimeStats2)(1)
	ExecTimeAvg2(i) = ExecTimeStats2(i,1);
	ExecTimeSTD2(i) = ExecTimeStats2(i,2);
	ExecTimeMax2(i) = ExecTimeStats2(i,3);
	ExecTimeMin2(i) = ExecTimeStats2(i,4);
	ExecTimeNN2(i) = ExecTimeStats2(i,5);
endfor
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * EnterTimeAvg0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeAvg0.dat tmp;
tmp = [X 1000 * EnterTimeSTD0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeSTD0.dat tmp
tmp = [X 1000 * EnterTimeMax0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeMax0.dat tmp
tmp = [X 1000 * EnterTimeMin0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeMin0.dat tmp
tmp = [X 1000 * EnterTimeNN0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeNN0.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * EnterTimeAvg1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeAvg1.dat tmp;
tmp = [X 1000 * EnterTimeSTD1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeSTD1.dat tmp
tmp = [X 1000 * EnterTimeMax1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeMax1.dat tmp
tmp = [X 1000 * EnterTimeMin1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeMin1.dat tmp
tmp = [X 1000 * EnterTimeNN1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeNN1.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * EnterTimeAvg2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeAvg2.dat tmp;
tmp = [X 1000 * EnterTimeSTD2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeSTD2.dat tmp
tmp = [X 1000 * EnterTimeMax2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeMax2.dat tmp
tmp = [X 1000 * EnterTimeMin2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeMin2.dat tmp
tmp = [X 1000 * EnterTimeNN2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/EnterTimeNN2.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * ExitTimeAvg0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeAvg0.dat tmp;
tmp = [X 1000 * ExitTimeSTD0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeSTD0.dat tmp
tmp = [X 1000 * ExitTimeMax0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeMax0.dat tmp
tmp = [X 1000 * ExitTimeMin0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeMin0.dat tmp
tmp = [X 1000 * ExitTimeNN0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeNN0.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * ExitTimeAvg1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeAvg1.dat tmp;
tmp = [X 1000 * ExitTimeSTD1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeSTD1.dat tmp
tmp = [X 1000 * ExitTimeMax1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeMax1.dat tmp
tmp = [X 1000 * ExitTimeMin1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeMin1.dat tmp
tmp = [X 1000 * ExitTimeNN1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeNN1.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * ExitTimeAvg2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeAvg2.dat tmp;
tmp = [X 1000 * ExitTimeSTD2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeSTD2.dat tmp
tmp = [X 1000 * ExitTimeMax2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeMax2.dat tmp
tmp = [X 1000 * ExitTimeMin2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeMin2.dat tmp
tmp = [X 1000 * ExitTimeNN2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExitTimeNN2.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * CreationTimeAvg0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeAvg0.dat tmp;
tmp = [X 1000 * CreationTimeSTD0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeSTD0.dat tmp
tmp = [X 1000 * CreationTimeMax0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeMax0.dat tmp
tmp = [X 1000 * CreationTimeMin0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeMin0.dat tmp
tmp = [X 1000 * CreationTimeNN0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeNN0.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * CreationTimeAvg1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeAvg1.dat tmp;
tmp = [X 1000 * CreationTimeSTD1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeSTD1.dat tmp
tmp = [X 1000 * CreationTimeMax1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeMax1.dat tmp
tmp = [X 1000 * CreationTimeMin1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeMin1.dat tmp
tmp = [X 1000 * CreationTimeNN1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeNN1.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * CreationTimeAvg2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeAvg2.dat tmp;
tmp = [X 1000 * CreationTimeSTD2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeSTD2.dat tmp
tmp = [X 1000 * CreationTimeMax2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeMax2.dat tmp
tmp = [X 1000 * CreationTimeMin2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeMin2.dat tmp
tmp = [X 1000 * CreationTimeNN2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/CreationTimeNN2.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * ExecTimeAvg0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeAvg0.dat tmp;
tmp = [X 1000 * ExecTimeSTD0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeSTD0.dat tmp
tmp = [X 1000 * ExecTimeMax0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeMax0.dat tmp
tmp = [X 1000 * ExecTimeMin0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeMin0.dat tmp
tmp = [X 1000 * ExecTimeNN0];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeNN0.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * ExecTimeAvg1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeAvg1.dat tmp;
tmp = [X 1000 * ExecTimeSTD1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeSTD1.dat tmp
tmp = [X 1000 * ExecTimeMax1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeMax1.dat tmp
tmp = [X 1000 * ExecTimeMin1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeMin1.dat tmp
tmp = [X 1000 * ExecTimeNN1];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeNN1.dat tmp
X = [4096 8192 16384 32768 65536 131072 262144 524288 1048576]';
tmp = [X 1000 * ExecTimeAvg2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeAvg2.dat tmp;
tmp = [X 1000 * ExecTimeSTD2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeSTD2.dat tmp
tmp = [X 1000 * ExecTimeMax2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeMax2.dat tmp
tmp = [X 1000 * ExecTimeMin2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeMin2.dat tmp
tmp = [X 1000 * ExecTimeNN2];
save -ascii  /home/angelo/Devel/RTJPerf/WS/ExecTimeNN2.dat tmp
save -ascii /home/angelo/Devel/RTJPerf/WS/SMTimingStats.ows
