/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.util.json.JSONObject;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
                        
                        NomeArquivo += UUID.randomUUID().toString();
                        String NomeArquivoExt = NomeArquivo + "." + ExtensaoArquivo;

                        // Inicia os objetos de conexão da Amazon S3 com as credenciais
                        AmazonS3 ObjAmazonS3 = new AmazonS3Client(new PropertiesCredentials(upload.class.getResourceAsStream("AwsCredenciais.properties")));
                            
                        // Envia para o servidor da Amazon
                        ObjAmazonS3.putObject("renansbtech", NomeArquivoExt, ObjItemUpload.getInputStream(), null);

                        // Converte o Arquivo                        
                        HttpClient ObjHTTPClient = new DefaultHttpClient();
                        try {
                            // Cria o JSON e envia para o Zencoder...
                            JSONObject ObjJSONTotal = new JSONObject();
                            JSONObject ObjJSONOutput = new JSONObject();
                            JSONObject ObjJSONThumb = new JSONObject();
                            
                            ObjJSONThumb.put("number", 1);
                            ObjJSONThumb.put("width", 300);
                            ObjJSONThumb.put("height", 160);
                            ObjJSONThumb.put("base_url", "s3://renansbtech");
                            ObjJSONThumb.put("filename", NomeArquivo + "_thumb.png");
                            
                            ObjJSONOutput.put("url", "s3://renansbtech/" + NomeArquivo + "_saida.mp4");
                            ObjJSONOutput.put("base_url", "s3://renansbtech");
                            ObjJSONOutput.put("filename", NomeArquivo + "_saida.mp4");
                            ObjJSONOutput.put("format", "mp4");
                            ObjJSONOutput.put("video_codec", "h264");
                            ObjJSONOutput.put("audio_codec", "aac");
                            ObjJSONOutput.put("public", 1);
                            
                            ObjJSONThumb.put("number", 1);
                            ObjJSONThumb.put("base_url", "s3://renansbtech");
                            ObjJSONThumb.put("filename", NomeArquivo + "_thumb");
                            ObjJSONThumb.put("public", 1);
                            
                            ObjJSONOutput.put("thumbnails", ObjJSONThumb);
                            
                            
                            //ObjJSONOutput.put("thumbnails", ObjJSONThumb);
                                                       
                            //ObjJSONThumb.put("public", 1);                            
                            
                            ObjJSONTotal.put("test", true);
                            ObjJSONTotal.put("input", "s3://renansbtech/" + NomeArquivoExt);
                            ObjJSONTotal.put("output", ObjJSONOutput);
                     
                            // Enviando para o Zencoder
                            // Criei um arquivo de Propriedades para o ZEncoder para não dar commit.
                            
                            StringEntity ObjParamJSON = new StringEntity(ObjJSONTotal.toString());
                            HttpPost ObjRequestZE = new HttpPost("https://app.zencoder.com/api/v2/jobs");
                            ObjRequestZE.addHeader("Content-Type", "application/json");
                            ObjRequestZE.addHeader("Zencoder-Api-Key", (new PropertiesCredentials(upload.class.getResourceAsStream("ZencoderCredenciais.properties"))).getAWSAccessKeyId());
                            ObjRequestZE.setEntity(ObjParamJSON);
                            ObjHTTPClient.execute(ObjRequestZE);

                        } catch (Exception ex) {
                            response.sendRedirect("erro.jsp?idErro=e3&msg="+ex.getMessage());
                        } finally {
                            ObjHTTPClient.getConnectionManager().shutdown();
                        }
                        
                        // Redireciona o usuário para a página informando do Upload.
                        response.sendRedirect("sucesso.jsp");
                    }
                }
            } catch (FileUploadException ex) {
                response.sendRedirect("erro.jsp?idErro=e2&msg="+ex.getMessage());
            }           
            
        } else {
            response.sendRedirect("erro.jsp?idErro=e1");
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