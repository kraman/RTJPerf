#!/usr/bin/octave -q

CT = [loadMatrix("ScopedMemoryTiming0/EnterTime", "%f")' ;\ 
      loadMatrix("ScopedMemoryTiming0/ExitTime", "%f")'  ;\
      loadMatrix("ScopedMemoryTiming0/ExecTime", "%f")' ;\
      loadMatrix("ScopedMemoryTiming0/CreationTime", "%f")' ];

LT = [loadMatrix("ScopedMemoryTiming1/EnterTime", "%f")' ;\ 
      loadMatrix("ScopedMemoryTiming1/ExitTime", "%f")'  ;\
      loadMatrix("ScopedMemoryTiming1/ExecTime", "%f")' ;\
      loadMatrix("ScopedMemoryTiming1/CreationTime", "%f")' ];
            
hold on;
printf("Parsed Data...\n");

printf("Showing Enter-Exit times for CTMemory...\n");
for i = 1:2
    plot(CT(i,10:(size(CT)(2))));
endfor

printf("Are you ready to see the LTMemory Results?...\n");
scanf("%s", 1);

for i = 1:2
    plot(LT(i,10:(size(LT)(2))));
endfor
        
printf("Press a key and enter to exit...\n");
scanf("%s", 1);
