package com.goproject100.demo.api.controller;

import com.goproject100.demo.model.DataPacket;
import com.goproject100.demo.model.DataPacketResponse;
import com.goproject100.demo.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This {@link Controller} provides very basic implementations.
 */
@Controller
public class DataPacketController {

    private static final Logger LOG = LoggerFactory.getLogger(DataPacketController.class);

    /**
     * This endpoint handles new {@link DataPacket} objects being posted to the application.
     * @param dataPacket the {@link DataPacket} provided
     * @return a {@link DataPacketResponse} indicating the result of this add
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public @ResponseBody
    DataPacketResponse postNewData(final @RequestBody DataPacket dataPacket) {
        LOG.debug("POST submitting new data packet {}", dataPacket);

        //TODO: this method
        return DataPacketResponse.newSuccessResponse();
    }

    /**
     * This endpoint handles retrieval requests for the last 1 to 10 {@link DataPacket} requests.
     * @param numberToRetrieve the number of recent {@link DataPacket} objects to retrieve
     * @return a {@link List} of the {@link DataPacket} objects
     */
    @RequestMapping(value = "/packets/{number}", method = RequestMethod.GET)
    public @ResponseBody List<DataPacket> getRecentPackets(final @PathVariable("number") int numberToRetrieve) {
        LOG.debug("GET request for the last {} data packets", numberToRetrieve);

        //TODO: this method
        return new ArrayList<DataPacket>();
    }

    /**
     * This endpoint handles registering a new {@link URL} listener for a given {@link UserType}.
     * @param url the {@link URL} to call on data receipt
     * @param type the {@link UserType} of {@link DataPacket} for which to call the provided {@link URL}.
     * @return a {@link DataPacketResponse} indicating the success or failure of the registration
     */
    @RequestMapping(value = "/registerEndpoint/{type}", method = RequestMethod.POST)
    public @ResponseBody
    DataPacketResponse registerNewEndpoint(final @RequestBody URL url,
                                                             final @PathVariable("type") UserType type) {
        LOG.debug("POST to register URL \"{}\" for type {}", url, type);

        //TODO: this method
        return DataPacketResponse.newSuccessResponse();
    }
}
