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
            URL url = new URL("https://app.zencoder.com/api/v2/jobs.json?api_key=" + (new PropertiesCredentials(upload.class.getResourceAsStream("ZencoderCredenciais.properties"))).getAWSAccessKeyId() + "&per_page=5");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            System.out.println("Response code of the object is "+code);
            if (code==200)
            {
                InputStreamReader in = new InputStreamReader((InputStream) connection.getContent());
                BufferedReader buff = new BufferedReader(in);
                String conteudoLido = "";
                String linha;
                do {
                  linha = buff.readLine();
                  if (linha != null) {
                    conteudoLido += linha;
                  }
                } while (linha != null);
    
                out.print(conteudoLido);
            } 
            else {
                JSONObject ObjJSONErro = new JSONObject();
                ObjJSONErro.put("Erro", "Erro ao recuperar lista de arquivos.");
                out.print(ObjJSONErro.toString());
            } 
        }
            
        catch (Exception ex) {
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
