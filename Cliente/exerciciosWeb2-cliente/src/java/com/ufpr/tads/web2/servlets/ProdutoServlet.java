/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import com.ufpr.tads.web2.beans.Produto;
import java.io.IOException;
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
@WebServlet(name = "ProdutoServlet", urlPatterns = {"/ProdutoServlet"})
public class ProdutoServlet extends HttpServlet {

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

            Produto p = null;
            List<Produto> lista = null;
            String action = (String) request.getAttribute("redirecionar");

            if(action == null){
                action = (String) request.getParameter("action");
            }
            RequestDispatcher rd = null;
            if(action != null){
                switch (action){
                    case "new":
                        insertProduto(request);
                        request.setAttribute("redirecionar", "listaProduto");
                        rd = request.getRequestDispatcher("ProdutoServlet");
                        break;
                    case "show":
                        p = getProduto(Integer.parseInt(request.getParameter("id")));
                        
                        request.setAttribute("produto", p);
                        rd = request.getRequestDispatcher("visualizarProduto.jsp");
                        break;
                    case "update" :
                        p = getProduto(Integer.parseInt(request.getParameter("id")));
                        
                        request.setAttribute("produto", p);
                        rd = request.getRequestDispatcher("formProduto.jsp");
                        break;
                    case "formUpdate" :
                        p = getProduto(Integer.parseInt(request.getParameter("id")));
                        
                        request.setAttribute("produto", p);
                        rd = request.getRequestDispatcher("formProduto.jsp");
                        break;
                    case "newProduto":
                        rd = request.getRequestDispatcher("formProduto.jsp");
                        break;
                    case "listaProduto":
                        request.setAttribute("lista", getListaProduto());
                        rd = request.getRequestDispatcher("produto.jsp");
                        break;
                    default :
                        request.setAttribute("lista", getListaProduto());
                        rd = request.getRequestDispatcher("atendimentoListar.jsp");
                }
            }
            else{
                rd = request.getRequestDispatcher("index.jsp");
            }
            rd.forward(request, response);
            
    }
    
    
    private List<Produto> getListaProduto(){
        
        Client client = ClientBuilder.newClient();
        Response resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/produtos").
        request(MediaType.APPLICATION_JSON).get();

        List<Produto> lista = resp.readEntity(new GenericType<List<Produto>>() {});
        
        return lista;
        
    }
    private Produto getProduto(int id){
        Client client = ClientBuilder.newClient();
        Response resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/produtos").
            path("/" + id).    
            request(MediaType.APPLICATION_JSON).get();

        Produto prod = resp.readEntity(new GenericType<Produto>() {});
        
        return prod;
        
    }
    private void insertProduto(HttpServletRequest request){
        Client client = ClientBuilder.newClient();
        Response resp = client.target("http://localhost:41786/exerciciosWeb2-local/webresources/produtos").
            path("/" + Integer.parseInt(request.getParameter("id"))).
            path("/"+ Integer.parseInt(request.getParameter("nome"))).
            request(MediaType.APPLICATION_JSON).post(Entity.json(request.getParameter("nome")),Response.class);

        Produto prod = resp.readEntity(new GenericType<Produto>() {});
        
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
