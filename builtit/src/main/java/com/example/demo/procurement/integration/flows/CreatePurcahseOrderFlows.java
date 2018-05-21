package com.example.demo.procurement.integration.flows;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageHeaders;

@Configuration
class CreatePurcahseOrderFlows {

//@Todo for project we will use this, right now we are going to REST TEMPLATE 2
    @Bean
    IntegrationFlow rentItFlow() {
        return IntegrationFlows.from("req-po-channel")
                .enrichHeaders(e->e.header(MessageHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .handle(Http.outboundGateway("http://localhost:8090/api/sales/orders")
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(String.class)
                )
                .handle("createPurchaseOrderCustomTransformer", "fromHALForms")
                .channel("rep-po-channel")
                .get();
    }
}
