<%-- 
    Document   : atendimento
    Created on : 09/05/2018, 23:42:47
    Author     : ArtVin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page errorPage="erro.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.css"/>
        <link rel="stylesheet" href="css/main.css"/>
        <script src="js/moment.js"></script>
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/main.js"></script>
        <script src="js/ui/jquery-ui.js"></script>
        <title>Novo Produto</title>
    </head>
    <body>
        
        <nav class="navbar navbar-expand-sm bg-primary navbar-dark">
            <span class="navbar-brand">Novo Produto</span>
        </nav>
        
        <c:if test="${not empty msg}">
            <div class="container alert alert-danger" role="alert" id="alert">
                <span>${msg}</span>
            </div>
        </c:if>
        
        <div class="container" id="client-form">
              
                <div class="form-group row">
                    <label for="cpf" class="col-sm-2 col-form-label">Produto *</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control" name="produto" placeholder="Produto" required value="${produto.nomeProduto}"/>
                    </div>
                </div>
                 
                <a class="btn btn-secondary" id="cancel" href="ProdutoServlet">Voltar</a>
        </div>
        
    </body>
</html>
