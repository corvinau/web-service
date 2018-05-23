/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.beans.Cliente;
import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.beans.TipoAtendimento;
import com.ufpr.tads.web2.beans.Usuario;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ArtVin
 */
@WebServlet(name = "AtendimentoServlet", urlPatterns = {"/AtendimentoServlet"})
public class AtendimentoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Client client = ClientBuilder.newClient();
            Response resp = null;
            Atendimento atendimento = null;
            List<Atendimento> lista = null;
            String action = (String) request.getAttribute("redirecionar");

            if(action == null){
                action = (String) request.getParameter("action");
            }
            RequestDispatcher rd = null;
            if(action != null){
                switch (action){
                    case "new":
                        atendimento = getPostData(request);
                        resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/atendimentos").
                                path("/"+ atendimento.getDscAtendimento() ).
                                path("/"+ atendimento.getProdutoAtendimento().getIdProduto() ).
                                path("/"+ atendimento.getTipoAtendimento().getIdTipoAtendimento()).
                                path("/"+ atendimento.getClienteAtendimento().getIdCliente()).
                                path("/"+ atendimento.getResAtendimento()).
                                request(MediaType.APPLICATION_JSON).post(Entity.json(atendimento),Response.class);

                        System.out.print(resp.getStatus());
                        request.setAttribute("redirecionar", "listaAtendimento");
                        rd = request.getRequestDispatcher("AtendimentoServlet");
                        break;
                    case "show":
                        resp = client.
                                target("http://localhost:41786/exerciciosWeb2-local/webresources/atendimentos").
                                path("/" + Integer.parseInt(request.getParameter("id"))).
                                request(MediaType.APPLICATION_JSON).get();

                        atendimento = resp.readEntity(new GenericType<Atendimento>() {});

                        request.setAttribute("atendimento", atendimento);
                        rd = request.getRequestDispatcher("atendimentoDetalhes.jsp");

                        
                        
                        break;
                    case "update" :
                        Atendimento atend = new Atendimento();
                        atend.setIdAtendimento(Integer.parseInt(request.getParameter("id")));
                        resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/atendimentos").
                                path("/"+ Integer.parseInt(request.getParameter("id"))).
                                request(MediaType.APPLICATION_JSON).put(Entity.json(atend), Response.class);

                        System.out.print(resp.getStatus());
                        request.setAttribute("redirecionar", "listaAtendimento");
                        rd = request.getRequestDispatcher("AtendimentoServlet");
                        break;
                    case "newAtendimento":
                        rd = request.getRequestDispatcher("atendimento.jsp");
                        request.setAttribute("clientes", getListaClientes());
                        request.setAttribute("tipos", getListaTipo());
                        request.setAttribute("produtos", getListaProduto());
                        break;
                    case "listaAtendimento":
                        resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/atendimentos").
                                request(MediaType.APPLICATION_JSON).get();

                        lista = resp.readEntity(new GenericType<List<Atendimento>>() {});

                        request.setAttribute("lista", lista);
                        rd = request.getRequestDispatcher("atendimentoListar.jsp");

                        break;
                    default :
                        resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/atendimentos").
                                request(MediaType.APPLICATION_JSON).get();

                        lista = resp.readEntity(new GenericType<List<Atendimento>>() {});
                        request.setAttribute("lista", lista);
                        rd = request.getRequestDispatcher("atendimentoListar.jsp");
                }
            }
            else{
                rd = request.getRequestDispatcher("index.jsp");
            }
            rd.forward(request, response);
            
    }
    
    private List<Cliente> getListaClientes(){
        Client client = ClientBuilder.newClient();        
        Response resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/atendimentos/getClientes").
        request(MediaType.APPLICATION_JSON).get();

        List<Cliente> lista = resp.readEntity(new GenericType<List<Cliente>>() {});
        
        return lista;
    }
    private List<TipoAtendimento> getListaTipo(){
        Client client = ClientBuilder.newClient();
        Response resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/atendimentos/getTipo").
        request(MediaType.APPLICATION_JSON).get();

        List<TipoAtendimento> lista = resp.readEntity(new GenericType<List<TipoAtendimento>>() {});
        
        return lista;
    }
    private List<Produto> getListaProduto(){
        
        Client client = ClientBuilder.newClient();
        Response resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/produtos").
        request(MediaType.APPLICATION_JSON).get();

        List<Produto> lista = resp.readEntity(new GenericType<List<Produto>>() {});
        
        return lista;
        
    }
    
    private Atendimento getPostData(HttpServletRequest request){
        Atendimento a = new Atendimento();
        
        Date data = new Date();
        a.setDtHrAtendimento(data);
        a.setDscAtendimento((String) request.getParameter("dsc"));
        String aux = (String)request.getParameter("resolvido");
        if(aux == null){
            a.setResAtendimento('N');
        }
        else{
            a.setResAtendimento('S');
        }
        Cliente c = new Cliente();
        c.setIdCliente(Integer.parseInt((String) request.getParameter("cliente")));
        a.setClienteAtendimento(c);
        
        TipoAtendimento tipo = new TipoAtendimento();
        tipo.setIdTipoAtendimento(Integer.parseInt((String) request.getParameter("tipoAtendimento")));
        a.setTipoAtendimento(tipo);
        
        Produto produto = new Produto();
        produto.setIdProduto(Integer.parseInt((String) request.getParameter("produto")));
        a.setProdutoAtendimento(produto);
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(0);
        a.setUsuarioAtendimento(usuario);
        
        
        
        return a;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
