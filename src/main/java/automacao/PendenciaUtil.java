package automacao;

import exceptions.ConversaoPendenciaException;

import java.util.List;

public abstract class PendenciaUtil {
    public abstract <T extends Pendencia> List<T> converterPendencia(AutomacaoApi automacaoApi, Integer idAutomacao) throws ConversaoPendenciaException;
}
