/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.dao.ProdutoDAO;

/**
 *
 * @author Tatiane
 */
public class ProdutoFacade {
    
    public static boolean insertProduto(Produto produto){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return produtoDAO.insertProduto(produto);
    }
    
    public static boolean updateProduto(Produto produto){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        return produtoDAO.updateProduto(produto);
        
    }
    
    public static boolean deleteProduto(int id){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        return produtoDAO.deleteProduto(id);
    }
    
    public static Produto getProduto(int id){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        return produtoDAO.getProduto(id);
    }
    
}
