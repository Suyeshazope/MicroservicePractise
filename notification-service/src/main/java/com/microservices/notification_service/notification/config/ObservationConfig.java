package com.microservices.notification_service.notification.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

public class ObservationConfig {

    private final ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory ;

    public ObservationConfig(ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory) {
        this.concurrentKafkaListenerContainerFactory = concurrentKafkaListenerContainerFactory;
    }

    @PostConstruct
    public void setObservationForKafkaTemplate(){
        concurrentKafkaListenerContainerFactory.getContainerProperties().setObservationEnabled(true);
    }

    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry){
        return new ObservedAspect(observationRegistry) ;
    }
}
