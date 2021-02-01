package controllers.utilizador;

import controllers.*;
import excepitions.*;
import models.TrazAqui;
import views.ViewErro;

import java.util.List;

public class ControllerUtilizadorAvalia implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codUtilizador;

    /**
     * Construtor Parametrizado de Controller_UtilizadorAvalia
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerUtilizadorAvalia(String cod, TrazAqui s){
        this.sistema = s;
        this.codUtilizador = cod;
    }

    /**
     * Com base nos inputs do Utilizador, tratar de avaliar uma Transportadora
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        if (opcao.get(0).equals("Avalia")) {
            try {
                this.sistema.avaliaTransportador(this.codUtilizador,opcao.get(1),Integer.parseInt(opcao.get(2)));
            } catch (ValorErradoException e) {
                ViewErro erro = new ViewErro(e.getMessage());
                erro.run();
            }
        }
    }
}
