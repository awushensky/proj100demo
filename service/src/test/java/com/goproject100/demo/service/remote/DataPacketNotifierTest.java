package com.goproject100.demo.service.remote;

import com.goproject100.demo.model.DataPacket;
import com.goproject100.demo.model.Position;
import com.goproject100.demo.model.UserType;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * These tests check the validity of the {@link DataPacketNotifier} logic.
 */
public class DataPacketNotifierTest {

    private static final String URL = "http://www.url.com";
    private static final DataPacket DATA_PACKET = new DataPacket(new Position("100", "100"), UserType.CUSTOMER);

    private DataPacketNotifier notifier;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = EasyMock.createStrictMock(RestTemplate.class);
        notifier = new DataPacketNotifier(URL, DATA_PACKET, restTemplate);
    }

    @Test
    public void testSuccess() {
        final ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);

        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andReturn(response);
        EasyMock.replay(restTemplate);

        notifier.run();

        EasyMock.verify(restTemplate);
    }

    @Test
    public void testRetry() {
        final ResponseEntity<Object> failureResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        final ResponseEntity<Object> successResponse = new ResponseEntity<>(HttpStatus.OK);

        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andReturn(failureResponse);
        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andThrow(new RuntimeException());
        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andReturn(failureResponse);
        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andReturn(successResponse);
        EasyMock.replay(restTemplate);

        notifier.run();

        EasyMock.verify(restTemplate);
    }

    @Test
    public void testFailure() {
        final ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andReturn(response);
        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andThrow(new RuntimeException());
        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andReturn(response);
        EasyMock.expect(restTemplate.postForEntity(URL, DATA_PACKET, Object.class)).andThrow(new RuntimeException());
        EasyMock.replay(restTemplate);

        try {
            notifier.run();
            Assert.fail();
        } catch (RuntimeException e) {
            //pass
        }

        EasyMock.verify(restTemplate);
    }
}
