package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades")
public interface CidadeControllerV2OpenApi {

	@Operation(summary = "Lista as cidades")
	CollectionModel<CidadeModelV2> listar();

	@Operation(summary = "Busca uma cidade por Id", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da cidade inválido",
					content = @Content(schema = @Schema(ref = "Problema"))
			)
	})
	CidadeModelV2 buscar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId);

	@Operation(summary = "Cadastra uma cidade", description = "Cadastro de uma cidade, "
			+ "necessita de um estado e um nome válido")
	CidadeModelV2 adicionar(
			@RequestBody(description = "Representação de uma nova cidade", required = true) CidadeInputV2 cidadeInput);

	@Operation(summary = "Atualizado uma cidade por ID")
	CidadeModelV2 atualizar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId,
			@RequestBody(description = "Representação de uma cidade com dados atualizados", required = true) CidadeInputV2 cidadeInput);

	@Operation(summary = "Excluir uma cidade por ID")
	void remover(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId);

}
