

package br.com.oficina.modelo;

public class Carro {
    private int id;
    private String modelo;
    private String marca;
    private String ano;
    private String placa;
    private String cor;
    private Cliente cliente;
    private boolean ativo;

    public Carro (int id, String modelo, String marca, String ano, String placa, String cor, Cliente cliente, boolean ativo) throws InsercaoException, EntidadeNulaException{
        this.setId(id);
        this.setModelo(modelo);
        this.setMarca(marca);
        this.setAno(ano);
        this.setPlaca(placa);
        this.setCor(cor);
        this.setCliente(cliente);
        this.setAtivo(ativo);
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) throws InsercaoException {
        if (id < 1){
            throw new InsercaoException("Código inválido: número negativo inserido");
        } else {
            this.id = id;
        }
    }
    
    public String getModelo() {
        return modelo;
    }

    
    public void setModelo(String modelo) throws InsercaoException {
        if (modelo.length() > 20){
            throw new InsercaoException("Modelo inválido: número de caracteres maior que 20");
        } else {
            this.modelo = modelo;
        }
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) throws InsercaoException {
        if (marca.length() > 22){
            throw new InsercaoException("Marca inválida: número de caracteres maior que 22");
        } else {
            this.marca = marca;
        }
    }
    
    public String getAno() {
        return ano;
    }
    
    public void setAno(String ano) throws InsercaoException {
        if (ano.length() > 4){
            throw new InsercaoException("Ano inválido: número de caracteres maior que 4");
        } else {
            this.ano = ano;
        }
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) throws InsercaoException {
        if (placa.length() > 22){
            throw new InsercaoException("Placa inválida: número de caracteres maior que 22");
        } else {
            this.placa = placa;
        }
    }
    
    public String getCor() {
        return cor;
    }
    
    public void setCor(String cor) throws InsercaoException {
        if (cor.length() > 15){
            throw new InsercaoException("Cor inválida: número de caracteres maior que 15");
        } else {
            this.cor = cor;
        }
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) throws EntidadeNulaException {
        if (cliente == null){
            throw new EntidadeNulaException("Cliente inválido: objeto passado com referência a nula");
        } else {
            this.cliente = cliente;
        }
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
