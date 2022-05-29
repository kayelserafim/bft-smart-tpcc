#!/usr/bin/env bash

G=2
N=4
ARGS="${@:2}"

# exit when any command fails
set -e

# shell debug 
set -x

# experiments
repeats=1
threads=(32)
conflicts=(0)

byzcast_path="./byzcast-async"
JAVA="java -Dlogback.configurationFile=$byzcast_path/config/logback.xml -classpath $byzcast_path/byzcast-server/target/byzcast-server-1.1-SNAPSHOT.jar ch.usi.inf.dslab.byzcast"

start_experiment(){
	stop_all
	tmux new-session -d -s byzcast

	for (( i = 1; i <= $N; i++ )); do
		for (( j = 1; j <=$(( G+1 )); j++ )); do
				tmux split -h -t byzcast
		done
		tmux select-layout -t byzcast tiled
	done
}

stop_all(){
	sleep 1
	echo "--- Stoping all"

	tmux kill-session -t byzcast | true
	find . -name "currentView" -exec rm -rf {} \;

	sleep 1
}

start_replicas(){
	tmux send-keys -t byzcast.1 "$JAVA.async.Replica -i0 -g0 -cp $byzcast_path/config/dev/g0 -lcs $byzcast_path/config/dev/g1 $byzcast_path/config/dev/g2" C-m
	tmux send-keys -t byzcast.2 "$JAVA.async.Replica -i1 -g0 -cp $byzcast_path/config/dev/g0 -lcs $byzcast_path/config/dev/g1 $byzcast_path/config/dev/g2" C-m
	tmux send-keys -t byzcast.3 "$JAVA.async.Replica -i2 -g0 -cp $byzcast_path/config/dev/g0 -lcs $byzcast_path/config/dev/g1 $byzcast_path/config/dev/g2" C-m
	tmux send-keys -t byzcast.4 "$JAVA.async.Replica -i3 -g0 -cp $byzcast_path/config/dev/g0 -lcs $byzcast_path/config/dev/g1 $byzcast_path/config/dev/g2" C-m

	tmux send-keys -t byzcast.5 "$JAVA.async.Replica -i0 -g1 -cp $byzcast_path/config/dev/g1" C-m
	tmux send-keys -t byzcast.6 "$JAVA.async.Replica -i1 -g1 -cp $byzcast_path/config/dev/g1" C-m
	tmux send-keys -t byzcast.7 "$JAVA.async.Replica -i2 -g1 -cp $byzcast_path/config/dev/g1" C-m
	tmux send-keys -t byzcast.8 "$JAVA.async.Replica -i3 -g1 -cp $byzcast_path/config/dev/g1" C-m

	tmux send-keys -t byzcast.9 "$JAVA.async.Replica -i0 -g2 -cp $byzcast_path/config/dev/g2" C-m
	tmux send-keys -t byzcast.10 "$JAVA.async.Replica -i1 -g2 -cp $byzcast_path/config/dev/g2" C-m
	tmux send-keys -t byzcast.11 "$JAVA.async.Replica -i2 -g2 -cp $byzcast_path/config/dev/g2" C-m
	tmux send-keys -t byzcast.12 "$JAVA.async.Replica -i3 -g2 -cp $byzcast_path/config/dev/g2" C-m
}

start_clients(){
	tmux switch -t byzcast
	echo
	# tmux send-keys -t byzcast.17 "$JAVA.async.Replica -i3 -gc ./config/localhost/g0 -lcs ./config/localhost/global1" C-m
}

for (( i = 0; i < repeats; i++ )); do
	for thread in "${threads[@]}"; do
		for conflict in "${conflicts[@]}"; do
			start_experiment
			start_replicas
			start_clients
			# start_master "${thread}"
			# start_replicas
			# start_clients "${conflict}"
			# stop_all
		done
	done
done
