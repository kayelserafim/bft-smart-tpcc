# bft-smart-tpcc
A TPC-C like test tool for BFT-SMaRt.

### Compiling ###

Type `./gradlew installDist` in the main directory. The required jar files and default configuration files will be available in the `build/install/bft-smart-tpcc` directory.

### How do I get set up? ###

To run any demonstration you first need to configure BFT-SMaRt-TPCC to define the protocol behavior and the location of each replica.
The servers must be specified in the configuration file (see `config/hosts.config`):

```
#server id, address and port (the ids from 0 to n-1 are the service replicas) 
0 127.0.0.1 11000 11001
1 127.0.0.1 11010 11011
2 127.0.0.1 11020 11021
3 127.0.0.1 11030 11031
```

**Execution.**

1) It is necessary to execute the initial database load, as follows. The TPCC workload must be specified in the properties file (see `config/workload.properties`). 

```
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCGeneratorApplication
```

You can run the TPCC Server by executing the following commands, from inside the main directory across three different consoles (3 replicas, to tolerate 1 fault):

1) To execute a server replica, it is necessary to use the following command.

```
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCServerApplication <process id> <num threads> <parallel SMR>

process id = the process identifier
num threads = number of worker threads
parallel SMR = true if should run as parallel SMR
```

2) For example, you should use the following commands to execute three replicas (to tolerate up to one crash failure) using the default lock free graph with 4 threads and parallel SMR execution.

```
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCServerApplication 0 4 true
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCServerApplication 1 4 true
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCServerApplication 2 4 true
```

You can run the TPCC Client by executing the following command, from inside the main directory:

1) To execute the clients, it is necessary to use the following command.

```
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCClientApplication <client id> <num clients> <parallel SMR>

client id = the client identifier
num clients = number of threads clients to be created in the process, each thread represents one client
parallel SMR = true if should run as parallel SMR
```

2) For example, you should use the following commands to execute 200 clients distributed in four machines/processes, using parallel SMR execution.

```
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCClientApplication 0 50 true
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCClientApplication 1 50 true
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCClientApplication 2 50 true
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCClientApplication 3 50 true
```
