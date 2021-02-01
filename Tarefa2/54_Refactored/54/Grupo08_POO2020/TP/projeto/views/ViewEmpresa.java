package projeto.views;
import projeto.controllers.ControllerEmpresa;
import projeto.interfaces.*;
import projeto.util.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que implementa a View da Empresa.
 * A view provoca interações com o usuário, que interage com o Controller e
 * é onde os dados solicitados do model são exibidos.
 */
public class ViewEmpresa {
    private final ControllerEmpresa controller;
    static String gg = "Opcao inválida!";
    static String yu = "%d -> %s";
    // Create a Logger
    Logger logger
            = Logger.getLogger(
            ViewEmpresa.class.getName());
    /**
     * Construtor parametrizado da ViewEmpresa
     */
    public ViewEmpresa(ControllerEmpresa controller) {
        this.controller = controller;
    }
static String qV = "qual o valor para N?";
    public void menuEmpresa() {
        int ciclo = 1;

        do {
            try {
                logger.log (Level.INFO, "\n" +
                                        "Bem vindo ao Menu da Empresa. Que tarefa pretende realizar?\n" +
                                        "\n" +
                                        "1 -> Notificacoes\n" +
                                        "2 -> Transportar Encomendas\n" +
                                        "3 -> Os N Clientes que mais aceitaram o transporte da empresa\n" +
                                        "4 -> Verificar a distância total percorrida (em kms)\n" +
                                        "5 -> As N empresas que mais utilizaram a aplicação (em distância percorrida)\n" +
                                        "6 -> As N empresas que mais utilizaram a aplicação (em número de encomendas)\n" +
                                        "7 -> Encomendas por aceitar\n" +
                                        "8 -> Faturacao Periódica\n" +
                                        "9 -> Definicoes\n" +
                                        "0 -> Logout");
                ciclo=menu(ciclo);
            } catch (IOException e) {
                logger.log (Level.INFO, String.format ("Ups! %s", e.getMessage ()));
            }
        } while (ciclo == 1);
    }

        private int menu(int ciclo) throws IOException {
            int num = Input.lerInt ();
            switch (num) {
                case 1:
                    this.menuNotificacoes ();
                    break;
                case 2:
                    this.tranportarEncomenda ();
                    break;
                case 3:
                    logger.log (Level.INFO, qV);
                    int n = Input.lerInt ();
                    this.topNClientsEmpresa (n);
                    break;
                case 4:
                    if (logger.isLoggable (Level.FINE))
                        logger.log (Level.INFO, String.format ("A empresa percorreu %s kms.", this.controller.getDistTotalEmpresa ()));
                    logger.log (Level.INFO, "Prima Enter para continuar!");
                    Input.lerString ();
                    break;
                case 5:
                    logger.log (Level.INFO, qV);
                    int nn = Input.lerInt ();
                    Collection<String> emps = this.controller.getTopNEmpresasDist (nn);
                    int i = 1;
                    for (String e : emps) {
                        if (logger.isLoggable (Level.FINE))
                            logger.log (Level.INFO, String.format (yu, i, e));
                        i++;
                    }
                    break;
                case 6:
                    logger.log (Level.INFO, qV);
                    int valor = Input.lerInt ();
                    Collection<String> col = this.controller.getTopNEmpresasEnc (valor);
                    int i2 = 1;
                    for (String e : col) {
                        if (logger.isLoggable (Level.FINE))
                            logger.log (Level.INFO, String.format (yu, i2, e));
                        i2++;
                    }
                    break;
                case 7:
                    logger.log (Level.INFO, "Lista de encomendas por aceitar");
                    this.encsPorTransportarEmpresa ();
                    break;
                case 8:
                    this.faturacaoPeriodica ();
                    break;
                case 9:
                    this.alteraDadosEmpresa ();
                    ciclo = 0;
                    break;
                case 0:
                    logger.log (Level.INFO, "A sair...");
                    ciclo = 0;
                    break;
                default:
                    logger.log (Level.INFO, gg);
                    break;
            }
            return ciclo;
        }
    /**
     * Metodo que altera os dados da empresa, caso esta assim o pretenda.
     */
    private void alteraDadosEmpresa() throws IOException {
        logger.log(Level.INFO,"Que dados pretende alterar?");
        logger.log(Level.INFO,"1 -> Nome\n" +
                           "2 -> Localizacao\n" +
                           "3 -> Raio de acao\n" +
                           "4 -> Transporte de medicamentos\n" +
                           "5 -> Capacidade de transporte de encomendas\n" +
                           "6 -> Taxa base\n" +
                           "7 -> NIF\n" +
                           "8 -> Apagar a conta");
        int dados = Input.lerInt();
        switch (dados) {
            case 1:
                logger.log(Level.INFO,"Para que nome deseja alterar?");
                String nome = Input.lerString ();
                this.controller.setNomeEmpresa (nome);
                this.controller.gravar ();
                break;
            case 2:
                float lon = this.getLongitude ();
                float lat = this.getLatitude ();
                this.controller.setLocEmpresa (lat, lon);
                this.controller.gravar ();
                break;
            case 3:
                logger.log(Level.INFO,"Qual é o novo raio de acao?");
                float raio = Input.lerFloat ();
                this.controller.setRaioEmpresa (raio);
                this.controller.gravar ();
                break;
            case 4:
                boolean medic;
                if (!this.controller.getMedicEmpresa ())
                    logger.log(Level.INFO,"Já tem um certificado de transporte de medicamentos? (true caso sim, false caso não)");
                else
                    logger.log(Level.INFO,"Pretende continuar a transportar medicamentos? (true caso sim, false caso não)");
                medic = Input.lerBoolean ();
                this.controller.setMedicEmpresa (medic);
                this.controller.gravar ();
                break;
            case 5:
                logger.log(Level.INFO,"Qual é a nova capacidade de transporte de encomendas");
                int cap = Input.lerInt ();
                this.controller.setCapMaxEmpresa (cap);
                this.controller.gravar ();
                break;
            case 6:
                logger.log(Level.INFO,"Qual é a nova taxa base?");
                float taxa = Input.lerFloat ();
                this.controller.setTaxaEmpresa (taxa);
                this.controller.gravar ();
                break;
            case 7:
                logger.log(Level.INFO,"Qual é o novo NIF?");
                String nif = Input.lerString ();
                this.controller.setNifEmpresa (nif);
                this.controller.gravar ();
                break;
            case 8:
                this.controller.apagaConta ();
                logger.log(Level.INFO,"Conta apagada com sucesso");
                break;
            default:
                logger.log(Level.INFO,gg);
                break;
        }
        logger.log(Level.INFO,"Pretende alterar mais algum dado? (true caso sim, false caso não)");
        boolean changeAgain = Input.lerBoolean();
        if (changeAgain) alteraDadosEmpresa();
    }

    private void menuNotificacoes() throws IOException {
        int i = 1;
        if (this.controller.avisosEmpresa().isEmpty()) {
            logger.log(Level.INFO,"Não existem avisos de momento.");
        } else {
            for (IAviso aviso : this.controller.avisosEmpresa()) {
                if(logger.isLoggable (Level.FINE))
                    logger.log(Level.INFO, String.format (yu, i, aviso.toString ()));
                i++;
            }
            this.controller.gravar();
        }
    }

    /**
     * Metodo que retorna
     */
    public void topNClientsEmpresa(int n) {
        logger.log(Level.INFO,"Lista de Clientes:");
        for(IUtilizador c : this.controller.topNClientesEmpresa(n))
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("ID do Cliente: %s", c.getId ()));
    }

    private void encsPorTransportarEmpresa() {
        int i = 1;
        Collection<IEncomenda> encs = this.controller.encsPorTransportar();
        for(IEncomenda enc : encs) {
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format (yu, i, enc.toString ()));
            i++;
        }
        logger.log(Level.INFO,"\nQuais pretende transportar? (Responda p.e 1,2,3)");
        String input = Input.lerString();
        String[] campos = input.split(",");
        int limit;
        Collection<IEncomenda> chosenEncs = new ArrayList<>();
        for(String s : campos) {
            limit = Integer.parseInt(s);
            if (limit > encs.size()) logger.log(Level.INFO,"Esse número não é valido!");
            else {
                IEncomenda e = this.getEncPos(limit, encs);
                if (e != null) chosenEncs.add(e);
            }
        }
        logger.log(Level.INFO,"Escolheu as seguintes encomendas: ");
        for(IEncomenda e : chosenEncs) {
            logger.log(Level.INFO,e.getID());
            this.controller.userRecebeOrcamento(e);
        }
    }

    public IEncomenda getEncPos(int pos, Collection<IEncomenda> col) {
        Iterator<IEncomenda> it = col.iterator();
        IEncomenda enc = null;
        for (; pos > 0 && it.hasNext(); pos--)
            enc = it.next();
        return enc;
    }

    private void faturacaoPeriodica() {
        logger.log(Level.INFO,"Qual das opcoes pretende escolher?\n" +
                           "1 -> Faturacao dentro de um intervalo de tempo\n" +
                           "2 -> Faturacao num determinado tempo (ano, dia ou mes)");
        int opcao = Input.lerInt();
        switch (opcao) {
            case 1:
                fatIntervalo ();
                break;
            case 2:
                fatTempo ();
                break;
            default:
                logger.log(Level.INFO,gg);
                break;
        }
    }

    private void fatIntervalo() {
        logger.log(Level.INFO,"Introduza o tempo inicial: (Formato: AAAA-MM-DD");
        String tempoI = Input.lerString();
        String[] tInicial = tempoI.split("-");
        int[] ti = new int[3];
        for(int i = 0; i < 3; i++)
            ti[i] = Integer.parseInt(tInicial[i]);
        logger.log(Level.INFO,"Introduza o tempo final: (Formato: AAAA-MM-DD");
        String tempoF = Input.lerString();
        String[] tFinal = tempoF.split("-");
        int[] tf = new int[3];
        for(int i = 0; i < 3; i++)
            tf[i] = Integer.parseInt(tFinal[i]);
        float res = this.controller.getTotalFaturadoPeriodo(ti, tf);
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("O total faturado nesse intervalo é de %s", res));
    }

    public void fatTempo() {
        logger.log(Level.INFO,"Introduza o tempo: (Formato: AAAA-MM-DD");
        String tempo = Input.lerString();
        String[] t = tempo.split("-");
        int[] newT = new int[3];
        for(int i = 0; i < 3; i++)
            newT[i] = Integer.parseInt(t[i]);
        float r = this.controller.getTotalFaturadoPeriodo(newT);
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("O total faturado nesse intervalo é de %s", r));
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


    private void tranportarEncomenda() throws IOException {
        Collection<IEncomenda> encs = this.controller.getEncomendasPT();
        // Primeiro tenho de ver se ele tem encomendas para entregar.
        if(!encs.isEmpty()) {
            int i = 0;
            for (IEncomenda e : encs) {
                if(logger.isLoggable (Level.FINE))
                    logger.log(Level.INFO, String.format (yu, i, e.getID ()));
                i++;
            }
            String escolhas = Input.lerString();
            String[] campos = escolhas.split(",");
            List<IEncomenda> chosenEncs = new ArrayList<>();
            int limit;
            Iterator<IEncomenda> it = encs.iterator();
            for (String s : campos) {
                limit = Integer.parseInt(s);
                IEncomenda encomenda = it.next();
                for (; limit > 0 && it.hasNext(); limit--, it.next())
                    encomenda = it.next();
                chosenEncs.add(encomenda);
            }
            float dist = 0;
            for (IEncomenda e : chosenEncs) {
                dist += this.transportePara(e);
            }
            this.controller.atualizaDist(dist);
            this.controller.gravar();
            logger.log(Level.INFO,"Já foram entregues todas a encomendas");
        }  else {
            logger.log(Level.INFO,"Não tem encomendas para entregar!");
        }
    }

    private float transportePara(IEncomenda e) {
        ILoja l = this.controller.getLoja(e.getLojaID());
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("A encomenda está na loja %s cuja localização é %n %s", l.getId (), l.getLocalizacao ().toString ()));
        mee();
        float tempChegarLoja = Input.lerFloat();

       mee();
        IUtilizador u = this.controller.getCliente(e.getUserID());
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("Agora tem de levar a encomenda para %s %n", u.getLocalizacao ().toString ()));

       mee();
       float tempoLojaCasa = Input.lerFloat();
        float tempo = tempoLojaCasa + tempChegarLoja;
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("Demorou %s minutos a fazer a entrega", tempo));
        float velo = this.controller.calculaVel(tempo, u, l);
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("A sua velocidade foi de %s", velo));
        this.controller.insereVelControl(velo);
        this.controller.notifyUser(u, e);
        this.controller.switch1(e);
        return velo * tempo;
    }

    private void mee(){
        logger.log(Level.INFO,"Quando chegar à loja escreva ok!");
        String ok = "";
        while(!ok.equals("ok")) {
            ok = Input.lerString();
            if(!ok.equals("ok")) logger.log(Level.INFO,"Escreva ok quando chegar (tudo em minusculas)");
        }
        logger.log(Level.INFO,"Quanto tempo demorou a chegar à loja (em minutos)");
    }
}