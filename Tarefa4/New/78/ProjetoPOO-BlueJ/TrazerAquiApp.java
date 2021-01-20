import controllers.ControllerLogin;
import models.sistema.LeituraFicheiros;
import models.TrazAqui;
import views.ViewErro;
import views.ViewLogin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrazerAquiApp {

    /**
     * Metodo que carrega os dados para o sistema.
     * Tenta carregar os dados em formato bin�rio que permite recuperar o estado do ponto de paragem
     * Em caso de erro volta a recarregar os dados default que est�o no ficheiro logs.
     * Se n�o conseguir ler os dados de nenhum dos ficheiros sai com codigo de erro 1.
     * @return o estado carregado do sistema.
     */
    private static final Logger logger = Logger.getLogger(TrazerAquiApp.class.getName());

    public static TrazAqui carregaSitemaBinario(){
        TrazAqui s = null;

        try {
            s = LeituraFicheiros.carregaEstado("EstadoBinario.txt");
        } catch (IOException | ClassNotFoundException | NullPointerException e) {

            ViewErro erro = new ViewErro("Erro ao carregar os dados. Recarregando sistema.");
            erro.run();

            try {
                s = LeituraFicheiros.lerFicheiro();
            } catch (IOException ioException) {
                logger.log(Level.INFO, ("Fischer Default em falta."));
                System.exit(1);
            }
        }

        return s;
    }

    public static void main(String[] args) {
        TrazAqui sistema = carregaSitemaBinario();

        ViewLogin view = new ViewLogin(new ControllerLogin(sistema));
        view.run();

        try {
            sistema.guardaEstado("EstadoBinario.txt");
        } catch (IOException | NullPointerException e) {
            logger.log(Level.INFO, e.getMessage());
        }

        System.exit(0);
    }
}
