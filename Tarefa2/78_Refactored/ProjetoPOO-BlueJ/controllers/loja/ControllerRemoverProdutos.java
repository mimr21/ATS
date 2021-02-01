package controllers.loja;

import controllers.*;
import excepitions.ProdutoNotFoundException;
import models.TrazAqui;
import views.ViewErro;

import java.util.List;

public class ControllerRemoverProdutos implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codigoLoja;

    /**
     * Construtor Parametrizado de Controller_RemoverProdutos
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerRemoverProdutos(TrazAqui s, String codLoja){
        this.sistema = s;
        this.codigoLoja = codLoja;
    }

    /**
     * Com base nos inputs do Utilizador, tratamos de remover Produtos
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        if ("R".equals(opcao.get(0))) {
            try {
                this.sistema.removerProdutoLoja(this.codigoLoja, opcao.get(1));
            } catch (ProdutoNotFoundException e) {
                ViewErro erro = new ViewErro("Produto nao existe. Impossivel remover");
                erro.run();
            }
        }
    }
}
