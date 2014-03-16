package com.goproject100.demo.api.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * This {@link WebApplicationInitializer} handles the initialization of the spring contexts for this application.
 */
public class DemoApplicationInitializer implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET = "dispatcher";

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {

        //Create the global application spring context
        final AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(GlobalContext.class);
        servletContext.addListener(new ContextLoaderListener(applicationContext));

        //Create the web spring context
        final AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebContext.class);

        //Map the request path / to the web spring context
        final ServletRegistration.Dynamic registration = servletContext.addServlet(DISPATCHER_SERVLET, new DispatcherServlet(webContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
