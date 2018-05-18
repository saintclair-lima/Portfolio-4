/*
item_estoque_codigo int not null constraint item_estoque_pk primary key,
item_estoque_nome varchar(25) not null unique,
item_estoque_descricao text not null unique,
item_estoque_preco numeric not null,
item_estoque_quantidade_estoque int not null,
item_estoque_ativo boolean);
 */
package br.com.oficina.modelo;

public class ItemEstoque {
    
    private int codigo;
    private String nome;
    private String descricao;
    private double preco;
    private static double quantidadeEstoque;
    private boolean ativo;
    
    public ItemEstoque(int codigo, String nome, String descricao, double preco, double quantidadeEstoque) throws InsercaoException {
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setQuantidadeEstoque(quantidadeEstoque);
        this.setAtivo(true);
    }
    
    public ItemEstoque(int codigo, String nome, String descricao, double preco, double quantidadeEstoque, boolean ativo) throws InsercaoException {
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setQuantidadeEstoque(quantidadeEstoque);
        this.setAtivo(ativo);
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) throws InsercaoException {
        this.codigo = codigo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public static double getQuantidadeEstoque() {
        return quantidadeEstoque;    
    }
    
    public static void setQuantidadeEstoque(double aQuantidadeEstoque) {
        quantidadeEstoque = aQuantidadeEstoque;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
