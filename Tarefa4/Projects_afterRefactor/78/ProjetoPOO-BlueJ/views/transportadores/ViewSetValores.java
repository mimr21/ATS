package views.transportadores;

import controllers.*;
import controllers.transportadores.ControllerSetValores;
import models.transportadores.ITransportadores;
import models.transportadores.Transportadora;
import models.transportadores.Voluntario;
import views.LeituraDados;

import java.util.logging.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ViewSetValores {

    /**
     * Variaveis Instancia
     */
    private ITransportadores transportador;
    private IControllers controller;

    Logger logger = Logger.getLogger(ViewSetValores.class.getName());

    /**
     * Construtor Parametrizado de View_SetValores
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewSetValores(ITransportadores t, ControllerSetValores c){
        this.transportador = t;
        this.controller = c;
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos, inserindo o Voluntario
     * informacoes sobre si para ajudar em futuros calculos e decisoes
     */
    private void runVoluntario(){
        String opcao;
        do {
            double vm = this.transportador.getVelocidadeMedia();
            double r = this.transportador.getRaio();
            logger.log(Level.INFO, "1-> Inserir nova velocidade media. ({0})",vm);
            logger.log(Level.INFO, "2-> Inserir novo raio de acao. ({0})",r);
            logger.log(Level.INFO, ("S-> Sair."));
            opcao = LeituraDados.lerString();
            opcao = opcao.toUpperCase();

            List<String> l = new ArrayList<>();
            switch (opcao){
                case "1" :
                    opcao = case1(l, "MudarVelocidade", "Insira a nova velocidade media:");
                    break;
                case "2" :
                    opcao = case1(l, "MudarRaio", "Insira o novo raio de acao:");
                    break;
                case "S" :
                    break;
                default:
                    logger.log(Level.INFO, ("Op��o Inv�lida."));
                    break;
            }
        }
        while (!opcao.equals("S"));
    }

    private String case1(List<String> l, String mudarVelocidade, String s) {
        String opcao;
        l.add(mudarVelocidade);
        logger.log(Level.INFO, (s));
        l.add(LeituraDados.lerDoubleComoString());
        this.controller.processa(l);
        opcao = "S";
        return opcao;
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos, inserindo a Transportadora
     * informacoes sobre si para ajudar em futuros calculos e decisoes
     */
    private void runTransportadora(){
        String opcao;
        do {
            double precoKm = this.transportador.getPrecoPorKm();
            double vm = this.transportador.getVelocidadeMedia();
            double r = this.transportador.getRaio();
            logger.log(Level.INFO, "1-> Inserir novo pre�o medio por kilometro. ({0})",precoKm);
            logger.log(Level.INFO, "2-> Inserir nova velocidade media. ({0})",vm);
            logger.log(Level.INFO, "3-> Inserir novo raio de acao. ({0})",r);
            logger.log(Level.INFO, ("S-> Sair."));
            opcao = LeituraDados.lerString();
            opcao = opcao.toUpperCase();

            List<String> l = new ArrayList<>();
            switch (opcao){
                case "1" :
                    opcao = case1(l, "MudarPreco", "Insira o novo pre�o:");
                    break;
                case "2" :
                    opcao = case1(l, "MudarVelocidade", "Insira a nova velocidade media:");
                    break;
                case "3" :
                    opcao = case1(l, "MudarRaio", "Insira o novo raio de a��o:");
                    break;
                case "S" :
                    break;
                default:
                    logger.log(Level.INFO, ("Op��o Inv�lida."));
                    break;
            }

        }
        while (!opcao.equals("S"));
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos, caso seja Transportadora ou Voluntario
     */
    public void run() {
        if(this.transportador instanceof Transportadora) runTransportadora();
        if(this.transportador instanceof Voluntario) runVoluntario();
    }
}
