package br.com.oficina.modelo;

public class Mecanico extends Funcionario{
    private Especialidade especialidade;
    private SetorOficina setorOficina;
    
    public Mecanico(int id, String nome, String login, String senha, String cpf,
                    String logradouro, int numero, String bairro, String cidade,
                    String complemento, String estado, String cep, String telefone,
                    String email, String observacoes, Especialidade especialidade,
                    SetorOficina setorOficina, boolean ativo) throws InsercaoException, EntidadeNulaException {
        
        super(id, nome, login, senha, cpf, logradouro, numero, bairro, cidade, complemento, estado, cep, telefone, email, observacoes, ativo);
        this.setSetorOficina(setorOficina);
        this.setEspecialidade(especialidade);
    } 
    
    public Especialidade getEspecialidade() {
        return especialidade;
    }
    
    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
    
    public SetorOficina getSetorOficina() {
        return setorOficina;
    }
    
    public void setSetorOficina(SetorOficina setorOficina) throws EntidadeNulaException {
        this.setorOficina = setorOficina;
    }
}
