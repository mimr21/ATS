package views.utilizador;

import controllers.IControllers;
import models.loja.Loja;
import views.LeituraDados;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewUtilizadorGeraEncomenda {

    public static final String INSIRA = "Insira: ";
    public static final String P_GINA_D_D = "P�gina %d/%d ";
    Logger logger = Logger.getLogger(ViewUtilizadorGeraEncomenda.class.getName());
    /**
     * Variaveis Instancia
     */
    private List<Loja> lojas;
    private IControllers controller;

    /**
     * Construtor Parametrizado de View_UtilizadorGeraEncomenda
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewUtilizadorGeraEncomenda(List<Loja> lojas, controllers.utilizador.ControllerUtilizadorGeraEncomenda controller){
        this.lojas = new ArrayList<>(lojas);
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
     * Apresenta no ecra as opcoes do Utilizador na seccao de gerar uma Encomenda
     *
     * @param totalPaginas representa total de paginas
     * @param paginaAtual representa a pagina atual
     */
    private void showOpcoes(int totalPaginas, int paginaAtual){
        if(totalPaginas<=1){
            logger.log(Level.INFO, ("Insira: Codigo da loja escolhida | S sair"));
        }
        else {
            case100(totalPaginas, paginaAtual);

        }
    }

    private void case100(int totalPaginas, int paginaAtual) {
        if(paginaAtual == 1){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | Codigo da loja escolhida | S sair"));
        }
        else{
            case101(totalPaginas, paginaAtual);
        }
    }

    private void case101(int totalPaginas, int paginaAtual) {
        if(paginaAtual == totalPaginas){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| - p�gina anterior | Codigo da loja escolhida | S sair"));
        }
        else{
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | - p�gina anterior | Codigo da loja escolhida | S sair"));
        }
    }

    /**
     * Apresenta no ecra o menu de Gerar uma Encomenda
     *
     * @param index correspondente ao indice
     * @param tamPag correspondente ao tamanho da pagina
     * @param elem correspondente aos elementos
     */
    private void showMenu(int index, int tamPag, int elem){
        int pos = (index*tamPag);
        for (int i=0; i<tamPag; i++){
            if(pos<elem){
                if(logger.isLoggable (Level.FINE))
                    logger.log(Level.INFO, (this.lojas.get(pos).toString()));
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
        int elem = this.lojas.size();
        int i = (elem%8==0)?elem/8:(elem/8)+1;
        int totalPaginas = (elem<8)?1:i;

        do {
            this.showMenu(index,tamPag,elem);
            this.showOpcoes(totalPaginas,index+1);
            opcao = LeituraDados.lerString();
            String opcaoTemp = opcao.toUpperCase();

            switch (opcaoTemp){
                case "+" :
                    index = this.avancaPagina(index,totalPaginas);
                    break;
                case "-" :
                    index = this.recuaPagina(index);
                    break;
                case "S" :
                    opcao = "S";
                    break;
                default:
                    opcao = case1(opcao);
                    break;
            }

        }
        while (!opcao.equals("S"));
    }

    private String case1(String opcao) {
        if(opcao.charAt(0) == 'l'){
            List<String> l = new ArrayList<>();
            l.add("CriaLinhaEncomenda");
            l.add(opcao);
            this.controller.processa(l);
            opcao = "S";
        }
        else{
            logger.log(Level.INFO, ("Codigo Invalido."));
        }
        return opcao;
    }

}
