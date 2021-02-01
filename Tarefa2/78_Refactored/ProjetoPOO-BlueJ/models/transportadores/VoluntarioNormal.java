package models.transportadores;

import models.sistema.PedidoLoja;
import models.utilizador.GPS;

import java.io.Serializable;

public class VoluntarioNormal extends Voluntario implements Serializable {


    /**
     * Construtor por Omissao
     */
    public VoluntarioNormal() {
        super();
    }

    /**
     * Construtor Parametrizado de VoluntarioNormal
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public VoluntarioNormal(String codVoluntario, String nomeVoluntario, GPS cord, double raio) {
        super(codVoluntario, nomeVoluntario, cord, raio);
    }

    /**
     * Construtor de copia de um VoluntarioEspecial
     * Aceita como parametros outro VoluntarioEspecial e utiliza os metodos
     * de acesso aos valores das Variaveis de Instancia
     */
    public VoluntarioNormal(VoluntarioNormal v) {
        super(v);
    }


    public ITransportadores copyVoluntarioNormal(ITransportadores v){
        VoluntarioNormal vol = new VoluntarioNormal();
        vol.setCodVoluntario(v.getCodigo());
        vol.setNomeVoluntario(((VoluntarioNormal)v).getNomeVoluntario());
        vol.setGPS(((VoluntarioNormal)v).getGPS());
        vol.setHistorico(((VoluntarioNormal)v).getHistorico());
        vol.setEncomendaAtual(((VoluntarioNormal)v).getEncomendaAtual());
        vol.setDisponivel(((VoluntarioNormal)v).getDisponivel());
        vol.setRaio(v.getRaio());
        vol.setVelocidadeMedia(((VoluntarioNormal)v).getVelocidadeMedia());
        vol.setClassificacoes(((VoluntarioNormal)v).getClassificacoes());
        vol.setNumAvaliacoes(((VoluntarioNormal)v).getNumAvaliacoes());
        return vol;
    }


    @Override
    public void setPrecoPorKm(double preco) {
    }

    /**
     * Como se trata de um Voluntario Normal, nunca ira transportar medicamentos
     *
     * @return sempre false
     */
    public boolean aceitoTransporteMedicamentos() {
        return false;
    }

    /**
     * Determina se aceita caracteristicas da encomenda, caso esta contenha algum tipo de
     * medicamento
     *
     * @param p correspondente ao PedidoLoja
     * @return true se aceitar transportar
     */
    @Override
    public boolean aceitaCaracteristicasEncomenda(PedidoLoja p) {
        if (!super.aceitaCaracteristicasEncomenda(p)) return false;
        if (p.isTemMedicamentos()) return this.aceitoTransporteMedicamentos();
        return true;
    }

    /**
     * Permite apresentar no ecra as informa�oes necessarias
     *
     * @return String com infotma��o da Classe
     */
    public String toString() {
        return super.toString();
    }

    /**
     * Verifica se 2 Objects s�o iguais
     *
     * @return true se Object for igual ao Voluntario
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        VoluntarioNormal t = (VoluntarioNormal) o;

        return (super.equals(t));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
