#!/bin/bash
export JRATE_IMMORTAL_MEMORY_SIZE=10000000
COUNT=300
TARGET=$1

declare -a MEM_TYPE
MEM_TYPE=("CTMemory" "LTMemory" "VTMemory")

OUT_DIR="SMTiming"

MAX_MEM_SIZE=1048576

I=0

while [ $I -lt 3 ];
do
    let MEM_SIZE=4096
    echo Running ${MEM_TYPE[$I]} Tests
    while [ $MEM_SIZE -le $MAX_MEM_SIZE ];
    do
        $TARGET --count $COUNT --scopedMemoryType ${MEM_TYPE[$I]} --memSize $MEM_SIZE\
         --outDir $OUT_DIR
         let MEM_SIZE=2*MEM_SIZE
    done

    let I=I+1
done

echo "Done..."
