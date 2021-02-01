package projeto.views;
import projeto.avisos.AvisoEncomendaEntregue;
import projeto.avisos.AvisoOrcamentoRecebido;
import projeto.controllers.ControllerUtilizador;
import projeto.encomenda.LinhaDeEncomenda;
import projeto.exceptions.EntidadeNaoExisteException;
import projeto.exceptions.ListaVaziaException;
import projeto.interfaces.*;
import projeto.util.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que implementa a View do Cliente.
 * É onde os dados solicitados do model são exibidos.
 * É a camada de interface com o usuário.
 * É utilizada para receber a entrada de dados e apresentar visualmente o resultado.
 */
public class ViewUtilizador {
    private final ControllerUtilizador controller;
    static String a = "Ups! %s";
    // Create a Logger
    Logger logger
            = Logger.getLogger(
            ViewUtilizador.class.getName());
    /**
     * Construtor parametrizado da ViewCliente.
     */
    public ViewUtilizador(ControllerUtilizador c){
        this.controller = c;
    }

    /**
     * Método de I/O que apresenta ao cliente as varias açoes que ele pode efetuar.
     */
    public void menuUtilizador() {
        int ciclo = 0;
        this.trataNotificacoes();

        try {
            do {
                logger.log (Level.INFO, "O que pretende fazer?");
                logger.log (Level.INFO, "\t1 -> Criar uma nova encomenda.");
                logger.log (Level.INFO, "\t2 -> Ver o histórico de Encomendas");
                logger.log (Level.INFO, "\t3 -> Ver os 10 clientes que mais usaram esta APP");
                logger.log (Level.INFO, "\t4 -> Classificar Voluntario/Empresa");
                logger.log (Level.INFO, "\t5 -> Definiçoes");
                logger.log (Level.INFO, "\t0 -> Logout");
                int opcao = Input.lerInt ();
                switch (opcao) {
                    case 1:
                        this.criaEncomenda ();
                        this.controller.gravar ();
                        break;
                    case 2:
                        this.mostraHistorico ();
                        break;
                    case 3:
                        this.mostraTop10 ();
                        break;
                    case 4:
                        this.classificar ();
                    case 5:
                        this.definicoes ();
                        break;
                    case 0:
                        ciclo = 1;
                        logger.log (Level.INFO, "Volte sempre!");
                        break;
                    default:
                        if (logger.isLoggable (Level.FINE))
                            logger.log (Level.INFO, String.format ("Ups! Opção Inválida. A opção %d não existe!", opcao));
                        break;
                }
            } while (ciclo == 0);
        } catch (ListaVaziaException | IOException exc) {
            logger.log (Level.INFO, String.format (a, exc.getMessage ()));
        }
    }

    /**
     * Método que percorre todas as notificações do Cliente e "trata-as", ou seja,
     * para o caso dos AvisoOrcamentoRecebido mostra o orçamento que a empresa fez pela entrega da sua encomenda
     * e para os outros simplesmente printa ao utilizador o que diz na notificação.
     */
    private void trataNotificacoes() {
        Collection<IAviso> avisos = this.controller.getAvisos();
        if(!avisos.isEmpty()) {
            for (IAviso b : avisos) {
                logger.log(Level.INFO,
                        a);
                if (b instanceof AvisoOrcamentoRecebido) {
                    logger.log(Level.INFO,"Deseja aceitar? (true para aceitar, false para recusar)");
                    boolean sn = Input.lerBoolean();
                    this.controller.trataEncomendaEmpresa(b, sn);
                } else if (b instanceof AvisoEncomendaEntregue) {
                    AvisoEncomendaEntregue a2 = (AvisoEncomendaEntregue) b;
                    int avaliacao;
                    do {
                        if(logger.isLoggable (Level.FINE))
                            logger.log(Level.INFO, String.format ("Avalie a entrega da encomenda %s entregue por %s (0 a 10)", a2.getIdEncomenda (), a2.getIdVoluntario ()));
                        avaliacao = Input.lerInt();
                        if (avaliacao > 10 || avaliacao < 0) logger.log(Level.INFO,"Avaliação inválida!");
                    } while(avaliacao > 10 || avaliacao < 0);
                    this.controller.avalia(a2.getIdVoluntario(), avaliacao);
                }
                logger.log(Level.INFO,"Prima enter para continuar a ver as notificações :)");
                Input.lerString();
                this.controller.removeNotificacao(b);
            }
        } else logger.log(Level.INFO,"Nao tem notificações!!");
    }

    /**
     * Metodo que cria uma encomenda com um respetivo id, conforme a loja e produtos que o cliente selecionou.
     * A encomenda é depois guardada no registo do utilizador e no registo de encomendas do trazAqui, para que as empresas
     * consigam procurar e oferecer um orçamento.
     * Todos os voluntarios que "possam" fazer a entrega são notificados.
     */
    private void criaEncomenda() {
        Collection<String[]> lojas = this.controller.getLojas();
        logger.log(Level.INFO,"Lojas disponiveis: ");
        for (String[] s : lojas) {
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("\tID: %s -> %s", s[0], s[1]));
        }
        logger.log(Level.INFO,"Escolha uma loja (escreva o id da loja pretendida)");
        String lojaID = Input.lerString();

        try {
            List<IProduto> c = (List<IProduto>) this.controller.getLojaProds(lojaID);
            int i = 1;
            // Mostrar ao utilizador os produtos disponiveis na loja.
            logger.log(Level.INFO,"Produtos disponiveis:");
            for(IProduto p : c) {
                if(logger.isLoggable (Level.FINE))
                    logger.log(Level.INFO, String.format ("%d -> %s --> %s euros.", i, p.getNome (), p.getPreco ()));
                i++;
            }

            // O cliente tem de escolher os produtos que quer encomendar
            Collection<LinhaDeEncomenda> list = new ArrayList<>();
            aux(c,list);
            IEncomenda e = this.controller.criaEncomenda(lojaID, list);
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("A encomenda foi feita com sucesso e foi lhe atribuido o id => %s", e.getID ()));
            logger.log(Level.INFO,"Prima enter para continuar :)");
            Input.lerString();
        } catch (EntidadeNaoExisteException exc) {
            logger.log (Level.INFO, String.format (a, exc.getMessage ()));
            this.criaEncomenda();
        }

    }

    private void aux(List<IProduto> c,Collection<LinhaDeEncomenda> list){
        int i = 1;
        while(i != 0) {
            logger.log(Level.INFO,"Digite o numero do produto que deseja comprar (ou 0 para sair)");
            i = Input.lerInt();
            if (i > 0 && i < c.size()) {
                logger.log(Level.INFO,"Digite a quantidade que deseja comprar:");
                int q = Input.lerInt();
                if(q > 0){
                    LinhaDeEncomenda l = this.controller.criaLinhaEncomenda(c.get(i-1), q);
                    list.add(l);
                }
                else
                if(logger.isLoggable (Level.FINE))
                    logger.log(Level.INFO, String.format ("Não pode comprar %d produtos!!", q));
            }
            else if (i != 0 && logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("A opcao %d não é uma opção válida!", i));
        }
    }
    /**
     * Método que mostra ao Utilizador o seu histórico de compras.
     * E, caso o Utilizador queira, mostra os detalhes sobre todas as encomendas.
     */
    private void mostraHistorico() throws ListaVaziaException {
        logger.log(Level.INFO,"Historico de Encomendas:");
        Collection<String> h = this.controller.historicoEncomendas();

        for (String s : h) {
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("\t%s", s));
        }
        try {
            logger.log(Level.INFO,"Escreva o id de uma das compras para mais detalhes ou 0 para sair");
            String id = Input.lerString();
            if(!id.equals("0")) {
                String encomenda = this.controller.getEncomenda(id);
                logger.log(Level.INFO,encomenda);
            }
        } catch (EntidadeNaoExisteException exc) {
            logger.log (Level.INFO, String.format (a, exc.getMessage ()));
            this.mostraHistorico();
        }
    }

    /**
     * Método que mostra os 10 clientes que mais encomendas realizaram nesta app.
     */
    private void mostraTop10() {
        Collection<String> top = this.controller.top10Clientes();
        int i = 1;
        for(String s : top) {
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("%d -> %s", i, s));
            i++;
        }
    }

    /**
     * Método que permite, ao Utilizador, alterar os seus dados pessoais.
     */
    private void definicoes() throws IOException {
        int opcao = 1;
        while(opcao != 0) {
            logger.log(Level.INFO,"DEFINIÇÕES:");
            logger.log(Level.INFO,"\t1 -> Mudar o nome");
            logger.log(Level.INFO,"\t2 -> Mudar a localizacao ");
            logger.log(Level.INFO,"\t3 -> Mudar o NIF");
            logger.log(Level.INFO,"\t4 -> Ver o meu perfil");
            logger.log(Level.INFO,"\t5 -> Apagar a conta");
            logger.log(Level.INFO,"\t0 -> Voltar ao menu anterior");
            opcao = Input.lerInt();
            switch (opcao) {
                case 1:
                    logger.log(Level.INFO,"Insira o seu novo nome:");
                    String nome = Input.lerString ();
                    this.controller.changeName (nome);
                    this.controller.gravar ();
                    break;
                case 2:
                    float lon = this.getLongitude ();
                    float lat = this.getLatitude ();
                    this.controller.changeLocal (lat, lon);
                    this.controller.gravar ();
                    break;
                case 3:
                    logger.log(Level.INFO,"Novo NIF:");
                    String nif = Input.lerString ();
                    this.controller.changeNIF (nif);
                    this.controller.gravar ();
                    break;
                case 4:
                    IUtilizador u = this.controller.getUtilizador ();
                    if(logger.isLoggable (Level.FINE)) {
                        logger.log (Level.INFO, String.format ("ID: %s", u.getId ()));
                        logger.log (Level.INFO, String.format ("Nome: %s", u.getNome ()));
                        logger.log (Level.INFO, String.format ("NIF: %s", u.getNIF ()));
                        logger.log (Level.INFO, u.getLocalizacao ().toString ());
                    }
                    logger.log(Level.INFO,"Prima Enter para continuar");
                    Input.lerString ();
                    break;
                case 5:
                    this.removerConta ();
                    break;
                case 0:
                    break;
                default:
                    opcao = -1;
                    logger.log(Level.INFO,"Opção inválida!!!");
                    break;
            }
        }
    }

    /**
     * Metodo que permite ao cliente classificar o voluntario ou empresa que efetuou o transporte da sua encomenda.
     */
    private void classificar() throws ListaVaziaException{
        logger.log(Level.INFO,"Historico de Encomendas:");
        this.mostraHistorico();
        logger.log(Level.INFO,"Escreva o id da encomenda que pretende classificar.");
        String idEnc = Input.lerString();
        logger.log(Level.INFO,"Qual a classificaçao que pretende dar a este trabalhador?");
        int classificacao = Input.lerInt();
        this.controller.classificar(idEnc, classificacao);
    }

    /**
     * Método que permite ao Utilizador remover a conta.
     */
    private void removerConta() throws IOException {
        logger.log(Level.INFO,"Tem a certeza que quer remover a conta? (true para remover, false para nao remover):");
        boolean remove = Input.lerBoolean();
        if(remove) {
            this.controller.removeConta();
            this.controller.gravar();
            logger.log(Level.INFO,"Conta removida com sucesso!");
        }
    }

    /**
     * Método que pede a latitude ao utilizador e verifica se é válida.
     * Uma vez que isto era repetido várias vezes neste código decidimos
     * criar este método para evitar repetição e tornar o código mais perceptivel.
     */
    private float getLatitude() {
        float ret = f(1);
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