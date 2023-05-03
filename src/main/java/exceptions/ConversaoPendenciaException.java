package exceptions;

public class ConversaoPendenciaException extends Exception {
    public ConversaoPendenciaException(Integer id) {
        super(String.format("Falha ao converter pendência da automação %d", id));
    }
}
