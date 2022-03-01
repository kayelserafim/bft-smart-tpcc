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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;

import bftsmart.tom.ServiceProxy;

/**
 *
 * @author Marcel Santos
 *
 */
public class YCSBClient extends DB {

    private static final Logger LOGGER = LoggerFactory.getLogger(YCSBClient.class);

    private static AtomicInteger counter = new AtomicInteger();
    private ServiceProxy proxy = null;

    @Override
    public void init() {
        Properties props = getProperties();
        int initId = Integer.parseInt(props.getProperty("smart-initkey"));
        int myId = initId + counter.addAndGet(1);
        proxy = new ServiceProxy(myId);
        LOGGER.info("YCSBKVClient. Initiated client id: {}", myId);
    }

    @Override
    public int delete(String arg0, String arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int insert(String table, String key, HashMap<String, ByteIterator> values) {
        Iterator<String> keys = values.keySet().iterator();
        Map<String, byte[]> map = new HashMap<>();
        while (keys.hasNext()) {
            String field = keys.next();
            map.put(field, values.get(field).toArray());
        }
        YCSBMessage msg = YCSBMessage.newInsertRequest(table, key, map);
        byte[] reply = proxy.invokeOrdered(msg.getBytes());
        YCSBMessage replyMsg = YCSBMessage.getObject(reply);
        return replyMsg.getResult();
    }

    @Override
    public int read(String table, String key, Set<String> fields, HashMap<String, ByteIterator> result) {
        Map<String, byte[]> results = new HashMap<>();
        YCSBMessage request = YCSBMessage.newReadRequest(table, key, fields, results);
        byte[] reply = proxy.invokeUnordered(request.getBytes());
        YCSBMessage replyMsg = YCSBMessage.getObject(reply);
        return replyMsg.getResult();
    }

    @Override
    public int scan(String arg0, String arg1, int arg2, Set<String> arg3, Vector<HashMap<String, ByteIterator>> arg4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(String table, String key, HashMap<String, ByteIterator> values) {
        Iterator<String> keys = values.keySet().iterator();
        Map<String, byte[]> map = new HashMap<>();
        while (keys.hasNext()) {
            String field = keys.next();
            map.put(field, values.get(field).toArray());
        }
        YCSBMessage msg = YCSBMessage.newUpdateRequest(table, key, map);
        byte[] reply = proxy.invokeOrdered(msg.getBytes());
        YCSBMessage replyMsg = YCSBMessage.getObject(reply);
        return replyMsg.getResult();
    }

}
