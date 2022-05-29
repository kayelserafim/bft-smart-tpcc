#!/usr/bin/env bash

# exit when any command fails
set -e

# shell debug 
set -x

# experiments
repeats=1
threads=(1)
target_lcas=(7 6 5 4 3 2 1 0)
target_group=7

# replicas address
declare -a g0=("node1" "node2" "node3" "node4")
declare -a g1=("node5" "node6" "node7" "node8")
declare -a g2=("node9" "node10" "node11" "node12")
declare -a g3=("node13" "node14" "node15" "node16")
declare -a g4=("node17" "node18" "node19" "node20")
declare -a g5=("node21" "node22" "node23" "node24")
declare -a g6=("node25" "node26" "node27" "node28")
declare -a g7=("node29" "node30" "node31" "node32")

# replicas configuration
declare -a groups=(g0 g1 g2 g3 g4 g5 g6 g7)

# clients address
# declare -a clients=("node34")
declare -a c1=("node34")
declare -a c2=("node34" "node35")
declare -a c3=("node34" "node35" "node44")
declare -a c4=("node34" "node35" "node44" "node37")
declare -a c5=("node34" "node35" "node44" "node37" "node38")
declare -a c6=("node34" "node35" "node44" "node37" "node38" "node39")
declare -a c7=("node34" "node35" "node44" "node37" "node38" "node39" "node40")
declare -a c8=("node34" "node35" "node44" "node37" "node38" "node39" "node40" "node41")
declare -a c9=("node34" "node35" "node44" "node37" "node38" "node39" "node40" "node41" "node42")
declare -a c10=("node34" "node35" "node44" "node37" "node38" "node39" "node40" "node41" "node42" "node43")

# declare -a clients_groups=(c1 c2 c3 c4 c5 c6 c7 c8 c9 c10)
declare -a clients_groups=(c1)


# paths
byzcast_path="/home/tarcisio/byzcast/byzcast-async"
replicabin="/usr/bin/java -Dlogback.configurationFile=./config/logback.xml -classpath $byzcast_path/byzcast-server/target/byzcast-server-1.1-SNAPSHOT.jar ch.usi.inf.dslab.byzcast"
clientbin="/usr/bin/java -Dlogback.configurationFile=./config/logback.xml -classpath $byzcast_path/byzcast-client/target/byzcast-client-1.1-SNAPSHOT.jar ch.usi.inf.dslab.byzcast"

start_experiment(){
	stop_all
	sleep 5s
}

stop_all(){
	sleep 1s
	echo "--- Stoping all"

	for group in "${groups[@]}"; do
		replicas="$group[@]"
		for local_replica in "${!replicas}"; do
			ssh $local_replica killall java &
		done
	done

	for client_group in "${clients_groups[@]}"; do
		clients="$client_group[@]"
		for client in "${!clients}"; do
			ssh $client killall java &
		done
	done

	sleep 3s

	find $byzcast_path -name "currentView" -exec rm -rf {} \;

	sleep 3s
}


start_replicas(){
	echo "--- Starting replicas"

	for group in "${groups[@]}"; do

		replicas=("$group[@]")

		# bash won't support multidimensional arrays
		arr=()
		for r in "${!replicas}"; do
			arr+=($r)
		done

		if [ $group == "g0" ]; then
			group_id=0
			LCS="-lcs $byzcast_path/config/prod/g1"
		fi

		if [ $group == "g1" ]; then
			group_id=1
			LCS="-lcs $byzcast_path/config/prod/g2"
		fi

		if [ $group == "g2" ]; then
			group_id=2
			LCS="-lcs $byzcast_path/config/prod/g3"
		fi

		if [ $group == "g3" ]; then
			group_id=3
			LCS="-lcs $byzcast_path/config/prod/g4"
		fi

		if [ $group == "g4" ]; then
			group_id=4
			LCS="-lcs $byzcast_path/config/prod/g5"
		fi

		if [ $group == "g5" ]; then
			group_id=5
			LCS="-lcs $byzcast_path/config/prod/g6"
		fi

		if [ $group == "g6" ]; then
			group_id=6
			LCS="-lcs $byzcast_path/config/prod/g7"
		fi

		if [ $group == "g7" ]; then
			group_id=7
			LCS=""
		fi

		for local_replica_id in "${!arr[@]}"; do
				ssh ${arr[$local_replica_id]} cd "$byzcast_path; $replicabin.async.Replica -i $local_replica_id -g $group_id -ng -cp $byzcast_path/config/prod/$group $LCS $ARGS" &
		done
	done	
	sleep 60s
}

start_clients(){
	echo "--- Starting clients"

	declare -a aclients=$3;
	# echo "${#aclients[@]}"

	# rebuild the array to get the number of clients
	clientarr=()
	for c in "${!aclients}"; do
		clientarr+=($c)
	done

	declare -a clients_number="${#clientarr[@]}"
	declare -a logdir='mgroup_'$target_group'_mlca_'$2'_clients_'$clients_number'_threads_'$1
	mkdir -p $byzcast_path/$logdir

	for client in "${!aclients}"; do
		ssh $client "cd $byzcast_path; $clientbin.client.Client -gc ./config/prod/g$2 -i $RANDOM -g$target_group -c$1 -d120 -lp $logdir" &
	done

	sleep 150s

	# for client in "${clients[@]}"; do
	# 	echo $client;
	# done
}

trap "stop_all; exit 255" SIGINT SIGTERM


for (( i = 0; i < repeats; i++ )); do
	start_experiment
		for thread in "${threads[@]}"; do
				for target_lca in "${target_lcas[@]}"; do
					start_replicas
						for clients_group in "${clients_groups[@]}"; do
							clients=("$clients_group[@]")
							start_clients "${thread}" "${target_lca}" "$clients"
						done
					stop_all
				done
		done
done
