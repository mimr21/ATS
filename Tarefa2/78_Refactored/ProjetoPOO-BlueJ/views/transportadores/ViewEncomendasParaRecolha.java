package views.transportadores;

import controllers.*;
import controllers.transportadores.ControllerAceitarEncomenda;
import views.LeituraDados;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewEncomendasParaRecolha {

    public static final String INSIRA = "Insira:";
    public static final String P_GINA_D_D = "P�gina %d/%d ";
    Logger logger = Logger.getLogger(ViewEncomendasParaRecolha.class.getName());
    /**
     * Variaveis Instancia
     */
    private List<String> encomendasParaRecolha;
    private IControllers controller;

    /**
     * Construtor Parametrizado de View_EncomendasParaRecolha
     * Aceita como par�metros os valores para cada Vari�vel de Instancia
     */
    public ViewEncomendasParaRecolha(List<String> l, ControllerAceitarEncomenda controller){
        this.encomendasParaRecolha = new ArrayList<>(l);
        this.controller = controller;
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
     * Apresenta no ecra as opcoes na seccao de Encomendas para Recolher
     *
     * @param totalPaginas representa total de paginas
     * @param paginaAtual representa a pagina atual
     */
    private void showOpcoes(int totalPaginas, int paginaAtual){
        if(totalPaginas<=1){
            logger.log(Level.INFO, (INSIRA + " S sair | C�digo da encomenda a aceitar."));
        }
        else {
            case100(totalPaginas, paginaAtual);

        }
    }

    private void case100(int totalPaginas, int paginaAtual) {
        if(paginaAtual == 1){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA, " ", P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | S sair | C�digo da encomenda a aceitar."));
        }
        else{
            case101(totalPaginas, paginaAtual);
        }
    }

    private void case101(int totalPaginas, int paginaAtual) {
        if(paginaAtual == totalPaginas){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA," ", P_GINA_D_D, paginaAtual, totalPaginas, "| - p�gina anterior | S sair | C�digo da encomenda a aceitar."));
        }
        else{
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA, " ", P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | - p�gina anterior | S sair | C�digo da encomenda a aceitar."));
        }
    }

    /**
     * Apresenta no ecra o menu de Encomendas para Recolher
     *
     * @param index correspondente ao indice
     * @param tamPag correspondente ao tamanho da pagina
     * @param elem correspondente aos elementos
     */
    private void showMenu(int index, int tamPag, int elem){
        int pos = (index*tamPag);
        for (int i=0; i<tamPag; i++){
            if(pos<elem){
                logger.log(Level.INFO, (this.encomendasParaRecolha.get(pos)));
                pos++;
            }else{
                logger.log(Level.INFO, ("---"));
            }
        }
    }

    /**
     * Quando nao houver informacao a ser apresentada sao apresentados tracos no ecra
     *
     * @param tamPag correspondente ao tamanho da pagina
     */
    private void showVazio(int tamPag){
        for (int i=0; i<tamPag; i++){
            logger.log(Level.INFO, ("---"));
        }
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos
     */
    public void run(){
        int index = 0;
        int tamPag = 8;
        int elem = this.encomendasParaRecolha.size();
        int i = (elem % 8 == 0) ? elem / 8 : (elem / 8) + 1;
        int totalPaginas = (elem<8)?1: i;
        if(elem==0){
            extracted1(index, tamPag, totalPaginas);
        }
        else {
            extracted2(index, tamPag, elem, totalPaginas);
        }
    }

    private void extracted2(int index, int tamPag, int elem, int totalPaginas) {
        String opcao;
        do {
            this.showMenu(index, tamPag, elem);
            this.showOpcoes(totalPaginas, index + 1);
            opcao = LeituraDados.lerString();
            String opcaoTemp = opcao.toUpperCase();

            switch (opcaoTemp) {
                case "+":
                    index = this.avancaPagina(index, totalPaginas);
                    break;
                case "-":
                    index = this.recuaPagina(index);
                    break;
                case "S":
                    opcao = "S";
                    break;
                default:
                    if(opcao.charAt(0)=='e'){
                        List<String> l = new ArrayList<>();
                        l.add("PedeEncomenda");
                        l.add(opcao);
                        this.controller.processa(l);
                        opcao = "S";
                    }
                    else {
                        logger.log(Level.INFO, ("Op��o Invalida"));
                    }
                    break;
            }
        }
        while (!opcao.equals("S"));
    }

    private void extracted1(int index, int tamPag, int totalPaginas) {
        String opcao;
        do {
            this.showVazio(tamPag);
            this.showOpcoes(totalPaginas, index + 1);
            opcao = LeituraDados.lerString();
            opcao = opcao.toUpperCase();
        }
        while (!opcao.equals("S"));
    }
}
