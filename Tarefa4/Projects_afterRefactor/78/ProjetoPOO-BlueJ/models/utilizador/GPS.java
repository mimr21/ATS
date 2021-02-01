package models.utilizador;

import java.io.Serializable;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.pow;

public class GPS implements Serializable {

    /**
     * Variaveis Instancia
     */
    private double x;
    private double y;

    /**
     * Construtor por omiss�o de GPS
     */
    public GPS(){
        this.x = 0;
        this.y = 0;
    }

    /**
     * Construtor Parametrizado de GPS
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public GPS(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Construtor de copia de um GPS
     * Aceita como parametros outro GPS e utiliza os metodos
     * de acesso aos valores das Variaveis de Instancia
     */
    public GPS(GPS g){
        this.x = g.getX();
        this.y = g.getY();
    }

    public GPS copyGPS (GPS g){
        GPS gp = new GPS();
        gp.setX(g.getX());
        gp.setY(g.getY());
        return gp;
    }

    /**
     * Devolve valor referente � Coordenada x
     * @return valor x
     */
    public double getX(){
        return this.x;
    }

    /**
     * Devolve valor referente � Coordenada y
     * @return valor y
     */
    public double getY(){
        return this.y;
    }

    /**
     * Atualiza valor da Coordenada x
     * @param x novo de x
     */
    public void setX(double x){
        this.x = x;
    }

    /**
     * Atualiza valor da Coordenada y
     * @param y novo de y
     */
    public void setY(double y){
        this.y = y;
    }

    /**
     * Com base nas suas coordenadas e nas coordenadas de outra localizacao
     * calcula qual a distancia a essa localizacao
     *
     * @param gps correspondente a localizacao do lugar que pretendemos calcular a distancia
     * @return distancia entre os dois lugares
     */
    public double distancia(GPS gps){
        return sqrt(pow(this.x - gps.getX(), 2) + pow(this.y - gps.getY(), 2));
    }

    /**
     * Tentar calcular a distancia, nao com base na longitude e latitude, mas sim
     * na conversao destes para Km.
     *
     * @param gps2 correspondente a localizacao do lugar que pretendemos calcular a distancia
     * @return distancia entre os dois lugares mas em Km
     */
    public double distanciaRealEmKM(GPS gps2) {
        double eQuatorialEarthRadius = 6378.1370D;
        double d2r = (Math.PI / 180D);

        double dlong = (gps2.getY() - this.y) * d2r;
        double dlat = (gps2.getX() - this.x) * d2r;

        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(this.x * d2r) * Math.cos(gps2.getX() * d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        return eQuatorialEarthRadius * c;

    }

    /**
     * Metodo que devolve a representa�ao em String do GPS
     * @return String com Informa��o do GPS
     */
    public String toString(){
        return "Longitude: " + this.y + "," +
                "Latitude: " + this.x;
    }

    /**
     * Metodo que determina se dois GPS s�o iguais
     * @return booleano que � verdadeiro se todos os valores forem iguais
     */
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        GPS g = (GPS)o;

        return (this.x == g.getX() &&
                this.y == g.getY());
    }

    public int hashCode(){ return 0;}
}

