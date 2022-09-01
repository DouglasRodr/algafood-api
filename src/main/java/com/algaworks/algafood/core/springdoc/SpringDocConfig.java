package com.algaworks.algafood.core.springdoc;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.model.input.EstadoIdInput;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
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
	
    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

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
							.tags(tags())
							.components(new Components()
			                        .schemas(gerarSchemasV1())
			                        .responses(gerarResponses())
			                );
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
							.tags(tags())
							.components(new Components()
									.schemas(gerarSchemasV2())
			                        .responses(gerarResponses())
			                );			
					addGlobalResponses(openApi);				
				}).build();
	}
	
	private void addGlobalResponses(OpenAPI openApi) {
		openApi.getPaths()
		 .values()
         .forEach(pathItem -> pathItem.readOperationsMap()
	         .forEach((httpMethod, operation) -> {
	             ApiResponses responses = operation.getResponses();
                 switch (httpMethod) {
	                 case GET:
	                     responses.addApiResponse("406", new ApiResponse().$ref(notAcceptableResponse));
	                     responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
	                     break;
	                 case POST:
	                     responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
	                     responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
	                     break;
	                 case PUT:
	                     responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
	                     responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
	                     break;
	                 case DELETE:
	                     responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
	                     break;
	                 default:
	                     responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
	                     break;
	             }
	         })
         );
	}
	
	private List<Tag> tags() {
		return Arrays.asList(new Tag().name("Cidades").description("Gerencia as cidades"));
	}
	
    private Map<String, Schema> gerarSchemasV1() {
        final Map<String, Schema> schemaMap = new HashMap<>();
        
        Map<String, Schema> cidadeModelSchema = ModelConverters.getInstance().read(CidadeModel.class);
        Map<String, Schema> cidadeInputSchema = ModelConverters.getInstance().read(CidadeInput.class);
        
        Map<String, Schema> estadoModelSchema = ModelConverters.getInstance().read(EstadoModel.class);
        Map<String, Schema> estadoIdInputSchema = ModelConverters.getInstance().read(EstadoIdInput.class);
        
        schemaMap.putAll(cidadeModelSchema);
        schemaMap.putAll(cidadeInputSchema);
        
        schemaMap.putAll(estadoModelSchema);
        schemaMap.putAll(estadoIdInputSchema);
        
        schemaMap.putAll(gerarSchemasProblema());

        return schemaMap;
    }
    
    private Map<String, Schema> gerarSchemasV2() {
        final Map<String, Schema> schemaMap = new HashMap<>();
        
        Map<String, Schema> cidadeModelSchema = ModelConverters.getInstance().read(CidadeModelV2.class);
        Map<String, Schema> cidadeInputSchema = ModelConverters.getInstance().read(CidadeInputV2.class);
        
        schemaMap.putAll(cidadeModelSchema);
        schemaMap.putAll(cidadeInputSchema);
        
        schemaMap.putAll(gerarSchemasProblema());

        return schemaMap;
    }
    
    private Map<String, Schema> gerarSchemasProblema() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }
    
    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem>().$ref("Problema")));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                .description("Recurso não encontrado")
                .content(content));

        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));

        return apiResponseMap;
    }
	
}