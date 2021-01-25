package projeto.views;
import projeto.controllers.ControllerLoja;
import projeto.exceptions.EntidadeNaoExisteException;
import projeto.exceptions.ListaVaziaException;
import projeto.interfaces.IEncomenda;
import projeto.interfaces.IProduto;
import projeto.util.Input;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que implementa a View da Loja.
 * É onde os dados solicitados do model são exibidos.
 * É a camada de interface com o usuário.
 * É utilizada para receber a entrada de dados e apresentar visualmente o resultado.
 */
public class ViewLoja {
    private final ControllerLoja controller;
    // Create a Logger
    Logger logger
            = Logger.getLogger(
            ViewLoja.class.getName());
    /**
     * Construtor parametrizado da ViewLoja.
     */
    public ViewLoja(ControllerLoja c) {
        this.controller = c;
    }

    /**
     * Menu principal que mostra ao utilizador o que pode fazer e como fazer
     */
    public void menuLojas() {
        int ciclo = 0;

        do {
            try {
                logger.log (Level.INFO, "O que pretende fazer?");
                logger.log (Level.INFO, "\t1 -> Declarar uma encomenda como pronta!");
                logger.log (Level.INFO, "\t2 -> Adicionar produtos à lista de produtos");
                logger.log (Level.INFO, "\t3 -> Remover produtos da lista de produtos");
                logger.log (Level.INFO, "\t4 -> Alterar o tamanho da fila");
                logger.log (Level.INFO, "\t5 -> Ver os produtos prontos para entregar");
                logger.log (Level.INFO, "\t6 -> Alterar dados");
                logger.log (Level.INFO, "\t0 -> Logout");
                ciclo= menu(ciclo);
            } catch (ListaVaziaException | IOException exc) {
                logger.log (Level.INFO, String.format ("Ups! %s", exc.getMessage ()));
            }
        } while (ciclo == 0);
    }

    private int menu(int ciclo) throws IOException, ListaVaziaException {
        int opcao = Input.lerInt ();
        switch (opcao) {
            case 1:
                this.prontarEnc ();
                break;
            case 2:
                this.adicionaProdutos ();
                logger.log (Level.INFO, "Produto adicionado!");
                this.controller.gravar ();
                break;
            case 3:
                this.removeProdutos ();
                logger.log (Level.INFO, "Produto Removido!");
                this.controller.gravar ();
                break;
            case 4:
                logger.log (Level.INFO, "Quantas pessoas estao de momento na fila?");
                int tamanhofila = Input.lerInt ();
                this.controller.qtsPessoasAtual (tamanhofila);
                this.controller.gravar ();
                break;
            case 5:
                logger.log (Level.INFO, "Produtos prontos para entregar:");
                Collection<String> h = this.controller.historicoEncomendas ();
                for (String s : h) {
                    if (logger.isLoggable (Level.FINE))
                        logger.log (Level.INFO, String.format ("\t%s", s));
                }
                meeAux ();
                break;
            case 6:
                boolean sair = this.alteraDadosLoja ();
                if (sair) ciclo = 1;
                break;
            case 0:
                ciclo = 1;
                break;
            default:
                if (logger.isLoggable (Level.FINE))
                    logger.log (Level.INFO, String.format ("Ups! Opção Inválida. A opção %d não existe!", opcao));
                break;
        }
        return ciclo;
    }
    private void meeAux(){
        try {
            logger.log(Level.INFO,"Escreva o id de uma das compras para mais detalhes ou 0 para sair");
            String id = Input.lerString ();
            String encomenda = this.controller.getEncomenda (id);
            logger.log(Level.INFO,encomenda);
        } catch (EntidadeNaoExisteException exc) {
            logger.log(Level.INFO,exc.getMessage ());
        }
    }
    private void prontarEnc() throws IOException {
        int i = 1;
        List<IEncomenda> encs = (List<IEncomenda>) this.controller.getEncomendas();
        for(IEncomenda e : encs) {
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("%d -> %s", i, e.getID ()));
            i++;
        }
        logger.log(Level.INFO,"Qual é a encomenda que já está pronta? (0 para sair)");
        int pronta = Input.lerInt();
        if(pronta > 0 && pronta <= encs.size()) {
            IEncomenda e = encs.get(pronta - 1);
            int total = this.controller.prontarEnc(e);
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format ("%d voluntarios receberam notificação sobre esta encomenda!", total));
            this.controller.gravar();
        }
    }

    /**
     * Metodo que adiciona produtos à lista de produtos de uma dada loja.
     */
    private void adicionaProdutos () {
        double peso = 0;
        float preco = 0;
        logger.log(Level.INFO,"Insira o nome produto que pretende adicionar à lista de produtos?");
        String nome = Input.lerString();
        logger.log(Level.INFO,"Insira o peso produto que pretende adicionar à lista de produtos?");
        while (peso <= 0) {
            peso = Input.lerDouble();
            if (peso <= 0 && logger.isLoggable (Level.FINE))
                logger.log (Level.INFO, String.format ("Um produto não pode ter peso %s %n  Insira um valor válido:", peso));
        }
        logger.log(Level.INFO,"Insira o preço produto que pretende adicionar à lista de produtos?");
        while(preco <= 0) {
            preco = Input.lerFloat();
            if(preco <= 0 && logger.isLoggable (Level.FINE))
                    logger.log(Level.INFO, String.format ("Um produto não pode ter preco %s %n Insira um valor válido:", preco));
        }
        logger.log(Level.INFO,"O produto que pretende adicionar é um produto medicinal? (true para sim, false para nao)");
        boolean medicinal = Input.lerBoolean();
        this.controller.adicionaProdutos(nome, peso, preco, medicinal, 0);
    }

    /**
     * Metodo que remove produtos da lista de produtos de uma dada loja.
     */
    private void removeProdutos() {
        Collection<IProduto> prods = this.controller.getProdutos();
        for(IProduto p : prods)
            if(logger.isLoggable (Level.FINE))
                logger.log(Level.INFO, String.format (" -> %s %s", p.getCodigo (), p.getNome ()));
        if(logger.isLoggable (Level.FINE))
            logger.log(Level.INFO,"Insira o codigo do produto que pretende remover da lista de produtos?");
        String codigo = Input.lerString();
        // Aqui devia se verificar se o produto existe mas nao sei fazer isso.
        this.controller.removeProdutoControl(codigo);
    }

    /**
     * Metodo que altera os dados da loja, caso esta assim o pretenda.
     */
    private boolean alteraDadosLoja() throws IOException {
        boolean r = false;
        logger.log(Level.INFO,"Que dados pretende alterar?");
        logger.log(Level.INFO,"1 -> Nome\n" +
                           "2 -> Localizacao\n" +
                           "3 -> Informacao sobre os dados da fila\n" +
                           "4 -> Tempo medio de atendimento\n" +
                           "5 -> Apagar a Conta");
        int dados = Input.lerInt();
        switch (dados) {
            case 1:
                logger.log(Level.INFO,"Para que nome deseja alterar?");
                String nome = Input.lerString ();
                this.controller.setNomeLoja (nome);
                this.controller.gravar ();
                break;
            case 2:
                float lon = this.getLongitude ();
                float lat = this.getLatitude ();
                this.controller.setLocLoja (lat, lon);
                this.controller.gravar ();
                break;
            case 3:
                logger.log(Level.INFO,"Pretende fornecer dados sobre a fila? (true para sim, false para nao)");
                boolean dadosFila = Input.lerBoolean ();
                this.controller.setDFilaControl (dadosFila);
                this.controller.gravar ();
                break;
            case 4:
                float tempA = 0;
                logger.log(Level.INFO,"Qual é o novo tempo de atendimento medio?");
                while (tempA <= 0) {
                    tempA = Input.lerFloat ();
                    if (tempA <= 0 && logger.isLoggable (Level.FINE))
                          logger.log(Level.INFO, String.format ("O tempo médio nao pode ser %s %n Insira um valor válido:", tempA));
                }
                this.controller.setTempMedControl (tempA);
                this.controller.gravar ();
                break;
            case 5:
                logger.log(Level.INFO,"Tem a certeza que deseja apagar a conta? (true para sim, false para nao)");
                r = Input.lerBoolean ();
                if (r) {
                    this.controller.removeConta ();
                    logger.log(Level.INFO,"Conta apagada com sucesso!");
                }
                this.controller.gravar ();
                break;
            default:
                logger.log(Level.INFO,"Opcao inválida!");
                break;
        }
        if(!r) {
            logger.log(Level.INFO,"Pretende alterar mais algum dado? (true caso sim, false caso não)");
            boolean changeAgain = Input.lerBoolean();
            if (changeAgain) alteraDadosLoja();
        }
        return r;
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