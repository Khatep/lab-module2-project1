package org.kaspi.labmodule2project1.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${clients.kaspi-lab.delivery-service}")
    private String deliveryServiceBaseUrl;

    @Bean
    @Qualifier("deliveryWebClient")
    public WebClient deliveryWebClient() {
        return WebClient.builder()
                .baseUrl(deliveryServiceBaseUrl)
                .build();
    }
}
