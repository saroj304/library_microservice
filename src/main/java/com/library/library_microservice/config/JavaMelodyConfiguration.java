package com.library.library_microservice.config;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionListener;
import net.bull.javamelody.*;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@ConditionalOnWebApplication
public class JavaMelodyConfiguration {

    public static final String REGISTRATION_BEAN_NAME = "javamelody-registration";

    @Bean
    /**
     * Registers JavaMelody’s HTTP session listener once (skips if already registered)
     *SessionListener (which watches when user sessions start and end)
     */

    public ServletListenerRegistrationBean<HttpSessionListener> monitoringSessionListener(ServletContext ctx) {
        // Pass in a real SessionListener instance
        ServletListenerRegistrationBean<HttpSessionListener> bean =
                new ServletListenerRegistrationBean<>(new SessionListener());

        // Skip if already registered by web-fragment.xml
        if (ctx.getFilterRegistration("javamelody") != null) {
            bean.setEnabled(false);
        }
        return bean;
    }

    /**
     * // Registers JavaMelody’s web filter for all http requests, enabling request timing and excluding static assets
     * @return
     */
    @Bean(name = REGISTRATION_BEAN_NAME)
    public FilterRegistrationBean monitoringFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter((Filter) new MonitoringFilter());
        bean.addUrlPatterns("/*");
        bean.setName("javamelody");
        bean.addInitParameter("log", "true");
        bean.addInitParameter("url-exclude-pattern",
                "/webjars/.*|/css/.*|/images/.*|/fonts/.*|/js/.*|/monitoring");
        return bean;
    }

    @Bean
    public SpringDataSourceBeanPostProcessor monitoringDataSourceBeanPostProcessor(
            @Value("${javamelody.excluded-datasources:}") String excluded) {
        SpringDataSourceBeanPostProcessor p = new SpringDataSourceBeanPostProcessor();
        if (!excluded.isBlank()) {
            p.setExcludedDatasources(
                    new HashSet<>(Arrays.asList(excluded.split(",")))
            );
        }
        return p;
    }

    /**
     * Creates an advisor to apply JavaMelody monitoring
     * only to beans or methods explicitly annotated with @MonitoredWithSpring.
     */
//    @Bean
//    public MonitoringSpringAdvisor monitoringSpringAdvisor() {
//        // Pointcut that only picks up methods annotated with @MonitoredWithSpring
//        AnnotationMatchingPointcut pc =
//                new AnnotationMatchingPointcut(null, MonitoredWithSpring.class);
//        return new MonitoringSpringAdvisor(pc);
//    }

    /**
     * Monitor all beans annotated with @Service.
     * This will wrap every public method in every @Service bean.
     */
    @Bean
    public MonitoringSpringAdvisor serviceAdvisor() {
        AnnotationMatchingPointcut pc =
                new AnnotationMatchingPointcut(org.springframework.stereotype.Service.class, true);
        return new MonitoringSpringAdvisor(pc);
    }

    /**
     * Monitor all beans annotated with @Controller or @RestController.
     */
    @Bean
    public MonitoringSpringAdvisor controllerAdvisor() {
        // Pointcut matching @Controller-annotated classes
        AnnotationMatchingPointcut pcController =
                new AnnotationMatchingPointcut(org.springframework.stereotype.Controller.class, true);

        // Pointcut matching @RestController-annotated classes
        AnnotationMatchingPointcut pcRestController =
                new AnnotationMatchingPointcut(org.springframework.web.bind.annotation.RestController.class, true);

        // Combine them via a ComposablePointcut instance
        ComposablePointcut combined = new ComposablePointcut(pcController)
                .union(pcRestController);

        return new MonitoringSpringAdvisor(combined);
    }
}
