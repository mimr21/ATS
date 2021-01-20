package models.sistema;

import models.TrazAqui;
import models.encomendas.Encomenda;
import models.encomendas.LinhaEncomenda;
import models.loja.Loja;
import models.loja.LojaComFilasEspera;
import models.loja.Produto;
import models.loja.Stock;
import models.transportadores.*;
import models.utilizador.GPS;
import models.utilizador.Utilizador;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LeituraFicheiros {

    private static String nomeFicheiro = "ProjetoPOO-BlueJ/logs_20200416.txt";
    static Logger logger = Logger.getLogger(LeituraFicheiros.class.getName());

    private LeituraFicheiros() {
    }

    /**
     * Metodo que realiza a leitura do ficheiro logs.
     * Retorna o TrazAqui com os dados defalt
     *
     * Depois deve abrir a aplicacao com o estado em binario e nao com o ficheiro logs.
     */
    public static TrazAqui lerFicheiro() throws IOException{
        Map<String,Loja> ls = new HashMap<>();
        Map<String,Utilizador> us = new HashMap<>();
        Map<String, ITransportadores> ts = new HashMap<>();
        Stock stock = new Stock();
        Map<String,Encomenda> encomendas = new HashMap<>();

        List<PedidoLoja> pls = new ArrayList<>();
        TrazAqui s;

        int numLojas =0;
        FileReader arq = null;
        BufferedReader lerArq = null;
        tryCatchLerFicheiro(ls, us, ts, stock, encomendas, pls, numLojas, arq, lerArq);


        Produto medicamento = new Produto("p1","Brufene",0.05,5.30,true);
        stock.addStock(medicamento);
        List<Produto> lp = stock.getListaProdutos();

        for (Produto p : lp){
            for (Loja l : ls.values()){
                l.adicionaProdutoStock(p);
            }
        }

            /*
             * Proximos Codigos para ser gerados
             * Indice 0 -> encomendas -> Maior 8779 -> 8780
             * Indice 1 -> utilizadores -> Maior 97 -> 98
             * Indice 2 -> lojas -> Maior 83 -> 84
             * Indice 3 -> voluntarios -> Maior 73 -> 74
             * Indice 4 -> transportadoras -> Maior 51 -> 52
             * Indice 5 -> produtos -> Maior ??? -> 100
             */

        List<Integer> lprox = new ArrayList<>();
        lprox.add(0,8780);
        lprox.add(1,98);
        lprox.add(2,84);
        lprox.add(3,74);
        lprox.add(4,52);
        lprox.add(5,100);

        Map<String,Perfil> perfis = new HashMap<>();

        for (Loja l : ls.values()){
            Perfil p = new Perfil(TiposUtilizadores.LOJAS,l.getCodigoLoja(),l.getCodigoLoja(),"1234");
            perfis.put(p.getEmail(),p.copyPerfil(p));
        }

        for (Utilizador l : us.values()){
            Perfil p = new Perfil(TiposUtilizadores.UTILIZADORES,l.getCodUtilizador(),l.getCodUtilizador(),"1234");
            perfis.put(p.getEmail(),p.copyPerfil(p));
        }

        for (ITransportadores l : ts.values()){
            Perfil p;
            if(l instanceof Transportadora) p = new Perfil(TiposUtilizadores.TRANSPORTADORA,l.getCodigo(),l.getCodigo(),"1234");
            else p = new Perfil(TiposUtilizadores.VOLUNTARIOS,l.getCodigo(),l.getCodigo(),"1234");
            perfis.put(p.getEmail(),p.copyPerfil(p));
        }

        Perfil pS = new Perfil(TiposUtilizadores.SISTEMA,"Pedro Veloso","pedroVeloso","1234");
        perfis.put(pS.getEmail(),pS.copyPerfil(pS));

        s = new TrazAqui(us, ls, ts, new HashMap<>(),lprox,perfis, new HashMap<>());

        for (PedidoLoja pla : pls){
            boolean conseguiu = s.atribuiEntregador(pla);
            if(!conseguiu){
                ls.get(pla.getLoja()).addPedidoUtilizador(new PedidoUtilizador(pla.getEncomenda(),us.get(pla.getUtilizador()).getGps().copyGPS(pla.getGpsUtilizador()),LocalDateTime.now()));
            }
        }

        return s;
    }

    private static void tryCatchLerFicheiro(Map<String, Loja> ls, Map<String, Utilizador> us, Map<String, ITransportadores> ts, Stock stock, Map<String, Encomenda> encomendas, List<PedidoLoja> pls, int numLojas, FileReader arq, BufferedReader lerArq) throws IOException{
        try {
            arq =  new FileReader(nomeFicheiro);
            lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
            int opcao = -1;
            while (linha != null) {

                opcao = linha.codePointAt(0) - 65;

                String[] r = linha.split(":");
                String[] valoresLinha = r[1].split(",");
                switch (opcao) {
                    case 0:       //Aceites
                        case1(ls, us, encomendas.get(r[1].trim()), pls);
                        break;
                    case 4:      //Encomendas
                        case2(stock, encomendas, valoresLinha);
                        break;
                    case 11:    //Lojas
                        numLojas = getNumLojas(ls, numLojas, valoresLinha);
                        break;
                    case 19:      //Transportadoras
                        case3(ts, valoresLinha);
                        break;
                    case 20:      //Utilizador
                        case4(us, valoresLinha);
                        break;
                    case 21:      //Voluntarios
                        case5(ts, valoresLinha);
                        break;
                    default:
                        logger.log(Level.INFO, ("Errou"));
                        break;


                }

                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
        }
        catch(Exception e) {logger.log(Level.INFO,e.getMessage());}
        finally {
            lerArq.close();
            arq.close();
        }
    }

    private static void case5(Map<String, ITransportadores> ts, String[] valoresLinha) {
        GPS gps = new GPS(Double.parseDouble(valoresLinha[2]), Double.parseDouble(valoresLinha[3]));
        Voluntario voluntario = new VoluntarioNormal(valoresLinha[0], valoresLinha[1], gps, Double.parseDouble(valoresLinha[4]));

        ts.put(voluntario.getCodigo(),voluntario);
    }

    private static void case4(Map<String, Utilizador> us, String[] valoresLinha) {
        GPS gps = new GPS(Double.parseDouble(valoresLinha[2]), Double.parseDouble(valoresLinha[3]));
        Utilizador utilizador = new Utilizador(valoresLinha[0], valoresLinha[1], gps);

        us.put(utilizador.getCodUtilizador(),utilizador);
    }

    private static void case3(Map<String, ITransportadores> ts, String[] valoresLinha) {
        GPS gps = new GPS(Double.parseDouble(valoresLinha[2]), Double.parseDouble(valoresLinha[3]));
        Transportadora p = new TransportadoraNormal(valoresLinha[0], valoresLinha[1], gps, valoresLinha[4], Double.parseDouble(valoresLinha[5]), Double.parseDouble(valoresLinha[6]));

        ts.put(p.getCodigo(),p);
    }

    private static int getNumLojas(Map<String, Loja> ls, int numLojas, String[] valoresLinha) {
        if(numLojas <3){
            GPS gps = new GPS(Double.parseDouble(valoresLinha[2]), Double.parseDouble(valoresLinha[3]));
            Loja loja = new LojaComFilasEspera(valoresLinha[0], valoresLinha[1], gps);
            ls.put(loja.getCodigoLoja(), loja);
            numLojas++;
        }else{
            GPS gps = new GPS(Double.parseDouble(valoresLinha[2]), Double.parseDouble(valoresLinha[3]));
            Loja loja = new Loja(valoresLinha[0], valoresLinha[1], gps);
            ls.put(loja.getCodigoLoja(), loja);
        }
        return numLojas;
    }

    private static void case2(Stock stock, Map<String, Encomenda> encomendas, String[] valoresLinha) {
        List<LinhaEncomenda> linhaEnc = new ArrayList<>();
        for(int i = 4; i< valoresLinha.length; i+=4){
            LinhaEncomenda l = new LinhaEncomenda(valoresLinha[i], valoresLinha[i+1],Double.parseDouble(valoresLinha[i+2]),Double.parseDouble(valoresLinha[i+3]));

            Produto p = new Produto(valoresLinha[i], valoresLinha[i+1],0.0,(Double.parseDouble(valoresLinha[i+3])/Double.parseDouble(valoresLinha[i+2])),false);
            stock.addStock(p);

            linhaEnc.add(l);
        }

        Encomenda e = new Encomenda(valoresLinha[0], valoresLinha[1], valoresLinha[2],Double.parseDouble(valoresLinha[3]),new ArrayList<>());
        e.setLinhasEncomenda(linhaEnc);
        encomendas.put(e.getCodEncomenda(),e);
    }

    private static void case1(Map<String, Loja> ls, Map<String, Utilizador> us, Encomenda ea1, List<PedidoLoja> pls) {
        Encomenda ea = ea1;
        Loja lea = ls.get(ea.getCodLoja());
        Utilizador uea = us.get(ea.getCodUtilizador());

        PedidoUtilizador pu = new PedidoUtilizador(ea,uea.getGps().copyGPS(uea.getGps()), LocalDateTime.now());

        PedidoLoja pl = new PedidoLoja(pu, LocalDateTime.now(),lea.getCodigoLoja(),lea.getCord().copyGPS(lea.getCord()),false,"None");
        pls.add(pl);
    }

    /**
     * Metodo que l� o estado dos nossos dados guardados em binario
     * @param nomeFicheiroBinario nome do ficheiro onde contem os dados a ler.
     * @return A estrutura carregado e pronta a utilizar.
     */
    public static TrazAqui carregaEstado(String nomeFicheiroBinario) throws IOException, ClassNotFoundException, NullPointerException{
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(nomeFicheiroBinario);
            ois = new ObjectInputStream(fis);
        }
        catch(NullPointerException e){logger.log(Level.INFO, e.getMessage());}
        finally{
            ois.close();
            fis.close();
        }

        return (TrazAqui) ois.readObject();
    }
}
