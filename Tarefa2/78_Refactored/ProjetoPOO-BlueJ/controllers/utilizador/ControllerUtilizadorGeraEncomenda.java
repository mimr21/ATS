package controllers.utilizador;

import controllers.*;
import excepitions.*;
import models.loja.Produto;
import models.TrazAqui;
import views.utilizador.ViewUtilizadorGeraLinhaEncomenda;
import views.ViewErro;

import java.util.List;

public class ControllerUtilizadorGeraEncomenda implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui trazAqui;
    private String utilizador;

    /**
     * Construtor Parametrizado de Controller_UtilizadorGeraEncomenda
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerUtilizadorGeraEncomenda(TrazAqui s, String u){
        this.trazAqui = s;
        this.utilizador = u;
    }

    /**
     * Com base nos inputs do Utilizador, tratar de gerar uma Encomenda
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        if (opcao.get(0).equals("CriaLinhaEncomenda")) {
            try {
                case1(opcao);
            }catch (CodigoNotFoundException e){
                ViewErro erro = new ViewErro(e.getMessage());
                erro.run();
            }
        }
    }

    private void case1(List<String> opcao) throws CodigoNotFoundException {
        List<Produto> produtos = this.trazAqui.getProdutosLoja(opcao.get(1));
        ViewUtilizadorGeraLinhaEncomenda view = new ViewUtilizadorGeraLinhaEncomenda(produtos,new ControllerUtilizadorGeraLinhaEncomenda(this.trazAqui,this.utilizador, opcao.get(1)));
        view.run();
    }
}
