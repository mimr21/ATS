import excepitions.CodigoNotFoundException;
import models.sistema.LeituraFicheiros;
import models.TrazAqui;

import java.io.IOException;
import java.util.Scanner;

public class TestesPerformance {

    public static void main(String[] args) {

        TrazAqui s = null;
        String loja, utilizador , transp;
        long startTime;
        long endTime;
        long timeElapsed;

        Scanner in = new Scanner(System.in);
        System.out.println("Qual o ficheiro inserido na classe LeituraFicheiros? \n[1] - logsSmall.txt \n[2] - logsBig.txt");

        String file = in.nextLine();

        // Se for o ficheiro logsSmall
        if(file.equals("1")) {
            loja = "l11";
            utilizador = "u96";
            transp = "t53";
        }
        //Se for o ficheiro logsBig
        else {
            loja = "l55369";
            utilizador = "u20801";
            transp = "t20271";
        }

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        //Carregar ficheiros
        startTime = System.nanoTime();
        try {
            s = LeituraFicheiros.lerFicheiro();
        } catch (IOException ioException) {
            System.out.println("Ficheiro Default em falta.");
            System.exit(1);
        }
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos LeituraFicheiros: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        //Testar funções do model

        startTime = System.nanoTime();
        s.getHistoricoLoja(loja);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos getHistoricoLoja: " + timeElapsed / 1000000);



        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.getListPedidosLoja(loja);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos getListPedidosLoja: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.getLojasDisponiveis();
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos getLojasDisponiveis: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.getHistoricoUtilizador(utilizador);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos getHistoricoUtilizador: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.getPedidosTransportadorasPendentes(utilizador);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos getPedidosTransportadorasPendentes: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.getHistoricoTransportadores(transp);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos getHistoricoTransportadores: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        try {
            s.totalFaturadoLoja(loja);
        } catch (CodigoNotFoundException e) {
            e.printStackTrace();
        }
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos totalFaturadoLoja: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.top10Transportadoras();
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos top10Transportadoras: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.top10Utilizadores();
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos top10Utilizadores: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

    }
}