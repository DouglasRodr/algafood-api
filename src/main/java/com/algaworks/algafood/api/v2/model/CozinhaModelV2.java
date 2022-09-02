package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
@Schema(name = "CozinhaModel")
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {

	@Schema(example = "1")
    private Long idCozinha;
    
	@Schema(example = "Brasileira")
    private String nomeCozinha;
    
}