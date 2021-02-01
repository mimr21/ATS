package views;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewHistorico {
    public static final String INSIRA = "Insira: ";
    public static final String P_GINA_D_D = "P�gina %d/%d ";
    private static Logger logger = Logger.getLogger(ViewHistorico.class.getName());
    /**
     * Variaveis Instancia
     */
    private List<String> historico;

    /**
     * Construtor Parametrizado de View_Historico
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewHistorico(List<String> l){
        this.historico = new ArrayList<>(l);
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
     * Apresenta no ecra as opcoes da seccao Historico
     *
     * @param totalPaginas representa total de paginas
     * @param paginaAtual representa a pagina atual
     */
    private void showOpcoes(int totalPaginas, int paginaAtual){
        if(totalPaginas<=1){
            logger.log(Level.INFO, ("Insira: S sair"));
        }
        else {
            case100(totalPaginas, paginaAtual);

        }
    }

    private void case100(int totalPaginas, int paginaAtual) {
        if(paginaAtual == 1){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | S sair"));
        }
        else{
            case101(totalPaginas, paginaAtual);
        }
    }

    private void case101(int totalPaginas, int paginaAtual) {
        if(paginaAtual == totalPaginas){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| - p�gina anterior | S sair"));
        }
        else{
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | - p�gina anterior | S sair"));
        }
    }

    /**
     * Apresenta no ecra o menu da seccao Historico
     *
     * @param index correspondente ao indice
     * @param tamPag correspondente ao tamanho da pagina
     * @param elem correspondente aos elementos
     */
    private void showMenu(int index, int tamPag, int elem){
        int pos = (index*tamPag);
        for (int i=0; i<tamPag; i++){
            if(pos<elem){
                logger.log(Level.INFO, (this.historico.get(pos)));
                pos++;
            }else{
                logger.log(Level.INFO, ("---"));
            }
        }
    }

    /**
     * Quando nao houver mais informacao para ser apresentada, sao colocados tracos no ecra
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
        String opcao;
        int index = 0;
        int tamPag = 8;
        int elem = this.historico.size();
        int i = (elem % 8 == 0) ? elem / 8 : (elem / 8) + 1;
        int totalPaginas = (elem<8)?1: i;
        if(elem==0){
            do {
                this.showVazio(tamPag);
                this.showOpcoes(totalPaginas, index + 1);
                opcao = LeituraDados.lerString();
                opcao = opcao.toUpperCase();
            }
            while (!opcao.equals("S"));
        }
        else {
            do {
                this.showMenu(index, tamPag, elem);
                this.showOpcoes(totalPaginas, index + 1);
                opcao = LeituraDados.lerString();
                opcao = opcao.toUpperCase();
                switch (opcao) {
                    case "+":
                        index = this.avancaPagina(index, totalPaginas);
                        break;
                    case "-":
                        index = this.recuaPagina(index);
                        break;
                    case "S":
                        break;
                    default:break;
                }

            }
            while (!opcao.equals("S"));
        }
    }
}
