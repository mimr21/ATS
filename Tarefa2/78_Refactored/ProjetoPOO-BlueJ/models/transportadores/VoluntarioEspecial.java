package models.transportadores;

import models.sistema.PedidoLoja;
import models.utilizador.GPS;

import java.io.Serializable;

public class VoluntarioEspecial extends Voluntario implements Serializable{

    /**
     * Variaveis Instancia
     */
    private boolean aceitoMedicamentos;

    /**
     * Construtor por Omissao
     */
    public VoluntarioEspecial(){
        super();
    }

    /**
     * Construtor Parametrizado de VoluntarioEspecial
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public VoluntarioEspecial(String codVoluntario, String nomeVoluntario, GPS cord, double raio, boolean aceitoM){
        super(codVoluntario, nomeVoluntario, cord, raio);
        this.aceitoMedicamentos = aceitoM;
    }

    /**
     * Construtor de copia de um VoluntarioEspecial
     * Aceita como parametros outro VoluntarioEspecial e utiliza os metodos
     * de acesso aos valores das Variaveis de Instancia
     */
    public VoluntarioEspecial(VoluntarioEspecial v){
        super(v);
        this.aceitoMedicamentos = v.isAceitoMedicamentos();
    }

    public ITransportadores copyVoluntarioEspecial(ITransportadores v){
        VoluntarioEspecial vol = new VoluntarioEspecial();
        vol.setCodVoluntario(v.getCodigo());
        vol.setNomeVoluntario(((VoluntarioEspecial)v).getNomeVoluntario());
        vol.setGPS(((VoluntarioEspecial)v).getGPS());
        vol.setHistorico(((VoluntarioEspecial)v).getHistorico());
        vol.setEncomendaAtual(((VoluntarioEspecial)v).getEncomendaAtual());
        vol.setDisponivel(((VoluntarioEspecial)v).getDisponivel());
        vol.setRaio(v.getRaio());
        vol.setVelocidadeMedia(((VoluntarioEspecial)v).getVelocidadeMedia());
        vol.setClassificacoes(((VoluntarioEspecial)v).getClassificacoes());
        vol.setNumAvaliacoes(((VoluntarioEspecial)v).getNumAvaliacoes());
        vol.setAceitoMedicamentos(((VoluntarioEspecial)v).isAceitoMedicamentos());
        return vol;
    }

    /**
     * Indica se aceita transportar medicamentos
     *
     * @return true caso consiga transportar medicamentos
     */
    public boolean isAceitoMedicamentos() {
        return aceitoMedicamentos;
    }

    /**
     * Definir se voluntario aceita ou nao transportar medicamentos
     *
     * @param aceitoMedicamentos correspondente ao novo estado de aceitacao de medicamentos
     */
    public void setAceitoMedicamentos(boolean aceitoMedicamentos) {
        this.aceitoMedicamentos = aceitoMedicamentos;
    }


    @Override
    public void setPrecoPorKm(double preco) {

    }

    /**
     * Indica se aceita transportar medicamentos
     *
     * @return true caso consiga transportar medicamentos
     */
    public boolean aceitoTransporteMedicamentos(){
        return this.aceitoMedicamentos;
    }

    /**
     * Determina se aceita caracteristicas da encomenda, caso esta contenha algum tipo de
     * medicamento
     *
     * @param p correspondente ao PedidoLoja
     * @return true se aceitar transportar
     */
    @Override
    public boolean aceitaCaracteristicasEncomenda(PedidoLoja p){
        if(!super.aceitaCaracteristicasEncomenda(p)) return false;
        if(p.isTemMedicamentos()) return this.aceitoTransporteMedicamentos();
        return true;

    }

    /**
     * Permite apresentar no ecra as informa�oes necessarias
     *
     * @return String com a informa��o da Classe
     */
    public String toString(){
        return super.toString() + "Transporta Medicamentos: " + this.aceitoMedicamentos;
    }

    /**
     * Verifica se 2 Objects s�o iguais
     *
     * @return true se Object for igual ao Voluntario
     */
    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        VoluntarioEspecial t = (VoluntarioEspecial) o;

        return (super.equals(t) &&
                this.aceitoMedicamentos == t.isAceitoMedicamentos());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
