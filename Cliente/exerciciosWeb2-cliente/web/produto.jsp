<%-- 
    Document   : atendimentoListar
    Created on : 09/05/2018, 23:42:57
    Author     : ArtVin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.ufpr.tads.web2.beans.Cliente"%>
<%@page import="java.util.List"%>
<%@page errorPage="erro.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.css"/>
        <link rel="stylesheet" href="css/main.css"/>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <title>Listar Produtos</title>
    </head>
    <body>

        <div class="container" id="info-table">
            <table class="table table-hover">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">Produto</th>
                        <th scope="col">Detalhes</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${lista}" var="produto" >
                        <tr>
                            <td>${produto.nomeProduto}</td>
                            <td>
                                <a href="ProdutoServlet?action=show&id=${produto.idProduto}">
                                    <i class="material-icons">visibility</i> 
                                </a>
                                <a href="ProdutoServlet?action=formUpdate&id=${produto.idProduto}">
                                    <i class="material-icons">edit</i> 
                                </a>
                                <a href="ProdutoServlet?action=remove&id=${produto.idProduto}">
                                    <i class="material-icons">delete</i> 
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
                <a class="btn btn-secondary" id="cancel" href="index.jsp">Voltar</a>
        </div>
    </body>
</html>