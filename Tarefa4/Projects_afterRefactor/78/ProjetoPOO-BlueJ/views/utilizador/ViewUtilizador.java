package views.utilizador;

import controllers.*;
import models.TrazAqui;
import models.utilizador.Utilizador;
import views.LeituraDados;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewUtilizador {

    /**
     * Variaveis Instancia
     */
    private IControllers controller;
    private int notificacoes;

    private TrazAqui sistema;
    private Utilizador utilizador;
    private String nome;
    private String cod;

    Logger logger = Logger.getLogger(ViewUtilizador.class.getName());

    /**
     * Construtor Parametrizado de View_Utilizador
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewUtilizador(IControllers controller, TrazAqui s, String cod, String nome){
        this.controller = controller;
        this.sistema = s;
        this.utilizador = s.getUtilizador(cod);
        this.notificacoes = utilizador.getNumeroNotificacaoes();
        this.nome = nome;
        this.cod = cod;
    }

    /**
     * Recarrega o menu do Utilizador ja com o seu nome e numero de notificacoes
     */
    private void relload(){
        this.utilizador = sistema.getUtilizador(this.cod);
        this.notificacoes = utilizador.getNumeroNotificacaoes();
    }

    /**
     * Apresenta o menu com informacoes importantes ao utilizador
     *
     * @return String correspondente ao que foi intruzido pelo utilizador no input
     */
    private String showMenu(){
        String opcao = "";

        logger.log(Level.INFO, "Bem Vindo :  {0} ",this.nome);
        logger.log(Level.INFO, ("1-> Pedir Encomenda."));
        logger.log(Level.INFO, ("2-> Avaliar Voluntarios/Transportadoras."));
        logger.log(Level.INFO, ("3-> Hist�rico de Encomendas."));
        if(this.notificacoes>0) logger.log(Level.INFO, "4-> Notifica��es ({0}).",this.notificacoes);
        logger.log(Level.INFO, ("S-> Sair"));

        opcao = LeituraDados.lerString();
        return opcao.toUpperCase();
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos
     */
    public void run(){
        String opcao;
        do {
            this.relload();
            opcao = this.showMenu();

            if(opcao.equals("4") && this.notificacoes<=0) opcao = "I";

            List<String> l = new ArrayList<>();
            switch (opcao){
                case "1" :
                    case1(l, "CriaEncomenda");
                    break;
                case "2" :
                    case1(l, "Avaliar");
                    break;
                case "3" :
                    case1(l, "Historico");
                    break;
                case "4" :
                    case1(l, "MensagensPendentes");
                    break;
                case "S" :
                    break;
                default:
                    logger.log(Level.INFO, ("Op��o Invalida."));
                    break;
            }
        }
        while (!opcao.equals("S"));
    }

    private void case1(List<String> l, String criaEncomenda) {
        l.add(criaEncomenda);
        this.controller.processa(l);
    }
}
