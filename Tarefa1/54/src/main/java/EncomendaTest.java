/*import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

/**
 * A classe de teste EncomendaTest.
 *
public class EncomendaTest{
    private projeto.projeto.encomenda.encomenda enc;
    /**
     * Construtor default para a classe de teste EncomendaTest
     *
    public EncomendaTest(){
    }

    /**
     * Define a .
     *
     * Chamado antes de cada método de caso de teste.
     *
    @Before
    public void setUp(){
        List<projeto.encomenda.LinhaDeEncomenda> lista = new ArrayList<>();
        this.enc = new projeto.projeto.encomenda.encomenda("000001", "Continente", "Joao",  2, false, lista);
        projeto.encomenda.LinhaDeEncomenda l1 = new projeto.encomenda.LinhaDeEncomenda("1", "sem descriçao", 4, 2, false);
        projeto.encomenda.LinhaDeEncomenda l2 = new projeto.encomenda.LinhaDeEncomenda("2", "sem descriçao", 3, 20, false);
        projeto.encomenda.LinhaDeEncomenda l3 = new projeto.encomenda.LinhaDeEncomenda("3", "sem descriçao", 10, 0.2f, false);
        enc.insereLinhaEnc(l1);
        enc.insereLinhaEnc(l2);
        enc.insereLinhaEnc(l3);
    }

    /**
     * Tears down the test fixture.
     *
     * Chamado após cada método de teste de caso.
     *
    @After
    public void tearDown(){
    }
    
    @Test
    public void testeContainsMed(){
        assertFalse("Esta a detetar uma linha verdadeira e sao todas falsas", enc.containsMed());
        projeto.encomenda.LinhaDeEncomenda l = new projeto.encomenda.LinhaDeEncomenda("4", "sem descriçao", 1, 200, true);
        enc.insereLinhaEnc(l);
        assertTrue("Nao esta a detetar a linha verdadeira", enc.containsMed());
    }
    
    @Test
    public void testeCalculaPrecoTotal(){
        float esperado = 4 * 2 + 3 * 20 + 10 * 0.2f;
        float obtido = enc.calculaPrecoTotal();
        assertEquals("Preço total mal calculado", esperado, obtido,0.001f);
    }
}
*/