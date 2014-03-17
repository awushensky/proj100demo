package com.goproject100.demo.service.remote;

import com.goproject100.demo.model.DataPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This {@link Runnable} handles notifying external endpoints of new data packets.
 */
public class DataPacketNotifier implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(DataPacketNotifier.class);

    private final String url;
    private final DataPacket dataPacket;

    /**
     * Construct a new {@link Runnable} {@link DataPacketNotifier} with a URL and a {@link DataPacket}.
     * @param url the URL to notify
     * @param dataPacket the {@link DataPacket} to notify the URL of
     */
    public DataPacketNotifier(final String url, final DataPacket dataPacket) {
        this.url = url;
        this.dataPacket = dataPacket;
    }

    @Override
    public void run() {
        LOG.debug("Notifying endpoint {} of data packet {}", url, dataPacket);
        //TODO: this
    }
}
