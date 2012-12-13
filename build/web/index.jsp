<%-- 
    Document   : index
    Created on : 10/12/2012, 23:39:37
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Amazon Cloud Uploader - Selecione um vídeo para enviar</title>

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
	
	#divVideo {
		width: 400px;
		border: 1px dotted #33F;	
	}
</style>

<script src="http://code.jquery.com/jquery-1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function ValidarVideo() {
		if(!/(\.flv|\.avi|\.mov|\.mpg|\.wmv|\.m4v|\.3gp|\.dv)$/i.test(document.getElementById("arquivoUpload").value)) {
			alert("O arquivo não parece ser um vídeo num formato válido");
			return false;
		} else {
			return true;
		}
	}
</script>
</head>

<body>
<div id="head">
	Amazon Cloud Uploader<br />
    <p>Amazon S3 + Zencoder</p>
</div>
<div id="body">
	<p align="center">Selecione abaixo o vídeo que deseja enviar:</p>
    <br />
    <div align="center">
    	<div id="divVideo">
            <FORM ENCTYPE="multipart/form-data" ACTION="upload" METHOD="POST" onsubmit="return ValidarVideo()">
                <center>
                    <INPUT NAME="arquivoUpload" ID="arquivoUpload" TYPE="file">&nbsp;<INPUT TYPE="submit" VALUE="Enviar" >
                </center>
            </FORM>
    	</div>
    </div>
    <br />
	<p align="center">Formatos permitidos: .flv, .avi, .mov, .mpg, .wmv, .m4v, .3gp, .dv</p>    
        <br />
        <p align="center"><a href="sucesso.jsp">Ver Arquivos Enviados</a></p>
        
</div>
<div id="footer">
	Amazon Cloud Uploader<br />
    Desenvolvido por Renan Elias<br />
    Dezembro/2012
</div>
</body>
</html>
