package controllers.utilizador;

import controllers.IControllers;
import models.loja.Loja;
import models.sistema.PedidoCompleto;
import models.sistema.PedidoTransportadora;
import models.TrazAqui;
import views.utilizador.ViewPedidosPendentes;
import views.utilizador.ViewUtilizadorAvaliar;
import views.utilizador.ViewUtilizadorGeraEncomenda;
import views.ViewHistorico;
import java.util.List;

public class ControllerUtilizador implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codigoUtilizador;

    /**
     * Construtor Parametrizado de ControllerUtilizador
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerUtilizador(TrazAqui s, String utilizador){
        this.sistema = s;
        this.codigoUtilizador = utilizador;
    }

    /**
     * Com base nos inputs do Utilizador, processar as diferentes opcoes possiveis num Utilizador
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        switch (opcao.get(0)){
            case "NumeroMensagens" :
                break;
            case "CriaEncomenda" :
                case1();
                break;
            case "Avaliar" :
                case2();
                break;
            case "Historico" :
                case3();
                break;
            case "MensagensPendentes" :
                case4();
                break;
            default: break;
        }
    }

    private void case4() {
        List<String> l = this.sistema.getNotificacoesUtilizador(this.codigoUtilizador);
        List<PedidoTransportadora> lt = this.sistema.getPedidosTransportadorasPendentes(this.codigoUtilizador);
        ViewPedidosPendentes viewAux = new ViewPedidosPendentes(lt,new ControllerUtilizadorAceitaPendentes(this.sistema,this.codigoUtilizador),l);
        viewAux.run();
        this.sistema.limpaNotificacoesUtilizador(this.codigoUtilizador);
    }

    private void case3() {
        List<String> historico = this.sistema.getHistoricoUtilizador(this.codigoUtilizador);
        ViewHistorico viewAux = new ViewHistorico(historico);
        viewAux.run();
    }

    private void case2() {
        List<PedidoCompleto> ut = this.sistema.getTransportadoresParaAvaliar(this.codigoUtilizador);
        ViewUtilizadorAvaliar viewAux = new ViewUtilizadorAvaliar(ut,new ControllerUtilizadorAvalia(this.codigoUtilizador,this.sistema));
        viewAux.run();
    }

    private void case1() {
        List<Loja> lojas = this.sistema.getLojasDisponiveis();
        ViewUtilizadorGeraEncomenda viewAux = new ViewUtilizadorGeraEncomenda(lojas,new ControllerUtilizadorGeraEncomenda(this.sistema,this.codigoUtilizador));
        viewAux.run();
    }
}
