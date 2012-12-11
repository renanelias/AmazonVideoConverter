<%-- 
    Document   : index
    Created on : 10/12/2012, 23:39:37
    Author     : renan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página JSP</title>
    </head>
    <body>
        <h1>Upload de Vídeo:</h1>
        <FORM ENCTYPE="multipart/form-data" ACTION="upload" METHOD="POST">
            <center>
                <INPUT NAME="arquivoUpload" TYPE="file">&nbsp;<INPUT TYPE="submit" VALUE="Enviar" >
            </center>
        </FORM>
    </body>
</html>
