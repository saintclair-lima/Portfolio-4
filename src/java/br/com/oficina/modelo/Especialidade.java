package br.com.oficina.modelo;

public class Especialidade {
    private int codigo;
    private String nome;
    private double custoHora;
    private boolean ativo;
    
    public Especialidade(int codigo, String nome, double custoHora, boolean ativo) throws InsercaoException{
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setCustoHora(custoHora);
        this.setAtivo(ativo);        
    }
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) throws InsercaoException {
        if (codigo < 1){
            throw new InsercaoException("Código inválido: valor menor que 1");
        } else {
            this.codigo = codigo;
        }
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) throws InsercaoException {
        if (nome.length() > 20){
            throw new InsercaoException("Nome Inválido: número de caracteres maior que 20");
        } else {
            this.nome = nome;
        }
    }
    
    public double getCustoHora() {
        return custoHora;
    }
    
    public void setCustoHora(double custoHora) throws InsercaoException {
        if (custoHora < 0){
            throw new InsercaoException("Custo por hora inválido: valor negativo");
        } else {
            this.custoHora = custoHora;
        }
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}