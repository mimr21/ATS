package controllers;

import excepitions.*;
import models.TrazAqui;
import views.ViewErro;
import views.ViewHistorico;
import java.util.ArrayList;
import java.util.List;

public class ControllerSistemaTodo implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;

    /**
     * Construtor Parametrizado de Controller_SistemaTodo
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerSistemaTodo(TrazAqui s){
        this.sistema = s;
    }

    /**
     * Com base nos inputs do Utilizador, processar as diferentes opcoes de visualizar
     * opcoes extra como top transportadoras, top utilizadores e faturado
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        switch (opcao.get(0)){
            case "Faturado" :
                case1(opcao);
                break;


            case "10Utilizadores" :
                case2(this.sistema.top10Utilizadores());
                break;


            case "10Transportadoras" :
                case2(this.sistema.top10Transportadoras());
                break;

            default:break;
        }
    }

    private void case2(List<String> strings) {
        List<String> l = strings;
        ViewHistorico view = new ViewHistorico(l);
        view.run();
    }

    private void case1(List<String> opcao) {
        try {
            List<String> l = new ArrayList<>();
            if(opcao.get(1).charAt(0) == 'l'){
                l = this.sistema.totalFaturadoLoja(opcao.get(1));
            }else{
                if(opcao.get(1).charAt(0) == 't'){
                    l = this.sistema.totalFaturadoTransportadora(opcao.get(1));
                }else{
                    l.add("Codigo Invalido");
                }
            }
            ViewHistorico view = new ViewHistorico(l);
            view.run();
        }catch (CodigoNotFoundException e){
            ViewErro erro = new ViewErro("Codigo inv�lido");
            erro.run();
        }
    }
}
