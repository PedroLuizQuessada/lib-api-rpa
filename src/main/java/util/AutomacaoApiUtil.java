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
}
