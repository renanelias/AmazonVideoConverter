/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.util.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author renan
 */
public class videos extends HttpServlet {

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
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            // Faz uma requisição ao servidor do Zencoder solicitando a lista dos 5 últimos JOBS
            URL ObjURL = new URL("https://app.zencoder.com/api/v2/jobs.json?api_key=" + (new PropertiesCredentials(upload.class.getResourceAsStream("ZencoderCredenciais.properties"))).getAWSAccessKeyId() + "&per_page=5");
            HttpURLConnection ObjURLConexao = (HttpURLConnection)ObjURL.openConnection();
            ObjURLConexao.setRequestMethod("GET");
            ObjURLConexao.connect();
            
            // deu certo a requisição
            if (ObjURLConexao.getResponseCode() == 200)
            {
                // Obtém o stream retornado pela requisição
                InputStreamReader ObjInputStream = new InputStreamReader((InputStream) ObjURLConexao.getContent());
                BufferedReader buff = new BufferedReader(ObjInputStream);
                String conteudoLido = "";
                String linha;
                
                // Lê linha a linha e armazena na variável
                do {
                  linha = buff.readLine();
                  if (linha != null) {
                    conteudoLido += linha;
                  }
                } while (linha != null);
    
                // Escreve o JSON recebido
                out.print(conteudoLido);
            } 
            else {
                // Não deu certo. Envia um JSON de erro.
                JSONObject ObjJSONErro = new JSONObject();
                ObjJSONErro.put("Erro", "Erro ao recuperar lista de arquivos.");
                out.print(ObjJSONErro.toString());
            } 
        }            
        catch (Exception ex) {
            // Não deu certo. Envia um JSON de erro.
            out.print("{\"Erro\": \"Erro ao recuprar lista de arquivos.\"}");
        }
        finally {            
            out.close();
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
        return "Short description";
    }// </editor-fold>
}
