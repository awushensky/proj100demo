package com.goproject100.demo.api.config;

import com.goproject100.demo.service.config.ServiceContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * This spring context is global to all servlets and filters.
 */
@Configuration
@Import({ ServiceContext.class })
public class GlobalContext {
}
