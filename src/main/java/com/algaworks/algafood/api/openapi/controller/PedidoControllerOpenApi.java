package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiImplicitParams({
    	@ApiImplicitParam(name = "clienteId", value = "ID do cliente para filtro da pesquisa",
				example = "1", dataType = "int"),
    	@ApiImplicitParam(name = "restauranteId", value = "ID do restaurante para filtro da pesquisa",
				example = "1", dataType = "int"),
    	@ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora de criação inicial para filtro da pesquisa",
			example = "2019-10-30T00:00:00Z", dataType = "date-time"),
    	@ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora de criação final para filtro da pesquisa",
			example = "2019-11-01T10:00:00Z", dataType = "date-time"),
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Pesquisa os pedidos")
    PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);
    
    @ApiOperation("Registra um pedido")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    PedidoModel adicionar(
    		@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
    		PedidoInput pedidoInput);
    
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    PedidoModel buscar(
    		@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", 
    			required = true)
    		String codigoPedido);

}
