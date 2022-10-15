package automacao;

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

public class AutomacaoApi {
    private Integer id;

    private StatusEnum status;

    private boolean ativo;

    private boolean domingo;

    private boolean segunda;

    private boolean terca;

    private boolean quarta;

    private boolean quinta;

    private boolean sexta;

    private boolean sabado;

    private String horarioInicio;

    private String horarioFim;

    public AutomacaoApi(Integer id, StatusEnum status, boolean ativo, boolean domingo, boolean segunda, boolean terca,
                        boolean quarta, boolean quinta, boolean sexta, boolean sabado, String horarioInicio,
                        String horarioFim) {
        this.id = id;
        this.status = status;
        this.ativo = ativo;
        this.domingo = domingo;
        this.segunda = segunda;
        this.terca = terca;
        this.quarta = quarta;
        this.quinta = quinta;
        this.sexta = sexta;
        this.sabado = sabado;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public AutomacaoApi(StatusEnum status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public boolean isSegunda() {
        return segunda;
    }

    public void setSegunda(boolean segunda) {
        this.segunda = segunda;
    }

    public boolean isTerca() {
        return terca;
    }

    public void setTerca(boolean terca) {
        this.terca = terca;
    }

    public boolean isQuarta() {
        return quarta;
    }

    public void setQuarta(boolean quarta) {
        this.quarta = quarta;
    }

    public boolean isQuinta() {
        return quinta;
    }

    public void setQuinta(boolean quinta) {
        this.quinta = quinta;
    }

    public boolean isSexta() {
        return sexta;
    }

    public void setSexta(boolean sexta) {
        this.sexta = sexta;
    }

    public boolean isSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(String horarioFim) {
        this.horarioFim = horarioFim;
    }

    public static AutomacaoApi recuperarDados(String link, Integer idAutomacao) throws RecuperarDadosException {
        try {
            URL url = new URL(link + idAutomacao);
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

        return new AutomacaoApi(id, status, ativo, domingo, segunda, terca, quarta, quinta, sexta, sabado,
                horarioInicio, horarioFim);
    }
}
