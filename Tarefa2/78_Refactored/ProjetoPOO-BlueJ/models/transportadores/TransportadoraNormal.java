package models.transportadores;

import models.sistema.*;
import models.utilizador.GPS;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class TransportadoraNormal extends Transportadora {

    /**
     * Construtor por Omissao
     */
    public TransportadoraNormal(){
        super();
    }

    /**
     * Construtor Parametrizado de TransportadoraNormal
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public TransportadoraNormal(String a, String b, GPS g, String c, double r, double p) {
        super(a, b, g, c, r, p);
    }

    /**
     * Construtor de copia de uma TransportadoraNormal
     * Aceita como parametros outra TransportadoraNormal e utiliza os metodos
     * de acesso aos valores das Variaveis de Instancia
     */
    public TransportadoraNormal(TransportadoraNormal t){
        super(t);
    }

    public ITransportadores copyTransportadoraNormal(ITransportadores t){
        TransportadoraNormal tr = new TransportadoraNormal();
        tr.setCodEncomendaAtual(((TransportadoraNormal)t).getCodEncomendaAtual());
        tr.setGPS(((TransportadoraNormal)t).getGPS());
        tr.setCodEmpresa(t.getCodigo());
        tr.setClassificacoes(((TransportadoraNormal)t).getClassificacoes());
        tr.setNumAvaliacoes(((TransportadoraNormal)t).getNumAvaliacoes());
        tr.setDisponivel(((TransportadoraNormal)t).getDisponivel());
        tr.setNomeEmpresa(((TransportadoraNormal)t).getNomeEmpresa());
        tr.setNIF(((TransportadoraNormal)t).getNIF());
        tr.setRaio(t.getRaio());
        tr.setPrecoPorKm(t.getPrecoPorKm());
        tr.setVelocidadeMedia(t.getVelocidadeMedia());
        return tr;
    }


    /**
     * Calcula o preco que estara associado ao transporte do Pedido. Esse preco
     * sera afetado por fatores como distancia total a percorrer, uma taxa associada ao peso que
     * a encomenda tem .Uma vez que nunca podera transportar medicamentos, nao tem nenhuma taxa associada
     * ao transporte de medicamentos
     *
     * @param p correspondente ao PedidoLoja
     * @return do preco total do transporte
     */
    public double precoTransporte(PedidoLoja p){
        double distanciaLoja = super.getGPS().distancia(p.getGpsLoja());
        double distanciaUtilizador = p.getGpsLoja().distancia(p.getGpsUtilizador());
        double taxaPeso = super.calculaTaxaPeso(p.getEncomenda().getPeso());

        return ((distanciaLoja + distanciaUtilizador)*super.getPrecoPorKm())*taxaPeso;
    }

    /**
     * Determina o tempo de transporte de uma Encomenda a um Utilizador.
     * Este tempo sera afetado por fatores como distancias quer a loja quer ao utilizador e do tempo de espera na fila de uma loja
     * (caso esta exista). Para alem disso, decidimos inserir fatores de aleatoriedade que afetarao o tempo de transporte.
     * Fatores como estado do clima, estado do pavimento e transito sao fatores que achamos adequados e realistas para
     * afetar o tempo de transporte.
     *
     * @param p correspondente ao PedidoLoja
     * @return tempo associado ao Transporte da encomenda
     */
    public double tempoTransporte(PedidoLoja p) throws NoSuchAlgorithmException {
        double distanciaLoja = super.getGPS().distancia(p.getGpsLoja());
        double distanciaUtilizador = p.getGpsLoja().distancia(p.getGpsUtilizador());
        double taxaEsperaLoja = super.calculaTempoEspera(p.getEstadoEsperaLoja());

        return (((distanciaLoja + distanciaUtilizador)/super.getVelocidadeMedia())+taxaEsperaLoja) * Aleatoridade.geraTaxaAleatoridade();
    }

    /**
     * Uma vez que � uma transportadora normal, nunca ira transportar medicamentos
     *
     * @return false sempre
     */
    public boolean aceitoTransporteMedicamentos(){
        return false;
    }

    /**
     * Ocupa Transportadora com um Pedido, indicando o tempo previsto de transporte
     * e criando um Pedido Transportadora com base no Peido Loja
     *
     * @param p correspondente ao Pedido da Loja
     * @return PedidoTransportadora criado
     */
    public IPedidosTransportadores ocupaTransportadora(PedidoLoja p) throws NoSuchAlgorithmException {
        String tempoPrevisto = String.format("%f horas",this.tempoTransporte(p.copyPedidoLoja(p)));
        PedidoTransportadora pt = new PedidoTransportadora(p,super.getCodigo(),super.getNomeEmpresa(),this.precoTransporte(p),LocalDateTime.now(),LocalDateTime.now(),false, tempoPrevisto,super.getClassificacoes(),this.calculaDistancia(p));
        super.adicionaHistorico((PedidoTransportadora) pt.copyPedidoTransportadora(pt));
        super.setCodEncomendaAtual(pt.getCodigoPedido());
        return pt.copyPedidoTransportadora(pt);
    }

    /**
     * Determina se aceita caracteristicas da encomenda, caso esta contenha algum tipo de
     * medicamento
     *
     * @param p correspondente ao PedidoLoja
     * @return true se aceitar transportar
     */
    public boolean aceitaCaracteristicasEncomenda(PedidoLoja p){
        if(!super.aceitaCaracteristicas(p)) return false;
        if(p.isTemMedicamentos()) return this.aceitoTransporteMedicamentos();
        return true;
    }

    /**
     * Permite apresentar no ecra as informacoes necessarias
     *
     * @return String com informacao da Classe
     */
    @Override
    public String toString(){
        return super.toString();
    }

    /**
     * Verifica se 2 Objects sao iguais
     *
     * @return true se Object for igual a TransportadoraNormal
     */
    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        TransportadoraNormal t = (TransportadoraNormal) o;

        return (super.equals(t));
    }
    @Override
    public int hashCode(){
        return super.hashCode();
    }


}
