package bftsmart.microbenchmark.tpcc.util;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.control.Try;

public class InetAddresses {

    private static final Logger LOGGER = LoggerFactory.getLogger(InetAddresses.class);

    private InetAddresses() {
        super();
    }

    public static String getHostName() {
        return Try.of(InetAddress::getLocalHost)
                .onFailure(e -> LOGGER.error("Erro ao recuperar o endereço de Local Host.", e))
                .getOrElse(InetAddress.getLoopbackAddress())
                .getCanonicalHostName();
    }

}
