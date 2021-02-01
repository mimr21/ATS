package views;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LeituraDados {

    public static final String VALOR_INVALIDO = "Valor Invalido";
    public static final String NOVO_VALOR = "Novo valor: ";

    static Logger logger = Logger.getLogger(LeituraDados.class.getName());

    private LeituraDados() {
    }


    /**
     * Le do stdin
     *
     * @return input do Utilizador
     */
    public static String lerString() {
        Scanner input = new Scanner(System.in);
        return input.next();
    }

    /**
     * Converter para String a opcao do Utilizador
     *
     * @return string correspodente ao Inteiro
     */
    public static String lerIntComoString() {
        Scanner input = new Scanner(System.in);
        boolean ok = false;
        String txt = "";
        while(!ok) {
            try {
                txt = input.next();
                Integer.parseInt(txt);
                ok = true;
            }
            catch(NumberFormatException e)
            {
                logger.log(Level.INFO, (VALOR_INVALIDO));
                logger.log(Level.INFO, NOVO_VALOR);
                input.next();
            }
        }
        return txt;
    }

    /**
     * Converter para String a opcao do Utilizador
     *
     * @return string correspodente ao Double
     */
    public static String lerDoubleComoString() {
        Scanner input = new Scanner(System.in);
        boolean ok = false;
        String txt = "";
        while(!ok) {
            try {
                txt = input.next();
                Double.parseDouble(txt);
                ok = true;
            }
            catch(NumberFormatException e)
            {
                logger.log(Level.INFO, (VALOR_INVALIDO));
                logger.log(Level.INFO,NOVO_VALOR);
                input.next();
            }
        }
        return txt;
    }

    /**
     * Converter para String a opcao do Utilizador
     *
     * @return string correspodente ao Inteiro anterior
     */
    public static String lerIntAnteriorComoString() {
        Scanner input = new Scanner(System.in);
        boolean ok = false;
        String txt = "";
        int res = 0;
        while(!ok) {
            try {
                txt = input.next();
                res = Integer.parseInt(txt);
                ok = true;
            }
            catch(NumberFormatException e)
            {
                logger.log(Level.INFO, (VALOR_INVALIDO));
                logger.log(Level.INFO, NOVO_VALOR);
                input.next();
            }
        }
        return String.valueOf(res-1);
    }
}
