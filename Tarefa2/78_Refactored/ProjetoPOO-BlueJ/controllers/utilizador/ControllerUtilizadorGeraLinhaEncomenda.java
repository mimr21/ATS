package controllers.utilizador;

import controllers.*;
import excepitions.*;
import models.encomendas.Encomenda;
import models.encomendas.IEncomendas;
import models.encomendas.LinhaEncomenda;
import models.loja.Produto;
import models.TrazAqui;
import views.ViewErro;

import java.util.ArrayList;
import java.util.List;

public class ControllerUtilizadorGeraLinhaEncomenda implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui trazAqui;
    private String codLoja;
    private String codUtilizador;
    private Double peso;
    private List<LinhaEncomenda> linhaCriada;

    /**
     * Construtor Parametrizado de Controller_UtilizadorGeraLinhaEncomenda
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerUtilizadorGeraLinhaEncomenda(TrazAqui s, String u, String l){
        this.trazAqui = s;
        this.codLoja = l;
        this.codUtilizador = u;
        this.linhaCriada = new ArrayList<>();
        this.peso = 0.0;
    }

    /**
     * Com base nos inputs do Utilizador, tratar de gerar uma linha de Encomenda
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){

        switch (opcao.get(0)){
            case "Adiciona" :
                case1(opcao);
                break;

            case "Finaliza" :
                case2();
                break;

            default:break;
        }
    }

    private void case2() {
        if(!linhaCriada.isEmpty()){
            IEncomendas e = new Encomenda("", this.codUtilizador, this.codLoja, peso, this.linhaCriada);
            e.setCodEncomenda(this.trazAqui.geraCodigoEncomenda());
            this.trazAqui.utilizadorToLoja(e);
        }
    }

    private void case1(List<String> opcao) {
        try {
            Produto p = this.trazAqui.getProdutoDaLoja(this.codLoja, opcao.get(1));
            double quantidade = Double.parseDouble(opcao.get(2));
            double preco = p.getPreco()*quantidade;
            this.peso += p.getPeso();
            LinhaEncomenda l = new LinhaEncomenda(p.getCodigoProduto(),p.getNomeProduto(),quantidade,preco);
            this.linhaCriada.add(l);
        }catch (ProdutoNotFoundException e){
            ViewErro viewErro = new ViewErro(e.getMessage());
            viewErro.run();
        }
    }
}
