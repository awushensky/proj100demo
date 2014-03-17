package com.goproject100.demo.service.logic.impl;

import com.goproject100.demo.data.dao.DataPacketDAO;
import com.goproject100.demo.model.DataPacket;
import com.goproject100.demo.model.UserType;
import com.goproject100.demo.service.logic.DataPacketService;
import com.goproject100.demo.service.remote.DataPacketNotifier;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * This {@link DataPacketService} handles interfaces with the database and other web services.
 */
public class DataPacketServiceImpl implements DataPacketService {

    private static final Logger LOG = LoggerFactory.getLogger(DataPacketServiceImpl.class);

    private static final int MAX_RETRIEVAL_NUMBER = 10;

    @Autowired
    private DataPacketDAO dataPacketDAO;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ExecutorService executorService;

    @Override
    public void receiveNewDataPacket(final DataPacket dataPacket) throws IOException {
        final String dataPacketString = mapper.writeValueAsString(dataPacket);
        dataPacketDAO.newDataPacket(dataPacketString, dataPacket.getType());

        final List<String> consumerUrls = dataPacketDAO.getListeners(dataPacket.getType());
        LOG.info("Notifying {} consumers of new data packet {}", consumerUrls.size(), dataPacket);

        for (String consumerUrl : consumerUrls) {
            executorService.execute(new DataPacketNotifier(consumerUrl, dataPacket));
        }
    }

    @Override
    public void registerNewListener(final String url, final UserType type) {
        dataPacketDAO.registerListener(url, type);
    }

    @Override
    public List<DataPacket> getRecentDataPackets(final int number, final UserType type) throws IOException {
        final List<String> dataPacketStrings = dataPacketDAO.getDataPackets(type, Math.max(number, MAX_RETRIEVAL_NUMBER));
        final List<DataPacket> dataPackets = new ArrayList<>(dataPacketStrings.size());

        LOG.debug("Retrieved {} DataPackets of type {} from the database", dataPacketStrings.size(), type);

        for (String dataPacketString : dataPacketStrings) {
            dataPackets.add(mapper.readValue(dataPacketString, DataPacket.class));
        }

        return dataPackets;
    }
}
