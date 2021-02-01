package projeto.encomenda;
import projeto.interfaces.IEncomenda;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que implementa uma encomenda.
 * Uma encomenda e' um conjunto de linhas de encomenda, e mais algumas informaçoes sobre a encomenda.
 */
public class Encomenda implements IEncomenda, Comparable<IEncomenda>, Serializable {
    private final String id;
    private final String idLoja;
    private String idTransportador;
    private final String idUser;
    private final float peso;
    private Collection<LinhaDeEncomenda> linhaEncomendas;
    private LocalDate tempRecolha;
    private LocalDate tempEntrega;
    private float precoEntrega;

    /**
     * Variavel de classe
     */
    private static int lastnumber;
    
    /*
     * Construtores da Classe encomenda.
     * Declaracao dos construtores por omissao, parametrizado e de copia.
     */
    /**
     * Construtor por omissao de encomenda.
     */
    public Encomenda() {
        this.id = "";
        this.idLoja = "";
        this.idTransportador = "";
        this.idUser = "";
        this.peso = 0;
        this.linhaEncomendas = new ArrayList<>();
        this.precoEntrega = 0;
        this.tempEntrega = LocalDate.now();
        this.tempRecolha = LocalDate.now();
    }
    /**
     * Construtor parametrizado de encomenda.
     * Aceita como parametros quatro Strings uma para o id da encomenda, outra para o id da loja,
     * outra para o id da empresa e o ultimo para o id do Utilizador.
     * Recebe tambem uma lista de LinhaDeEncomenda, um float para o peso da encomenda e um boolean para identificar se foi aceite ou nao.
     */
    public Encomenda(String i, String loja, String idEmpresa, String user, float p, Collection<LinhaDeEncomenda> l) {
        this.id = i;
        this.idLoja = loja;
        this.idTransportador = idEmpresa;
        this.idUser = user;
        this.peso = p;
        setLinhas(l);
    }

    /**
     * Construtor por copia de encomenda.
     * Aceita como parametro outra encomenda e utiliza os metodos de acesso aos valores das variaveis de instancia.
     */
    public Encomenda(Encomenda e) {
        this.id = e.getID();
        this.idLoja = e.getLojaID();
        this.idTransportador = e.getIdTransportador();
        this.idUser = e.getUserID();
        this.peso = e.getPeso();
        this.linhaEncomendas = e.getLinhas();
        this.tempEntrega = e.getTempEntrega();
        this.tempRecolha = e.getTempRecolha();
        this.precoEntrega = e.getPrecoEntrega();
    }
    
    /*
     * Getters e Setters
     */
    /**
     * Devolve o codigo da encomenda.
     */
    public String getID() {
        return this.id;
    }

    /**
     * Devolve o codigo da loja onde a encomenda esta para ser recolhida.
     */
    public String getLojaID() {
        return this.idLoja;
    }

    /**
     * Devolve o codigo da empresa que recolher a encomenda.
     */
    public String getIdTransportador() {
        return idTransportador;
    }

    /**
     * Devolve o codigo do destinatario da encomenda.
     */
    public String getUserID() {
        return this.idUser;
    }

    /**
    * Devolve o peso da encomenda.
    */
    public float getPeso() {
        return this.peso;
    }

    /**
     * Devolve o tempo de recolha
     * @return tempo de recolha
     */
    public LocalDate getTempRecolha() {
        return tempRecolha;
    }

    /**
     * Devolve o tempo de entrega
     * @return tempo de entrega
     */
    public LocalDate getTempEntrega() {
        return tempEntrega;
    }

    /**
     * Devolve o preco de entrega
     * @return preco de entrega
     */
    public float getPrecoEntrega() {
        return precoEntrega;
    }

    /**
     * Devolve as Linhas de encomenda desta encomenda.
     */
    public Collection<LinhaDeEncomenda> getLinhas() {
        return this.linhaEncomendas.stream()
                .map(LinhaDeEncomenda::clon)
                .collect(Collectors.toList());
    }

    /**
     * Metodo de classe
     */
    public static int getLastnumber() {
        return Encomenda.lastnumber;
    }

    /**
     * Atualiza o id da Empresa
     * @param idTransportador - id da Empresa
     */
    public void setIdTransportador(String idTransportador) {
        this.idTransportador = idTransportador;
    }

    /**
     * Atualiza as Linhas de encomenda na encomenda.
     * @param novasLinhas - List<LinhaDeEncomenda> lista atualizada.
     */
    public void setLinhas(Collection<LinhaDeEncomenda> novasLinhas) {
        this.linhaEncomendas = novasLinhas.stream()
                .map(LinhaDeEncomenda::clon)
                .collect(Collectors.toList());
    }

    /*
     * Restantes Metodos de Instancia
     */
    /**
     * Metodo de classe que incrementa a variavel de classe.
     */
    public static void incLastnumber() {
        Encomenda.lastnumber++;
    }

    /**
     * Metodo que verifica se existe alguma linha de encomenda medicinal na encomenda.
     * @return boolean Verdadeiro caso exista.
     */
    public boolean containsMed() {
        for (LinhaDeEncomenda l : this.linhaEncomendas)
            if (l.isMedicinal())
                return true;
        return false;
    }


    /**
     * Metodo que calcula o preço total da encomenda.
     * @return float - preço total da encomenda.
     */
    public float calculaPrecoTotal() {
        float total = 0;
        for(LinhaDeEncomenda l : this.linhaEncomendas) {
            total += l.getPreco() * l.getQuantidade();
        }
        return total;
    }
    
    /**
     * Compara 2 encomendas atraves do seu ID.
     * Vamos precisar disto apra ordenar as encomendas num TreeSet.
     */
    public int compareTo(IEncomenda e) {
        return this.id.compareTo(e.getID());
    }

    /**
     * Verifica se uma determinada empresa transportou a encomenda
     * @param idEmp id da Empresa
     * @return true caso e empresa tenha transportado a encomenda, false em caso contrário
     */
    private boolean empresaNaoTransportouEnc(String idEmp) {
        return !idEmp.equals(this.idTransportador);
    }

    /**
     * Verifica se a encomenda transportou uma encomenda. Caso tenha transportado, verifica se
     * o tempo de entrega está dentro do intervalo fornecido
     * @param idEmp id da Empreas
     * @param tInicial um array para o tempo inicial, contendo o ano, mes e dia, respetivamente
     * @param tFinal um array para o tempo final, contendo o ano, mes e dia, respetivamente
     * @return true caso se encontre dentro do intervalo, false em caso contrário
     */
    public boolean empTransportouTempo(String idEmp, int[] tInicial, int[] tFinal) {
        String[] campos = this.tempEntrega.toString().split("-");
        int anoEnc = Integer.parseInt(campos[0]);
        int mesEnc = Integer.parseInt(campos[1]);
        int diaEnc = Integer.parseInt(campos[2]);
        if (this.empresaNaoTransportouEnc(idEmp)) return false;
        return tInicial[0] <= anoEnc && tInicial[1] <= mesEnc && tInicial[2] <= diaEnc &&
                tFinal[0] >= anoEnc && tFinal[1] >= mesEnc && tFinal[2] >= diaEnc;
    }

    /**
     * Verifica se a empresa transportou uma encomenda. Caso tenha transportado, verifica se
     * o tempo de entrega corresponde ao tempo fornecido.
     * @param idEmp id da Empresa
     * @param tempo um array que contém o ano, mes e dia, respetivamernte
     * @return true caso o tempo fornecido seja igual ao tempo de entrega, false em caso contrário
     */
    public boolean empTransportouTempo(String idEmp, int[] tempo) {
        String[] campos = this.tempEntrega.toString().split("-");
        int anoEnc = Integer.parseInt(campos[0]);
        int mesEnc = Integer.parseInt(campos[1]);
        int diaEnc = Integer.parseInt(campos[2]);
        if (!this.empresaNaoTransportouEnc(idEmp)) return false;
        return tempo[0] <= anoEnc && tempo[1] <= mesEnc && tempo[2] <= diaEnc;
    }
    /**
     * Metodo que faz uma copia do objeto receptor da mensagem.
     * @return objeto clone do objeto que recebe a mensagem.
     */
    public Encomenda clon() {
        return new Encomenda(this);
    }

    /**
     * Metodo que devolve a representaçao em String da encomenda.
     * @return String com as variaveis desta instancia.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nID da encomenda:").append(this.id)
                .append("\nID da Loja:").append(this.idLoja)
                .append("\nID do Utilizador:").append(this.idUser)
                .append("\nPeso da encomenda:").append(this.peso);
        for(LinhaDeEncomenda l : this.linhaEncomendas){
            sb.append("\n\t").append(l.toString());
        }
        sb.append("\n");
        return sb.toString();
    }
    /**
     * Metodo que determina se duas Encomendas sao iguais.
     * @return boolean verdadeiro caso o id de duas Encomendas seja igual.
     */
    public boolean equals(Object o) {
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Encomenda e = (Encomenda) o;
        return this.id.equals(e.getID());
    }

    /**
     * Método hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, idLoja, idTransportador, idUser, peso, linhaEncomendas, tempRecolha, tempEntrega, precoEntrega);
    }
}