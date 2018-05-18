package br.com.oficina.modelo;

import br.com.oficina.utils.CNP;


public class Cliente extends Pessoa{
    private String cnpj;
    public Cliente(int id, String nome, String cpf, String cnpj, String logradouro,
            int numero, String bairro, String cidade, String complemento, String estado,
            String cep, String telefone, String email, String observacoes, boolean ativo) throws InsercaoException {
        
        super(id, nome, cpf, logradouro, numero, bairro, cidade, complemento, estado, cep, telefone, email, observacoes, ativo);
        this.setCnpj(cnpj);
    }

    public String getCnpj() {
        return cnpj;
    }
    
    public String getCnpj(boolean SQL) {
        if (SQL && this.getCnpj().equals("")){
            return "NULL";
        } else {
            return cnpj;
        }
    }
    
    public void setCnpj(String cnpj) throws InsercaoException {
        if (cnpj == null || cnpj.equals("")){
            this.cnpj = "";
        } else if (! CNP.isValidCNPJ(cnpj)){
            throw new InsercaoException("CNPJ invalido");
        } else {
            this.cnpj = cnpj;
        }
    }
    
}
