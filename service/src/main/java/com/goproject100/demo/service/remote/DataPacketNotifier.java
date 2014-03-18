package com.goproject100.demo.service.remote;

import com.goproject100.demo.model.DataPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * This {@link Runnable} handles notifying external endpoints of new data packets.
 */
public class DataPacketNotifier implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(DataPacketNotifier.class);
    private static final int RETRY_DELAY = 5000;
    private static final int RETRY_LIMIT = 3;

    private final String url;
    private final DataPacket dataPacket;
    private final RestTemplate restTemplate;

    private int retryCount = 0;

    /**
     * Construct a new {@link Runnable} {@link DataPacketNotifier} with a URL and a {@link DataPacket}.
     * @param url the URL to notify
     * @param dataPacket the {@link DataPacket} to notify the URL of
     */
    public DataPacketNotifier(final String url, final DataPacket dataPacket, final RestTemplate restTemplate) {
        this.url = url;
        this.dataPacket = dataPacket;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        LOG.debug("Notifying endpoint {} of data packet {}", url, dataPacket);

        try {
            ResponseEntity<Object> response = restTemplate.postForEntity(url, dataPacket, Object.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                LOG.warn("Received HTTP code " + response.getStatusCode() + " from POST to " + url);
                retry();
            }
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
            retry();
        }

    }

    /**
     * Retry the request to the provided endpoint.
     */
    private void retry() {
        if (retryCount >= RETRY_LIMIT) {
            throw new RuntimeException("Failed to send data packet " + dataPacket + " to " + url + " after " + RETRY_LIMIT + " attempts.");
        }

        try {
            LOG.debug("Waiting {} before retry attempt {} for " + dataPacket + " to listener " + url, RETRY_DELAY, retryCount + 1);
            Thread.sleep(RETRY_DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        retryCount++;
        run();
    }
}
