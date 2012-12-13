<%-- 
    Document   : sucesso
    Created on : 11/12/2012, 22:45:07
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Amazon Cloud Uploader - Vídeo Enviado com Sucesso!</title>

<style type="text/css">
	* {
		font-family: Verdana, Geneva, sans-serif;
		font-size: 10pt;
	}
	
	body {
		margin: 0px 0px 0px 0px;	
	}
	
	#head {
		width: 100%;
		height: 100px;	
		background-color: #09F;
		border-bottom: 2px solid #33F;
		font-size: 30px;
		padding: 10px 10px 10px 10px;
	}
	
	#body {
		padding: 10px 10px 10px 10px;
	}
	
	#footer {
		padding-top: 50px;
		font-size: 8pt;	
		text-align: center;
	}

</style>

<script src="http://code.jquery.com/jquery-1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="player/jwplayer.js" ></script>

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
<div id="head">
	Amazon Cloud Uploader<br />
    <p>Amazon S3 + Zencoder</p>
</div>
<div id="body">
	<p align="center">Arquivo enviado com sucesso!</p>
	<p align="center">Selecione um vídeo na lista abaixo para reproduzir no navegador:</p>
    <br />
    
    <div id="play"></div>
    
    <div align="center">
        <p align="center"><a href="#" onclick="return AtualizarLista(true)">Atualizar Lista</a> | <a href="index.jsp">Enviar Outro Vídeo</a></p>
        <div id="listaVideos" align="center">
            Carregando Vídeos
        </div>
    </div>  
</div>
<div id="footer">
	Amazon Cloud Uploader<br />
    Desenvolvido por Renan Elias<br />
    Dezembro/2012
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
