/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.services;

import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.beans.Cliente;
import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.beans.TipoAtendimento;
import com.ufpr.tads.web2.exceptions.ErroBuscandoClienteException;
import com.ufpr.tads.web2.facade.AtendimentoFacade;
import com.ufpr.tads.web2.facade.ClientesFacade;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author ArtVin
 */
@Path("atendimentos")
public class AtendimentosResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AtendimentosResource
     */
    public AtendimentosResource() {
    }

    /**
     * Retrieves representation of an instance of com.ufpr.tads.web2.services.AtendimentosResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        
        
        try {
            List<Atendimento> atendimentos = AtendimentoFacade.getListaAtendimento();
            GenericEntity<List<Atendimento>> lista =
            new GenericEntity<List<Atendimento>>(atendimentos) {};
            return Response
                .ok()
                .entity(lista)
                .build();
        } 
        catch (ErroBuscandoClienteException ex) {
            
        }
        return null;
    }
   
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Atendimento getAtendimento(@PathParam("id") String id) {
        Atendimento aux = null;
        try{
            aux = AtendimentoFacade.getAtendimento(Integer.parseInt(id));
        }
        catch ( ErroBuscandoClienteException err){
            
        }
        return aux;
    }
    
    @POST
    @Path("/{dsc}/{idProduto}/{idTipo}/{idCliente}/{resAtendimento}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postAtendimento(@PathParam("dsc") String dsc,
                                      @PathParam("idProduto") String idProduto,
                                      @PathParam("idTipo") String idTipo,
                                      @PathParam("idCliente") String idCliente,
                                      @PathParam("resAtendimento") String resAtendimento) {
        Atendimento aux = new Atendimento();
        aux.setDtHrAtendimento(new Date());
        
        aux.setDscAtendimento(dsc);
        
        Produto prod = new Produto();
        prod.setIdProduto(Integer.parseInt(idProduto));
        aux.setProdutoAtendimento(prod);
        
        TipoAtendimento tipo = new TipoAtendimento();
        tipo.setIdTipoAtendimento(Integer.parseInt(idTipo));
        aux.setTipoAtendimento(tipo);
        
        Cliente cliente = new Cliente();
        cliente.setIdCliente(Integer.parseInt(idCliente));
        aux.setClienteAtendimento(cliente);
        
        aux.setResAtendimento(resAtendimento.charAt(0));
        
        
        
            if(AtendimentoFacade.insertAtendimento(aux)){
                return Response.ok().build();
            }
        
        return Response.serverError().build();
    }
    /**
     * PUT method for updating or creating an instance of AtendimentosResource
     * @param content representation for the resource
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putResolvido(@PathParam("id") String id ) {
        if(AtendimentoFacade.setResolvido(Integer.parseInt(id))){
            return Response.ok().build();
        }
        
        return Response.serverError().build();
        
    }
    
    
    @GET
    @Path("/getClientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientes() {
        
        
            List<Cliente> clientes = ClientesFacade.buscarTodos();
            GenericEntity<List<Cliente>> lista =
            new GenericEntity<List<Cliente>>(clientes) {};
            return Response
                .ok()
                .entity(lista)
                .build();
        
    }
    @GET
    @Path("/getTipo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaTipo() {
        
        
            List<TipoAtendimento> tipos = AtendimentoFacade.getListaTipoAtendimento();
            GenericEntity<List<TipoAtendimento>> lista =
            new GenericEntity<List<TipoAtendimento>>(tipos) {};
            return Response
                .ok()
                .entity(lista)
                .build();
        
    }

    
    
}
