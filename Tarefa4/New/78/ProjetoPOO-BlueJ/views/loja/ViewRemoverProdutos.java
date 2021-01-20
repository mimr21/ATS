package views.loja;

import controllers.*;
import controllers.loja.ControllerRemoverProdutos;
import models.loja.Produto;
import views.LeituraDados;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewRemoverProdutos {

    public static final String INSIRA = "Insira: ";
    public static final String P_GINA_D_D = "P�gina %d/%d ";
    Logger logger = Logger.getLogger(ViewRemoverProdutos.class.getName());
    /**
     * Variaveis Instancia
     */
    private List<Produto> listaProdutos;
    private IControllers controller;

    /**
     * Construtor Parametrizado de View_Loja
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewRemoverProdutos(List<Produto> listaProdutos, ControllerRemoverProdutos controllers){
        this.listaProdutos = listaProdutos;
        this.controller = controllers;
    }

    /**
     * Devolve o resultado de andar com o indice de uma pagina
     * para a frente
     *
     * @param index correspondente ao indice
     * @param totalPaginas correspondente a um total de paginas
     * @return indice incrementado
     */
    private int avancaPagina(int index, int totalPaginas){
        if(index < totalPaginas-1) index++;
        return index;
    }

    /**
     * Devolve o resultado de andar uma pagina para tras
     *
     * @param index correspondente ao indice
     * @return indice decrementado
     */
    private int recuaPagina(int index){
        if(index > 0) index--;
        return index;
    }

    /**
     * Apresenta no ecra as opcoes da Loja na seccao de Remover Produtos
     *
     * @param totalPaginas representa total de paginas
     * @param paginaAtual representa a pagina atual
     */
    private void showOpcoes(int totalPaginas, int paginaAtual){
        if(totalPaginas<=1){
            logger.log(Level.INFO, ("Insira: R remover produto | S sair"));
        }
        else {
            case100(totalPaginas, paginaAtual);
        }
    }

    private void case100(int totalPaginas, int paginaAtual) {
        if(paginaAtual == 1){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | R remover produto | S sair"));
        }
        else{
            case101(totalPaginas, paginaAtual);
        }
    }

    private void case101(int totalPaginas, int paginaAtual) {
        if(paginaAtual == totalPaginas){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas, "| - p�gina anterior | R remover produto | S sair"));
        }
        else{
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO,  String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas+"| + pr�xima p�gina | - p�gina anterior | R remover produto | S sair"));
        }
    }

    /**
     * Apresenta no ecra o menu da seccao de Remover Produtos
     *
     * @param index correspondente ao indice
     * @param tamPag correspondente ao tamanho da pagina
     * @param elem correspondente aos elementos
     */
    private void showMenu(int index, int tamPag, int elem){
        int pos = (index*tamPag);
        for (int i=0; i<tamPag; i++){
            if(pos<elem){
                Produto p = this.listaProdutos.get(pos);
                logger.log(Level.INFO, (p.getCodigoProduto()+" -> "+p.getNomeProduto()+" ("+p.getPreco()+" €)"));
                pos++;
            }else{
                logger.log(Level.INFO, ("---"));
            }
        }
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos
     */
    public void run(){
        String opcao;
        int index = 0;
        int tamPag = 8;
        int elem = this.listaProdutos.size();
        int i = (elem%8==0)?elem/8:(elem/8)+1;
        int totalPaginas = (elem<8)?1:i;

        do {
            this.showMenu(index,tamPag,elem);
            this.showOpcoes(totalPaginas,index+1);
            opcao = LeituraDados.lerString();
            String opcaoTemp = opcao.toUpperCase();

            switch (opcaoTemp.charAt(0)){
                case '+' :
                    index = this.avancaPagina(index,totalPaginas);
                    break;


                case '-' :
                    index = this.recuaPagina(index);
                    break;


                case 'S' :
                    opcao = "S";
                    break;


                case 'R' :
                    List<String> l = new ArrayList<>();
                    l.add("R");
                    logger.log(Level.INFO, ("Insira o codigo do produto a remover."));
                    l.add(LeituraDados.lerString());
                    this.controller.processa(l);
                    opcao = "S";
                    break;
                default:break;
                
            }

        }
        while (!opcao.equals("S"));
    }
}
