package controllers.loja;

import controllers.*;
import excepitions.*;
import models.sistema.PedidoLoja;
import models.TrazAqui;
import views.ViewErro;

import java.util.List;

public class ControllerAceitaPedidos implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codigoLoja;

    /**
     * Construtor Parametrizado de Controller_AceitaPedidos
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerAceitaPedidos(TrazAqui s, String codLoja){
        this.sistema = s;
        this.codigoLoja = codLoja;
    }

    /**
     * Com base nos inputs do Utilizador, tratamos de Aceitar um Pedido
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        if ("Aceitar".equals(opcao.get(0))) {
            try {
                case1(opcao);
            }catch (EncomendaNotFoundException e){
                case3("Encomenda Inv�lida.");
            }
        }
    }

    private void case1(List<String> opcao) throws EncomendaNotFoundException {
        PedidoLoja pl = this.sistema.getPedidoUtilizadorDaLoja(Integer.parseInt(opcao.get(1)),this.codigoLoja);
        if(this.sistema.atribuiEntregador(pl)) {
            case2(opcao);
        }
        else {
            case3("Encomenda rejeitada pelo sistema. Sem transportadores disponiveis.");
        }
    }

    private void case3(String s) {
        ViewErro view = new ViewErro(s);
        view.run();
    }

    private void case2(List<String> opcao) {
        this.sistema.removeListaEspera(this.codigoLoja, Integer.parseInt(opcao.get(1)));
        case3("Encomenda aceite pelo sistema.");
    }
}
