package com.algaworks.algafood.core.springdoc;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfig {

	@Bean
	public GroupedOpenApi groupedOpenApiv1() {
		return GroupedOpenApi.builder()
				.group("AlgaFood API v1")
				.packagesToScan("com.algaworks.algafood.api")
				.pathsToMatch("/v1/**")
				.addOpenApiCustomiser(openApi -> {
					openApi.info(new Info().title("AlgaFood API v1")
							.version("v1")
							.description("REST API do AlgaFood")
							.license(new License().name("Apache 2.0").url("http://springdoc.com")))
							.externalDocs(new ExternalDocumentation()
									.description("AlgaWorks")
									.url("https://algaworks.com"));
				}).build();
	}

	@Bean
	public GroupedOpenApi groupedOpenApiv2() {
		return GroupedOpenApi.builder()
				.group("AlgaFood API v2")
				.packagesToScan("com.algaworks.algafood.api")
				.pathsToMatch("/v2/**")
				.addOpenApiCustomiser(openApi -> {
					openApi.info(new Info().title("AlgaFood API v2")
							.version("v2")
							.description("REST API do AlgaFood")
							.license(new License().name("Apache 2.0").url("http://springdoc.com")))
							.externalDocs(new ExternalDocumentation()
									.description("AlgaWorks")
									.url("https://algaworks.com"));
				}).build();
	}

}