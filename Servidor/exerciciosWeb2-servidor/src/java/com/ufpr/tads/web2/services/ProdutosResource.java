/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.services;

import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.facade.AtendimentoFacade;
import com.ufpr.tads.web2.facade.ProdutoFacade;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author ArtVin
 */
@Path("produtos")
public class ProdutosResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProdutosResource
     */
    public ProdutosResource() {
    }

    /**
     * Retrieves representation of an instance of com.ufpr.tads.web2.services.ProdutosResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProdutos() {
        List<Produto> produtos = AtendimentoFacade.getListaProduto();
            GenericEntity<List<Produto>> lista =
            new GenericEntity<List<Produto>>(produtos) {};
            return Response
                .ok()
                .entity(lista)
                .build();
        
        
        
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProdutos(@PathParam("id") String id) {
        Produto aux = ProdutoFacade.getProduto(Integer.parseInt(id));
        if( aux != null){
            GenericEntity<Produto> produto = new GenericEntity<Produto>(aux) {};
            return Response.ok().entity(produto).build();
        }
        return Response.serverError().build();
        
    }
    
    
    @POST
    @Path("/{nomeProduto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(@PathParam("/nomeProduto") String prod) {
        //TODO return proper representation object
        Produto p = new Produto();
        p.setNomeProduto(prod);
        
        if(ProdutoFacade.insertProduto(p)){
            return Response.ok().build();
        }
        
        return Response.serverError().build();
        
        
    }

    /**
     * PUT method for updating or creating an instance of ProdutosResource
     * @param content representation for the resource
     */
    @PUT
    @Path("/{id}/{nomeProduto}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(@PathParam("id") String id,
                        @PathParam("nomeProduto") String nome) {
        Produto p = new Produto();
        p.setIdProduto(Integer.parseInt(id));
        p.setNomeProduto(nome);
        
        if(ProdutoFacade.updateProduto(p)){
            return Response.ok().build();
        }
        
        return Response.serverError().build();
        
        
    }
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(@PathParam("id") String id) {
       
        if(ProdutoFacade.deleteProduto(Integer.parseInt(id))){
            return Response.ok().build();
        }
        
        return Response.serverError().build();
        
        
    }
}
