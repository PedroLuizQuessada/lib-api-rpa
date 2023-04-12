package exceptions;

public class TokenInvalidoException extends Exception {
    public TokenInvalidoException(String token) {
        super(String.format("Token %s inválido", token));
    }
}
