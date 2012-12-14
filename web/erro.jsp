<%-- 
    Document   : erro.jsp
    Created on : 11/12/2012, 22:30:58
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Erro inesperado! - Amazon Cloud Uploader - Por Renan Elias</title>
<meta name="description" content="Sistema web que faz o upload de vídeos para o servidor Amazon S3 e converte usando ZEncoder."/>
<meta name="keywords" content="amazon s3, cloud, video upload, video encoding, zencoder"/>

<link rel="stylesheet" type="text/css" href="style.css">

<!--[if IE]>
    <script type="text/javascript">
        document.createElement("article");
        document.createElement("nav");
        document.createElement("section");
        document.createElement("header");
        document.createElement("aside");
        document.createElement("figure");
        document.createElement("legend");
        document.createElement("footer");
    </script>
<![endif] -->

</head>

<body>
<div id="container">
	<header>
    	<h1>Amazon Cloud Uploader</h1>
        <h2>Amazon S3 + Zencoder</h2>
    </header>
    
    <nav>
    	<ul>
        	<li><a href="mailto:renan.elias@gmail.com?Subject=Contato" title="Entrar em contato" rel="me">Entrar em Contato</a></li>
            <li><a href="sucesso.jsp" title="Veja todos os vídeos">Visualizar arquivos enviados</a></li>
        </ul>
    </nav>
    
    <div id="content">
    	<article>
        	<h3>Ocorreu um erro!</h3>
                Ocorreu um erro em sua solicitação: <%
            String Mensagem = request.getParameter("msg");
            out.print(Mensagem == null ? "Erro desconhecido." : Mensagem);
        %>
        </article>
    </div>
    
    <footer>
        <nav>
            <ul>
                <li><a href="mailto:renan.elias@gmail.com?Subject=Contato" title="Entrar em contato" rel="me">Entrar em Contato</a></li>
                <li><a href="sucesso.jsp" title="Veja todos os vídeos">Visualizar arquivos enviados</a></li>
            </ul>
        </nav>
        <address>Amazon Cloud Uploader - <strong>Desenvolvido por Renan Elias</strong> - Dezembro/2012</address>
    </footer>
</div>

</body>
</html>
