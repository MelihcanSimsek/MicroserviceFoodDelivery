package com.simsek.food.delivery.microservice.user.config;

import com.simsek.food.delivery.microservice.user.client.PaymentClient;
import com.simsek.food.delivery.microservice.user.client.RoleClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${role.service.url}")
    private String roleClientUrl;

    @Value("${payment.service.url}")
    private String paymentClientUrl;

    private <T> T createClient(Class<T> clientClass, String baseUrl) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(clientClass);
    }

    @Bean
    public RoleClient roleClient() {
        return createClient(RoleClient.class, roleClientUrl);
    }

    @Bean
    public PaymentClient paymentClient() {
        return createClient(PaymentClient.class, paymentClientUrl);
    }
}
