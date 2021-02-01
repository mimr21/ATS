package controllers.loja;

import controllers.*;
import excepitions.*;
import models.loja.Produto;
import models.TrazAqui;
import views.loja.ViewAceitarPedidos;
import views.loja.ViewRemoverProdutos;
import views.ViewErro;
import views.ViewHistorico;

import java.util.List;

public class ControllerLoja implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;
    private String codigoLoja;

    /**
     * Construtor Parametrizado de Controller_Lojas
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerLoja(TrazAqui s, String codLoja){
        this.sistema = s;
        this.codigoLoja = codLoja;
    }

    /**
     * Com base nos inputs do Utilizador, tratamos nos orientar na nos Menus da Loja
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        switch (opcao.get(0)){
            case "AdicionarM" :
                case1(opcao, true);
                break;


            case "Adicionar" :
                case1(opcao, false);
                break;


            case "RemoverP" :
                try {
                    case2();
                } catch (CodigoNotFoundException e) {
                    case3(e);
                }
                break;


            case "Historico" :
                case4();
                break;


            case "Espera" :
                this.sistema.setPessoasEmEspera(this.codigoLoja,Integer.parseInt(opcao.get(1)));
                break;


            case "AceitarPedidos" :
                case6();
                break;

            default:break;
        }
    }

    private void case6() {
        List<String> l = this.sistema.getListPedidosLoja(this.codigoLoja);
        ViewAceitarPedidos view = new ViewAceitarPedidos(l,new ControllerAceitaPedidos(this.sistema,this.codigoLoja));
        view.run();
    }

    private void case4() {
        List<String> historico = this.sistema.getHistoricoLoja(this.codigoLoja);
        ViewHistorico viewAux = new ViewHistorico(historico);
        viewAux.run();
    }

    private void case3(CodigoNotFoundException e) {
        ViewErro erro = new ViewErro(e.getMessage());
        erro.run();
    }

    private void case2() throws CodigoNotFoundException {
        List<Produto> ps = this.sistema.getProdutosLoja(this.codigoLoja);
        ViewRemoverProdutos viewAux = new ViewRemoverProdutos(ps,new ControllerRemoverProdutos(this.sistema,this.codigoLoja));
        viewAux.run();
    }

    private void case1(List<String> opcao, boolean b) {
        Produto p = new Produto(this.sistema.geraCodigoProduto(), opcao.get(1), Double.parseDouble(opcao.get(2)), Double.parseDouble(opcao.get(3)), b);
        this.sistema.adicionaProdutoLoja(this.codigoLoja, p);
    }
}
