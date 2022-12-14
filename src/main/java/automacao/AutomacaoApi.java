package automacao;

import enums.StatusEnum;

import java.util.Calendar;

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
        if ((horaInicio > -1 && hora < horaInicio) ||
                (horaInicio > -1 && minutoInicio > -1 && hora == horaInicio && minuto < minutoInicio) ||
                (horaFim > -1 && hora > horaFim) ||
                (horaFim > -1 && minutoFim > -1 && hora == horaFim && minuto > minutoFim)) {
            return false;
        }

        return true;
    }

    private int getHora(String horario) {
        if (horario.length() > 0) {
            return Integer.parseInt(horario.substring(0, 2));
        }
        else {
            return -1;
        }
    }

    private int getMinuto(String horario) {
        if (horario.length() > 0) {
            return Integer.parseInt(horario.substring(3));
        }
        else {
            return -1;
        }
    }

    public StatusEnum getStatus() {
        return status;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public boolean isSegunda() {
        return segunda;
    }

    public boolean isTerca() {
        return terca;
    }

    public boolean isQuarta() {
        return quarta;
    }

    public boolean isQuinta() {
        return quinta;
    }

    public boolean isSexta() {
        return sexta;
    }

    public boolean isSabado() {
        return sabado;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public String getHorarioFim() {
        return horarioFim;
    }
}
