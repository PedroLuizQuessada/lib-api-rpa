package exceptions;

public class RequisicaoException extends Exception {
    public RequisicaoException(String link) {
        super(String.format("Falha ao realizar a requisição %s", link));
    }
}
