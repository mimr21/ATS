package projeto.views;

import projeto.controllers.Controller;
import projeto.exceptions.EntidadeNaoExisteException;
import projeto.interfaces.IEmpresa;
import projeto.interfaces.ILoja;
import projeto.interfaces.IUtilizador;
import projeto.interfaces.IVoluntario;
import projeto.util.Input;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Classe que implementa a StartView.
 * A StartView é o menu principal desta app.
 * Esta view é responsável por ajudar o cliente a criar uma conta, ou redirecionar os
 * utilizadores já com conta para as suas views respetivas. Por exemplo, um Utilizador ao fazer login é
 * redirecionado para a ViewUtilizador.
 */
public class StartView {
    private Controller controller;
    static String df = "Ups! ";
    static String ps = "Por fim, a sua password:";
    static String bf = "Conta criada com sucesso! Bem vindo, o seu id é ";
    // Create a Logger
    Logger logger
            = Logger.getLogger(
            StartView.class.getName());
    /**
     * Método que diz ao Utilizador para decidir qual é o metodo a usar para popular o programa.
     */
    public void start() {
        boolean ciclo = false;
        while(!ciclo) {
            logger.log(Level.INFO,"Escolha um ficheiro para popular o programa:");
            logger.log(Level.INFO,"1 -> Leitura dos logs");
            logger.log(Level.INFO,"2 -> Leitura do ficheiro objeto");
            logger.log(Level.INFO,"3 -> Nenhum");
            int escolha = Input.lerInt();
            try {
                switch (escolha) {
                    case 1:
                        this.controller = new Controller (1, false);
                        ciclo = true;
                        logger.log (Level.INFO,"AVISO: A password de todas as entidades é igual ao respetivo id!");
                        this.run (1);
                        break;
                    case 2:
                        this.controller = new Controller (2, true);
                        ciclo = true;
                        this.run (1);
                        break;
                    case 3:
                        this.controller = new Controller (3, false);
                        ciclo = true;
                        this.run (0);
                        break;
                    default:
                        break;
                }
            } catch (ClassNotFoundException | IOException exc1 ) {
                logger.log(Level.INFO, String.format ("%s%s", df, exc1.getMessage ()));
            }
        }
    }

    /**
     * Função que incializa a o MenuPrincipal, após a escolha do ficheiro de leitura.
     * Dá ao utilizador 3 opções:
     *      - Criar uma conta nova
     *      - Fazer login (que vai redirecionar-los) para outra View
     *      - Desligar a app
     * Caso o inteiro i seja 1, significa que irá ler de um ficheiro. Se for 0, não irá ler de nenhum.
     */
    public void run(int i) {
        int ciclo = 1;
        while(ciclo != 0) {
            logger.log(Level.INFO,"Escolha a sua opçao: ");
            logger.log(Level.INFO,"\t1 -> Criar conta");
            logger.log(Level.INFO,"\t2 -> Login");
            logger.log(Level.INFO,"\t0 -> Sair");
            int opcao = Input.lerInt();
            switch (opcao) {
                case 1:
                    this.criaConta (i);
                    break;
                case 2:
                    logger.log (Level.INFO,"Insira o seu id:");
                    String id = Input.lerString ();
                    logger.log (Level.INFO,"Insira a sua password:");
                    String pw = Input.lerString ();
                    this.login (id, pw);
                    break;
                default:
                    ciclo = 0;
                    logger.log (Level.INFO,"A sair...");
                    break;
            }
        }
    }

    /**
     * Método de I/O que ajuda o utilizador a criar uma conta, conforme o que ele quiser fazer na app.
     */
    private void criaConta(int i) {
        int ciclo = 1;
        while(ciclo != 0) {
            logger.log(Level.INFO," Introduza o seu tipo de entidade: ");
            logger.log(Level.INFO,"\t 1 - Utilizador");
            logger.log(Level.INFO,"\t 2 - Empresa");
            logger.log(Level.INFO,"\t 3 - Loja");
            logger.log(Level.INFO,"\t 4 - Voluntario");
            logger.log(Level.INFO,"\t 0 - Sair");
            int opcao = Input.lerInt();

            try {
                switch (opcao) {
                    case 1:
                        IUtilizador ut = this.criaUtilizador (i);
                        logger.log (Level.INFO,"Foi redirecionado para o menu de Utilizador!");
                        this.controller.changeMenu ("Utilizador", ut.getId (), ut.getPassword ());
                        ciclo = 0;
                        break;
                    case 2:
                        IEmpresa empresa = this.criaEmpresa (i);
                        logger.log (Level.INFO,"Foi redirecionado para o menu de Empresa!");
                        this.controller.changeMenu ("Empresa", empresa.getId (), empresa.getPassword ());
                        ciclo = 0;
                        break;
                    case 3:
                        ILoja loja = this.criaLoja (i);
                        logger.log (Level.INFO,"Foi redirecionado para o menu de Loja!");
                        this.controller.changeMenu ("Loja", loja.getId (), loja.getPassword ());
                        ciclo = 0;
                        break;
                    case 4:
                        IVoluntario voluntario = this.criaVoluntario (i);
                        logger.log (Level.INFO,"Foi redirecionado para o menu de Voluntario!");
                        this.controller.changeMenu ("Voluntario", voluntario.getId (), voluntario.getPassword ());
                        ciclo = 0;
                        break;
                    case 0:
                        ciclo = 0;
                        break;
                    default:
                        logger.log (Level.INFO,"Ups! Opçao Inválida!");
                        break;
                }
            } catch (IOException | EntidadeNaoExisteException exc) {
                logger.log(Level.INFO, String.format ("%s%s", df, exc.getMessage ()));
            }
        }
    }
        
    /**
     * Método interativo com I/O que ajuda o utilizador a criar uma conta na app.
     */
    private IUtilizador criaUtilizador(int i) throws IOException {
        // Id do cliente
        String id = "u" + controller.quantos("Clientes");

        // Aqui começamos a recolher os dados necessários para criar uma conta para o Utilizador.
        logger.log(Level.INFO,"Introduza o seu nome:");
        String nome = Input.lerString();
        float lon = this.getLongitude();
        float lat = this.getLatitude();
        logger.log(Level.INFO,"Introduza o seu nif:");
        String nif = Input.lerString();
        IUtilizador u;
        if (i == 0) {
            logger.log(Level.INFO,ps);
            String pw = Input.lerString();
            u = this.controller.adicionaCliente(id, pw, nome, lat, lon, nif);
        }
        else u = this.controller.adicionaCliente(id, id, nome, lat, lon, nif);
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("%s%s", bf, id));
        return u;
    }

    /**
     * Constrói uma Empresa com base nos dados que recebe.
     * Depois, adiciona-a ao respetivo Map que está definido no model.
     */
     private IEmpresa criaEmpresa(int i) throws IOException {
         // Id da Empresa
         String id =  "t" + controller.quantos("Empresas");

         // Nome da empresa
         logger.log(Level.INFO,"Introduza o nome da empresa:");
         String nome = Input.lerString();
         // Localização
         float lon = this.getLongitude();
         float lat = this.getLatitude();
         // Raio de acao
         logger.log(Level.INFO,"Introduza o seu raio de acao (em kms):");
         float raio = Input.lerFloat();
         // Pode tranportar medicamentos
         logger.log(Level.INFO,"Tem certificado para transportar medicamentos? (true caso sim, false caso nao)");
         boolean medic = Input.lerBoolean();
         // Capacidade maxima de encomendas ao mesmo tempo
         logger.log(Level.INFO,"Capacidade máxima de encomendas distintas que consegue entregar:");
         int cap = Input.lerInt();
         // Taxa que vão cobrar por encomenda
         logger.log(Level.INFO,"Taxa a cobrar por encomenda (caso a taxa seja de 5%, escreva 0,05):");
         float taxa = Input.lerFloat();
         // Nif da empresa
         logger.log(Level.INFO,"NIF:");
         String nif = Input.lerString();
         //Password
         IEmpresa e;
         if (i == 0) {
             logger.log(Level.INFO,ps);
             String pw = Input.lerString();
             e = this.controller.adicionaEmpresa(id, pw, nome, lat, lon, raio, medic, cap, 1+taxa, nif);
         }
         else e = this.controller.adicionaEmpresa(id, id , nome, lat, lon, raio, medic, cap, 1+taxa, nif);
         if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("%s%s", bf, id));
         return e;
     }

    /**
     * Constrói uma Loja com base nos dados que recebe.
     * Depois, adiciona-a ao respetivo Map que está definido no model.
     */
     private ILoja criaLoja(int i) throws IOException{
         // Id da Loja
         String id = "l" + this.controller.quantos("Lojas");

         // Nome da loja
         logger.log(Level.INFO,"Introduza o nome da loja: ");
         String nome = Input.lerString();
         // Localização da Loja
         float lon = this.getLongitude();
         float lat = this.getLatitude();
         //a loja contem informacoes sobre a fila
         logger.log(Level.INFO,"A sua loja contém informaçoes acerca da fila? (true para sim, false para nao): ");
         boolean dadosFila = Input.lerBoolean();
         //password
         ILoja l;
         if (i == 0) {
             logger.log(Level.INFO,ps);
             String pw = Input.lerString();
             l = this.controller.adicionaLoja(id, pw, nome, lat, lon, dadosFila);
         }
         else l = this.controller.adicionaLoja(id, id, nome, lat, lon, dadosFila);
         if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("%s%s", bf, id));
         return l;
     }

    /**
     * Constrói uma Loja com base nos dados que recebe.
     * Depois, adiciona-a ao respetivo Map que está definido no model.
     */
    private IVoluntario criaVoluntario(int i) throws IOException{
        // ID do Voluntário
        String id = "v" + this.controller.quantos("Voluntarios");

        //nome do voluntario
        logger.log(Level.INFO,"Introduza o seu nome:");
        String nome = Input.lerString();
        //onde o voluntario se encontra no momento atual
        float lon = this.getLongitude();
        float lat = this.getLatitude();
        //raio de açao do voluntario
        logger.log(Level.INFO,"Introduza o seu raio de açao:");
        float raio = Input.lerFloat();
        //diz-nos se tem um certificado para puder transportar medicamentos
        logger.log(Level.INFO,"Tem certificado para transportar medicamentos?");
        boolean medic = Input.lerBoolean();
        // Capacidade maxima de encomendas que consegue transportar
        logger.log(Level.INFO,"Quantidade máxima de Encomendas que consegue transportar");
        int cap = Input.lerInt();
        IVoluntario v;
        if (i == 0) {
            logger.log(Level.INFO,ps);
            String pw = Input.lerString();
            v = this.controller.adicionaVoluntario(id, pw, nome, lat, lon, raio, medic, cap);
        }
        else v = this.controller.adicionaVoluntario(id, id, nome, lat, lon, raio, medic, cap);
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("%s%s", bf, id));
        return v;
    }

    /**
     * Método que pede a latitude ao utilizador.
     * Uma vez que isto era repetido várias vezes neste código decidimos
     * criar este método para evitar repetição e tornar o código mais perceptivel.
     */
    private float getLatitude() {
        float ret = f(1);
        while (ret < -90 || ret > 90) {
            ret = Input.lerFloat();
            if(ret < -90 || ret > 90) logger.log(Level.INFO,"Ups! Valor Inválido! Por favor insira um valor entre -90 e 90:");
        }
        return ret;
    }

    /**
     * Método que pede a longitude ao utilizador.
     * Uma vez que isto era repetido várias vezes neste código decidimos
     * criar este método para evitar repetição e tornar o código mais perceptivel.
     */
    private float getLongitude() {
        float ret = f(0);

        while (ret < -180 || ret > 180) {
            ret = Input.lerFloat();
            if(ret < -180 || ret > 180) logger.log(Level.INFO,"Ups! Valor Inválido! Por favor insira um valor entre -180 e 180:");
        }
        return ret;
    }

    private float f(int i){

        if(i==0){
        logger.log(Level.INFO,"Introduza a sua longitude:");}
        else logger.log(Level.INFO,"Introduza a sua latitude:");
        return -200;
    }

    /**
     * Método que faz redirecionamento para outra View, conforme o id do User.
     * @param id - id do user
     */
    private void login(String id, String pw) {

        try {
            switch (id.charAt (0)) {
                case 'l':
                    this.controller.changeMenu ("Loja", id, pw);
                    break;
                case 't':
                    this.controller.changeMenu ("Empresa", id, pw);
                    break;
                case 'u':
                    this.controller.changeMenu ("Utilizador", id, pw);
                    break;
                case 'v':
                    this.controller.changeMenu ("Voluntario", id, pw);
                    break;
                default:
                    logger.log (Level.INFO,"Ups! ID inválido");
                    break;
            }
        } catch (EntidadeNaoExisteException exc) {
            logger.log(Level.INFO, String.format ("%s%s", df, exc.getMessage ()));
        }
    }
}