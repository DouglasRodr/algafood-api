package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
@Schema(name = "CidadeModel")
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

	@Schema(example = "1")
	private Long idCidade;
	
	@Schema(example = "Uberlândia")
	private String nomeCidade;
	
	@Schema(example = "1")
	private Long idEstado;
	
	@Schema(example = "Minas Gerais")
	private String nomeEstado;
	
}