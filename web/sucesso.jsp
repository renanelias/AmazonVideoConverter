<%-- 
    Document   : sucesso
    Created on : 11/12/2012, 22:45:07
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de Vídeos - Amazon Cloud Uploader - Por Renan Elias</title>
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
<script src="player/jwplayer.js" type="text/javascript"></script>
<script type="text/javascript">
	function IniciarVideo(URL, Thumb) {
		jwplayer("play").setup({
			file: URL,
			image: Thumb,
                        "width" : "100%"
		});
		return false;
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
            <li><a href="#" onClick="return AtualizarLista(true)" title="Atualizar lista de vídeos">Atualizar lista</a></li>
                <li><a href="index.jsp" title="Envie outro vídeo para a lista">Enviar outro vídeo</a></li>
        </ul>
    </nav>
    
    <div id="content">
    	<article>
            <header>
        	<div id="play"><p style="color:white">Selecione um vídeo na lista abaixo para reproduzir no navegador:</p></div>
            </header>
                <div align="center">
                    <div id="listaVideos" align="center">
                        Carregando Vídeos
                    </div>
                </div>  
            </div>
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

<script type="text/javascript">
	$(function(){
		AtualizarLista(true);
		window.setInterval("AtualizarLista(false)", 5000);
	});
	
	function AtualizarLista(Mensagem) {
		if (Mensagem) {
			$("#listaVideos").empty();
			$("#listaVideos").append("Atualizando Lista... Aguarde!");
		}
		
		$.getJSON("videos", function(data){
			$("#listaVideos").empty();
			
			$.each(data, function(i, item){
				var objOutput = item["job"].output_media_files[0];
				var objThumbs;
				
				try {
					objThumbs = item["job"].thumbnails[0];
				} catch (e) { 
					objThumbs = null;
				}
								
				if (objOutput.state == "finished") {
					$("#listaVideos").append("<table width='500' border='0' align='center'><tr><td width='200' height='160' align='center'><img src='"+ (objThumbs != null ? objThumbs.url : "player/mini.png") + "' style='width:200px; height: 160px' /></td><td width='226' align='center'><a href=\"#\" onclick=\"return IniciarVideo('" + objOutput.url + "', '" + (objThumbs != null ? objThumbs.url : null) + "')\">PLAY</a></td></tr><tr><td colspan='2' align='center'><hr size='1' /></td></tr></table>");
				} else if (objOutput.state == "waiting" || objOutput.state == "ready") {
					$("#listaVideos").append("<table width='500' border='0' align='center'><tr><td width='200' height='160' align='center'><img src='"+ (objThumbs != null ? objThumbs.url : "player/mini.png") + "' style='width:200px; height: 160px' /></td><td width='226' align='center'>Aguardando...</td></tr><tr><td colspan='2' align='center'><hr size='1' /></td></tr></table>");
				} else if (objOutput.state == "processing") {
					$("#listaVideos").append("<table width='500' border='0' align='center'><tr><td width='200' height='160' align='center'><img src='"+ (objThumbs != null ? objThumbs.url : "player/mini.png") + "' style='width:200px; height: 160px' /></td><td width='226' align='center'>Processando...</td></tr><tr><td colspan='2' align='center'><hr size='1' /></td></tr></table>");
				} else {
					$("#listaVideos").append("<table width='500' border='0' align='center'><tr><td width='200' height='160' align='center'><img src='"+ (objThumbs != null ? objThumbs.url : "player/mini.png") + "' style='width:200px; height: 160px' /></td><td width='226' align='center'>Vídeo Indisponível: " + objOutput.state + "</td></tr><tr><td colspan='2' align='center'><hr size='1' /></td></tr></table>");
				}                        
				
			});
		});
		
		return false;
	}
</script>

</body>
</html>

