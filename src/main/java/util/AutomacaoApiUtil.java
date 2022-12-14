package util;

import automacao.AutomacaoApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.StatusEnum;
import exceptions.AutomacaoNaoIdentificadaException;
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
    public static AutomacaoApi executarRequisicao(String link, Integer idAutomacao) throws RecuperarDadosException, AutomacaoNaoIdentificadaException {
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

            AutomacaoApi automacaoApi = mapToAutomacaoApi(map);
            if (automacaoApi.getStatus().equals(StatusEnum.NAOENCONTRADO)) {
                throw new AutomacaoNaoIdentificadaException(idAutomacao);
            }
            return automacaoApi;
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
            throw new RecuperarDadosException(String.format("status %s n??o identificado", statusTexto));
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
        //Tratando espa??os
        mensagem = mensagem.replace(" ", "ESPACO");

        //Tratando vogais com acentos
        mensagem = mensagem.replace("??", "aAGUDO");
        mensagem = mensagem.replace("??", "aCIRCUNFLEXO");
        mensagem = mensagem.replace("??", "aTIO");
        mensagem = mensagem.replace("??", "aCRASE");
        mensagem = mensagem.replace("??", "AAGUDO");
        mensagem = mensagem.replace("??", "ACIRCUNFLEXO");
        mensagem = mensagem.replace("??", "ATIO");
        mensagem = mensagem.replace("??", "ACRASE");
        mensagem = mensagem.replace("??", "eAGUDO");
        mensagem = mensagem.replace("??", "eCIRCUNFLEXO");
        mensagem = mensagem.replace("??", "eCRASE");
        mensagem = mensagem.replace("??", "EAGUDO");
        mensagem = mensagem.replace("??", "ECIRCUNFLEXO");
        mensagem = mensagem.replace("??", "ECRASE");
        mensagem = mensagem.replace("??", "iAGUDO");
        mensagem = mensagem.replace("??", "iCIRCUNFLEXO");
        mensagem = mensagem.replace("??", "iCRASE");
        mensagem = mensagem.replace("??", "IAGUDO");
        mensagem = mensagem.replace("??", "ICIRCUNFLEXO");
        mensagem = mensagem.replace("??", "ICRASE");
        mensagem = mensagem.replace("??", "oAGUDO");
        mensagem = mensagem.replace("??", "oCIRCUNFLEXO");
        mensagem = mensagem.replace("??", "oTIO");
        mensagem = mensagem.replace("??", "oCRASE");
        mensagem = mensagem.replace("??", "OAGUDO");
        mensagem = mensagem.replace("??", "OCIRCUNFLEXO");
        mensagem = mensagem.replace("??", "OTIO");
        mensagem = mensagem.replace("??", "OCRASE");
        mensagem = mensagem.replace("??", "uAGUDO");
        mensagem = mensagem.replace("??", "uCIRCUNFLEXO");
        mensagem = mensagem.replace("??", "uCRASE");
        mensagem = mensagem.replace("??", "UAGUDO");
        mensagem = mensagem.replace("??", "UCIRCUNFLEXO");
        mensagem = mensagem.replace("??", "UCRASE");

        //Tratando cedilhas
        mensagem = mensagem.replace("??", "cCEDILHA");
        mensagem = mensagem.replace("??", "CCEDILHA");

        //Tratando pontua????es
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
        //Tratando espa??os
        mensagem = mensagem.replace("ESPACO", " ");

        //Tratando vogais com acentos
        mensagem = mensagem.replace("aAGUDO", "??");
        mensagem = mensagem.replace("aCIRCUNFLEXO","??");
        mensagem = mensagem.replace("aTIO","??");
        mensagem = mensagem.replace("aCRASE","??");
        mensagem = mensagem.replace("AAGUDO","??");
        mensagem = mensagem.replace("ACIRCUNFLEXO", "??");
        mensagem = mensagem.replace("ATIO", "??");
        mensagem = mensagem.replace("ACRASE", "??");
        mensagem = mensagem.replace("eAGUDO", "??");
        mensagem = mensagem.replace("eCIRCUNFLEXO","??");
        mensagem = mensagem.replace("eCRASE","??");
        mensagem = mensagem.replace("EAGUDO","??");
        mensagem = mensagem.replace("ECIRCUNFLEXO","??");
        mensagem = mensagem.replace("ECRASE","??");
        mensagem = mensagem.replace("iAGUDO","??");
        mensagem = mensagem.replace("iCIRCUNFLEXO","??");
        mensagem = mensagem.replace("iCRASE","??");
        mensagem = mensagem.replace("IAGUDO","??");
        mensagem = mensagem.replace("ICIRCUNFLEXO","??");
        mensagem = mensagem.replace("ICRASE","??");
        mensagem = mensagem.replace("oAGUDO","??");
        mensagem = mensagem.replace("oCIRCUNFLEXO","??");
        mensagem = mensagem.replace("oTIO","??");
        mensagem = mensagem.replace("oCRASE","??");
        mensagem = mensagem.replace("OAGUDO","??");
        mensagem = mensagem.replace("OCIRCUNFLEXO","??");
        mensagem = mensagem.replace("OTIO","??");
        mensagem = mensagem.replace("OCRASE","??");
        mensagem = mensagem.replace("uAGUDO","??");
        mensagem = mensagem.replace("uCIRCUNFLEXO","??");
        mensagem = mensagem.replace("uCRASE","??");
        mensagem = mensagem.replace("UAGUDO","??");
        mensagem = mensagem.replace("UCIRCUNFLEXO","??");
        mensagem = mensagem.replace("UCRASE","??");

        //Tratando cedilhas
        mensagem = mensagem.replace("cCEDILHA","??");
        mensagem = mensagem.replace("CCEDILHA","??");

        //Tratando pontua????es
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
