<%-- 
    Document   : index
    Created on : 10/12/2012, 23:39:37
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Envie seu vídeo - Amazon Cloud Uploader - Por Renan Elias</title>
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

<script src="http://code.jquery.com/jquery-1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function ValidarVideo() {
		if(!/(\.flv|\.avi|\.mov|\.mpg|\.wmv|\.m4v|\.3gp|\.dv)$/i.test(document.getElementById("arquivoUpload").value)) {
			alert("O arquivo não parece ser um vídeo num formato válido");
			return false;
		} else {
                    document.getElementById("btnEnviar").value = "Enviando.... Aguarde!";
                    document.getElementById("btnEnviar").disabled = true;
			return true;
		}
	}
</script>
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
    	<fieldset><legend>Selecione abaixo o vídeo que deseja enviar:</legend>
        	<form enctype="multipart/form-data" action="upload" method="post" onSubmit="return ValidarVideo()">
            	<ul>
                	<li>
                    	<input name="arquivoUpload" id="arquivoUpload" type="file">
                        <label title="arquivoUpload">Formatos permitidos: .flv, .avi, .mov, .mpg, .wmv, .m4v, .3gp, .dv</label>
                    </li>
                	<li><input type="submit" value="Enviar" id="btnEnviar" class="button"></li>
                </ul>
            </form>
        </fieldset>
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
