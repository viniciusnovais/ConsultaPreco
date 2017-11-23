package pdasolucoes.com.br.consultapreco.Services;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.StringTokenizer;

import pdasolucoes.com.br.consultapreco.Model.Usuario;

/**
 * Created by PDA on 22/11/2017.
 */

public class AutenticacaoService {

    private static final String METHOD_NAME = "GetAutenticacao";
    private static final String ASMX = "Autenticacao.asmx";

    public static Usuario GetAutenticacao(String login, String senha) {
        Usuario usuario = new Usuario();
        SoapObject response;
        SoapObject request = new SoapObject(ConfigService.NAMESPACE, METHOD_NAME);

        try {

            PropertyInfo loginPropery = new PropertyInfo();
            loginPropery.setName("login_");
            loginPropery.setType(PropertyInfo.STRING_CLASS);
            loginPropery.setValue(login);

            request.addProperty(loginPropery);

            PropertyInfo senhaPropery = new PropertyInfo();
            senhaPropery.setName("senha_");
            senhaPropery.setType(PropertyInfo.STRING_CLASS);
            senhaPropery.setValue(senha);

            request.addProperty(senhaPropery);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(ConfigService.URL + ASMX);
            transportSE.call(ConfigService.NAMESPACE + METHOD_NAME, envelope);

            response = (SoapObject) envelope.getResponse();

            if (!response.toString().equals("anyType{}")) {
                if (response.getPropertyAsString("mensagemErro").equals("anyType{}")) {
                    usuario.setId(Integer.parseInt(response.getPropertyAsString("Codigo")));
                    usuario.setNome(response.getPropertyAsString("Nome"));
                    usuario.setMsgErro("");
                } else {
                    usuario.setMsgErro(response.getPropertyAsString("mensagemErro"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
