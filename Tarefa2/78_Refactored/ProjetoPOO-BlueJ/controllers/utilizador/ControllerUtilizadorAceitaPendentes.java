package controllers.utilizador;

import controllers.*;
import models.TrazAqui;

import java.util.List;

public class ControllerUtilizadorAceitaPendentes implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codUtilizador;

    /**
     * Construtor Parametrizado de Controller_UtilizadorAceitaPendentes
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerUtilizadorAceitaPendentes(TrazAqui s, String cod){
        this.sistema = s;
        this.codUtilizador = cod;
    }

    /**
     * Com base nos inputs do Utilizador, tratar de Aceitar Encomendas Pendentes
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        switch (opcao.get(0)){
            case "Aceita" :
                case1(opcao);
                break;

            case "Rejeita" :
                case2(opcao);
                break;
            
            default:break;
        }
    }

    private void case2(List<String> opcao) {
        this.sistema.rejeitaTransportadoraPendente(this.codUtilizador, opcao.get(1));
    }

    private void case1(List<String> opcao) {
        this.sistema.aceitaTransportadoraPendente(this.codUtilizador, opcao.get(1));
    }
}
