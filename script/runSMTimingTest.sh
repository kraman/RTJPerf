#!/bin/bash
export JRATE_IMMORTAL_MEMORY_SIZE=10000000
COUNT=100
MEM_TYPE="CTMemory"
OUT_DIR="SMTiming"


echo "Running CTMemory Tests"
for size in 4096 8192 16384 32768 65536 131072 262144 524288 1048576; do
    $1 --count $COUNT --scopedMemoryType $MEM_TYPE --memSize $size --outDir $OUT_DIR
done

MEM_TYPE=LTMemory

echo "Running LTMemory Tests"
for size in 4096 8192 16384 32768 65536 131072 262144 524288 1048576; do
    $1 --count $COUNT --scopedMemoryType $MEM_TYPE --memSize $size --outDir $OUT_DIR
done

echo "Done..."
