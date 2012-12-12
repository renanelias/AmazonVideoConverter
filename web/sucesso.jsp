<%-- 
    Document   : sucesso
    Created on : 11/12/2012, 22:45:07
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sucesso</title>
        <script src="http://code.jquery.com/jquery-1.8.3.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="player/jwplayer.js" ></script>
        
        <script type="text/javascript">
            function IniciarVideo(URL, Thumb) {
                jwplayer("play").setup({
                    file: URL,
                    image: Thumb
                });
                return false;
            }
        </script>
            
        <style type="text/css">
            ul.lista li a:link, ul.lista li a:visited {
                text-decoration: none;
                color: white;
            }
            ul.lista li a:hover {
                text-decoration: underline; 
                color: white;
            }
            ul.lista li a:active {
                text-decoration: none
            }            
            
            #divVideos {
                text-align:center;
            }
            #play {
                font-family: Verdana;
                font-size: larger;
            }            
            ul.lista {
                    list-style-type: none; 
                    padding: 0px 0px 0px 0px;
            }
            
            ul.lista li {
                    float: left;
                    padding-top: 5px;
                    padding-right: 5px;
                    list-style-type: none; 
            }
            div.divVideo{
                    position:relative;
                    width:200px;
                    background-color: black;
            }
            div.descricao{
                    position:absolute;
                    bottom:0px;
                    left:0px;
                    width:100%;
                    background-color:black;
                    font-family: 'Verdana';
                    font-size:12px;
                    color:white;
                    opacity:0.6;
                    filter:alpha(opacity=60); /* IE */
            }
            p.textoDescricao{
                    padding:10px;
                    margin:0px;
                    font-weight: bold;
            }
        </style>

    </head>
    <body>
        <h1>Seu vídeo foi enviado com sucesso!</h1>
        <div id="play">Clique em algum vídeo para tocar...</div>
        <div id="divVideos" align="center">
            <p align="left"><a href="#" onclick="return AtualizarLista(true)">Atualizar Lista</a> | <a href="index.jsp">Enviar Outro Vídeo</a></p>
            <ul id="listaVideos" class="lista">
                <li>Carregando Vídeos</li>
            </ul>
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
                            $("#listaVideos").append("<li><div class='divVideo'><img src='"+ (objThumbs != null ? objThumbs.url : null) + "' style='width:200px; height: 160px' /><div class='descricao'><p class='textoDescricao'><a href=\"#\" onclick=\"return IniciarVideo('" + objOutput.url + "', '" + (objThumbs != null ? objThumbs.url : null) + "')\">PLAY</a></p><div></div></li>");
                        } else if (objOutput.state == "waiting") {
                            $("#listaVideos").append("<li><div class='divVideo'><img src='"+ (objThumbs != null ? objThumbs.url : null) + "' style='width:200px; height: 160px' /><div class='descricao'><p class='textoDescricao'>Aguardando...</p><div></div></li>");
                        } else if (objOutput.state == "processing") {
                            $("#listaVideos").append("<li><div class='divVideo'><img src='"+ (objThumbs != null ? objThumbs.url : null) + "' style='width:200px; height: 160px' /><div class='descricao'><p class='textoDescricao'>Processando...</p><div></div></li>");
                        } else {
                            $("#listaVideos").append("<li><div class='divVideo'><img src='"+ (objThumbs != null ? objThumbs.url : null) + "' style='width:200px; height: 160px' /><div class='descricao'><p class='textoDescricao'>Vídeo Indisponível: " + objOutput.state + "</p><div></div></li>");
                        }                        
                        
                    });
                });
                
                return false;
            }
        </script>
    </body>
</html>
