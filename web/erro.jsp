<%-- 
    Document   : erro.jsp
    Created on : 11/12/2012, 22:30:58
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erro ao converter vídeo</title>
    </head>
    <body>
        <h1>Ocorreu um erro:</h1>
        <h3>Ocorreu um erro em sua solicitação: <%
            if (request.getParameter("idErro") != null) {
                %>Código de erro inválido.<%
            } else {
                String CodigoErro = request.getParameter("idErro").toString();
                String Mensagem = request.getParameter("msg");
                if (CodigoErro.equals("e1")) {
                    %>O Formulário enviado não é um arquivo.<%
                } else if (CodigoErro.equals("e2")) {
                    %>Ocorreu um erro ao receber o arquivo e salvar no servidor Amazon S3<% out.print(Mensagem == null ? "." : ": " + Mensagem);
                } else if (CodigoErro.equals("e3")) {
                    %>Ocorreu um erro ao solicitar a conversão do vídeo no site da Zencoder<% out.print(Mensagem == null ? "." : ": " + Mensagem);
                } else {
                    %>Código de erro desconhecido.<%
                }
            }
        %></h3>
    </body>
</html>
