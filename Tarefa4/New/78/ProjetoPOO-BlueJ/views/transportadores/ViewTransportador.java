package views.transportadores;

import controllers.*;
import models.transportadores.ITransportadores;
import models.transportadores.Transportadora;
import models.TrazAqui;
import models.transportadores.Voluntario;
import views.LeituraDados;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewTransportador {

    /**
     * Variaveis Instancia
     */
    private IControllers controller;
    Logger logger = Logger.getLogger(ViewTransportador.class.getName());

    private TrazAqui sistema;
    private ITransportadores transportadore;
    private String nome;
    private String cod;

    private boolean estado;
    private boolean ocupado;

    /**
     * Construtor Parametrizado de View_Transportador
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewTransportador(TrazAqui s, String cod, IControllers controller){
        this.sistema = s;
        this.transportadore = s.getTransportador(cod);
        this.controller = controller;
        this.escolheTipoTransportadore();
        this.cod = cod;
    }

    /**
     * Funcao que descobre se se trata de uma transportadora ou voluntario, e conforme
     * o caso ira alterar as informacoes das variaveis de instancia
     */
    private void escolheTipoTransportadore(){
        if(this.transportadore instanceof Voluntario){
            Voluntario v = (Voluntario) this.transportadore;
            this.nome = v.getNomeVoluntario();
        }

        if(this.transportadore instanceof Transportadora){
            Transportadora t = (Transportadora) this.transportadore;
            this.nome = t.getNomeEmpresa();
        }
    }

    /**
     * Recarrega as informacoes nas variaveis de instancia caso seja Voluntario
     */
    private void rellodValoresVoluntario() {
        this.transportadore = sistema.getTransportador(this.cod);
        Voluntario v = (Voluntario) this.transportadore;
        this.estado = v.getDisponivel();
        this.ocupado = !v.getEncomendaAtual().equals("None");
    }

    /**
     * Recarrega as informacoes nas variaveis de instancia caso seja Transportadora
     */
    private void rellodValoresTransportadora() {
        this.transportadore = sistema.getTransportador(this.cod);
        Transportadora t = (Transportadora) this.transportadore;
        this.estado = t.getDisponivel();
        this.ocupado = !t.getCodEncomendaAtual().equals("None");
    }

    /**
     * Apresenta o menu caso seja Voluntario
     *
     * @return input do Voluntario com base nas informacoes que lhe foram apresentadas no ecra
     */
    private String showMenuVoluntario(){
        String opcao = "";

        logger.log(Level.INFO, "Bem Vindo : {0} ", this.nome);
        logger.log(Level.INFO,"Disponivel? {0} ",this.estado);
        logger.log(Level.INFO, "Ocupado? {0} \n",this.ocupado);
        logger.log(Level.INFO, ("1 -> Ver o historico de encomendas aceites."));
        if(!this.ocupado) logger.log(Level.INFO, ("2 -> Mudar Estado."));
        if(this.estado && !this.ocupado) logger.log(Level.INFO, ("A -> Pedir Encomenda para entrega."));
        if(this.ocupado) logger.log(Level.INFO, ("F -> Finalizar entrega."));
        logger.log(Level.INFO, ("V -> Mudar Valores."));
        logger.log(Level.INFO, (" "));
        logger.log(Level.INFO, ("S-> Sair"));

        opcao = LeituraDados.lerString();
        return opcao.toUpperCase();
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos, caso seja Voluntario
     */
    private void runVoluntario(){
        String opcao;
        do {
            this.rellodValoresVoluntario();
            opcao = this.showMenuVoluntario();

            if(opcao.equals("A") && this.ocupado) opcao = "I";
            if(opcao.equals("2") && this.ocupado) opcao = "I";
            if(opcao.equals("F") && !this.ocupado) opcao = "I";

            List<String> l = new ArrayList<>();
            switch (opcao){
                case "1" :
                    case1(l, "Historico");
                    break;
                case "2" :
                    case1(l, "MudarEstado");
                    break;
                case "A" :
                    case1(l, "PedirEncomenda");
                    break;
                case "F" :
                    case1(l, "Finaliza");
                    logger.log(Level.INFO, ("Encomenda Entregue com sucesso."));
                    break;
                case "V" :
                    case1(l, "MudarValores");
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

    private void case1(List<String> l, String historico) {
        l.add(historico);
        this.controller.processa(l);
    }

    /**
     * Apresenta o menu caso seja Transportadora
     *
     * @return input da Transportadora com base nas informacoes que lhe foram apresentadas no ecra
     */
    private String showMenuTransportadora(){
        String opcao = "";

        logger.log(Level.INFO, "Bem Vindo : {0} ", this.nome);
        logger.log(Level.INFO,"Disponivel? {0} ",this.estado);
        logger.log(Level.INFO, "Ocupado? {0} \n",this.ocupado);
        logger.log(Level.INFO, ("1 -> Ver o historico de encomendas aceites."));
        if(!this.ocupado) logger.log(Level.INFO, ("2 -> Mudar Estado."));
        if(this.estado && !this.ocupado) logger.log(Level.INFO, ("A -> Pedir encomenda para entrega."));
        if(this.ocupado && !this.estado) logger.log(Level.INFO, ("F -> Finalizar entrega."));
        logger.log(Level.INFO, ("V -> Mudar Valores."));
        logger.log(Level.INFO, (" "));
        logger.log(Level.INFO, ("S-> Sair"));

        opcao = LeituraDados.lerString();
        return opcao.toUpperCase();
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos, caso seja Transportadora
     */
    private void runTransportadora(){
        String opcao;
        do {
            this.rellodValoresTransportadora();
            opcao = this.showMenuTransportadora();

            if(opcao.equals("A") && this.ocupado) opcao = "I";
            if(opcao.equals("2") && this.ocupado) opcao = "I";
            if(opcao.equals("F") && !this.ocupado && this.estado) opcao = "I";

            List<String> l = new ArrayList<>();
            switch (opcao){
                case "1" :
                    case1(l, "Historico");
                    break;
                case "2" :
                    case1(l, "MudarEstado");
                    break;
                case "A" :
                    case1(l, "PedirEncomenda");
                    break;
                case "F" :
                    case1(l, "Finaliza");
                    logger.log(Level.INFO, ("Encomenda Entregue com sucesso."));
                    break;
                case "V" :
                    case1(l, "MudarValores");
                    break;
                case "S" :
                    break;
                default:
                    logger.log(Level.INFO, ("Op�ao Inv�lida."));
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
        if(this.transportadore instanceof Transportadora) runTransportadora();
        if(this.transportadore instanceof Voluntario) runVoluntario();
    }
}
