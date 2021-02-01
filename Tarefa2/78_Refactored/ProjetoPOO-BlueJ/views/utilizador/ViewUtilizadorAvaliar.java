package views.utilizador;

import controllers.IControllers;
import models.sistema.PedidoCompleto;
import views.LeituraDados;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewUtilizadorAvaliar {

    public static final String INSIRA = "Insira: ";
    public static final String P_GINA_D_D = "P�gina %d/%d ";
    Logger logger = Logger.getLogger(ViewUtilizadorAvaliar.class.getName());


    /**
     * Variaveis Instancia
     */
    private IControllers controller;
    private List<PedidoCompleto> transportadoresParaAvaliar;

    /**
     * Construtor Parametrizado de View_UtilizadorAvaliar
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewUtilizadorAvaliar(List<PedidoCompleto> t, controllers.utilizador.ControllerUtilizadorAvalia controller){
        this.transportadoresParaAvaliar = new ArrayList<>(t);
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
     * Apresenta no ecra as opcoes do Utilizador na seccao de Avaliacao de Transportadores
     *
     * @param totalPaginas representa total de paginas
     * @param paginaAtual representa a pagina atual
     */
    private void showOpcoes(int totalPaginas, int paginaAtual){
        if(totalPaginas<=1){
            logger.log(Level.INFO, ("Insira: Codigo para avaliar | S sair"));
        }
        else {
            case100(totalPaginas, paginaAtual);
        }
    }

    private void case100(int totalPaginas, int paginaAtual) {
        if(paginaAtual == 1){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, (INSIRA +String.format(P_GINA_D_D, paginaAtual, totalPaginas)+"| + pr�xima página | Codigo para avaliar | S sair"));
        }
        else{
            case101(totalPaginas, paginaAtual);
        }
    }

    private void case101(int totalPaginas, int paginaAtual) {
        if(paginaAtual == totalPaginas){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, (INSIRA +String.format(P_GINA_D_D, paginaAtual, totalPaginas)+"| - p�gina anterior | Codigo para avaliar | S sair"));
        }
        else{
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, (INSIRA +String.format(P_GINA_D_D, paginaAtual, totalPaginas)+"| + pr�xima p�gina | - p�gina anterior | Codigo para avaliar | S sair"));
        }
    }

    /**
     * Apresenta no ecra o menu dos transportadores por avaliar
     *
     * @param index correspondente ao indice
     * @param tamPag correspondente ao tamanho da pagina
     * @param elem correspondente aos elementos
     */
    private void showMenu(int index, int tamPag, int elem){
        int pos = (index*tamPag);
        for (int i=0; i<tamPag; i++){
            if(pos<elem){
                case1(pos);
                pos++;
            }else{
                logger.log(Level.INFO, ("---"));
            }
        }
    }

    private void case1(int pos) {
        PedidoCompleto p = this.transportadoresParaAvaliar.get(pos);
        String s = p.getCodigoTransportadora();
        if (s.charAt(0) == 'v') {
            case2(p);
        } else {
            case3(p);
        }
    }

    private void case3(PedidoCompleto p) {
        String tempo = p.calculaTempoTransorte();
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, (String.format("Transportadora: %s -> Entregou em %s por %f euros", p.getCodigoTransportadora(),tempo, p.getPrecoSugerido())));
    }

    private void case2(PedidoCompleto p) {
        String tempo = p.calculaTempoTransorte();
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, (String.format("Voluntario: %s -> Entregou em %s", p.getCodigoTransportadora(),tempo)));
    }

    /**
     * Verifica se o codigo do transportador introduzido pelo utilizador existe na lista
     * de transportadores por avaliar
     *
     * @param cod correspondente ao codigo do transportador
     * @return true se codigo existir na lista
     */
    private boolean codigoValido(String cod){
        boolean res = false;
        Iterator<PedidoCompleto> it = this.transportadoresParaAvaliar.iterator();
        PedidoCompleto s;
        while (it.hasNext() && !res){
            s = it.next();
            res = s.getCodigoTransportadora().equals(cod);
        }
        return res;
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos
     */
    public void run(){
        String opcao;
        int index = 0;
        int tamPag = 8;
        int elem = this.transportadoresParaAvaliar.size();
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
                    opcao = case4(opcao);
                    break;
            }

        }
        while (!opcao.equals("S"));
    }

    private String case4(String opcao) {
        if(this.codigoValido(opcao)){
            List<String> l = new ArrayList<>();
            l.add("Avalia");
            l.add(opcao);
            logger.log(Level.INFO, ("Insira a avalia��o a atribuir :"));
            l.add(LeituraDados.lerIntComoString());
            this.controller.processa(l);
            opcao = "S";
        }
        else{
            logger.log(Level.INFO, ("Codigo invalido."));
        }
        return opcao;
    }
}
