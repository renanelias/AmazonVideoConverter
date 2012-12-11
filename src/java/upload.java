/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
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

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                FileItemFactory ObjFactory = new DiskFileItemFactory();
                ServletFileUpload ObjUpload = new ServletFileUpload(ObjFactory);
                List<FileItem> ListaItens = ObjUpload.parseRequest(request);

                Iterator ObjIterator = ListaItens.iterator();
                while (ObjIterator.hasNext()) {
                    FileItem ObjItemUpload = (FileItem) ObjIterator.next();

                    if (!ObjItemUpload.isFormField()) {
                        // Cria nome do arquivo (key) para ser enviado ao servidor
                        String NomeArquivo = ObjItemUpload.getName().substring(0,ObjItemUpload.getName().lastIndexOf("."));
                        String ExtensaoArquivo = ObjItemUpload.getName().substring(ObjItemUpload.getName().lastIndexOf(".") + 1,ObjItemUpload.getName().length()); 
  
                        NomeArquivo += UUID.randomUUID().toString() + "." + ExtensaoArquivo;

                        // Inicia os objetos de conexão da Amazon S3 com as credenciais
                        AmazonS3 ObjAmazonS3 = new AmazonS3Client(new PropertiesCredentials(upload.class.getResourceAsStream("AwsCredenciais.properties")));
                            
                        // Envia para o servidor da Amazon
                        ObjAmazonS3.putObject("renansbtech", NomeArquivo, ObjItemUpload.getInputStream(), null);

                        // Converte o Arquivo
                        
                        
                        // Redireciona o usuário para a página informando do Upload.
                        response.sendRedirect("index.jsp");
                    }
                }
            } catch (FileUploadException ex) {
                throw new ServletException(ex.getMessage(), ex.getCause());
            }           
            
        } else {
            throw new ServletException("O formulário não é um upload.");
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





        
        
        
        /*
        
        
        
        
        
        
        String TipoRequisicao = request.getContentType();
        if ((TipoRequisicao != null) && (TipoRequisicao.indexOf("multipart/form-data") >= 0)) {
                DataInputStream ObjInputStream = new DataInputStream(request.getInputStream());

                int TamanhoUpload = request.getContentLength();
                byte BufferArquivo[] = new byte[TamanhoUpload];
                int TamanhoLido = 0;

                while (TamanhoLido < TamanhoUpload) {
                    TamanhoLido += ObjInputStream.read(BufferArquivo, TamanhoLido, TamanhoUpload);
                }
                
                InputStream ObjInputStreamEnviar = new ByteArrayInputStream(BufferArquivo);
                String NomeVideo = UUID.randomUUID().toString();
                
                System.out.println(upload.class.getResourceAsStream("AwsCredenciais.properties"));
                
                try {
                    AmazonS3 ObjAmazonS3 = new AmazonS3Client(new PropertiesCredentials(upload.class.getResourceAsStream("AwsCredenciais.properties")));

                    PutObjectRequest PutObjectRequestCallBack = new PutObjectRequest("renansbtech", NomeVideo, ObjInputStreamEnviar, null);
                    PutObjectRequestCallBack.setProgressListener(new ProgressListener() {

                        @Override
                        public void progressChanged(ProgressEvent progressEvent) {
                            if (progressEvent.getEventCode() == ProgressEvent.COMPLETED_EVENT_CODE) {
                                // Chamar Rotina para conversão dos dados...
                                
                            }
                        }
                    });
                    ObjAmazonS3.putObject(PutObjectRequestCallBack);
                    response.sendRedirect("index.jsp");
                } catch (Exception e) {
                    System.out.println("Erro"+ e.getMessage());
                }
                
                
                /*
                FileOutputStream ObjOutputStream = new FileOutputStream(CaminhoSalvarEnviado);
                ObjOutputStream.write(BufferArquivo);
                ObjOutputStream.flush();
                ObjOutputStream.close();
        * /


        } else {
            throw new ServletException("Formulário de entrada inválido");
        }
        * */