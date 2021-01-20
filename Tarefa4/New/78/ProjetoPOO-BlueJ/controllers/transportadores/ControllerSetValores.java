package controllers.transportadores;

import controllers.*;
import models.TrazAqui;
import views.ViewErro;

import java.util.List;

public class ControllerSetValores implements IControllers {

    public static final String VALOR_ALTERADO_COM_SUCESSO = "Valor alterado com sucesso";
    public static final String VALOR_INVALIDO = "Valor invalido";
    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codigoTransportador;

    /**
     * Construtor Parametrizado de Controller_SetValores
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerSetValores(TrazAqui s, String cod){
        this.sistema = s;
        this.codigoTransportador = cod;
    }

    /**
     * Com base nos inputs do Utilizador, tratamos de Definir valores dos Utilizadores
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){

        switch (opcao.get(0)){
            case "MudarPreco" :
                case1(opcao);
                break;
            case "MudarVelocidade" :
                case2(opcao);
                break;
            case "MudarRaio" :
                case3(opcao);
                break;
            default:break;
        }
    }

    private void case3(List<String> opcao) {
        double val = Double.parseDouble(opcao.get(1));
        if(val>0){
            this.sistema.mudaRaioAcao(codigoTransportador,val);
            ViewErro view = new ViewErro(ggetValorAlteradoComSucesso());
            view.run();
        }
        else{
            ViewErro view = new ViewErro(VALOR_INVALIDO);
            view.run();
        }
    }

    private String ggetValorAlteradoComSucesso() {
        return VALOR_ALTERADO_COM_SUCESSO;
    }

    private void case2(List<String> opcao) {
        double val = Double.parseDouble(opcao.get(1));
        if(val>0){
            this.sistema.mudaVelocidadeMedia(codigoTransportador,val);
            ViewErro view = new ViewErro(VALOR_ALTERADO_COM_SUCESSO);
            view.run();
        }
        else{
            ViewErro view = new ViewErro(VALOR_INVALIDO);
            view.run();
        }
    }

    private void case1(List<String> opcao) {
        double val = Double.parseDouble(opcao.get(1));
        if(val>=0){
            this.sistema.mudaPrecoMedio(codigoTransportador,val);
            ViewErro view = new ViewErro(VALOR_ALTERADO_COM_SUCESSO);
            view.run();
        }
        else{
            ViewErro view = new ViewErro(VALOR_INVALIDO);
            view.run();
        }
    }
}
