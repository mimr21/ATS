package projeto;


import projeto.model.TrazAqui;

import java.util.Scanner;

public class TestesPerformance {

    public static void main(String[] args) {
        TrazAqui s = new TrazAqui();
        String loja, utilizador , transp;
        int n;
        long startTime;
        long endTime;
        long timeElapsed;

        Scanner in = new Scanner(System.in);
        System.out.println("Qual o ficheiro inserido na classe Model.TrazAqui? \n[1] - logsSmall.txt \n[2] - logsBig.txt");

        String file = in.nextLine();

        // Se for o ficheiro logsSmall
        if(file.equals("1")) {
            loja = "l11";
            utilizador = "u96";
            transp = "t53";
            n = 10;
        }
        //Se for o ficheiro logsBig
        else {
            loja = "l55369";
            utilizador = "u20801";
            transp = "t20271";
            n = 100;
        }

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        //Carregar ficheiros
        startTime = System.nanoTime();
        s.parse();
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos TrazAqui.parse: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        //Testar funções do model

        startTime = System.nanoTime();
        s.topNClientesEmpresa(transp, n);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos topNClientesEmpresa: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.topNEmpresasMaisUsadas(n);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos topNEmpresasMaisUsadas: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.topNEmpresasDist(n);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos topNEmpresasDist: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

        startTime = System.nanoTime();
        s.topNClientesMaisEncomendaram(n);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Tempo de execução em milisegundos topNClientesMaisEncomendaram: " + timeElapsed / 1000000);

        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("-------------------- Bytes usados: " + memory);

    }
}
