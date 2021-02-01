package views.loja;

import controllers.*;
import models.loja.Loja;
import models.loja.LojaComFilasEspera;
import models.TrazAqui;
import views.LeituraDados;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewLoja {

    /**
     * Variaveis Instancia
     */
    private IControllers controller;
    private int listaEspera;

    private TrazAqui sistema;
    private Loja loja;
    private String cod;
    private String nome;

    Logger logger = Logger.getLogger(ViewLoja.class.getName());

    /**
     * Construtor Parametrizado de View_Loja
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewLoja(IControllers controller, TrazAqui s, String cod, String nome){
        this.controller = controller;
        this.sistema = s;
        this.loja = this.sistema.getLoja(cod);
        this.listaEspera = this.loja.getListaEspera().size();
        this.nome = nome;
        this.cod = cod;
    }

    /**
     * Recarrega as informacoes nas variaveis de instancia da Loja
     */
    private void relload(){
        this.loja = sistema.getLoja(this.cod);
        this.listaEspera = this.loja.getListaEspera().size();
    }

    /**
     * Apresenta no ecra o menu principal da Loja
     */
    private String showMenu(){
        String opcao = "";

        logger.log(Level.INFO, "Bem Vindo : {0} \n",this.nome);
        logger.log(Level.INFO, ("1-> Adicionar Produto."));
        logger.log(Level.INFO, ("2-> Remover Produto."));
        logger.log(Level.INFO, ("3-> Estatisticas Da Loja."));
        if(this.listaEspera>0) logger.log(Level.INFO, "4-> Pedidos Pendentes ({0}).",this.listaEspera);
        if(this.loja instanceof LojaComFilasEspera) logger.log(Level.INFO, ("5-> Indicar pessoas na fila."));
        logger.log(Level.INFO, (" "));
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

            if(opcao.equals("4") && this.listaEspera<=0) opcao = "I";
            if(opcao.equals("5") && !(this.loja instanceof LojaComFilasEspera)) opcao = "I";

            switch (opcao){
                case "1" :
                    case5();
                    break;
                case "2" :
                    case20("RemoverP");
                    break;
                case "3" :
                    case20("Historico");
                    break;
                case "4" :
                    case20("AceitarPedidos");
                    break;
                case "5" :
                    case21();
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

    private void case21() {
        List<String> l = new ArrayList<>();
        l.add("Espera");
        logger.log(Level.INFO, ("Numero de pessoas:"));
        l.add(LeituraDados.lerIntComoString());
        this.controller.processa( l);
    }

    private void case20(String removerP) {
        List<String> l = new ArrayList<>();
        l.add(removerP);
        this.controller.processa(l);
    }

    private void case5() {
        List<String> l = new ArrayList<>();
        logger.log(Level.INFO, ("Insira o nome do Produto:"));
        String nomeee = LeituraDados.lerString();
        logger.log(Level.INFO, ("Insira o peso do Produto:"));
        String peso = LeituraDados.lerDoubleComoString();
        logger.log(Level.INFO, ("Insira o pre�o do Produto:"));
        String preco = LeituraDados.lerDoubleComoString();
        logger.log(Level.INFO, ("O Produto � um medicamento: (S/N)"));
        String ismedicamento = LeituraDados.lerString();
        boolean isMedicamento = ismedicamento.equalsIgnoreCase("S");

        if(isMedicamento){
            l.add("AdicionarM");
            l.add(nomeee);
            l.add(peso);
            l.add(preco);
            this.controller.processa(l);
        }
        else{
            l.add("Adicionar");
            l.add(nomeee);
            l.add(peso);
            l.add(preco);
            this.controller.processa(l);
        }
    }
}
