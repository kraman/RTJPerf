#!/usr/bin/octave -q 

X = [32 64 128 256 512 1024 2048 4096 8192 16384]';


CT = [\
	loadMatrix("AllocTime0/AllocTime32", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime64", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime128", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime256", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime512", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime1024", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime2048", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime4096", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime8192", "%f")';\ 
	loadMatrix("AllocTime0/AllocTime16384", "%f")';\ 
];


LT = [\
	loadMatrix("AllocTime1/AllocTime32", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime64", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime128", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime256", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime512", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime1024", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime2048", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime4096", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime8192", "%f")';\ 
	loadMatrix("AllocTime1/AllocTime16384", "%f")';\ 
];


VT = [\
	loadMatrix("AllocTime2/AllocTime32", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime64", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime128", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime256", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime512", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime1024", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime2048", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime4096", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime8192", "%f")';\ 
	loadMatrix("AllocTime2/AllocTime16384", "%f")';\ 
];
CT32 = 1000 * CT(1, 5:1:size(CT)(2));
CT64 = 1000 * CT(2, 5:1:size(CT)(2));
CT128 = 1000 * CT(3, 5:1:size(CT)(2));
CT256 = 1000 * CT(4, 5:1:size(CT)(2));
CT512 = 1000 * CT(5, 5:1:size(CT)(2));
CT1024 = 1000 * CT(6, 5:1:size(CT)(2));
CT2048 = 1000 * CT(7, 5:1:size(CT)(2));
CT4096 = 1000 * CT(8, 5:1:size(CT)(2));
CT8192 = 1000 * CT(9, 5:1:size(CT)(2));
CT16384 = 1000 * CT(10, 5:1:size(CT)(2));
LT32 = 1000 * LT(1, 5:1:size(LT)(2));
LT64 = 1000 * LT(2, 5:1:size(LT)(2));
LT128 = 1000 * LT(3, 5:1:size(LT)(2));
LT256 = 1000 * LT(4, 5:1:size(LT)(2));
LT512 = 1000 * LT(5, 5:1:size(LT)(2));
LT1024 = 1000 * LT(6, 5:1:size(LT)(2));
LT2048 = 1000 * LT(7, 5:1:size(LT)(2));
LT4096 = 1000 * LT(8, 5:1:size(LT)(2));
LT8192 = 1000 * LT(9, 5:1:size(LT)(2));
LT16384 = 1000 * LT(10, 5:1:size(LT)(2));
VT32 = 1000 * VT(1, 5:1:size(VT)(2));
VT64 = 1000 * VT(2, 5:1:size(VT)(2));
VT128 = 1000 * VT(3, 5:1:size(VT)(2));
VT256 = 1000 * VT(4, 5:1:size(VT)(2));
VT512 = 1000 * VT(5, 5:1:size(VT)(2));
VT1024 = 1000 * VT(6, 5:1:size(VT)(2));
VT2048 = 1000 * VT(7, 5:1:size(VT)(2));
VT4096 = 1000 * VT(8, 5:1:size(VT)(2));
VT8192 = 1000 * VT(9, 5:1:size(VT)(2));
VT16384 = 1000 * VT(10, 5:1:size(VT)(2));


[A S M m N] = asmm(1000 * CT(1, 5:(size(CT)(2))));
CTStats = [A S M m N];
for i = 2:size(CT)(1)
	[A S M m N] = asmm(1000 * CT(i, 5:(size(CT)(2))));
	CTStats = [CTStats; [A S M m N]];
endfor



[A S M m N] = asmm(1000 * LT(1, 5:(size(LT)(2))));
LTStats = [A S M m N];
for i = 2:size(LT)(1)
	[A S M m N] = asmm(1000 * LT(i, 5:(size(LT)(2))));
	LTStats = [LTStats; [A S M m N]];
endfor



[A S M m N] = asmm(1000 * VT(1, 5:(size(VT)(2))));
VTStats = [A S M m N];
for i = 2:size(VT)(1)
	[A S M m N] = asmm(1000 * VT(i, 5:(size(VT)(2))));
	VTStats = [VTStats; [A S M m N]];
endfor

CT32pd = GenDistr(CT32);
CT64pd = GenDistr(CT64);
CT128pd = GenDistr(CT128);
CT256pd = GenDistr(CT256);
CT512pd = GenDistr(CT512);
CT1024pd = GenDistr(CT1024);
CT2048pd = GenDistr(CT2048);
CT4096pd = GenDistr(CT4096);
CT8192pd = GenDistr(CT8192);
CT16384pd = GenDistr(CT16384);
CTAvg = [X  CTStats(1:1:size(CTStats)(1), 1)];
CTStd = [X CTStats(1:1:size(CTStats)(1), 2)];
CTMax = [X CTStats(1:1:size(CTStats)(1), 3)];
CTMin = [X CTStats(1:1:size(CTStats)(1), 4)];
CTNN  = [X CTStats(1:1:size(CTStats)(1), 5)];
LTAvg = [X  LTStats(1:1:size(LTStats)(1), 1)];
LTStd = [X LTStats(1:1:size(LTStats)(1), 2)];
LTMax = [X LTStats(1:1:size(LTStats)(1), 3)];
LTMin = [X LTStats(1:1:size(LTStats)(1), 4)];
LTNN  = [X LTStats(1:1:size(LTStats)(1), 5)];
VTAvg = [X  VTStats(1:1:size(VTStats)(1), 1)];
VTStd = [X VTStats(1:1:size(VTStats)(1), 2)];
VTMax = [X VTStats(1:1:size(VTStats)(1), 3)];
VTMin = [X VTStats(1:1:size(VTStats)(1), 4)];
VTNN  = [X VTStats(1:1:size(VTStats)(1), 5)];
LT32pd = GenDistr(LT32);
LT64pd = GenDistr(LT64);
LT128pd = GenDistr(LT128);
LT256pd = GenDistr(LT256);
LT512pd = GenDistr(LT512);
LT1024pd = GenDistr(LT1024);
LT2048pd = GenDistr(LT2048);
LT4096pd = GenDistr(LT4096);
LT8192pd = GenDistr(LT8192);
LT16384pd = GenDistr(LT16384);
CTAvg = [X  CTStats(1:1:size(CTStats)(1), 1)];
CTStd = [X CTStats(1:1:size(CTStats)(1), 2)];
CTMax = [X CTStats(1:1:size(CTStats)(1), 3)];
CTMin = [X CTStats(1:1:size(CTStats)(1), 4)];
CTNN  = [X CTStats(1:1:size(CTStats)(1), 5)];
LTAvg = [X  LTStats(1:1:size(LTStats)(1), 1)];
LTStd = [X LTStats(1:1:size(LTStats)(1), 2)];
LTMax = [X LTStats(1:1:size(LTStats)(1), 3)];
LTMin = [X LTStats(1:1:size(LTStats)(1), 4)];
LTNN  = [X LTStats(1:1:size(LTStats)(1), 5)];
VTAvg = [X  VTStats(1:1:size(VTStats)(1), 1)];
VTStd = [X VTStats(1:1:size(VTStats)(1), 2)];
VTMax = [X VTStats(1:1:size(VTStats)(1), 3)];
VTMin = [X VTStats(1:1:size(VTStats)(1), 4)];
VTNN  = [X VTStats(1:1:size(VTStats)(1), 5)];
VT32pd = GenDistr(VT32);
VT64pd = GenDistr(VT64);
VT128pd = GenDistr(VT128);
VT256pd = GenDistr(VT256);
VT512pd = GenDistr(VT512);
VT1024pd = GenDistr(VT1024);
VT2048pd = GenDistr(VT2048);
VT4096pd = GenDistr(VT4096);
VT8192pd = GenDistr(VT8192);
VT16384pd = GenDistr(VT16384);
CTAvg = [X  CTStats(1:1:size(CTStats)(1), 1)];
CTStd = [X CTStats(1:1:size(CTStats)(1), 2)];
CTMax = [X CTStats(1:1:size(CTStats)(1), 3)];
CTMin = [X CTStats(1:1:size(CTStats)(1), 4)];
CTNN  = [X CTStats(1:1:size(CTStats)(1), 5)];
LTAvg = [X  LTStats(1:1:size(LTStats)(1), 1)];
LTStd = [X LTStats(1:1:size(LTStats)(1), 2)];
LTMax = [X LTStats(1:1:size(LTStats)(1), 3)];
LTMin = [X LTStats(1:1:size(LTStats)(1), 4)];
LTNN  = [X LTStats(1:1:size(LTStats)(1), 5)];
VTAvg = [X  VTStats(1:1:size(VTStats)(1), 1)];
VTStd = [X VTStats(1:1:size(VTStats)(1), 2)];
VTMax = [X VTStats(1:1:size(VTStats)(1), 3)];
VTMin = [X VTStats(1:1:size(VTStats)(1), 4)];
VTNN  = [X VTStats(1:1:size(VTStats)(1), 5)];
save -ascii /home/angelo/Devel/RTJPerf/WS/SMAllocTimingStats.ows
save -ascii /home/angelo/Devel/RTJPerf/WS/CTAvg.dat CTAvg;
save -ascii /home/angelo/Devel/RTJPerf/WS/CTStd.dat CTStd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CTMax.dat CTMax;
save -ascii /home/angelo/Devel/RTJPerf/WS/CTMin.dat CTMin;
save -ascii /home/angelo/Devel/RTJPerf/WS/CTNN.dat  CTNN;
save -ascii /home/angelo/Devel/RTJPerf/WS/LTAvg.dat LTAvg;
save -ascii /home/angelo/Devel/RTJPerf/WS/LTStd.dat LTStd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LTMax.dat LTMax;
save -ascii /home/angelo/Devel/RTJPerf/WS/LTMin.dat LTMin;
save -ascii /home/angelo/Devel/RTJPerf/WS/LTNN.dat  LTNN;
save -ascii /home/angelo/Devel/RTJPerf/WS/VTAvg.dat VTAvg;
save -ascii /home/angelo/Devel/RTJPerf/WS/VTStd.dat VTStd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VTMax.dat VTMax;
save -ascii /home/angelo/Devel/RTJPerf/WS/VTMin.dat VTMin;
save -ascii /home/angelo/Devel/RTJPerf/WS/VTNN.dat  VTNN;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT32pd.dat CT32pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT64pd.dat CT64pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT128pd.dat CT128pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT256pd.dat CT256pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT512pd.dat CT512pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT1024pd.dat CT1024pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT2048pd.dat CT2048pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT4096pd.dat CT4096pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT8192pd.dat CT8192pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/CT16384pd.dat CT16384pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT32pd.dat LT32pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT64pd.dat LT64pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT128pd.dat LT128pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT256pd.dat LT256pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT512pd.dat LT512pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT1024pd.dat LT1024pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT2048pd.dat LT2048pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT4096pd.dat LT4096pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT8192pd.dat LT8192pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/LT16384pd.dat LT16384pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT32pd.dat VT32pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT64pd.dat VT64pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT128pd.dat VT128pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT256pd.dat VT256pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT512pd.dat VT512pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT1024pd.dat VT1024pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT2048pd.dat VT2048pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT4096pd.dat VT4096pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT8192pd.dat VT8192pd;
save -ascii /home/angelo/Devel/RTJPerf/WS/VT16384pd.dat VT16384pd;
