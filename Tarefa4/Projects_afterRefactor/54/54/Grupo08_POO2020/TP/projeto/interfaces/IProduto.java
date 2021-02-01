package projeto.interfaces;
import java.io.Serializable;

public interface IProduto extends Serializable {
    String getCodigo();
    String getNome();
    float getPreco();
    double getPeso();
    boolean getMedicinal();
    IProduto clon();
}
