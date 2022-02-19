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

Initial database load:

```
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCGeneratorApplication
```

You can run the TPCC Server by executing the following commands, from within the main directory across four different consoles (3 replicas, to tolerate 1 fault):

```
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCServerApplication 0
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCServerApplication 1
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCServerApplication 2
```

The TPCC worload must be specified in the properties file (see `config/workload.properties`). You can run the TPCC Client by executing the following command, from within the main directory:

```
./smartrun.sh bftsmart.microbenchmark.tpcc.TPCCClientApplication
```
