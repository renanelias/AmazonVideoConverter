/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author renan
 */
public class upload extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Verifica se é um post de um formulário de upload
            if (ServletFileUpload.isMultipartContent(request)) {
                
                FileItemFactory ObjFactory = new DiskFileItemFactory();
                ServletFileUpload ObjUpload = new ServletFileUpload(ObjFactory);
                List<FileItem> ListaItens = ObjUpload.parseRequest(request);

                Iterator ObjIterator = ListaItens.iterator();
                while (ObjIterator.hasNext()) {
                    FileItem ObjItemUpload = (FileItem) ObjIterator.next();

                    if (!ObjItemUpload.isFormField()) {
                        // Verifica se o tipo do arquivo é vídeo...
                        if (!ObjItemUpload.getContentType().contains("video")) {
                            response.sendRedirect("erro.jsp?idErro=e4");
                        } else {
                            // Cria nome do arquivo (key) para ser enviado ao servidor
                            String NomeArquivo = ObjItemUpload.getName().substring(0,ObjItemUpload.getName().lastIndexOf("."));
                            String ExtensaoArquivo = ObjItemUpload.getName().substring(ObjItemUpload.getName().lastIndexOf(".") + 1,ObjItemUpload.getName().length()); 

                            // Adiciona UUID random para não permitir que existam arquivos repetidos no S3
                            NomeArquivo += UUID.randomUUID().toString();

                            // Permite apenas nomes com letras e números
                            NomeArquivo = NomeArquivo.replaceAll("[^a-zA-Z0-9]+","");

                            // Adiciona a extensão no nome do arquivo
                            String NomeArquivoExt = NomeArquivo + "." + ExtensaoArquivo;

                            // Envia o arquivo para o servidor da Amazon
                            AwsUtil.EnviarStream(NomeArquivoExt, ObjItemUpload.getInputStream());

                            // Converte o Arquivo Enviado                   
                            ZencoderUtil.SolicitarConversao(NomeArquivo, NomeArquivoExt);
                            
                            // Redireciona o usuário para página de sucesso
                            response.sendRedirect("sucesso.jsp");
                        }
                    }
                }
            }
        } catch(Exception ex) {
            // Redireciona o usuário para página de erro.
            response.sendRedirect("erro.jsp?msg=" + ex.getMessage());
        }
    }    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Efetua o Download e Upload de um vídeo para o servidor da Amazon";
    }// </editor-fold>
}