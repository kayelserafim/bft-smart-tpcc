#!/usr/bin/env bash


target_nodes=(node31 node90 node91 node92)

for node in "${target_nodes[@]}"
do
 echo "Killing all user process on $node..."
 ssh $node killall -u kayel
 echo "All user processes were killed on $node..."
done