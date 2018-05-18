/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.oficina.modelo;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author saintclair
 */
public abstract class EventoDescritivelDatavel extends EventoDatavel{
    private String descricaoAbertura;
    private String descricaoEncerramento;
    private final Set<String> LISTA_ESTADOS = new HashSet<String>(Arrays.asList(new String[] {"Aberto", "Em Andamento", "Cancelado", "Concluído"}));
    protected String estado;
    private boolean ativo;
    
    public EventoDescritivelDatavel(GregorianCalendar dataAbertura, String descricaoAbertura) throws InsercaoException, EntidadeNulaException{
        super(dataAbertura);
        this.setDescricaoAbertura(descricaoAbertura);
        
        String dataEnc = null;
        this.setDataEncerramento(dataEnc);
        this.setDescricaoEncerramento("");
        this.setEstado("Aberto");
    }
    
    public String getDescricaoAbertura() {
        return descricaoAbertura;
    }
    
    public void setDescricaoAbertura(String descricaoAbertura) {
        this.descricaoAbertura = descricaoAbertura;
    }
    
    public String getDescricaoEncerramento() {
        return descricaoEncerramento;
    }
    
    public void setDescricaoEncerramento(String descricaoEncerramento) {
        this.descricaoEncerramento = descricaoEncerramento;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) throws InsercaoException {
        if (! LISTA_ESTADOS.contains(estado)){
            throw new InsercaoException("Estado inválido: estado inserido (" + estado + ") não é válido");
        }else {
            this.estado = estado;
        }
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    @Override
    public String getDataEncerramentoString() {
        if (! this.getEstado().equals("Concluído")){
            return "Ainda não Concluído";
        } else {
            return super.getDataEncerramentoString();
        }
    }
    
    @Override
    public String getDataEncerramentoStringFormatada() {
        if (! this.getEstado().equals("Concluído")){
            return "Ainda não Concluído";
        } else {
            return super.getDataEncerramentoString();
        }
    }
}
