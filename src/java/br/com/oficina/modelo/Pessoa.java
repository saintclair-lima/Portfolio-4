package br.com.oficina.modelo;
import br.com.oficina.utils.CNP;
import br.com.oficina.utils.Utils;

public class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private String logradouro;
    private int numeroCasa;
    private String bairro;
    private String cidade;
    private String complemento;
    private String estado;
    private String cep;
    private String telefone;
    private String email;
    private String observacoes;
    private boolean ativo;
    
    public Pessoa(int id, String nome, String cpf, String logradouro, int numero,
            String bairro, String cidade, String complemento, String estado, String cep,
            String telefone, String email,  String observacoes, boolean ativo) throws InsercaoException{
        
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setLogradouro(logradouro);
        this.setNumeroCasa(numero);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setComplemento(complemento);
        this.setEstado(estado);
        this.setCep(cep);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setObservacoes(observacoes);
        this.setAtivo(ativo);
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) throws InsercaoException {
        if (id < 0){
            throw new InsercaoException("Codigo invalido: valor menor que zero");
        } else {
            this.id = id;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws InsercaoException {
        if (nome.length() > 50){
            throw new InsercaoException("Nome inválido: número de caracteres maior que 50");
        } else {
            this.nome = nome;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws InsercaoException {
        if (! CNP.isValidCPF(cpf)){
            throw new InsercaoException("CPF invalido");
        } else {
            this.cpf = cpf;
        }
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) throws InsercaoException {
        if (logradouro.length() > 40){
            throw new InsercaoException("Logradouro inválido: número de caracteres maior que 40");
        } else {
            this.logradouro = logradouro;
        }
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) throws InsercaoException {
        if (numeroCasa < 1){
            throw new InsercaoException("Número inválido: número negativo");
        } else {
            this.numeroCasa = numeroCasa;
        }
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) throws InsercaoException {
        if (bairro.length() > 20){
            throw new InsercaoException("Bairro inválido: número de caracteres maior que 20");
        } else {
            this.bairro = bairro;
        }
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) throws InsercaoException {
        if (cidade.length() > 25){
            throw new InsercaoException("Cidade inválida: número de caracteres maior que 25");
        } else {
            this.cidade = cidade;
        }
    }

    public String getComplemento() {
        return complemento;
    }

    public String getComplemento(boolean SQL) {
        if (SQL && this.complemento.equals("")){
            return "NULL";
        } else {
            return complemento;
        }
    }

    public void setComplemento(String complemento) throws InsercaoException {
        complemento = Utils.checaNull(complemento);
        if (complemento.length() > 250){
            throw new InsercaoException("Complemento inválido: número de caracteres maior que 250");
        } else {
            this.complemento = complemento;
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) throws InsercaoException {
        if (estado.length() > 2){
            throw new InsercaoException("Estado inválido: número de caracteres maior que 2");
        } else {
            this.estado = estado;
        }
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) throws InsercaoException {
        try{
            boolean numeroNegativo = Integer.parseInt(cep) < 0;
            if (numeroNegativo){
                throw new InsercaoException("CEP inválido: conversão em número com valor negativo");
            }
        } catch (NumberFormatException e){
            throw new InsercaoException("CEP inválido: detectada presença de caracteres não numéricos" + e.getMessage());
        }
        if (cep.length() > 8){
            throw new InsercaoException("CEP inválido: numero de caracteres maior que 8");
        } else {
            this.cep = cep;
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) throws InsercaoException {
        try{
            boolean numeroNegativo = Long.parseLong(telefone) < 0;
            if (numeroNegativo){
                throw new InsercaoException("Telefone inválido: conversão em número com valor negativo");
            }
        } catch (NumberFormatException e){
            throw new InsercaoException("Telefone inválido: detectada presença de caracteres não numéricos" + e.getMessage());
        }
        
        if (telefone.length() > 14){
            throw new InsercaoException("Telefone inválido: mais que 14 dígitos inseridos");
        } else {
            this.telefone = telefone;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getEmail(boolean SQL) {
        if (SQL && this.email.equals("")){
            return "NULL";
        } else {
            return email;
        }
    }

    public void setEmail(String email) throws InsercaoException {
        email = Utils.checaNull(email);
        if (email == "" || email == null){
            this.email = email;
        } else if (email.length() > 40){
            throw new InsercaoException("Email inválido: mais que 40 dígitos inseridos");
        } else {
            this.email = email;
        }
    }

    public String getObservacoes() {
        return observacoes;
    }

    public String getObservacoes(boolean SQL) {
        if (SQL && this.observacoes == ""){
            return "NULL";
        } else {
            return observacoes;
        }
    }

    public void setObservacoes(String observacoes) throws InsercaoException {
        observacoes = Utils.checaNull(observacoes);
        if (observacoes.length() > 500){
            throw new InsercaoException("Valor de observações inválido: mais que 500 dígitos inseridos");
        } else {
            this.observacoes = observacoes;
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
