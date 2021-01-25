package projeto.util;

import static java.lang.System.err;
import static java.lang.System.in;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que abstrai a utilização da classe Scanner, escondendo todos os
 * problemas relacionados com excepções, e que oferece métodos simples e
 * robustos para a leitura de valores de tipos simples e String.
 * É uma classe de serviços, como Math e outras de Java, pelo que devem ser
 * usados os métodos de classe implementados.
 */
public class Input {

    static String a = "Novo valor: ";
    // Create a Logger
    static Logger logger
            = Logger.getLogger(
            Input.class.getName());
    /*
     * Métodos de Classe
     */
    public static String lerString() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        String txt = "";
        while(!ok) {
            try {
                txt = input.nextLine();
                ok = true;
            }
            catch(InputMismatchException e)
            { logger.log(Level.INFO,"Texto Invalido");
                err.print(a);
                input.nextLine();
            }
        }
        return txt;
    }

    public static int lerInt() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        int i = 0;
        while(!ok) {
            try {
                i = input.nextInt();
                ok = true;
            }
            catch(InputMismatchException e)
            { logger.log (Level.INFO,"Inteiro Invalido");
                err.print(a);
                input.nextLine();
            }
        }
        return i;
    }

    public static double lerDouble() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        double d = 0.0;
        while(!ok) {
            try {
                d = input.nextDouble();
                ok = true;
            }
            catch(InputMismatchException e)
            { logger.log (Level.INFO,"Valor real Invalido");
                err.print(a);
                input.nextLine();
            }
        }
        return d;
    }

    public static float lerFloat() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        float f = 0.0f;
        while(!ok) {
            try {
                f = input.nextFloat();
                ok = true;
            }
            catch(InputMismatchException e)
            { logger.log (Level.INFO,"Valor real Invalido");
                err.print(a);
                input.nextLine();
            }
        }
        return f;
    }

    public static boolean lerBoolean() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        boolean b = false;
        while(!ok) {
            try {
                b = input.nextBoolean();
                ok = true;
            }
            catch(InputMismatchException e)
            { logger.log (Level.INFO,"Booleano Invalido");
                err.print(a);
                input.nextLine();
            }
        }
        return b;
    }
}