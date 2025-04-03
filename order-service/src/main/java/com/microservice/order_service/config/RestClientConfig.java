package com.microservice.order_service.config;

import com.microservice.order_service.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${inventory.url}")
    private String inventoryServiceUrl ;

    @Bean
    public InventoryClient inventoryClient(){
        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build() ;

        var restClientAdaptor = RestClientAdapter.create(restClient) ;
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdaptor).build() ;
        return  httpServiceProxyFactory.createClient(InventoryClient.class) ;
    }

//    public ClientHttpRequestFactory getClientRequestFactory(){
//        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.defaults()
//                .withConnectTimeout(Duration.ofSeconds(3))
//                .withReadTimeout(Duration.ofSeconds(3)) ;
//
//        return ClientHttpRequestFactories.get(clientHttpRequestFactorySettings) ;
//    }
}
