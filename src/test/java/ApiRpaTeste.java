import automacao.AutomacaoApi;
import automacao.Requisicao;
import exceptions.*;
import org.junit.Assert;
import org.junit.Test;
import util.AutomacaoApiUtil;

public class ApiRpaTeste {

    private static final String LINK_RECUPERAR_DADOS = "https://sistemato-saocristovao.herokuapp.com/sistemato/recuperardados";
    private static final String LINK_REGISTRAR_LOG = "https://sistemato-saocristovao.herokuapp.com/sistemato/registrarlog";
    private static final String LINK_PROCESSAR_PENDENCIA = "https://sistemato-saocristovao.herokuapp.com/sistemato/processarpendencia";
    private static final String TOKEN = "0d2d81d9-9bfe-47e2-9aec-37b56795e4b5";
    private static final Integer ID_AUTOMACAO = 4;
    private static final String MENSAGEM = "teste";
    private static final Integer ID_PENDENCIA = 4;

    @Test
    public void testarRecuperarDados() throws AutomacaoNaoIdentificadaException, RecuperarDadosException, RequisicaoException, TokenInvalidoException, MensagemInvalidaException {
        Requisicao requisicao = new Requisicao(LINK_RECUPERAR_DADOS, TOKEN, ID_AUTOMACAO, MENSAGEM, ID_PENDENCIA);
        AutomacaoApi automacaoApi = AutomacaoApiUtil.executarRequisicao(requisicao);
        Assert.assertNotNull(automacaoApi);
    }

    @Test
    public void testarRegistrarLog() throws AutomacaoNaoIdentificadaException, RecuperarDadosException, RequisicaoException, TokenInvalidoException, MensagemInvalidaException {
        Requisicao requisicao = new Requisicao(LINK_REGISTRAR_LOG, TOKEN, ID_AUTOMACAO, MENSAGEM, ID_PENDENCIA);
        AutomacaoApi automacaoApi = AutomacaoApiUtil.executarRequisicao(requisicao);
        Assert.assertNotNull(automacaoApi);
    }

    @Test
    public void testarProcessarPendencia() throws MensagemInvalidaException, TokenInvalidoException, RecuperarDadosException, AutomacaoNaoIdentificadaException, RequisicaoException {
        Requisicao requisicao = new Requisicao(LINK_PROCESSAR_PENDENCIA, TOKEN, ID_AUTOMACAO, MENSAGEM, ID_PENDENCIA);
        AutomacaoApi automacaoApi = AutomacaoApiUtil.executarRequisicao(requisicao);
        Assert.assertNotNull(automacaoApi);
    }
}
