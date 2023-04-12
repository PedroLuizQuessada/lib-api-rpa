package exceptions;

public class MensagemInvalidaException extends Exception {
    public MensagemInvalidaException(String mensagem) {
        super(String.format("Mensagem %s inválida", mensagem));
    }
}
