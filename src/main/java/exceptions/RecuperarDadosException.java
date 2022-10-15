package exceptions;

public class RecuperarDadosException extends Exception {
    public RecuperarDadosException(String mensagemErro) {
        super(String.format("Falha ao recuperar dados: %s", mensagemErro));
    }
}
