
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.util.json.JSONObject;
import javax.servlet.ServletException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author renan
 */
public class ZencoderUtil {
    
    // Solicita a conversão de um vídeo enviado para o servidor da Amazon.
    public static void SolicitarConversao(String NomeArquivo, String NomeArquivoExt) throws ServletException  {
        HttpClient ObjHTTPClient = new DefaultHttpClient();
        try {
            // Cria o JSON e envia para o Zencoder...
            JSONObject ObjJSONTotal = new JSONObject();
            JSONObject ObjJSONOutput = new JSONObject();
            JSONObject ObjJSONThumb = new JSONObject();

            // Criando parâmetros para geração da Thumbnail
            ObjJSONThumb.put("width", 300);
            ObjJSONThumb.put("height", 160);
            ObjJSONThumb.put("number", 1);
            ObjJSONThumb.put("base_url", "s3://" + AwsUtil.NomeBucket);
            ObjJSONThumb.put("filename", NomeArquivo + "_thumb");
            ObjJSONThumb.put("public", 1);

            // Criando parâmetros de saída do vídeo
            ObjJSONOutput.put("url", "s3://" + AwsUtil.NomeBucket + "/" + NomeArquivo + "_saida.mp4");
            ObjJSONOutput.put("base_url", "s3://" + AwsUtil.NomeBucket);
            ObjJSONOutput.put("filename", NomeArquivo + "_saida.mp4");
            ObjJSONOutput.put("format", "mp4");
            ObjJSONOutput.put("video_codec", "h264");
            ObjJSONOutput.put("audio_codec", "aac");
            ObjJSONOutput.put("public", 1);                                
            ObjJSONOutput.put("thumbnails", ObjJSONThumb);

            // Juntando todas as partes do JSON
            ObjJSONTotal.put("test", true);
            ObjJSONTotal.put("input", "s3://" + AwsUtil.NomeBucket + "/" + NomeArquivoExt);
            ObjJSONTotal.put("output", ObjJSONOutput);

            // Enviando para o Zencoder
            // Criei um arquivo de Propriedades para o ZEncoder para não dar commit na chave....
            StringEntity ObjParamJSON = new StringEntity(ObjJSONTotal.toString());
            HttpPost ObjRequestZE = new HttpPost("https://app.zencoder.com/api/v2/jobs");
            ObjRequestZE.addHeader("Content-Type", "application/json");
            ObjRequestZE.addHeader("Zencoder-Api-Key", (new PropertiesCredentials(upload.class.getResourceAsStream("ZencoderCredenciais.properties"))).getAWSAccessKeyId());
            ObjRequestZE.setEntity(ObjParamJSON);
            ObjHTTPClient.execute(ObjRequestZE);

        } catch (Exception ex) {
            throw new ServletException(ex.getMessage());
        } finally {
            ObjHTTPClient.getConnectionManager().shutdown();
        }
    }
}
