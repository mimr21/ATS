package projeto.interfaces;
import java.io.Serializable;

public interface IEmpresa extends Serializable, IVoluntario {
    float getTaxa();
    void setTaxa(float taxa);
    void setNif(String nif);
    double getDist();
    void setDist(double dis);
    IEmpresa clon();
}