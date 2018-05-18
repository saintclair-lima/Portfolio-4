
package br.com.oficina.modelo;

/**
forma_pagamento_codigo int not null constraint forma_pagamento_pk primary key,
forma_pagamento_nome varchar(20) unique,
forma_pagamento_ativo boolean
 */
public class FormaPagamento {
    private int codigo;
    private String nome;
    private boolean ativo;
    
    public FormaPagamento(int codigo, String nome){
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setAtivo(true);
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
