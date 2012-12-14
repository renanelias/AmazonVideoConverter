
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author renan
 */
public class AwsUtil {
    // Define qual o Bucket a ser utilizado para salvar os vídeos
    public static final String NomeBucket = "renansbtech";
    
    // Envia uma inputstream para o servidor da amazon.
    public static void EnviarStream(String NomeArquivoExt, InputStream ObjInputArquivoUpload) 
            throws IOException, ServletException {
        
        try {
            // Cria o Objeto de conexão com o S3...
            AmazonS3 ObjAmazonS3 = new AmazonS3Client(new PropertiesCredentials(upload.class.getResourceAsStream("AwsCredenciais.properties")));

            // Cria uma lista de PartResponse. Vai ter uma de cada para cada parte do upload enviada.
            List<PartETag> ListaPartETags = new ArrayList<PartETag>();

            // Inicializa Objeto de requisição de upload com múltiplas partes
            InitiateMultipartUploadRequest ObjRequestInicial = new InitiateMultipartUploadRequest(NomeBucket, NomeArquivoExt);
            InitiateMultipartUploadResult ObjResponseInicial = ObjAmazonS3.initiateMultipartUpload(ObjRequestInicial);

            // Na documentação, para vídeos maiores que 10 mb, o buffer mínimo é 5 MB. Vamos manter 5 MB para todos os casos.
            int TamanhoBuffer = 5 * 1024 * 1024; // Buffer de 5 mb.
        
            int QuantidadeBytesLidos = 0;
            int ParteEnvio = 1;
            byte[] buffer = new byte[TamanhoBuffer];

            // Lê o Buffer...
            while ((QuantidadeBytesLidos = ObjInputArquivoUpload.read(buffer)) != -1) {

                // Faz um trim no Buffer para deixá-lo com o tamanho correto que foi lido.
                byte[] bufferTamanhoCorreto = new byte[QuantidadeBytesLidos];
                System.arraycopy(buffer, 0, bufferTamanhoCorreto, 0, QuantidadeBytesLidos);

                // Converte os dados lidos em InputStream para enviar ao servidor...
                InputStream ObjInputStreamEnviar = new ByteArrayInputStream(bufferTamanhoCorreto);

                // Cria um Request para enviar esta parte do arquivo...
                UploadPartRequest ObjUploadRequest = new UploadPartRequest();
                ObjUploadRequest.setBucketName(NomeBucket);
                ObjUploadRequest.setKey(NomeArquivoExt);
                ObjUploadRequest.setUploadId(ObjResponseInicial.getUploadId());
                ObjUploadRequest.setInputStream(ObjInputStreamEnviar);
                ObjUploadRequest.setPartNumber(ParteEnvio);
                ObjUploadRequest.setPartSize(QuantidadeBytesLidos);

                // Adiciona esta parte na lista e envia ao Amazon S3
                ListaPartETags.add(ObjAmazonS3.uploadPart(ObjUploadRequest).getPartETag());   

                ParteEnvio++;                                    
            }

            // Completa o Envio
            CompleteMultipartUploadRequest ObjRequisicaoCompletar = new CompleteMultipartUploadRequest(NomeBucket, NomeArquivoExt, ObjResponseInicial.getUploadId(), ListaPartETags);
            ObjAmazonS3.completeMultipartUpload(ObjRequisicaoCompletar);
        } catch (Exception ex) {
            throw new ServletException(ex.getMessage());
        }
    }
    
}
