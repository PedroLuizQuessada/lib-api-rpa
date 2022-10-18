package util;

import automacao.AutomacaoApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.StatusEnum;
import exceptions.RecuperarDadosException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AutomacaoApiUtil {
    public static AutomacaoApi executarRequisicao(String link) throws RecuperarDadosException {
        try {
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            int status = con.getResponseCode();
            Reader streamReader;
            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            }
            else {
                streamReader = new InputStreamReader(con.getInputStream());
            }
            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            Map<String, Object> map = new ObjectMapper().readValue(String.valueOf(content), HashMap.class);

            return mapToAutomacaoApi(map);
        }
        catch (IOException e) {
            throw new RecuperarDadosException(e.getMessage());
        }
    }

    private static AutomacaoApi mapToAutomacaoApi(Map<String, Object> map) throws RecuperarDadosException {
        Integer id = (Integer) map.get("id");
        StatusEnum status;
        String statusTexto = "";
        try {
            status = StatusEnum.valueOf(String.valueOf(map.get("status")));
        }
        catch (Exception e) {
            throw new RecuperarDadosException(String.format("status %s não identificado", statusTexto));
        }
        boolean ativo = (boolean) map.get("ativo");
        boolean domingo = (boolean) map.get("domingo");
        boolean segunda = (boolean) map.get("segunda");
        boolean terca = (boolean) map.get("terca");
        boolean quarta = (boolean) map.get("quarta");
        boolean quinta = (boolean) map.get("quinta");
        boolean sexta = (boolean) map.get("sexta");
        boolean sabado = (boolean) map.get("sabado");
        String horarioInicio = String.valueOf(map.get("horarioInicio"));
        String horarioFim = String.valueOf(map.get("horarioFim"));

        return new AutomacaoApi(status, ativo, domingo, segunda, terca, quarta, quinta, sexta, sabado,
                horarioInicio, horarioFim);
    }

    public static String converterMensagemParaRequisicao(String mensagem) {
        //Tratando espaços
        mensagem = mensagem.replace(" ", "ESPACO");

        //Tratando vogais com acentos
        mensagem = mensagem.replace("á", "aAGUDO");
        mensagem = mensagem.replace("â", "aCIRCUNFLEXO");
        mensagem = mensagem.replace("ã", "aTIO");
        mensagem = mensagem.replace("à", "aCRASE");
        mensagem = mensagem.replace("Á", "AAGUDO");
        mensagem = mensagem.replace("Â", "ACIRCUNFLEXO");
        mensagem = mensagem.replace("Ã", "ATIO");
        mensagem = mensagem.replace("À", "ACRASE");
        mensagem = mensagem.replace("é", "eAGUDO");
        mensagem = mensagem.replace("ê", "eCIRCUNFLEXO");
        mensagem = mensagem.replace("è", "eCRASE");
        mensagem = mensagem.replace("É", "EAGUDO");
        mensagem = mensagem.replace("Ê", "ECIRCUNFLEXO");
        mensagem = mensagem.replace("È", "ECRASE");
        mensagem = mensagem.replace("í", "iAGUDO");
        mensagem = mensagem.replace("î", "iCIRCUNFLEXO");
        mensagem = mensagem.replace("ì", "iCRASE");
        mensagem = mensagem.replace("Í", "IAGUDO");
        mensagem = mensagem.replace("Î", "ICIRCUNFLEXO");
        mensagem = mensagem.replace("Ì", "ICRASE");
        mensagem = mensagem.replace("ó", "oAGUDO");
        mensagem = mensagem.replace("ô", "oCIRCUNFLEXO");
        mensagem = mensagem.replace("õ", "oTIO");
        mensagem = mensagem.replace("ò", "oCRASE");
        mensagem = mensagem.replace("Ó", "OAGUDO");
        mensagem = mensagem.replace("Ô", "OCIRCUNFLEXO");
        mensagem = mensagem.replace("Õ", "OTIO");
        mensagem = mensagem.replace("Ò", "OCRASE");
        mensagem = mensagem.replace("ú", "uAGUDO");
        mensagem = mensagem.replace("û", "uCIRCUNFLEXO");
        mensagem = mensagem.replace("ù", "uCRASE");
        mensagem = mensagem.replace("Ú", "UAGUDO");
        mensagem = mensagem.replace("Û", "UCIRCUNFLEXO");
        mensagem = mensagem.replace("Ù", "UCRASE");

        //Tratando cedilhas
        mensagem = mensagem.replace("ç", "cCEDILHA");
        mensagem = mensagem.replace("Ç", "CCEDILHA");

        //Tratando pontuações
        mensagem = mensagem.replace("@", "ARROBA");
        mensagem = mensagem.replace("!", "PONTOEXCLAMACAO");
        mensagem = mensagem.replace("?", "PONTOINTERROGACAO");
        mensagem = mensagem.replace("//", "BARRADUPLA");
        mensagem = mensagem.replace("[", "ABRECOLCHETES");
        mensagem = mensagem.replace("]", "FECHACOLCHETES");
        mensagem = mensagem.replace("(", "ABREPARENTESES");
        mensagem = mensagem.replace(")", "FECHAPARENTESES");

        return mensagem;
    }

    public static String converterMensagemDaRequisicao(String mensagem) {
        //Tratando espaços
        mensagem = mensagem.replace("ESPACO", " ");

        //Tratando vogais com acentos
        mensagem = mensagem.replace("aAGUDO", "á");
        mensagem = mensagem.replace("aCIRCUNFLEXO","â");
        mensagem = mensagem.replace("aTIO","ã");
        mensagem = mensagem.replace("aCRASE","à");
        mensagem = mensagem.replace("AAGUDO","Á");
        mensagem = mensagem.replace("ACIRCUNFLEXO", "Â");
        mensagem = mensagem.replace("ATIO", "Ã");
        mensagem = mensagem.replace("ACRASE", "À");
        mensagem = mensagem.replace("eAGUDO", "é");
        mensagem = mensagem.replace("eCIRCUNFLEXO","ê");
        mensagem = mensagem.replace("eCRASE","è");
        mensagem = mensagem.replace("EAGUDO","É");
        mensagem = mensagem.replace("ECIRCUNFLEXO","Ê");
        mensagem = mensagem.replace("ECRASE","È");
        mensagem = mensagem.replace("iAGUDO","í");
        mensagem = mensagem.replace("iCIRCUNFLEXO","î");
        mensagem = mensagem.replace("iCRASE","ì");
        mensagem = mensagem.replace("IAGUDO","Í");
        mensagem = mensagem.replace("ICIRCUNFLEXO","Î");
        mensagem = mensagem.replace("ICRASE","Ì");
        mensagem = mensagem.replace("oAGUDO","ó");
        mensagem = mensagem.replace("oCIRCUNFLEXO","ô");
        mensagem = mensagem.replace("oTIO","õ");
        mensagem = mensagem.replace("oCRASE","ò");
        mensagem = mensagem.replace("OAGUDO","Ó");
        mensagem = mensagem.replace("OCIRCUNFLEXO","Ô");
        mensagem = mensagem.replace("OTIO","Õ");
        mensagem = mensagem.replace("OCRASE","Ò");
        mensagem = mensagem.replace("uAGUDO","ú");
        mensagem = mensagem.replace("uCIRCUNFLEXO","û");
        mensagem = mensagem.replace("uCRASE","ù");
        mensagem = mensagem.replace("UAGUDO","Ú");
        mensagem = mensagem.replace("UCIRCUNFLEXO","Û");
        mensagem = mensagem.replace("UCRASE","Ù");

        //Tratando cedilhas
        mensagem = mensagem.replace("cCEDILHA","ç");
        mensagem = mensagem.replace("CCEDILHA","Ç");

        //Tratando pontuações
        mensagem = mensagem.replace("ARROBA","@");
        mensagem = mensagem.replace("PONTOEXCLAMACAO","!");
        mensagem = mensagem.replace("PONTOINTERROGACAO","?");
        mensagem = mensagem.replace("BARRADUPLA","//");
        mensagem = mensagem.replace("ABRECOLCHETES","[");
        mensagem = mensagem.replace("FECHACOLCHETES","]");
        mensagem = mensagem.replace("ABREPARENTESES","(");
        mensagem = mensagem.replace("FECHAPARENTESES",")");

        return mensagem;
    }
}
