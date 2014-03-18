package com.goproject100.demo.data.dao;

import com.goproject100.demo.data.config.DatabaseContext;
import com.goproject100.demo.model.UserType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * These tests check the validity of the {@link DataPacketDAO} mapper.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseContext.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DataPacketDAOTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataPacketDAO dataPacketDAO;

    private Connection connection;

    /**
     * Run each test with a new JDBC connection to the H2DB.
     */
    @Before
    public void setUp() throws Exception {
        connection = dataSource.getConnection();
    }

    /**
     * Ensure the jdbc connection to H2DB is closed after each test.
     */
    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    /**
     * Test retrieving a number of {@link com.goproject100.demo.model.DataPacket} objects.
     */
    @Test
    public void testGetDataPackets() throws Exception {
        connection.prepareStatement("INSERT INTO demo.data_packets (data_packet, type) VALUES ('{\"position\":{\"long\":\"100\",\"lat\":\"100\"},\"type\":\"CUSTOMER\"}', 1)").execute();
        connection.prepareStatement("INSERT INTO demo.data_packets (data_packet, type) VALUES ('{\"position\":{\"long\":\"100\",\"lat\":\"100\"},\"type\":\"CUSTOMER\"}', 1)").execute();
        connection.prepareStatement("INSERT INTO demo.data_packets (data_packet, type) VALUES ('{\"position\":{\"long\":\"101\",\"lat\":\"101\"},\"type\":\"CUSTOMER\"}', 1)").execute();
        connection.prepareStatement("INSERT INTO demo.data_packets (data_packet, type) VALUES ('{\"position\":{\"long\":\"102\",\"lat\":\"102\"},\"type\":\"CUSTOMER\"}', 1)").execute();
        connection.commit();

        final List<String> dataPackets = dataPacketDAO.getDataPackets(UserType.CUSTOMER, 3);

        Assert.assertEquals(3, dataPackets.size());
        Assert.assertEquals("{\"position\":{\"long\":\"102\",\"lat\":\"102\"},\"type\":\"CUSTOMER\"}", dataPackets.get(0));
        Assert.assertEquals("{\"position\":{\"long\":\"101\",\"lat\":\"101\"},\"type\":\"CUSTOMER\"}", dataPackets.get(1));
        Assert.assertEquals("{\"position\":{\"long\":\"100\",\"lat\":\"100\"},\"type\":\"CUSTOMER\"}", dataPackets.get(2));
    }

    /**
     * Test retrieving a list of listeners for a given {@link UserType}.
     */
    @Test
    public void testGetListeners() throws Exception {
        connection.prepareStatement("INSERT INTO demo.listeners (url, type) VALUES ('http://www.goproject100.com/1', 1)").execute();
        connection.prepareStatement("INSERT INTO demo.listeners (url, type) VALUES ('http://www.goproject100.com/2', 1)").execute();
        connection.prepareStatement("INSERT INTO demo.listeners (url, type) VALUES ('http://www.goproject100.com/3', 2)").execute();
        connection.prepareStatement("INSERT INTO demo.listeners (url, type) VALUES ('http://www.goproject100.com/4', 2)").execute();
        connection.prepareStatement("INSERT INTO demo.listeners (url, type) VALUES ('http://www.goproject100.com/1', 2)").execute();
        connection.commit();

        final List<String> customerConsumerUrls = dataPacketDAO.getListeners(UserType.CUSTOMER);
        final List<String> vehicleConsumerUrls = dataPacketDAO.getListeners(UserType.VEHICLE);
        final List<String> supportTechConsumerUrls = dataPacketDAO.getListeners(UserType.SUPPORT_TECHNICIANS);

        Assert.assertEquals(2, customerConsumerUrls.size());
        Assert.assertEquals(3, vehicleConsumerUrls.size());
        Assert.assertEquals(0, supportTechConsumerUrls.size());

        Assert.assertTrue(customerConsumerUrls.contains("http://www.goproject100.com/1"));
        Assert.assertTrue(customerConsumerUrls.contains("http://www.goproject100.com/2"));

        Assert.assertTrue(vehicleConsumerUrls.contains("http://www.goproject100.com/3"));
        Assert.assertTrue(vehicleConsumerUrls.contains("http://www.goproject100.com/4"));
        Assert.assertTrue(vehicleConsumerUrls.contains("http://www.goproject100.com/1"));
    }

    /**
     * Test registering a new listener for a {@link UserType}.
     */
    @Test
    public void testRegisterListener() throws Exception {

        dataPacketDAO.registerListener("http://www.goproject100.com/1", UserType.CUSTOMER);
        dataPacketDAO.registerListener("http://www.goproject100.com/2", UserType.CUSTOMER);
        dataPacketDAO.registerListener("http://www.goproject100.com/1", UserType.VEHICLE);
        dataPacketDAO.registerListener("http://www.goproject100.com/3", UserType.VEHICLE);
        dataPacketDAO.registerListener("http://www.goproject100.com/4", UserType.VEHICLE);
        dataPacketDAO.registerListener("http://www.goproject100.com/1", UserType.SUPPORT_TECHNICIANS);
        dataPacketDAO.registerListener("http://www.goproject100.com/3", UserType.SUPPORT_TECHNICIANS);
        dataPacketDAO.registerListener("http://www.goproject100.com/5", UserType.SUPPORT_TECHNICIANS);

        final List<String> customerConsumerUrls = dataPacketDAO.getListeners(UserType.CUSTOMER);
        final List<String> vehicleConsumerUrls = dataPacketDAO.getListeners(UserType.VEHICLE);
        final List<String> supportTechConsumerUrls = dataPacketDAO.getListeners(UserType.SUPPORT_TECHNICIANS);

        Assert.assertEquals(2, customerConsumerUrls.size());
        Assert.assertEquals(3, vehicleConsumerUrls.size());
        Assert.assertEquals(3, supportTechConsumerUrls.size());

        Assert.assertTrue(customerConsumerUrls.contains("http://www.goproject100.com/1"));
        Assert.assertTrue(customerConsumerUrls.contains("http://www.goproject100.com/2"));

        Assert.assertTrue(vehicleConsumerUrls.contains("http://www.goproject100.com/1"));
        Assert.assertTrue(vehicleConsumerUrls.contains("http://www.goproject100.com/3"));
        Assert.assertTrue(vehicleConsumerUrls.contains("http://www.goproject100.com/4"));

        Assert.assertTrue(supportTechConsumerUrls.contains("http://www.goproject100.com/1"));
        Assert.assertTrue(supportTechConsumerUrls.contains("http://www.goproject100.com/3"));
        Assert.assertTrue(supportTechConsumerUrls.contains("http://www.goproject100.com/5"));
    }

    /**
     * Test registering a new listener for a {@link UserType} when a matching registration already exists.
     */
    @Test
    public void testRegisterListenerUniqueViolation() throws Exception {

        dataPacketDAO.registerListener("http://www.goproject100.com/1", UserType.CUSTOMER);

        try {
            dataPacketDAO.registerListener("http://www.goproject100.com/1", UserType.CUSTOMER);
            Assert.fail();
        } catch (Exception e) {
            //pass
        }
    }

    /**
     * Test receiving a new {@link com.goproject100.demo.model.DataPacket}.
     */
    @Test
    public void testNewDataPacket() throws Exception {

        dataPacketDAO.newDataPacket("data packet data", UserType.CUSTOMER);
        dataPacketDAO.newDataPacket("data packet data", UserType.CUSTOMER);
        dataPacketDAO.newDataPacket("data packet data2", UserType.VEHICLE);

        List<String> customerDataPackets = dataPacketDAO.getDataPackets(UserType.CUSTOMER, 10);
        List<String> vehicleDataPackets = dataPacketDAO.getDataPackets(UserType.VEHICLE, 10);

        Assert.assertEquals(2, customerDataPackets.size());
        Assert.assertEquals(1, vehicleDataPackets.size());

        Assert.assertEquals("data packet data", customerDataPackets.get(0));
        Assert.assertEquals("data packet data", customerDataPackets.get(1));
        Assert.assertEquals("data packet data2", vehicleDataPackets.get(0));
    }
}
