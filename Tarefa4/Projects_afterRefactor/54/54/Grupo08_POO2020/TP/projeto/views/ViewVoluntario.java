package projeto.views;

import projeto.avisos.AvisoVoluntario;
import projeto.controllers.ControllerVoluntario;
import projeto.exceptions.EntidadeNaoExisteException;
import projeto.exceptions.ListaVaziaException;
import projeto.interfaces.IAviso;
import projeto.interfaces.IEncomenda;
import projeto.interfaces.ILoja;
import projeto.interfaces.IUtilizador;
import projeto.util.Input;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que implementa a View do Voluntario.
 * A view provoca interações com o usuário, que interage com o Controller e
 * é onde os dados solicitados do model são exibidos.
 */
public class ViewVoluntario {
    private final ControllerVoluntario controller;
    // Create a Logger
    Logger logger
            = Logger.getLogger(
            ViewVoluntario.class.getName());
    /**
     * Construtor parametrizado da ViewVoluntario
     */
    public ViewVoluntario(ControllerVoluntario c) {
        this.controller = c;
    }

    /**
     * Método de I/O que apresenta ao voluntario as varias açoes que ele pode efetuar.
     */
    public void menuVoluntario(){
        int ciclo = 0;

        while (ciclo == 0) {
            try {
                logger.log(Level.INFO,"O que pretende fazer?");
                logger.log(Level.INFO,"\t1 -> Fazer a entrega de uma encomenda");
                logger.log(Level.INFO,"\t2 -> Ver o histórico de encomendas que realizou");
                logger.log(Level.INFO,"\t3 -> Alterar dados");
                logger.log(Level.INFO,"\t4 -> Mostrar avisos");
                logger.log(Level.INFO,"\t0 -> Logout");
                int opcao = Input.lerInt();
                switch (opcao) {
                    case 1:
                        this.fazEntrega ();
                        break;
                    case 2:
                        this.mostraHistorico ();
                        break;
                    case 3:
                        this.alteraDadosVoluntario ();
                        break;
                    case 4:
                        this.avisosVoluntario ();
                        break;
                    case 0:
                        ciclo = 1;
                        break;
                    default:
                        if(logger.isLoggable (Level.FINE))
                            logger.log(Level.INFO, String.format ("Ups! Opção Inválida. A opção %d não existe!", opcao));
                        break;
                }
            } catch (ListaVaziaException | IOException exc) {
                logger.log(Level.INFO, String.format ("Ups! %s", exc.getMessage ()));
            }
        }
    }

    /**
     * Metodo que processa a entrega da encomenda que o voluntario realizará.
     */
    private void fazEntrega() throws IOException {
        logger.log(Level.INFO,"Neste momento, esta disponivel para fazer entregas? (true caso sim, false caso não)");
        logger.log(Level.INFO,"Enquanto faz a entrega não pode utilizar mais nada na app.");
        boolean dispEntregas = Input.lerBoolean();
        if (!dispEntregas) {
            return;
        }
        this.controller.changeEstado();
        // Verificamos se a lista de encomendas do utilizador está vazia ou se tem encomendas por entregar
        if (this.controller.estaVaziaControl ()) {
            logger.log (Level.INFO, "De momento nao existem encomendas a ser entregues!");
        } else {
            aux();
        }
    }

    private void aux() throws IOException {
        // Tenho de percorrer a lista de encomendas e o voluntário escolhe qual vai entregar
        List<IEncomenda> encs = (List<IEncomenda>) this.controller.getEncsPorEntregar();
        int i = 1;
        for (IEncomenda s : encs) {
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("%d -> %s", i, s.getID ()));
            i++;
        }
        logger.log(Level.INFO,"Escolha qual quer transportar!");
        int enc = Input.lerInt();
        IEncomenda e = encs.get(enc - 1);
        ILoja l = this.controller.getLoja(e.getLojaID());
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("A encomenda está na loja %s cuja localização é %n %s", l.getId (), l.getLocalizacao ().toString ()));

        mem();
        float tempChegarLoja = Input.lerFloat();
        IUtilizador u = this.controller.getClienteControl(e.getUserID());
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("A encomenda está na loja %s cuja localização é %n %s", u.getId(), u.getLocalizacao ().toString ()));

        mem();
        float tempoLojaCasa = Input.lerFloat();
        float tempo = tempoLojaCasa + tempChegarLoja;
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("Demorou %s minutos a fazer a entrega", tempo));
        float velo = this.controller.calculaVelo(tempo, u, l);
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO, String.format ("A sua velocidade foi de %s", velo));
        this.controller.insereVelControl(velo);
        this.controller.notifyUser(u, e);
        this.controller.switch1(e);

        this.controller.grava();
    }
private void mem(){
    logger.log(Level.INFO,"Quando chegar à loja escreva ok!");
    String ok = "";
    while(!ok.equals("ok")) {
        ok = Input.lerString();
        if(!ok.equals("ok")) logger.log(Level.INFO,"Escreva ok quando chegar (tudo em minusculas)");
    }
    logger.log(Level.INFO,"Quanto tempo demorou a chegar à loja (em minutos)");

    // Aqui ele já chegou à loja
    logger.log(Level.INFO,"Quando pegar na encomenda escreva ok!"); ok = "";
    while(!ok.equals("ok")) {
        ok = Input.lerString();
        if(!ok.equals("ok")) logger.log(Level.INFO,"Escreva ok quando tiver a encomenda (tudo em minusculas)");
    }
}
    /**
     * Método que mostra o historico de compras ao utilizador.
     */
    private void mostraHistorico() throws ListaVaziaException {
        logger.log(Level.INFO,"Historico de Pedidos:");
        Collection<String> h = this.controller.historicoEncomendas();
        for (String s : h) {
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("\t%s", s));
        }
        try {
            logger.log(Level.INFO,"Escreva o id de uma das compras para mais detalhes ou 0 para sair");
            String id = Input.lerString();
            if(!id.equals("0")) {
                IEncomenda encomenda = this.controller.getEncomenda(id);
                if(logger.isLoggable (Level.FINE))
                    logger.log(Level.INFO,encomenda.toString());
            }
        } catch (EntidadeNaoExisteException exc) { logger.log (Level.INFO, String.format ("Ups! %s", exc.getMessage ()));
        }
    }

    /**
     * Metodo que altera os dados do voluntario caso ele assim o pretenda.
     */
    private void alteraDadosVoluntario() throws IOException {
        logger.log(Level.INFO,"Que dados pretende alterar?");
        logger.log(Level.INFO,"1 -> Nome\n" +
                           "2 -> Localizacao\n" +
                           "3 -> Raio de ação\n" +
                           "4 -> Transporte de medicamentos\n" +
                           "5 -> Capacidade de transporte de encomendas\n" +
                           " 6 -> Apagar a conta");
        int dados = Input.lerInt();
        switch (dados) {
            case 1:
                logger.log(Level.INFO,"Para que nome deseja alterar?");
                String nome = Input.lerString ();
                this.controller.setNomeVoluntario (nome);
                this.controller.grava ();
                break;
            case 2:
                float lon = this.getLongitude ();
                float lat = this.getLatitude ();
                this.controller.setLocVoluntario (lat, lon);
                this.controller.grava ();
                break;
            case 3:
                float raio = 0;
                logger.log(Level.INFO,"Qual é o novo raio de acao?");
                raio=umm(raio);
                this.controller.setRaioVoluntario (raio);
                this.controller.grava ();
                break;
            case 4:
                boolean medic;
                if (this.controller.getMedicControl ()) {
                    logger.log(Level.INFO,"Pretende deixar de transportar medicamentos? (true caso sim, false caso não)");
                } else {
                    logger.log(Level.INFO,"Já tem um certificado de transporte de medicamentos? (true caso sim, false caso não)");
                }
                medic = Input.lerBoolean ();
                this.controller.setMedicVoluntario (medic);
                this.controller.grava ();
                break;
            case 5:
                int cap = 0;
                logger.log(Level.INFO,"Qual é a nova capacidade de transporte de encomendas");
                cap=umm2(cap);
                this.controller.setCapMaxVoluntario (cap);
                this.controller.grava ();
                break;
            case 6:
                this.controller.apagaConta ();
                break;
            default:
                logger.log(Level.INFO,"Opcao inválida!");
                break;
        }
        logger.log(Level.INFO,"Pretende alterar mais algum dado? (true caso sim, false caso não)");
        boolean changeAgain = Input.lerBoolean();
        if (changeAgain) alteraDadosVoluntario();
    }

    private float umm(float raio){
        while (raio <= 0) {
            raio = Input.lerFloat ();
            if (raio <= 0 && logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("O raio nao pode tomar o valor de %s %n Insira um valor válido!", raio));
        }
        return raio;
    }

    private int umm2(int cap){
        while (cap <= 0) {
            cap = Input.lerInt ();
            if (cap <= 0 && logger.isLoggable (Level.FINE))
                logger.log (Level.INFO, String.format ("Nao pode transportar %d %n Insira um valor válido!", cap));
        }
        return cap;
    }

    /**
     * Método que trata dos avisos do Voluntário.
     */
    private void avisosVoluntario() throws IOException {
        Collection<IAviso> col = this.controller.getAvisosVols();
        if (col.isEmpty()) logger.log(Level.INFO,"Não existem avisos de momento.");
        else {
            logger.log(Level.INFO,"Lista de avisos: ");
            for (IAviso aviso : col) {
                AvisoVoluntario a = (AvisoVoluntario) aviso;
                if(this.controller.trataAviso(a)) {
                    if(logger.isLoggable (Level.FINE))
                        logger.log(Level.INFO, String.format ("Deseja entregar a encomenda %s ?", a.getIdEncomenda ()));
                    boolean aceita = Input.lerBoolean();
                    if(aceita) {
                        this.controller.pegaEncomenda(a);
                    }
                }
                this.controller.remNotificacao(a);
            }
            this.controller.grava();
        }
    }

    /**
     * Método que pede a latitude ao utilizador e verifica se é válida.
     * Uma vez que isto era repetido várias vezes neste código decidimos
     * criar este método para evitar repetição e tornar o código mais perceptivel.
     */
    private float getLatitude() {
        float ret =f(1);
        while (ret < -90 || ret > 90) {
            ret = Input.lerFloat();
            if(ret < -90 || ret > 90){
                logger.log(Level.INFO,"Ups! Valor Inválido! Por favor insira um valor entre -90 e 90:");
            }
        }
        return ret;
    }

    /**
     * Método que pede a longitude ao utilizador e verifica se é válida.
     * Uma vez que isto era repetido várias vezes neste código decidimos
     * criar este método para evitar repetição e tornar o código mais perceptivel.
     */
    private float getLongitude() {
        float ret = f(0);
        while (ret < -180 || ret > 180) {
            ret = Input.lerFloat();
            if(ret < -180 || ret > 180){
                logger.log(Level.INFO,"Ups! Valor Inválido! Por favor insira um valor entre -180 e 180:");
            }
        }
        return ret;
    }


    private float f(int i){
        if(i==0){
            logger.log(Level.INFO,"Introduza a sua longitude:");}
        else logger.log(Level.INFO,"Introduza a sua latitude:");
        return -200;
    }

}
