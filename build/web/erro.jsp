<%-- 
    Document   : erro.jsp
    Created on : 11/12/2012, 22:30:58
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Amazon Cloud Uploader - Ocorreu um erro</title>

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


</head>

<body>
<div id="head">
	Amazon Cloud Uploader<br />
    <p>Amazon S3 + Zencoder</p>
</div>
<div id="body">
	<p align="left" style="color: red; font-size: 20pt">Ocorreu um erro!</p>
	<p align="left" style="font-size: 15pt">Ocorreu um erro em sua solicitação: <%
            if (request.getParameter("idErro") == null) {
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
                } else if (CodigoErro.equals("e4")) {
                    %>O Arquivo Enviado não é um vídeo<%
                } else {
                    %>Código de erro desconhecido.<%
                }
            }
        %></p>
</div>
<div id="footer">
	Amazon Cloud Uploader<br />
    Desenvolvido por Renan Elias<br />
    Dezembro/2012
</div>

</body>
</html>
