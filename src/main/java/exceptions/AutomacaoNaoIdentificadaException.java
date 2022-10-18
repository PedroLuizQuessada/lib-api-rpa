package exceptions;

public class AutomacaoNaoIdentificadaException extends Exception {
    public AutomacaoNaoIdentificadaException(Integer idAutomacao) {
        super(String.format("Automação %d não identificada", idAutomacao));
    }
}
