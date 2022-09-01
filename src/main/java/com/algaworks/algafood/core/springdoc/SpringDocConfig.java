package com.algaworks.algafood.core.springdoc;

import java.util.Arrays;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
@SecurityScheme(name = "security_auth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
        )))
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
									.url("https://algaworks.com"))
							.tags(tags());
					addGlobalResponses(openApi);
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
									.url("https://algaworks.com"))
							.tags(tags());
					
					addGlobalResponses(openApi);				
				}).build();
	}
	
	private void addGlobalResponses(OpenAPI openApi) {
		openApi.getPaths()
	        .values()
	        .stream()
	        .flatMap(pathItem -> pathItem.readOperations().stream())
	        .forEach(operation -> {
	            ApiResponses responses = operation.getResponses();
	
	            ApiResponse apiResponseNaoEncontrado = new ApiResponse().description("Recurso não encontrado");
	            ApiResponse apiResponseErroInterno = new ApiResponse().description("Erro interno no servidor");
	            ApiResponse apiResponseSemRepresentacao = new ApiResponse()
	                    .description("Recurso não possui uma representação que poderia ser aceita pelo consumidor");
	
	            responses.addApiResponse("404", apiResponseNaoEncontrado);
	            responses.addApiResponse("406", apiResponseSemRepresentacao);
	            responses.addApiResponse("500", apiResponseErroInterno);
	        });	
	}
	
	private List<Tag> tags() {
		return Arrays.asList(new Tag().name("Cidades").description("Gerencia as cidades"));
	}
	
}