#!/usr/bin/env bash

# exit when any command fails
set -e

# shell debug 
set -x

TIMEOUT=120
FOLDERS=(sync async)

calcule_lat(){
	for d in $1/* ; do
		TOTAL_CLIENTS=$(ls -1q "$d"/ | wc -l)
		SUM_LATENCY=$(grep -R Mean "$d" | awk '{print $3}' |  paste -sd+ - | bc)
        LATENCY_MEAN=$(echo $SUM_LATENCY/$TOTAL_CLIENTS | bc)
	
		echo $d $LATENCY_MEAN >> lat-lan-$1-8-level.dat
	done
}

for folder in "${FOLDERS[@]}"; do
    calcule_lat "${folder}"
done

# calcule_lat