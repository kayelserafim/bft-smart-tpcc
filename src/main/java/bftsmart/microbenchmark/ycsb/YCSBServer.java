/**
 * Copyright (c) 2007-2013 Alysson Bessani, Eduardo Alchieri, Paulo Sousa, and the authors indicated in the @author tags
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bftsmart.microbenchmark.ycsb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bftsmart.microbenchmark.ycsb.YCSBMessage.Entity;
import bftsmart.microbenchmark.ycsb.YCSBMessage.Type;
import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultRecoverable;

/**
 *
 * @author Marcel Santos
 *
 */
public class YCSBServer extends DefaultRecoverable {

    private static final Logger LOGGER = LoggerFactory.getLogger(YCSBServer.class);
    private TreeMap<String, YCSBTable> mTables;

    public static void main(String[] args) {
        if (args.length == 1) {
            new YCSBServer(Integer.parseInt(args[0]));
        } else {
            LOGGER.error("Usage: java ... YCSBServer <replica_id>");
        }
    }

    private YCSBServer(int id) {
        this.mTables = new TreeMap<>();
        new ServiceReplica(id, this, this);
    }

    @Override
    public byte[][] appExecuteBatch(byte[][] commands, MessageContext[] msgCtx) {
        byte[][] replies = new byte[commands.length][];
        int index = 0;
        for (byte[] command : commands) {
            if (msgCtx != null && msgCtx[index] != null && msgCtx[index].getConsensusId() % 1000 == 0) {
                LOGGER.info("YCSBServer executing CID: {}", msgCtx[index].getConsensusId());
            }

            YCSBMessage aRequest = YCSBMessage.getObject(command);
            YCSBMessage reply = YCSBMessage.newErrorMessage("");
            if (aRequest == null) {
                replies[index] = reply.getBytes();
                continue;
            }
            LOGGER.info("[INFO] Processing an ordered request");
            switch (aRequest.getType()) {
            // ##### operation: create #####
            case CREATE:
                // ##### entity: record #####
                if (aRequest.getEntity() == Entity.RECORD) {
                    if (!mTables.containsKey(aRequest.getTable())) {
                        mTables.put(aRequest.getTable(), new YCSBTable());
                    }
                    if (!mTables.get(aRequest.getTable()).containsKey(aRequest.getKey())) {
                        mTables.get(aRequest.getTable()).put(aRequest.getKey(), aRequest.getValues());
                        reply = YCSBMessage.newResponse(0);
                    }
                }
                break;
            // ##### operation: update #####
            case UPDATE:
                // ##### entity: record #####
                if (aRequest.getEntity() == Entity.RECORD) {
                    if (!mTables.containsKey(aRequest.getTable())) {
                        mTables.put(aRequest.getTable(), new YCSBTable());
                    }
                    mTables.get(aRequest.getTable()).put(aRequest.getKey(), aRequest.getValues());
                    reply = YCSBMessage.newResponse(1);
                }
                break;
            default:
                LOGGER.info("[INFO] Entity {} not supported", aRequest.getType());
            }
            LOGGER.info("[INFO] Sending reply");
            replies[index++] = reply.getBytes();
        }
        return replies;
    }

    @Override
    public byte[] appExecuteUnordered(byte[] theCommand, MessageContext theContext) {
        YCSBMessage aRequest = YCSBMessage.getObject(theCommand);
        YCSBMessage reply = YCSBMessage.newErrorMessage("");
        if (aRequest == null) {
            return reply.getBytes();
        }
        LOGGER.info("[INFO] Processing an unordered request");

        // ##### operation: read AND entity: record #####
        if (aRequest.getType() == Type.READ && aRequest.getEntity() == Entity.RECORD) {
            if (!mTables.containsKey(aRequest.getTable())) {
                reply = YCSBMessage.newErrorMessage("Table not found");
            } else if (!mTables.get(aRequest.getTable()).containsKey(aRequest.getKey())) {
                reply = YCSBMessage.newErrorMessage("Record not found");
            } else {
                reply = YCSBMessage.newResponse(mTables.get(aRequest.getTable()).get(aRequest.getKey()), 0);
            }

        }
        LOGGER.info("[INFO] Sending reply");
        return reply.getBytes();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void installSnapshot(byte[] state) {
        try (InputStream is = new ByteArrayInputStream(state); ObjectInput in = new ObjectInputStream(is)) {
            mTables = (TreeMap<String, YCSBTable>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.info("[ERROR] Error deserializing state", e);
        }
    }

    @Override
    public byte[] getSnapshot() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(mTables);
            return bos.toByteArray();
        } catch (IOException ioe) {
            LOGGER.error("[ERROR] Error serializing state", ioe);
            return "ERROR".getBytes();
        }
    }

}