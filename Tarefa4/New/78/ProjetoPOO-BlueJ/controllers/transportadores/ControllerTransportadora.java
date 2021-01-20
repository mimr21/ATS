package controllers.transportadores;

import controllers.*;
import models.TrazAqui;
import views.transportadores.ViewEncomendasParaRecolha;
import views.transportadores.ViewSetValores;
import views.ViewHistorico;
import java.util.List;

public class ControllerTransportadora implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codigoTransportador;

    /**
     * Construtor Parametrizado de Controller_SetValores
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerTransportadora(TrazAqui s, String cod){
        this.sistema = s;
        this.codigoTransportador = cod;
    }

    /**
     * Com base nos inputs do Utilizador, processar as diferentes opcoes possiveis uma Transportadora
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){

        switch (opcao.get(0)){
            case "MudarEstado" :
                case1();
                break;
            case "Finaliza" :
                case2();
                break;
            case "PedirEncomenda" :
                case3();
                break;
            case "Historico" :
                case4();
                break;
            case "MudarValores" :
                case5();
                break;
            default: break;
        }
    }

    private void case5() {
        ViewSetValores view = new ViewSetValores(this.sistema.getTransportador(this.codigoTransportador),new ControllerSetValores(this.sistema,this.codigoTransportador));
        view.run();
    }

    private void case4() {
        List<String> historico = this.sistema.getHistoricoTransportadores(this.codigoTransportador);
        ViewHistorico viewAux = new ViewHistorico(historico);
        viewAux.run();
    }

    private void case3() {
        List<String> encomendasNoSistema = this.sistema.encomendaParaEntrega(this.codigoTransportador);
        ViewEncomendasParaRecolha viewAux = new ViewEncomendasParaRecolha(encomendasNoSistema, new ControllerAceitarEncomenda(this.sistema,this.codigoTransportador));
        viewAux.run();
    }

    private void case2() {
        this.sistema.finalizaTransportadores(this.codigoTransportador);
    }

    private void case1() {
        this.sistema.mudaEstado(this.codigoTransportador);
    }
}
