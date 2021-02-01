package views.loja;

import controllers.IControllers;
import controllers.loja.ControllerAceitaPedidos;
import views.LeituraDados;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewAceitarPedidos {

    public static final String INSIRA = "Insira: ";
    public static final String P_GINA_D_D = "P�gina %d/%d ";
    private static final Logger logger = Logger.getLogger(ViewAceitarPedidos.class.getName());


    /**
     * Variaveis Instancia
     */
    private List<String> listaPedidos;
    private IControllers controller;

    /**
     * Construtor Parametrizado de View_AceitarPedidos
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewAceitarPedidos(List<String> listaPedidos, ControllerAceitaPedidos controllers){
        this.listaPedidos = listaPedidos;
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
     * Apresenta no ecra as opcoes da Loja na seccao de Aceitar Pedidos
     *
     * @param totalPaginas representa total de paginas
     * @param paginaAtual representa a pagina atual
     */
    private void showOpcoes(int totalPaginas, int paginaAtual){
        if(totalPaginas<=1){
            logger.log(Level.INFO, "Insira: Press A para aceitar pedido | S sair");
        }
        else {
            case1(totalPaginas, paginaAtual);
        }
    }

    private void case1(int totalPaginas, int paginaAtual) {
        if(paginaAtual == 1) {
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA, P_GINA_D_D, paginaAtual, totalPaginas, "| + pr�xima p�gina | Press A para aceitar pedido | S sair"));
        }
        else{
            case2(totalPaginas, paginaAtual);
        }
    }

    private void case2(int totalPaginas, int paginaAtual) {
        if(paginaAtual == totalPaginas){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA, P_GINA_D_D, paginaAtual, totalPaginas,"| - p�gina anterior | Press A para aceitar pedido | S sair"));
        }
        else{
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | - p�gina anterior | Press A para aceitar pedido | S sair"));
        }
    }

    /**
     * Apresenta no ecra o menu da seccao de Aceitar Pedidos
     *
     * @param index correspondente ao indice
     * @param tamPag correspondente ao tamanho da pagina
     * @param elem correspondente aos elementos
     */
    private void showMenu(int index, int tamPag, int elem){
        int pos = (index*tamPag);
        for (int i=0; i<tamPag; i++){
            if(pos<elem){
                logger.log(Level.INFO, this.listaPedidos.get(pos));
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
        int elem = this.listaPedidos.size();
        int i = (elem % 8 == 0) ? elem / 8 : (elem / 8) + 1;
        int totalPaginas = (elem<8)?1: i;

        do {
            this.showMenu(index,tamPag,elem);
            this.showOpcoes(totalPaginas,index+1);
            opcao = case4(LeituraDados.lerString());
            opcao = case4(opcao.toUpperCase());

            switch (opcao){
                case "+" :
                    index = this.avancaPagina(index,totalPaginas);
                    break;


                case "-" :
                    index = this.recuaPagina(index);
                    break;


                case "S" :
                    opcao = case4("S");
                    break;


                case "A" :
                    case5();
                    opcao = case4("S");
                    break;
                default:break;

            }

        }
        while (!opcao.equals("S"));
    }

    private void case5() {
        logger.log(Level.INFO, "Insira o indice da encomenda a aceitar.");
        try {
            List<String> l = new ArrayList<>();
            l.add("Aceitar");
            l.add(LeituraDados.lerIntAnteriorComoString());
            this.controller.processa(l);
        }catch (InputMismatchException e){
            logger.log(Level.INFO, "Posi��o invalida.");
        }
    }

    private String case4(String s) {
        String opcao;
        opcao = s;
        return opcao;
    }


}

