package util;

import automacao.AutomacaoApi;
import automacao.Requisicao;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.StatusEnum;
import exceptions.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class AutomacaoApiUtil {
    private static final int TIMEOUT_SEGUNDOS = 30;
    public static AutomacaoApi executarRequisicao(Requisicao requisicao) throws AutomacaoNaoIdentificadaException, RecuperarDadosException, RequisicaoException, TokenInvalidoException, MensagemInvalidaException {
        Map<String, Object> map;
        try {
            URL url = new URL(requisicao.getLink());
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setConnectTimeout(TIMEOUT_SEGUNDOS * 1000);
            httpConn.setReadTimeout(TIMEOUT_SEGUNDOS * 1000);

            httpConn.setRequestProperty("cache-control", "no-cache");
            httpConn.setRequestProperty("content-type", "application/json");

            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write(requisicaoToJson(requisicao));
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            map = new ObjectMapper().readValue(response, HashMap.class);
        }
        catch (IOException e) {
            throw new RequisicaoException(requisicao.getLink());
        }

        AutomacaoApi automacaoApi = mapToAutomacaoApi(map);
        if (automacaoApi.getStatus().equals(StatusEnum.TOKENINVALIDO)) {
            throw new TokenInvalidoException(requisicao.getToken());
        }
        if (automacaoApi.getStatus().equals(StatusEnum.NAOENCONTRADO)) {
            throw new AutomacaoNaoIdentificadaException(requisicao.getIdAutomacao());
        }
        if (automacaoApi.getStatus().equals(StatusEnum.MENSAGEM_INVALIDA)) {
            throw new MensagemInvalidaException(requisicao.getMensagem());
        }
        return automacaoApi;
    }

    private static AutomacaoApi mapToAutomacaoApi(Map<String, Object> map) throws RecuperarDadosException {
        Integer id = (Integer) map.get("id");
        StatusEnum status;
        String statusTexto = "";
        try {
            status = StatusEnum.valueOf(String.valueOf(map.get("status")));
        }
        catch (Exception e) {
            throw new RecuperarDadosException(String.format("Status %s não identificado", statusTexto));
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

    private static String requisicaoToJson(Requisicao requisicao) {
        return String.format("{\n  \"link\":\"%s\",\n  \"token\":\"%s\",\n  \"idAutomacao\":%d,\n  \"mensagem\":\"%s\"\n}", requisicao.getLink(), requisicao.getToken(), requisicao.getIdAutomacao(), requisicao.getMensagem());
    }
}
