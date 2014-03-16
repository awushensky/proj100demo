package com.goproject100.demo.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * This spring context is for the web requests.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.goproject100.demo.api.controller")
public class WebContext {
}
