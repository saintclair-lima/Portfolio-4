package br.com.oficina.modelo;

public class Tecnico extends Funcionario {

    public Tecnico(int id, String nome, String login, String senha, String cpf,
            String logradouro, int numero, String bairro, String cidade, String complemento,
            String estado, String cep, String telefone, String email, String observacoes, boolean ativo) throws InsercaoException {
        
        super(id, nome, login, senha, cpf, logradouro, numero, bairro, cidade, complemento, estado, cep, telefone, email, observacoes, ativo);
    }
    
}
