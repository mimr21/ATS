/*import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

/**
 * A classe de teste LojaTest.
 *
public class LojaTest{
    private projeto.entidades.Loja loja;
    private projeto.projeto.encomenda.encomenda enc1;
    private projeto.projeto.encomenda.encomenda enc2;
    
    /**
     * Construtor default para a classe de teste LojaTest
     *
    public LojaTest(){
    }

    /**
     * Define a projeto.entidades.Loja.
     *
     * Chamado antes de cada método de caso de teste.
     *
    @Before
    public void setUp(){
        projeto.encomenda.LinhaDeEncomenda l1, l2, l3;
        List<projeto.projeto.encomenda.encomenda> encs = new ArrayList<>();
        this.loja = new projeto.entidades.Loja("001", "Lidl", new projeto.util.GPS(13,12), encs, true, 3, 1, new ArrayList<>());
        
        List<projeto.encomenda.LinhaDeEncomenda> lista = new ArrayList<>();
        projeto.projeto.encomenda.encomenda enc = new projeto.projeto.encomenda.encomenda("000001", "Continente", "Joao",  2, false, lista);
        l1 = new projeto.encomenda.LinhaDeEncomenda("pao", "sem descriçao", 4, 2, false);
        l2 = new projeto.encomenda.LinhaDeEncomenda("chocolate", "sem descriçao", 3, 20, false);
        l3 = new projeto.encomenda.LinhaDeEncomenda("bananas", "sem descriçao", 10, 0.2f, false);
        enc.insereLinhaEnc(l1);
        enc.insereLinhaEnc(l2);
        enc.insereLinhaEnc(l3);
        
        List<projeto.encomenda.LinhaDeEncomenda> lista1 = new ArrayList<>();
        projeto.projeto.encomenda.encomenda enc1 = new projeto.projeto.encomenda.encomenda("000002", "Continente", "Joao",  0.5f, true, lista1);
        l1 = new projeto.encomenda.LinhaDeEncomenda("pao", "sem descriçao", 4, 0.1f, false);
        l2 = new projeto.encomenda.LinhaDeEncomenda("fiambre", "sem descriçao", 16, 0.2f, false);
        l3 = new projeto.encomenda.LinhaDeEncomenda("queijo", "sem descriçao", 32, 0.3f, false);
        enc1.insereLinhaEnc(l1);
        enc1.insereLinhaEnc(l2);
        enc1.insereLinhaEnc(l3);
        
        List<projeto.encomenda.LinhaDeEncomenda> lista2 = new ArrayList<>();
        projeto.projeto.encomenda.encomenda enc2 = new projeto.projeto.encomenda.encomenda("000002", "Continente", "Joao",  3, true, lista1);
        l1 = new projeto.encomenda.LinhaDeEncomenda("chocolate", "sem descriçao", 1, 5, false);
        l2 = new projeto.encomenda.LinhaDeEncomenda("vinho", "sem descriçao", 1, 5, false);
        enc1.insereLinhaEnc(l1);
        enc1.insereLinhaEnc(l2);
        
        List<projeto.encomenda.LinhaDeEncomenda> lista3 = new ArrayList<>();
        projeto.projeto.encomenda.encomenda enc3 = new projeto.projeto.encomenda.encomenda("000002", "Continente", "Joao",  1, false, lista1);
        l1 = new projeto.encomenda.LinhaDeEncomenda("Bruffen", "sem descriçao", 1, 6, true);
        l2 = new projeto.encomenda.LinhaDeEncomenda("Agua", "sem descriçao", 4, 0.1f, false);
        enc1.insereLinhaEnc(l1);
        enc1.insereLinhaEnc(l2);
        
        this.loja.adicionaEnc(enc); this.loja.adicionaEnc(enc1); this.loja.adicionaEnc(enc2); this.loja.adicionaEnc(enc3);
        this.enc1 = new projeto.projeto.encomenda.encomenda(enc3);
        this.enc2 = new projeto.projeto.encomenda.encomenda(enc2);
    }

    /**
     * Tears down the test fixture.
     *
     * Chamado após cada método de teste de caso.
     *
    @After
    public void tearDown(){
    }
    
    /*
    @Test
    public void testeEncomendaPronta(){
        assertTrue("Nao encontrou a encomenda Pronta", this.loja.encomendaPronta(this.enc2));
        assertFalse("Deveria ser falso porque a encomenda nao esta pronta", this.loja.encomendaPronta(this.enc1));
    }*
}*/
