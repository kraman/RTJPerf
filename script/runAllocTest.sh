#!/bin/bash

export JRATE_IMMORTAL_MEMORY_SIZE=10000000
COUNT=1000
OUT_DIR="SMAllocTime"

TARGET=$1

declare -a MEM_TYPE

MEM_TYPE=("CTMemory" "LTMemory" "VTMemory")

echo "Running CTMemory Test"
    
# done    

ALLOC_SIZE=32
BASE_MEM_SIZE=128000
MEM_SLACK=400000
MAX_SIZE=16384

I=0

while [ $I -lt 3 ];
do
    let MEM_SIZE=BASE_MEM_SIZE+MEM_SLACK
    echo I $I ${MEM_TYPE[$I]}

    while [ $ALLOC_SIZE -le $MAX_SIZE ];
    do
        $TARGET --count $COUNT --scopedMemoryType ${MEM_TYPE[$I]} --memSize $MEM_SIZE \
         --outDir $OUT_DIR --allocSize $ALLOC_SIZE
        let ALLOC_SIZE=ALLOC_SIZE*2
        let BASE_MEM_SIZE=2*BASE_MEM_SIZE
        let MEM_SIZE=BASE_MEM_SIZE+MEM_SLACK
    done

    let I=I+1
    let ALLOC_SIZE=32
    let BASE_MEM_SIZE=128000
done
