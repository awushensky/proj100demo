package com.goproject100.demo.service.logic;

import com.goproject100.demo.model.DataPacket;
import com.goproject100.demo.model.UserType;

import java.io.IOException;
import java.util.List;

/**
 * This class services business logic requests from elsewhere in the application.
 */
public interface DataPacketService {

    /**
     * Receive a new {@link DataPacket} and notify all consumers.
     * @param dataPacket the {@link DataPacket} received
     * @throws IOException if unable to serialize the {@link DataPacket} to a string for storage
     */
    void receiveNewDataPacket(DataPacket dataPacket) throws IOException;

    /**
     * Register a new listener in the database.
     * @param url the URL of the listener to be called
     * @param type the {@link UserType} this listener is listening for
     */
    void registerNewListener(String url, UserType type);

    /**
     * Retrieve the last N &lt;= 10 {@link DataPacket} objects of a particular {@link UserType}.
     * @param number the number of data packets to retrieve
     * @param type the {@link UserType} of the data packets to retrieve
     * @return the {@link List} of {@link DataPacket} objects
     * @throws IOException if an error occurs reading the {@link DataPacket} objects from the database
     */
    List<DataPacket> getRecentDataPackets(int number, UserType type) throws IOException;
}
