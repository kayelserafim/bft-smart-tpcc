#!/usr/bin/env bash

# exit when any command fails
set -e

# shell debug 
set -x

# experiments
target_nodes=(node90 node91 node92)
threads=4
parallel=true

tpcc_path="./build/install/bft-smart-tpcc"
JAVA="java -Dlogback.configurationFile=$tpcc_path/config/logback.xml -cp '$tpcc_path/lib/*'"

start_experiment() {
	stop_all
	tmux new-session -d -s bft-tpcc

	for ((i = 0; i < ${#target_nodes[@]}; i++)); do
		tmux split -h -t bft-tpcc
	done
	tmux select-layout -t bft-tpcc tiled
}

stop_all() {
	sleep 1
	echo "--- Stoping all"

	tmux kill-session -t bft-tpcc | true
	find . -name "currentView" -exec rm -rf {} \;

	sleep 1
}

start_replicas(){
	for ((i = 0; i < ${#target_nodes[@]}; i++)); do
		 tmux send-keys -t bft-tpcc.$i "ssh -T -o ServerAliveInterval=7200 ${target_nodes[$i]}" C-m
		 tmux send-keys -t bft-tpcc.$i "killall -9 java" C-m
		 tmux send-keys -t bft-tpcc.$i "cd bft-smart-tpcc/build/install/bft-smart-tpcc/" C-m
         tmux send-keys -t bft-tpcc.$i "./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCServerApplication $i $threads $parallel" C-m
    done
}

start_experiment
start_replicas

tmux attach-session -t bft-tpcc