package com.example.demo;

import com.example.demo.procurement.integration.gateways.RentalGateway;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class BuiltitApplication {
	@Configuration
	static class ObjectMapperCustomizer {
		@Autowired
		@Qualifier("_halObjectMapper")
		private ObjectMapper springHateoasObjectMapper;

		@Bean(name = "objectMapper")
		ObjectMapper objectMapper() {
			return springHateoasObjectMapper
					.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
					.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
					.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
					.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
					.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
					.registerModules(new JavaTimeModule());
		}
		@Bean
		public RestTemplate restTemplate() {
			RestTemplate _restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			messageConverters.add(new MappingJackson2HttpMessageConverter(springHateoasObjectMapper));
			_restTemplate.setMessageConverters(messageConverters);
			return _restTemplate;
		}
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BuiltitApplication.class, args);
		RentalGateway service = ctx.getBean(RentalGateway.class);

		System.out.println(
				service.findPlants("exc", LocalDate.now(), LocalDate.now().plusDays(2))
		);

	}
}
