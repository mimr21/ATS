package views;


import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewErro {

    private static Logger logger = Logger.getLogger(ViewErro.class.getName());

    /**
     * Variaveis Instancia
     */
    private String mensagem;

    /**
     * Construtor Parametrizado de View_Erro
     * Aceita como parametros os valores para cada Variavel de Instancia
     */
    public ViewErro(String erro){
        this.mensagem = erro;
    }

    /**
     * Apresenta no ecra a mensagem de erro, esperando depois 1 segundo antes de terminar
     */
    public void run(){
        logger.log(Level.INFO, (""));
        logger.log(Level.INFO, (""));
        logger.log(Level.INFO, (""));
        logger.log(Level.INFO, (""));
        logger.log(Level.INFO, (""));
        logger.log(Level.INFO, (this.mensagem));
        logger.log(Level.INFO, (""));
        logger.log(Level.INFO, (""));
        logger.log(Level.INFO, (""));
        logger.log(Level.INFO, (""));

        try { Thread.sleep (1000); }
        catch (InterruptedException e) {
            logger.log(Level.INFO, "Interrupted!");
            Thread.currentThread().interrupt();
        }
    }
}
