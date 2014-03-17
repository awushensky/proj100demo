package com.goproject100.demo.service.config;

import com.goproject100.demo.data.config.DatabaseContext;
import com.goproject100.demo.service.logic.DataPacketService;
import com.goproject100.demo.service.logic.impl.DataPacketServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This {@link Configuration} sets up the business logic in the service module
 */
@Configuration
@Import({ DatabaseContext.class })
public class ServiceContext {

    @Bean
    public DataPacketService dataPacketService() {
        return new DataPacketServiceImpl();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }
}
