package com.goproject100.demo.api.config;

import com.goproject100.demo.service.logic.DataPacketService;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * This spring context contains the mocks necessary to stand up the
 * {@link com.goproject100.demo.api.controller.DataPacketController} with no external dependencies.
 */
@Configuration
@Import({ WebContext.class })
public class MockWebContext {

    @Bean
    public DataPacketService dataPacketService() {
        return EasyMock.createStrictMock(DataPacketService.class);
    }
}
