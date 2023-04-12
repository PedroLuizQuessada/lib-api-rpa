import automacao.AutomacaoApi;
import automacao.Requisicao;
import exceptions.*;
import org.junit.Assert;
import org.junit.Test;
import util.AutomacaoApiUtil;

public class ApiRpaTeste {

    private static final String LINK_RECUPERAR_DADOS = "http://localhost:8080/sistemato/recuperardados";
    private static final String LINK_REGISTRAR_LOG = "http://localhost:8080/sistemato/registrarlog";
    private static final String TOKEN = "75190170-ecc1-48ff-ac33-9a7d12d4adcf";
    private static final Integer ID_AUTOMACAO = 1;
    private static final String MENSAGEM = "teste";

    @Test
    public void testarRecuperarDados() throws AutomacaoNaoIdentificadaException, RecuperarDadosException, RequisicaoException, TokenInvalidoException, MensagemInvalidaException {
        Requisicao requisicao = new Requisicao(LINK_RECUPERAR_DADOS, TOKEN, ID_AUTOMACAO, MENSAGEM);
        AutomacaoApi automacaoApi = AutomacaoApiUtil.executarRequisicao(requisicao);
        Assert.assertNotNull(automacaoApi);
    }

    @Test
    public void testarRegistrarLog() throws AutomacaoNaoIdentificadaException, RecuperarDadosException, RequisicaoException, TokenInvalidoException, MensagemInvalidaException {
        Requisicao requisicao = new Requisicao(LINK_REGISTRAR_LOG, TOKEN, ID_AUTOMACAO, MENSAGEM);
        AutomacaoApi automacaoApi = AutomacaoApiUtil.executarRequisicao(requisicao);
        Assert.assertNotNull(automacaoApi);
    }
}
