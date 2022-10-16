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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AutomacaoApi {
    private final StatusEnum status;

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

    public AutomacaoApi(StatusEnum status, boolean ativo, boolean domingo, boolean segunda, boolean terca,
                        boolean quarta, boolean quinta, boolean sexta, boolean sabado, String horarioInicio,
                        String horarioFim) {
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

        return new AutomacaoApi(status, ativo, domingo, segunda, terca, quarta, quinta, sexta, sabado,
                horarioInicio, horarioFim);
    }

    public boolean isExecutar(Calendar calendar) {
        if (!ativo) {
            return false;
        }

        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            case 1:
                if (!domingo) {
                    return false;
                }
                break;

            case 2:
                if (!segunda) {
                    return false;
                }
                break;

            case 3:
                if (!terca) {
                    return false;
                }
                break;

            case 4:
                if (!quarta) {
                    return false;
                }
                break;

            case 5:
                if (!quinta) {
                    return false;
                }
                break;

            case 6:
                if (!sexta) {
                    return false;
                }
                break;

            case 7:
                if (!sabado) {
                    return false;
                }
                break;
        }

        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        int horaInicio = getHora(horarioInicio);
        int minutoInicio = getMinuto(horarioInicio);
        int horaFim = getHora(horarioFim);
        int minutoFim = getMinuto(horarioFim);
        if (hora < horaInicio ||
                (hora == horaInicio && minuto < minutoInicio) ||
                hora > horaFim ||
                (hora == horaFim && minuto > minutoFim)) {
            return false;
        }

        return true;
    }

    private int getHora(String horario) {
        return Integer.parseInt(horario.substring(0, 2));
    }

    private int getMinuto(String horario) {
        return Integer.parseInt(horario.substring(3));
    }
}
