package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades")
public interface CidadeControllerV2OpenApi {

	@Operation(summary = "Lista as cidades")
	CollectionModel<CidadeModelV2> listar();

	@Operation(summary = "Busca uma cidade por Id")
	CidadeModelV2 buscar(Long cidadeId);

	@Operation(summary = "Cadastra uma cidade", description = "Cadastro de uma cidade, "
			+ "necessita de um estado e um nome válido")
	CidadeModelV2 adicionar(CidadeInputV2 cidadeInput);

	@Operation(summary = "Atualizado uma cidade por ID")
	CidadeModelV2 atualizar(Long cidadeId, CidadeInputV2 cidadeInput);
	
	@Operation(summary = "Excluir uma cidade por ID")
	void remover(Long cidadeId);

}
