package views.utilizador;
import controllers.utilizador.ControllerUtilizadorGeraLinhaEncomenda;
import models.loja.Produto;
import views.LeituraDados;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewUtilizadorGeraLinhaEncomenda {

    public static final String INSIRA = "Insira: ";
    public static final String P_GINA_D_D = "P�gina %d/%d ";
    Logger logger = Logger.getLogger(ViewUtilizadorGeraLinhaEncomenda.class.getName());

    /**
     * Variaveis Instancia
     */
    private List<Produto> produtos;
    private ControllerUtilizadorGeraLinhaEncomenda controller;

    /**
     * Construtor Parametrizado de View_UtilizadorGeraLinhaEncomenda
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewUtilizadorGeraLinhaEncomenda(List<Produto> produtos, ControllerUtilizadorGeraLinhaEncomenda controller){
        this.produtos = produtos;
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
     * Apresenta no ecra as opcoes do Utilizador na seccao de gerar uma linha de Encomenda
     *
     * @param totalPaginas representa total de paginas
     * @param paginaAtual representa a pagina atual
     */
    private void showOpcoes(int totalPaginas, int paginaAtual){
        if(totalPaginas<=1){
            logger.log(Level.INFO, ("Insira: Codigo do produto desejado | S finalizar encomenda"));
        }
        else {
            case100(totalPaginas, paginaAtual);
        }
    }

    private void case100(int totalPaginas, int paginaAtual) {
        if(paginaAtual == 1){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | Codigo do produto desejado | S finalizar encomenda"));
        }
        else{
            case101(totalPaginas, paginaAtual);
        }
    }

    private void case101(int totalPaginas, int paginaAtual) {
        if(paginaAtual == totalPaginas){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| - p�gina anterior | Codigo do produto desejado | S finalizar encomenda"));
        }
        else{
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,P_GINA_D_D, paginaAtual, totalPaginas,"| + pr�xima p�gina | - p�gina anterior | Codigo do produto desejado | S finalizar encomenda"));
        }
    }

    /**
     * Apresenta no ecra o menu de Gerar uma Linha Encomenda
     *
     * @param index correspondente ao indice
     * @param tamPag correspondente ao tamanho da pagina
     * @param elem correspondente aos elementos
     */
    private void showMenu(int index, int tamPag, int elem){
        int pos = (index*tamPag);
        for (int i=0; i<tamPag; i++){
            if(pos<elem){
                Produto p = this.produtos.get(pos);
                logger.log(Level.INFO, (p.getCodigoProduto()+" -> "+p.getNomeProduto()+" ("+p.getPreco()+" euros)"));
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
        int elem = this.produtos.size();
        int i = (elem % 8 == 0) ? elem / 8 : (elem / 8) + 1;
        int totalPaginas = (elem<8)?1: i;

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
                    opcao = case2();
                    break;
                default:
                    case1(opcao);
                    break;
            }
        }
        while (!opcao.equals("S"));
    }


    private String case2() {
        String opcao;
        List<String> l = new ArrayList<>();
        l.add("Finaliza");
        this.controller.processa(l);
        logger.log(Level.INFO, ("Encomenda finalizada com sucesso."));
        opcao = "S";
        return opcao;
    }

    private void case1(String opcao) {
        if(opcao.charAt(0)=='p'){
            List<String> l = new ArrayList<>();
            l.add("Adiciona");
            l.add(opcao);
            logger.log(Level.INFO, ("Insira a quantidade desejada:"));
            l.add(LeituraDados.lerDoubleComoString());
            this.controller.processa(l);
        }
        else{
            logger.log(Level.INFO, ("Codigo produto invalido."));
        }
    }
}
