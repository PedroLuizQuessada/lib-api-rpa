package automacao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Requisicao {
    private String link;
    private String token;
    private Integer idAutomacao;
    private String mensagem;

    @JsonCreator
    public Requisicao(@JsonProperty("link") String link, @JsonProperty("token") String token, @JsonProperty("idAutomacao") Integer idAutomacao, @JsonProperty("mensagem") String mensagem) {
        this.link = link;
        this.token = token;
        this.idAutomacao = idAutomacao;
        this.mensagem = mensagem;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIdAutomacao() {
        return idAutomacao;
    }

    public void setIdAutomacao(Integer idAutomacao) {
        this.idAutomacao = idAutomacao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
