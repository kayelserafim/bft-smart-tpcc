#!/usr/bin/env bash

for i in {90..92}
do
 echo "Killing all user process on node$i..."
 ssh node$i killall -u kayel
 echo "All user processes were killed on node$i..."
done