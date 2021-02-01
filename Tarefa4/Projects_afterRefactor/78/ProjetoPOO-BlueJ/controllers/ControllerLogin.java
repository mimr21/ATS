package controllers;

import controllers.loja.ControllerLoja;
import controllers.transportadores.ControllerTransportadora;
import controllers.transportadores.ControllerVoluntario;
import controllers.utilizador.ControllerUtilizador;
import models.sistema.TiposUtilizadores;
import models.transportadores.*;
import models.utilizador.GPS;
import models.loja.Loja;
import models.sistema.Perfil;
import models.TrazAqui;
import models.utilizador.Utilizador;
import views.ViewErro;
import views.ViewSistema;
import views.loja.ViewLoja;
import views.transportadores.ViewTransportador;
import views.utilizador.ViewUtilizador;
import excepitions.*;

import java.util.List;

public class ControllerLogin implements IControllers {

    /**
     * Variaveis Instancia
     */
    private TrazAqui sistema;

    /**
     * Construtor Parametrizado de Controller_Login
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ControllerLogin(TrazAqui s){
        this.sistema = s;
    }

    /**
     * Com base nos inputs do Utilizador, processar as diferentes opcoes possiveis de Login
     *
     * @param opcao correspondente a lista de opcoes inseridas pelo Utilizador
     */
    public void processa(List<String> opcao){
        switch (opcao.get(0)){
            case "Login":
                case20(opcao);
                break;

            case "Creat":
                case21(opcao);
                break;
            default:break;
        }
    }

    private void case21(List<String> opcao) {
        switch (opcao.get(1)){
            case "Voluntario" :
                case6(opcao);
                break;

            case "Utilizador" :
                case7(opcao);
                break;

            case "Transportadora" :
                case8(opcao);
                break;

            case "Loja" :
                case9(opcao);
                break;
            default:break;
        }
    }

    private void case20(List<String> opcao) {
        String email = opcao.get(1);
        String pass = opcao.get(2);
        try {
            Perfil p = this.sistema.getPerfil(email);
            if(pass.equals(p.getPass())){
                switch (p.getTipo()) {
                    case UTILIZADORES:
                        case1(p);
                        break;

                    case LOJAS:
                        case2(p);
                        break;

                    case VOLUNTARIOS:
                        case3(p);
                        break;

                    case TRANSPORTADORA:
                        case4(p);
                        break;

                    case SISTEMA:
                        case5();
                        break;
                    default:break;
                }
            }else {
                ViewErro viewErro = new ViewErro("PassWord Errada");
                viewErro.run();
            }
        }catch (ValorErradoException e) {
            ViewErro viewErro = new ViewErro("Email invalido");
            viewErro.run();
        }
    }

    private void case9(List<String> opcao) {
        String codigo = this.sistema.geraCodigoLoja();
        Perfil p = new Perfil(TiposUtilizadores.LOJAS,codigo, opcao.get(2), opcao.get(3));
        GPS gps = new GPS(Double.parseDouble(opcao.get(5)),Double.parseDouble(opcao.get(6)));
        Loja l = new Loja(codigo, opcao.get(4),gps);
        this.sistema.adicionaLoja(l);
        this.sistema.adicionaPerfil(p);

        Loja l2 = sistema.getLoja(p.getCodigo());
        IControllers controller = new ControllerLoja(sistema,p.getCodigo());
        ViewLoja viewLoja = new ViewLoja(controller,this.sistema,l2.getCodigoLoja(),l2.getNomeLoja());
        viewLoja.run();
    }

    private void case8(List<String> opcao) {
        String codigo = this.sistema.geraCodigoTransportadora();
        Perfil p = new Perfil(TiposUtilizadores.TRANSPORTADORA,codigo, opcao.get(2), opcao.get(3));
        GPS gps = new GPS(Double.parseDouble(opcao.get(6)),Double.parseDouble(opcao.get(7)));

        if(opcao.get(10).equalsIgnoreCase("S")){
            Transportadora v = new TransportadoraCertificada(codigo, opcao.get(4),gps, opcao.get(5),Double.parseDouble(opcao.get(9)),Double.parseDouble(opcao.get(8)),true);
            this.sistema.adicionaTransportador(v);
            this.sistema.adicionaPerfil(p);
        }
        else {
            Transportadora v = new TransportadoraNormal(codigo, opcao.get(4),gps, opcao.get(5),Double.parseDouble(opcao.get(9)),Double.parseDouble(opcao.get(8)));
            this.sistema.adicionaTransportador(v);
            this.sistema.adicionaPerfil(p);
        }

        case4(p);
    }

    private void case7(List<String> opcao) {
        String codigo = this.sistema.geraCodigoUtilizador();
        Perfil p = new Perfil(TiposUtilizadores.UTILIZADORES,codigo, opcao.get(2), opcao.get(3));
        String nome = String.format("%s %s", opcao.get(4), opcao.get(5));
        GPS gps = new GPS(Double.parseDouble(opcao.get(6)),Double.parseDouble(opcao.get(7)));
        Utilizador u = new Utilizador(codigo,nome,gps);
        this.sistema.adicionaUtilizador(u);
        this.sistema.adicionaPerfil(p);

        IControllers controller = new ControllerUtilizador(sistema,p.getCodigo());
        Utilizador u2 = sistema.getUtilizador(p.getCodigo());
        ViewUtilizador view = new ViewUtilizador(controller,this.sistema,u2.getCodUtilizador(),u2.getNome());
        view.run();
    }

    private void case6(List<String> opcao) {
        String codigo = this.sistema.geraCodigoVoluntario();
        Perfil p = new Perfil(TiposUtilizadores.VOLUNTARIOS,codigo, opcao.get(2), opcao.get(3));
        String nome = String.format("%s %s", opcao.get(4), opcao.get(5));
        GPS gps = new GPS(Double.parseDouble(opcao.get(6)),Double.parseDouble(opcao.get(7)));

        if(opcao.get(9).equalsIgnoreCase("S")){
            Voluntario v = new VoluntarioEspecial(codigo,nome,gps,Double.parseDouble(opcao.get(8)),true);
            this.sistema.adicionaTransportador(v);
            this.sistema.adicionaPerfil(p);
        }
        else {
            Voluntario v = new VoluntarioNormal(codigo,nome,gps,Double.parseDouble(opcao.get(8)));
            this.sistema.adicionaTransportador(v);
            this.sistema.adicionaPerfil(p);
        }

        case3(p);
    }

    private void case5() {
        IControllers controller = new ControllerSistemaTodo(sistema);
        ViewSistema viewSistema = new ViewSistema(controller);
        viewSistema.run();
    }

    private void case4(Perfil p) {
        ITransportadores t = sistema.getTransportador(p.getCodigo());
        IControllers controller = new ControllerTransportadora(sistema, p.getCodigo());
        ViewTransportador viewTransportadora = new ViewTransportador(this.sistema, t.getCodigo(), controller);
        viewTransportadora.run();
    }

    private void case3(Perfil p) {
        ITransportadores t = sistema.getTransportador(p.getCodigo());
        IControllers controller = new ControllerVoluntario(sistema, p.getCodigo());
        ViewTransportador viewVoluntario = new ViewTransportador(this.sistema, t.getCodigo(), controller);
        viewVoluntario.run();
    }

    private void case2(Perfil p) {
        Loja l = sistema.getLoja(p.getCodigo());
        IControllers controller = new ControllerLoja(sistema, p.getCodigo());
        ViewLoja viewLoja = new ViewLoja(controller,this.sistema,l.getCodigoLoja(),l.getNomeLoja());
        viewLoja.run();
    }

    private void case1(Perfil p) {
        IControllers controller = new ControllerUtilizador(sistema, p.getCodigo());
        Utilizador u = sistema.getUtilizador(p.getCodigo());
        ViewUtilizador view = new ViewUtilizador(controller,this.sistema,u.getCodUtilizador(),u.getNome());
        view.run();
    }
}
