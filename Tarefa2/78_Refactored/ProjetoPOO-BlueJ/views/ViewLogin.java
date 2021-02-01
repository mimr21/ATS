package views;

import controllers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewLogin {

    public static final String CREAT = "Creat";
    public static final String INSIRA_O_EMAIL = "Insira o email:";
    public static final String INSIRA_A_PASSWORD = "Insira a password:";
    public static final String INSIRA_A_SUA_LONGITUDE_COORDENADA_EM_X = "Insira a sua Longitude (coordenada em X)";
    public static final String INSIRA_A_SUA_LATITUDE_COORDENADA_EM_Y = "Insira a sua Latitude (coordenada em Y)";
    private static Logger logger = Logger.getLogger(ViewLogin.class.getName());

    /**
     * Variaveis Instancia
     */
    private ControllerLogin controller;

    /**
     * Construtor Parametrizado de View_Historico
     * Aceita como parâmetros os valores para cada Variável de Instância
     * @param controller
     */
    public ViewLogin(ControllerLogin controller){
        this.controller = controller;
    }

    /**
     * Sub menu correspondente a criacao de conta de um Utilizador
     */
    private void criarContaUtilizador(){
        List<String> l = new ArrayList<>();
        l.add(CREAT);
        l.add("Utilizador");
        logger.log(Level.INFO, (INSIRA_O_EMAIL));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, (INSIRA_A_PASSWORD));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, ("Insira o seu primeiro Nome"));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, ("Insira o seu ultimo Nome"));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, (INSIRA_A_SUA_LONGITUDE_COORDENADA_EM_X));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, (INSIRA_A_SUA_LATITUDE_COORDENADA_EM_Y));
        l.add(LeituraDados.lerDoubleComoString());
        this.controller.processa(l);
    }

    /**
     * Sub menu correspondente a criacao de conta de um Voluntario
     */
    private void criarContaVoluntario(){
        List<String> l = new ArrayList<>();
        l.add(CREAT);
        l.add("Voluntario");
        logger.log(Level.INFO, (INSIRA_O_EMAIL));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, (INSIRA_A_PASSWORD));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, ("Insira o seu primeiro Nome"));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, ("Insira o seu ultimo Nome"));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, (INSIRA_A_SUA_LONGITUDE_COORDENADA_EM_X));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, (INSIRA_A_SUA_LATITUDE_COORDENADA_EM_Y));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, ("Insira o seu raio de a��o"));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, ("Tem certificado para Transporte de medicamentos? (S/N)"));
        l.add(LeituraDados.lerString());
        this.controller.processa(l);
    }

    /**
     * Sub menu correspondente a criacao de conta de uma Transportadora
     */
    private void criarContaTransportadora(){
        List<String> l = new ArrayList<>();
        l.add(CREAT);
        l.add("Transportadora");
        logger.log(Level.INFO, (INSIRA_O_EMAIL));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, (INSIRA_A_PASSWORD));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, ("Insira o nome da Empresa"));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, ("Insira o nif da Empresa"));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, (INSIRA_A_SUA_LONGITUDE_COORDENADA_EM_X));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, (INSIRA_A_SUA_LATITUDE_COORDENADA_EM_Y));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, ("Insira a o pre�o medio por kilometro da sua empresa."));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, ("Insira o raio de a��o da sua Empresa."));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, ("Tem certificado para Transporte de medicamentos? (S/N)"));
        l.add(LeituraDados.lerString());
        this.controller.processa(l);
    }

    /**
     * Sub menu correspondente a criacao de conta de uma Loja
     */
    private void criarContaLoja(){
        List<String> l = new ArrayList<>();
        l.add(CREAT);
        l.add("Loja");
        logger.log(Level.INFO, (INSIRA_O_EMAIL));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, (INSIRA_A_PASSWORD));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, ("Insira o nome da loja"));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, (INSIRA_A_SUA_LONGITUDE_COORDENADA_EM_X));
        l.add(LeituraDados.lerDoubleComoString());
        logger.log(Level.INFO, (INSIRA_A_SUA_LATITUDE_COORDENADA_EM_Y));
        l.add(LeituraDados.lerDoubleComoString());
        this.controller.processa(l);
    }

    /**
     * Funcao que corre a view com todas as funcoes anterioes, de maneira
     * a interligar os diferentes processos
     */
    public void run(){
        String opcao;
        do{
            logger.log(Level.INFO, (""));
            logger.log(Level.INFO, (""));
            logger.log(Level.INFO, ("Bem Vindo ao TrazAqui"));
            logger.log(Level.INFO, (""));
            logger.log(Level.INFO, ("1-> Fazer login"));
            logger.log(Level.INFO, ("2-> Criar Conta"));
            logger.log(Level.INFO, (""));
            logger.log(Level.INFO, ("S-> sair"));
            logger.log(Level.INFO, ("Escolha a op��o pretendida."));
            opcao = LeituraDados.lerString();
            opcao = opcao.toUpperCase();
            switch (opcao){
                case "1" :
                    opcao = case1();
                    break;
                case "2" :
                    opcao = case2(opcao);
                    break;
                case "S" :
                    break;
                default:break;
            }
        }while (!opcao.equals("S"));
    }

    private String case2(String opcao) {
        logger.log(Level.INFO, ("1- Sou um Utilizador Normal."));
        logger.log(Level.INFO, ("2- Sou um Voluntario."));
        logger.log(Level.INFO, ("3- Sou uma empresa Transportadora."));
        logger.log(Level.INFO, ("4- Sou uma loja."));
        logger.log(Level.INFO, ("Insira o seu tipo:"));
        String opc = LeituraDados.lerIntComoString();

        switch (opc.toUpperCase()) {
            case "1": criarContaUtilizador(); opcao = "S"; break;
            case "2": criarContaVoluntario(); opcao = "S"; break;
            case "3": criarContaTransportadora(); opcao = "S"; break;
            case "4": criarContaLoja(); opcao = "S"; break;
            default: logger.log(Level.INFO, ("Comando Invalido")); break;
        }
        return opcao;
    }


    private String case1() {
        String opcao;
        List<String> l = new ArrayList<>();
        l.add("Login");
        logger.log(Level.INFO, ("Insira o seu email;"));
        l.add(LeituraDados.lerString());
        logger.log(Level.INFO, ("Insira a password;"));
        l.add(LeituraDados.lerString());
        this.controller.processa(l);
        opcao = "S";
        return opcao;
    }
}
