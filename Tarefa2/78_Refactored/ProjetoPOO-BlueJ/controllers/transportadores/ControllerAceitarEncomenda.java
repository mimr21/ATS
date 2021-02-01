package controllers.transportadores;

import controllers.*;
import excepitions.*;
import models.TrazAqui;
import views.ViewErro;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ControllerAceitarEncomenda implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codigoTransportador;

    /**
     * Construtor Parametrizado de Controller_AceitarEncomenda
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerAceitarEncomenda(TrazAqui s, String cod){
        this.sistema = s;
        this.codigoTransportador = cod;
    }

    /**
     * Com base nos inputs do Utilizador, tratamos de Aceitar Encomendas
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){

        if ("PedeEncomenda".equals(opcao.get(0))) {

            try {
                case1(opcao);
            }catch (NullPointerException | EncomendaNotFoundException | CodigoNotFoundException | NoSuchAlgorithmException e){
                ViewErro erro = new ViewErro("Encomenda n�o existe para entrega.");
                erro.run();
            }
        }
    }

    private void case1(List<String> opcao) throws EncomendaNotFoundException, CodigoNotFoundException, NoSuchAlgorithmException {
        boolean foiAceite = this.sistema.aceitaIntecaoDeEntrega(this.codigoTransportador, opcao.get(1));
        if (foiAceite) {
            ViewErro resposta = new ViewErro("Pedido de entrega Aceite pelo sitema.");
            resposta.run();
        } else {
            ViewErro resposta = new ViewErro("Pedido de entrega Rejeitado pelo sitema.");
            resposta.run();
        }
    }
}
