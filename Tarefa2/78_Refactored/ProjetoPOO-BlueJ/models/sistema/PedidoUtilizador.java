package models.sistema;

import models.encomendas.IEncomendas;
import models.encomendas.LinhaEncomenda;
import models.utilizador.GPS;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoUtilizador implements Serializable {

    /**
     * Variaveis Instancia
     */
    private String codigoPedido;

    //UtilizadorCriou
    private LocalDateTime dataCriacao;
    private String utilizador;
    private GPS gpsUtilizador;

    //Encomenda
    private transient IEncomendas e;

    /**
     * Construtor Parametrizado de PedidoTransportadora
     * @param e IEncomendas
     * @param gpsUtilizador GPS
     * @param t LocalDateTime
     */
    public PedidoUtilizador(IEncomendas e, GPS gpsUtilizador, LocalDateTime t) {

        this.codigoPedido = e.getCodEncomenda();
        this.utilizador = e.getCodUtilizador();
        this.gpsUtilizador = gpsUtilizador.copyGPS(gpsUtilizador);
        this.e = e.copyEncomenda(e);
        this.dataCriacao = t;
    }

    public PedidoUtilizador(){}


    public PedidoUtilizador copyPedidoUtilizador(PedidoUtilizador p) {
        PedidoUtilizador pe = new PedidoUtilizador();
        pe.setCodigoPedido(p.getCodigoPedido());
        pe.setUtilizador(p.getUtilizador());
        pe.setGpsUtilizador(p.getGpsUtilizador());
        pe.setEncomenda(p.getEncomenda());
        pe.setDataCriacao(p.getDataCriacao());
        return pe;
    }

    /**
     * Construtor de c�pia de uma PedidoTransportadora
     * @param p PedidoUtilizador
     */
    public PedidoUtilizador(PedidoUtilizador p) {
        this.codigoPedido = p.getCodigoPedido();
        this.utilizador = p.getUtilizador();
        this.gpsUtilizador = p.getGpsUtilizador();
        this.e = p.getEncomenda();
        this.dataCriacao = p.getDataCriacao();
    }

    /**
     * Devolve codigo do Pedido
     *
     * @return codigo Pedido
     */
    public String getCodigoPedido() {
        return codigoPedido;
    }

    /**
     * Definir novo codigo do Pedido
     *
     * @param codigoPedido correspondente ao novo codigo
     */
    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    /**
     * Devolve data de criacao do pedido
     *
     * @return data de criacao do Pedido Utilizador
     */
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    /**
     * Definir nova data Criacao
     *
     * @param dataCriacao correspondente a nova data
     */
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Devolve utilizador que fez Pedido
     *
     * @return utilizador
     */
    public String getUtilizador() {
        return this.utilizador;
    }

    /**
     * Definir utilizador que fez pedido
     *
     * @param utilizador correspondente ao novo Utilizador
     */
    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    /**
     * Devolve localizacao do Utilizador que fez Pedido
     *
     * @return localizacao do Utilizador
     */
    public GPS getGpsUtilizador() {
        return gpsUtilizador.copyGPS(gpsUtilizador);
    }

    /**
     * Definir localizacao do Utilizador
     *
     * @param gpsUtilizador correspondente a nova Localizacao
     */
    public void setGpsUtilizador(GPS gpsUtilizador) {
        this.gpsUtilizador = gpsUtilizador.copyGPS(gpsUtilizador);
    }

    /**
     * Devolve encomenda associada ao Pedido
     *
     * @return encomenda associada ao Pedido Utilizador
     */
    public IEncomendas getEncomenda() {
        return e.copyEncomenda(e);
    }

    /**
     * Definir a encomenda associada ao Pedido
     *
     * @param e correspondente a nova Encomenda
     */
    public void setEncomenda(IEncomendas e) {
        this.e = e.copyEncomenda(e);
    }

    /**
     * Devolve preco associado a encomenda
     *
     * @return preco da encomenda
     */
    public double getPrecoTotal(){
        return this.e.getLinhasEncomenda().parallelStream().mapToDouble(LinhaEncomenda::getValor).sum();
    }

    /**
     * Permite apresentar no ecra as informacoes necessarias
     *
     * @return String com informacao da Classe
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        double preco = this.e.getLinhasEncomenda().parallelStream().mapToDouble(LinhaEncomenda::getValor).sum();
        List<LinhaEncomenda> l = this.e.getLinhasEncomenda();
        sb.append("Encomenda: ").append(l.toString())
          .append(" | Pre�o Total: ").append(preco).append(" euros.");

        return sb.toString();
    }

    /**
     * Verifica se 2 Objects sao iguais
     *
     * @return true se Object for igual ao PedidoUtilizador
     */

    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        PedidoUtilizador p = (PedidoUtilizador) o;

        return (this.codigoPedido.equals(p.getCodigoPedido()) &&
                this.dataCriacao.equals(p.getDataCriacao()) &&
                this.utilizador.equals(p.getUtilizador()) &&
                this.gpsUtilizador.equals(p.getGpsUtilizador()) &&
                this.e.equals(p.getEncomenda()));
    }

    public int hashCode(){
        return 0;
    }



}
