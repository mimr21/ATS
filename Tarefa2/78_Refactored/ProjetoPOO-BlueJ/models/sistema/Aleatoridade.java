package models.sistema;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Aleatoridade {

    private Aleatoridade() {
    }

    /**
     * Funcao que define os nossos valores de Aleatoriedade que posteriormente
     * afetarao quer o tempo, quer o preco de transporte. Basicamente, criamos um gerador gera um numero aleatorio
     * de 0 a 3 e conforme o resultado, ira corresponder a um estado definido por nos(por exemplo,
     * quando calha 1 no estado do tempo atual, simboliza que o tempo esta nublado e assim para os outros tambem).
     *
     * @return double correspondente a taxa que sera acrescentada ao tempo de transporte e ao preco deste
     */


    public static double geraTaxaAleatoridade() throws NoSuchAlgorithmException {
        Random gerador = SecureRandom.getInstanceStrong();

        int estadoTempoAtual = gerador.nextInt(3);
        int estadoEstrada = gerador.nextInt(3);
        int estadoTransito = gerador.nextInt(3);

        double taxa = 1;

        if(CondicoesAtomosfericas.SOL.equalsEstadoTempo(estadoTempoAtual)){
            taxa += 0;
        }

        if(CondicoesAtomosfericas.NUBLADO.equalsEstadoTempo(estadoTempoAtual)){
            taxa += 0.07;
        }

        if(CondicoesAtomosfericas.CHUVA.equalsEstadoTempo(estadoTempoAtual)){
            taxa += 0.3;
        }

        if(EstadoEstrada.BOM.equalsEstadoEstradaVar(estadoEstrada)){
            taxa += 0;
        }

        if(EstadoEstrada.RAZOAVEL.equalsEstadoEstradaVar(estadoEstrada)){
            taxa += 0.09;
        }

        if(EstadoEstrada.PESSIMO.equalsEstadoEstradaVar(estadoEstrada)){
            taxa += 0.5;
        }

        if(Transito.INEXISTENTE.equalsEstadoEstrada(estadoTransito)){
            taxa += 0;
        }

        if(Transito.ACEITAVEL.equalsEstadoEstrada(estadoTransito)){
            taxa += 0.3;
        }

        if(Transito.CONGESTIONADO.equalsEstadoEstrada(estadoTransito)){
            taxa += 1;
        }

        return taxa;
    }
}
