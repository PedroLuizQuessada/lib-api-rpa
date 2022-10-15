package exceptions;

public class ConversaoException extends Exception {
    public ConversaoException(String json) {
        super(String.format("Falha ao converter JSON %s", json));
    }
}
