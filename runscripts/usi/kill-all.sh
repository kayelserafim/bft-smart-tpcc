#!/usr/bin/env bash
for i in {1..92}
do
 ssh node$i killall -u kayel
done