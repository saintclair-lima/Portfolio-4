package br.com.oficina.modelo;


public class Funcionario extends Pessoa{
    private String login;
    private String senha;
    public Funcionario(int id, String nome, String login, String senha, String cpf,
            String logradouro, int numero, String bairro, String cidade, String complemento,
            String estado, String cep, String telefone, String email, String observacoes,
            boolean ativo) throws InsercaoException {
        
        super(id, nome, cpf, logradouro, numero, bairro, cidade, complemento, estado, cep, telefone, email, observacoes, ativo);
        this.setLogin(login);
        this.setSenha(senha);
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) throws InsercaoException {
        if (login.length() > 20){
            throw new InsercaoException("Login inválido: número de caracteres maior que 20");
        } else{
            this.login = login;
        }
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) throws InsercaoException {
        if (senha.length() > 20){
            throw new InsercaoException("Senha inválida: número de caracteres maior que 20");
        } else{
            this.senha = senha;
        }
    }
    
}
