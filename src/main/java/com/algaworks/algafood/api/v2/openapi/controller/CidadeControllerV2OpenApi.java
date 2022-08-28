package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades")
public interface CidadeControllerV2OpenApi {

	CollectionModel<CidadeModelV2> listar();

	CidadeModelV2 buscar(Long cidadeId);

	CidadeModelV2 adicionar(CidadeInputV2 cidadeInput);

	CidadeModelV2 atualizar(Long cidadeId, CidadeInputV2 cidadeInput);

	void remover(Long cidadeId);

}
