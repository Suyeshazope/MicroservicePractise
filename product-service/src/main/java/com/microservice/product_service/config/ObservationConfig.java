package com.microservice.product_service.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;

public class ObservationConfig {

    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry){
        return new ObservedAspect(observationRegistry) ;
    }
}
