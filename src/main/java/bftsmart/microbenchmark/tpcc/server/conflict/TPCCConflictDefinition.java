package bftsmart.microbenchmark.tpcc.server.conflict;

import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import parallelism.MessageContextPair;
import parallelism.late.ConflictDefinition;

public class TPCCConflictDefinition extends ConflictDefinition {

    @Override
    public boolean isDependent(MessageContextPair arg0, MessageContextPair arg1) {
        TPCCCommand firstCommand = TPCCCommand.getObject(arg0.request.getContent());
        TPCCCommand secondCommand = TPCCCommand.getObject(arg1.request.getContent());
        
        firstCommand.getTransactionType();
        secondCommand.getTransactionType();
        return true;
    }

}
