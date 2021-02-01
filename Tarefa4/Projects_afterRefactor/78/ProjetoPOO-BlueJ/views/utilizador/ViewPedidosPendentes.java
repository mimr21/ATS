package views.utilizador;


import controllers.IControllers;
import controllers.utilizador.ControllerUtilizadorAceitaPendentes;
import models.sistema.PedidoTransportadora;
import views.LeituraDados;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewPedidosPendentes {

    public static final String INSIRA = "Insira: ";
    Logger logger = Logger.getLogger(ViewPedidosPendentes.class.getName());

    /**
     * Variaveis Instancia
     */
    private List<PedidoTransportadora> pedidosPendentes;
    private List<String> notificacoes;
    private IControllers controllers;

    /**
     * Construtor Parametrizado de View_PedidosPendentes
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewPedidosPendentes(List<PedidoTransportadora> pedidosPendentes, ControllerUtilizadorAceitaPendentes c, List<String> notificacoes){
        this.pedidosPendentes = pedidosPendentes;
        this.controllers = c;
        this.notificacoes = notificacoes;
    }

    /**
     * Maneira como ira ser apresentado no ecra os pedidos pendentes
     * de uma transportadora e onde se analiza o input desta, sabendo
     * assim se pretende aceitar um pedido pendente ou nao. Conforme essa
     * informacao, e "passada" a vez ao controller que tomara conta do programa
     */
    private void listaTransportadorasPendentes(){
        String opcao;

        for (PedidoTransportadora p : this.pedidosPendentes){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, (p.toStringUtilizador()));
            logger.log(Level.INFO, ("Aceita Proposta (S/N) | 1 sair"));
            opcao = LeituraDados.lerString();
            opcao = opcao.toUpperCase();

            List<String> l = new ArrayList<>();
            if(opcao.equals("S")) case1(p, l, "Aceita");
            else if (opcao.equals("N")) case1(p, l, "Rejeita");
        }
    }

    private void case1(PedidoTransportadora p, List<String> l, String aceita) {
        l.add(aceita);
        l.add(p.getCodigoPedido());
        this.controllers.processa(l);
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
     * Apresenta as opcoes da transportadora na seccao de pedidos pendentes no ecra
     *
     * @param totalPaginas representa total de paginas
     * @param paginaAtual representa a pagina atual
     */
    private void showOpcoes(int totalPaginas, int paginaAtual){
        if(totalPaginas<=1){
            logger.log(Level.INFO, ("Insira: S sair | V ver transportadoras pendentes"));
        }
        else {
            case100(totalPaginas, paginaAtual);

        }
    }

    private void case100(int totalPaginas, int paginaAtual) {
        if(paginaAtual == 1){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA, "P�gina %d/%d ", paginaAtual, totalPaginas, "| + pr�xima p�gina | V ver transportadoras pendentes | S sair"));
        }
        else{
            case101(totalPaginas, paginaAtual);
        }
    }

    private void case101(int totalPaginas, int paginaAtual) {
        if(paginaAtual == totalPaginas){
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,"P�gina %d/%d ", paginaAtual, totalPaginas,"| - p�gina anterior | V ver transportadoras pendentes | S sair"));
        }
        else{
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format(INSIRA,"Página %d/%d ", paginaAtual, totalPaginas,"| + próxima página | - página anterior | V ver transportadoras pendentes | S sair"));
        }
    }

    /**
     * Apresenta no ecra menu dos pedidos pendentes
     *
     * @param index correspondente ao indice
     * @param tamPag correspondente ao tamanho da pagina
     * @param elem correspondente aos elementos
     */
    private void showMenu(int index, int tamPag, int elem){
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, (this.pedidosPendentes.size() +" Transportadoras Pendentes."));
        int pos = (index*tamPag);
        for (int i=0; i<tamPag; i++){
            if(pos<elem){
                logger.log(Level.INFO, (this.notificacoes.get(pos)));
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
        int elem = this.notificacoes.size();
        int i = (elem%8==0)?elem/8:(elem/8)+1;
        int totalPaginas = (elem<8)?1:i;

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
                case "V":
                    if(!this.pedidosPendentes.isEmpty()){
                        this.listaTransportadorasPendentes();
                        opcao = "S";
                    }
                    else logger.log(Level.INFO, ("Sem transportadoras Pendentes."));
                    break;
                case "S":
                        break;

                default:break;
            }
        }
        while (!opcao.equals("S"));
    }
}
