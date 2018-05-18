package br.com.oficina.modelo;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class Pagamento extends EventoDatavel{
    private int numero;
    private final Set<String> LISTA_ESTADOS = new HashSet<String>(Arrays.asList(new String[] {"Pendente", "Pago", "Cancelado"}));
    private String estado;
    private long numeroNota;
    private FormaPagamento formaPagamento;
    private OrdemServico os;
    
    public Pagamento(int numero, GregorianCalendar dataAbertura, int numeroNota, FormaPagamento formaPagamento, OrdemServico os) throws EntidadeNulaException {
        super(dataAbertura);
        this.setNumero(numero);
        this.setEstado("Pendente");
        this.setNumeroNota(numeroNota);
        this.setFormaPagamento(formaPagamento);
        this.setOs(os);
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the numeroNota
     */
    public long getNumeroNota() {
        return numeroNota;
    }

    /**
     * @param numeroNota the numeroNota to set
     */
    public void setNumeroNota(long numeroNota) {
        this.numeroNota = numeroNota;
    }

    /**
     * @return the formaPagamento
     */
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * @param formaPagamento the formaPagamento to set
     */
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * @return the os
     */
    public OrdemServico getOs() {
        return os;
    }

    /**
     * @param os the os to set
     */
    public void setOs(OrdemServico os) {
        this.os = os;
    }
    
}
