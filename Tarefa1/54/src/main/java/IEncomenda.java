package projeto.interfaces;

import java.io.Serializable;

public interface IEncomenda extends Serializable {
    String getID();
    IEncomenda clon();
    float calculaPrecoTotal();
    boolean containsMed();
    String getUserID();
    String getIdTransportador();
    float getPeso();
    String getLojaID();
    void setIdTransportador(String idEmpresa);
    boolean empTransportouTempo(String idEmp, int[] tInicial, int[] tFinal);
    boolean empTransportouTempo(String idEmp, int[] tempo);
}
