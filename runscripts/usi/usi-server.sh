#!/bin/bash

#connect to server
echo "Connecting to the server..."

ssh -T -o ServerAliveInterval=7200 $1 << EOF
    #switch path
    echo "Switching the path"
    cd bft-smart-tpcc/build/install/bft-smart-tpcc/

    #run deploy script
    echo "Running deploy script"

    ./smartrun.sh -Dbft.replica-id=$2 -Dbft.num-of-threads=$3 -Dbft.parallel-smr=$4 bftsmart.microbenchmark.tpcc.TPCCServerApplication

    bash -l
EOF