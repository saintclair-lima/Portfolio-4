package br.com.oficina.modelo;

import java.util.GregorianCalendar;

public abstract class ModeloRaOs extends EventoDescritivelDatavel {
    private int numero;
    
    public ModeloRaOs(int numero, GregorianCalendar dataAbertura, String descricaoAbertura) throws InsercaoException, EntidadeNulaException{
        super(dataAbertura, descricaoAbertura);
        this.setNumero(numero);
    }
    
    public ModeloRaOs(int numero, GregorianCalendar dataAbertura, String descricaoAbertura, String estado) throws InsercaoException, EntidadeNulaException{
        super(dataAbertura, descricaoAbertura);
        this.setNumero(numero);
        this.setEstado(estado);
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) throws InsercaoException {
        if (numero < 0){
            throw new InsercaoException("Número inválido: valor negativo");
        } else {
            this.numero = numero;
        }
    }
    
    @Override
    public String toString(){
        String descricao ="===========================\n";
        descricao = descricao + "NÚMERO: " + Integer.toString(numero) + "\nDATA ABERTURA: " + this.getDataAberturaStringFormatada() + "\nDATA ENCERRAMENTO: " + this.getDataEncerramentoStringFormatada() + "\nTEXTO ABERTURA: " + this.getDescricaoAbertura() + "\nTEXTO ENCERRAMENTO: " + this.getDescricaoEncerramento() + "\nESTADO: " + this.getEstado() + "\nATIVO: " + Boolean.toString(this.isAtivo());
        return descricao;
    }
}
