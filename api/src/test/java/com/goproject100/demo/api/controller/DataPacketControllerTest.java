package com.goproject100.demo.api.controller;

import com.goproject100.demo.api.config.MockWebContext;
import com.goproject100.demo.model.DataPacket;
import com.goproject100.demo.model.Position;
import com.goproject100.demo.model.UserType;
import com.goproject100.demo.service.logic.DataPacketService;
import org.codehaus.jackson.map.ObjectMapper;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * These tests check the validity of the {@link DataPacketController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MockWebContext.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DataPacketControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DataPacketService service;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mapper = new ObjectMapper();
    }

    /************************************
     * postNewData tests
     ************************************/

    /**
     * Test posting a new {@link DataPacket} to the server.
     */
    @Test
    public void testPostNewData() throws Exception {

        final DataPacket dataPacket = new DataPacket(new Position("100", "100"), UserType.CUSTOMER);

        service.receiveNewDataPacket(EasyMock.anyObject(DataPacket.class));
        EasyMock.expectLastCall();

        EasyMock.replay(service);

        mockMvc.perform(post("/data").content(mapper.writeValueAsString(dataPacket))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.responseBody").value((Object) null))
                .andExpect(jsonPath("$.exception").value((Object) null));

        EasyMock.verify(service);
    }

    /**
     * Test posting a new {@link DataPacket} to the server when an exception occurs.
     */
    @Test
    public void testPostNewDataWithException() throws Exception {

        final DataPacket dataPacket = new DataPacket(new Position("100", "100"), UserType.CUSTOMER);

        service.receiveNewDataPacket(EasyMock.anyObject(DataPacket.class));
        EasyMock.expectLastCall().andThrow(new RuntimeException());

        EasyMock.replay(service);

        mockMvc.perform(post("/data").content(mapper.writeValueAsString(dataPacket))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.responseBody").value((Object) null))
                .andExpect(jsonPath("$.exception").exists());

        EasyMock.verify(service);
    }

    /************************************
     * getRecentPackets tests
     ************************************/

    /**
     * Test retrieving recent packets from the server.
     */
    @Test
    public void testGetRecentPackets() throws Exception {

        final List<DataPacket> dataPackets = new ArrayList<DataPacket>(2) {{
            add(new DataPacket(new Position("100", "100"), UserType.CUSTOMER));
            add(new DataPacket(new Position("101", "101"), UserType.CUSTOMER));
        }};

        EasyMock.expect(service.getRecentDataPackets(5, UserType.CUSTOMER)).andReturn(dataPackets);

        EasyMock.replay(service);

        mockMvc.perform(get("/packets/5/type/CUSTOMER").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.responseBody").exists())
                .andExpect(jsonPath("$.exception").value((Object) null));

        EasyMock.verify(service);
    }

    /**
     * Test retrieving recent packets from the server when an exception occurs.
     */
    @Test
    public void testGetRecentPacketsWithException() throws Exception {

        EasyMock.expect(service.getRecentDataPackets(5, UserType.CUSTOMER)).andThrow(new RuntimeException());

        EasyMock.replay(service);

        mockMvc.perform(get("/packets/5/type/CUSTOMER").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.responseBody").value((Object) null))
                .andExpect(jsonPath("$.exception").exists());

        EasyMock.verify(service);
    }

    /************************************
     * registerNewEndpoint tests
     ************************************/

    /**
     * Test registering a new listener endpoint.
     */
    @Test
    public void testRegisterNewEndpoint() throws Exception {

        service.registerNewListener("http://www.goproject100.com/1", UserType.CUSTOMER);
        EasyMock.expectLastCall();

        EasyMock.replay(service);

        mockMvc.perform(post("/registerEndpoint/CUSTOMER").content("http://www.goproject100.com/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.responseBody").value((Object) null))
                .andExpect(jsonPath("$.exception").value((Object) null));

        EasyMock.verify(service);
    }

    /**
     * Test registering a new listener endpoint when an exception occurs.
     */
    @Test
    public void testRegisterNewEndpointWithException() throws Exception {

        service.registerNewListener("http://www.goproject100.com/1", UserType.CUSTOMER);
        EasyMock.expectLastCall().andThrow(new RuntimeException());

        EasyMock.replay(service);

        mockMvc.perform(post("/registerEndpoint/CUSTOMER").content("http://www.goproject100.com/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.responseBody").value((Object) null))
                .andExpect(jsonPath("$.exception").exists());

        EasyMock.verify(service);
    }
}
