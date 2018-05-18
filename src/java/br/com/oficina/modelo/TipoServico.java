package br.com.oficina.modelo;

public class TipoServico {
    
    private int codigo;
    private String nome;
    private int duracao; //em dias
    private double valor;
    private boolean ativo;
    
    public TipoServico(int codigo, String nome, int duracao, double valor) throws InsercaoException{
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setDuracao(duracao);
        this.setValor(valor);
        this.setAtivo(true);
    }
    
    public TipoServico(int codigo, String nome, int duracao, double valor, boolean ativo) throws InsercaoException{
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setDuracao(duracao);
        this.setValor(valor);
        this.setAtivo(ativo);
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) throws InsercaoException {
        if (codigo < 0){
            throw new InsercaoException();
        } else {
            this.codigo = codigo;
        }
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getDuracao() {
        return duracao;
    }
    
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
