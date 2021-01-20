package views;

import controllers.IControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewSistema {

    /**
     * Variaveis Instancia
     */
    private IControllers controller;
    private static Logger logger = Logger.getLogger(ViewSistema.class.getName());

    /**
     * Construtor Parametrizado de View_Historico
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewSistema(IControllers controller){
        this.controller = controller;
    }

    /**
     * Apresenta no ecra o menu da seccao Sistema
     */
    private String showMenu(){
        String opcao = "";

        logger.log(Level.INFO, ("Bem vindo ao Sistema"));
        logger.log(Level.INFO, ("1-> Total faturado por uma empresa (loja/Transportadora)."));
        logger.log(Level.INFO, ("2-> Top 10 utilizadores."));
        logger.log(Level.INFO, ("3-> Top 10 empresas transportadoras."));
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
            opcao = this.showMenu();
            switch (opcao){
                case "1" :
                    case1();
                    break;
                case "2" :
                    case2("10Utilizadores");
                    break;
                case "3" :
                    case2("10Transportadoras");
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

    private void case2(String s) {
        List<String> l = new ArrayList<>();
        l.add(s);
        this.controller.processa(l);
    }

    private void case1() {
        List<String> l = new ArrayList<>();
        l.add("Faturado");
        logger.log(Level.INFO, ("Insira o codigo:"));
        l.add(LeituraDados.lerString());

        this.controller.processa(l);
    }
}
