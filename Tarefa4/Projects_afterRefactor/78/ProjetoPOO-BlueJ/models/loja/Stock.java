package models.loja;

import excepitions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Stock implements Serializable {

    /**
     * Variaveis de Instancia
     */
    private Map<String,Produto> stockVar;

    /**
     * Construtor por Omissao
     */
    public Stock() {
        this.stockVar = new HashMap<>();
    }

    public Stock copyStock(Stock s){
        Stock st = new Stock();
        st.setStockVar(s.getStockVar());
        return st;
    }

    /**
     * Construtor Parametrizado de Stock
     * @param stockVar Map<String,Produto>
     */
    public Stock(Map<String, Produto> stockVar) {
        this.setStockVar(stockVar);
    }

    /**
     * Construtor de copia de uma Stock
     * @param s Stock
     */
    public Stock(Stock s){
        this.stockVar = s.getStockVar();
    }

    /**
     * Devolve o Stock de Produtos, representado por um Map onde
     * as Keys sao o codigo do Produto e os Values sao os Produtos
     *
     * @return Map com os Produtos e os seus codigos
     */
    public Map<String, Produto> getStockVar() {
        HashMap<String,Produto> res = new HashMap<>();
        for (Map.Entry<String,Produto> s : this.stockVar.entrySet()){
            res.put(s.getKey(),s.getValue().copyProduto(s.getValue()));
        }

        return res;
    }

    /**
     * Definir o novo Stock
     *
     * @param stockVar correspondente ao novo Map que representara o Stock
     */
    public void setStockVar(Map<String, Produto> stockVar) {
        this.stockVar = new HashMap<>();
        for (Map.Entry<String,Produto> s : stockVar.entrySet()){
            this.stockVar.put(s.getKey(),s.getValue().copyProduto(s.getValue()));
        }
    }

    /**
     * Dado um codigo de um Produto tentar devolver o Produto associado a esse codigo
     *
     * @param codProduto correspondente ao codigo do produto
     * @return Produto associado ao codigo fornecido
     * @throws ProdutoNotFoundException caso o codigo do produto nao exista nao stock
     */
    public Produto getProduto(String codProduto) throws ProdutoNotFoundException{
        try {
            return this.stockVar.get(codProduto).copyProduto(this.stockVar.get(codProduto));
        }catch (NullPointerException e){
            throw new ProdutoNotFoundException("Produto inexistente");
        }
    }

    /**
     * Adicionar um Produto ao stock
     *
     * @param p correspondente ao Produto a adiconar
     */
    public void addStock(Produto p){
        this.stockVar.put(p.getCodigoProduto(),p.copyProduto(p));
    }

    /**
     * Dado um codigo de um Produto, vai ao Stock e caso esse codigo
     * esteja associado a um Produto ira remove-lo do Stock
     *
     * @param cod correspondente ao codigo do Produto que se pretende remover
     * @return se porduto foi removido com sucesso ou se na existe
     */
    public String removeProduto(String cod){
        String s = "";
        if(this.stockVar.containsKey(cod)) {
            this.stockVar.remove(cod);
            s = "Produto removido do Stock com sucesso.";

        }else {
            s = "Produto n�o existe em Stock.";
        }

        return s;
    }

    /**
     * Devolve tamanho do Stock
     *
     * @return tamanho do Stock
     */
    public int tamStock(){
        return this.stockVar.size();
    }

    /**
     * Devolve lista com todos os produtos presentes no Stock
     *
     * @return lista com todos Produtos
     */
    public List<Produto> getListaProdutos(){
        List<Produto> list = new ArrayList<>();
        for (Produto produto : this.stockVar.values()) {
            Produto clone = produto.copyProduto(produto);
            list.add(clone);
        }
        return list;
    }

    /**
     * Devolve lista com todos os Produtos que sao medicamentos no Stock
     *
     * @return lista com todos os Medicamentos
     */
    public List<Produto> getMedicamentos(){
        List<Produto> list = new ArrayList<>();
        for (Produto produto : this.stockVar.values()) {
            if (produto.isMedicamento()) {
                Produto clone = produto.copyProduto(produto);
                list.add(clone);
            }
        }
        return list;
    }

    /**
     * Permite apresentar no ecra as informacoes necessarias
     *
     * @return String com informacao da Classe
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;

        List<Produto> medicamentos = this.stockVar.values().parallelStream().filter(Produto::isMedicamento).collect(Collectors.toList());
        List<Produto> produtos = this.stockVar.values().parallelStream().filter(p->!p.isMedicamento()).collect(Collectors.toList());

        if(!medicamentos.isEmpty()){
            sb.append("Medicamentos:\n");
            for (Produto m : medicamentos) {
                sb.append(i + "� -> ").append(m.getCodigoProduto() + " | ").append(m.toString() + "\n");
                i++;
            }

            i=1;
            sb.append("\n");
        }

        if(!produtos.isEmpty()){
            sb.append("Produtos:\n");
            for (Produto p : produtos) {
                sb.append(i + "� -> ").append(p.getCodigoProduto() + " | ").append(p.toString() + "\n");
                i++;
            }
        }

        if(produtos.isEmpty() && medicamentos.isEmpty()){
            sb.append("N�o existem produtos disponiveis na loja\n");
        }

        return sb.toString();
    }

    /**
     * Verifica se 2 Objects sao iguais
     *
     * @return true se Object for igual ao Stock
     */

    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        Stock s = (Stock) o;

        return (this.stockVar.equals(s.getStockVar()));
    }
    public int hashCode(){
        return 0;
    }

}

