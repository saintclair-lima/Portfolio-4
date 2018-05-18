package br.com.oficina.modelo;

public class SetorOficina {
    private int codigo;
    private String nome;
    private Tecnico tecnico;
    private boolean ativo;
    
    public SetorOficina(int codigo, String nome, Tecnico tecnico, boolean ativo) throws InsercaoException, EntidadeNulaException{
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setTecnico(tecnico);
        this.setAtivo(ativo);
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) throws InsercaoException {
        if (codigo < 1){
            throw new InsercaoException("Código inválido: Número negativo detectado.");
        } else {
            this.codigo = codigo;
        }
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) throws InsercaoException {
        if (nome.length() > 30){
           throw new InsercaoException("Nome inválido: número de caracteres maior que 30");
        } else { 
            this.nome = nome;
        }
    }
    
    public Tecnico getTecnico() {
        return tecnico;
    }
    
    public void setTecnico(Tecnico tecnico) throws EntidadeNulaException {
        if (tecnico == null) {
            throw new EntidadeNulaException("Técnico Inválido: objeto passado com referência a nula");
        } else {
            this.tecnico = tecnico;
        }
        
    }
    
    public boolean isAtivo() {
        return ativo;
    }

    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
