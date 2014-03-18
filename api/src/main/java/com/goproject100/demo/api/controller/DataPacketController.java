package com.goproject100.demo.api.controller;

import com.goproject100.demo.model.DataPacket;
import com.goproject100.demo.model.DataPacketResponse;
import com.goproject100.demo.model.UserType;
import com.goproject100.demo.service.logic.DataPacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This {@link Controller} provides very basic implementations.
 */
@Controller
public class DataPacketController {

    private static final Logger LOG = LoggerFactory.getLogger(DataPacketController.class);

    @Autowired
    private DataPacketService dataPacketService;

    /**
     * This endpoint handles new {@link DataPacket} objects being posted to the application.
     * @param dataPacket the {@link DataPacket} provided
     * @return a {@link DataPacketResponse} indicating the result of this add
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public @ResponseBody DataPacketResponse postNewData(final @RequestBody DataPacket dataPacket) {
        LOG.debug("POST submitting new data packet {}", dataPacket);

        try {
            dataPacketService.receiveNewDataPacket(dataPacket);
        } catch (Exception e) {
            LOG.error("Error receiving new data packet " + dataPacket + ": " + e.getMessage(), e);
            return DataPacketResponse.newFailureResponse("Unable to post new data: " + e.getClass().getSimpleName());
        }

        return DataPacketResponse.newSuccessResponse();
    }

    /**
     * This endpoint handles retrieval requests for the last 1 to 10 {@link DataPacket} requests.
     * @param number the number of recent {@link DataPacket} objects to retrieve
     * @param type the {@link UserType} of the {@link DataPacket} to retrieve
     * @return a {@link List} of the {@link DataPacket} objects
     */
    @RequestMapping(value = "/packets/{number}/type/{type}", method = RequestMethod.GET)
    public @ResponseBody DataPacketResponse getRecentPackets(final @PathVariable("number") int number,
                                                           final @PathVariable("type") UserType type) {
        LOG.debug("GET request for the last {} data packets for type {}", number, type);

        final List<DataPacket> response;
        try {
            response = dataPacketService.getRecentDataPackets(number, type);
        } catch (Exception e) {
            LOG.error("Unable to get last " + number + " data packets for type " + type + ": " + e.getMessage(), e);
            return DataPacketResponse.newFailureResponse("Unable to retrieve recent packets: " + e.getClass().getSimpleName());
        }

        return DataPacketResponse.newSuccessResponse(response);
    }

    /**
     * This endpoint handles registering a new URL listener for a given {@link UserType}.
     * @param url the URL to call on data receipt
     * @param type the {@link UserType} of {@link DataPacket} for which to call the provided URL.
     * @return a {@link DataPacketResponse} indicating the success or failure of the registration
     */
    @RequestMapping(value = "/registerEndpoint/{type}", method = RequestMethod.POST)
    public @ResponseBody DataPacketResponse registerNewEndpoint(final @RequestBody String url,
                                                                final @PathVariable("type") UserType type) {
        LOG.debug("POST to register URL \"{}\" for type {}", url, type);

        try {
            dataPacketService.registerNewListener(url, type);
        } catch (Exception e) {
            LOG.error("Unable to register listener with URL '" + url + "' for type " + type + ": " + e.getMessage(), e);
            return DataPacketResponse.newFailureResponse("Unable to register new endpoint: " + e.getClass().getSimpleName());
        }

        return DataPacketResponse.newSuccessResponse();
    }
}
