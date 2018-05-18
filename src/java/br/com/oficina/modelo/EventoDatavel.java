/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.oficina.modelo;

import br.com.oficina.utils.Utils;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author saintclair
 */
public abstract class EventoDatavel {
    private GregorianCalendar dataAbertura;
    private GregorianCalendar dataEncerramento;
    
    EventoDatavel(GregorianCalendar dataAbertura) throws EntidadeNulaException{
        this.setDataAbertura(dataAbertura);
        GregorianCalendar dataEncerramento = null;
        this.setDataEncerramento(dataEncerramento);
    }
       
    
    public GregorianCalendar getDataAbertura() {
        return this.dataAbertura;
    }
    
    public Date getDataAberturaAsDate() {
        return Utils.getDataAsDate(this.dataAbertura);
    }
    
    public java.sql.Date getDataAberturaAsSQLDate(){
        return Utils.getDataAsSQLDate(dataAbertura);
    }
    
    public String getDataAberturaString() {
        return Utils.getDataString(this.dataAbertura);
    }
    
    public String getDataAberturaStringFormatada() {
        return Utils.getDataStringFormatada(this.dataAbertura);
    }
    
    public void setDataAbertura(GregorianCalendar dataAbertura) throws EntidadeNulaException {
        if (dataAbertura == null){
            throw new EntidadeNulaException("Data de Abertura inválida: objeto nulo passado como referência");
        } else {
            this.dataAbertura = dataAbertura;
        }
    }
    
    public void setDataAbertura(String dataString) throws InsercaoException {
        GregorianCalendar data;
        try {
            data = Utils.getGregCalendarDeString(dataString);
            this.dataAbertura = data;
        } catch (ParseException ex) {
            throw new InsercaoException("Data inválida: formato inserido inválido" + ex.getMessage());
        }
    }
    
    public GregorianCalendar getDataEncerramento() {
        return dataEncerramento;
    }
    
    public Date getDataEncerramentoAsDate() {
        return Utils.getDataAsDate(this.dataEncerramento);
    }
    
    public java.sql.Date getDataEncerramentoAsSQLDate(){
        return Utils.getDataAsSQLDate(dataEncerramento);
    }
    
    public String getDataEncerramentoString() {
        if (this.dataEncerramento == null){
            return "Ainda não Concluído";
        } else {
            return Utils.getDataString(this.dataEncerramento);
        }
    }
    
    public String getDataEncerramentoStringFormatada() {
        if (this.dataEncerramento == null){
            return "Ainda não Concluído";
        } else {
            return Utils.getDataStringFormatada(this.dataEncerramento);
        }
    }
    
    public void setDataEncerramento(GregorianCalendar dataEncerramento) throws EntidadeNulaException {
        this.dataEncerramento = dataEncerramento;
    }
    
    public void setDataEncerramento(String dataString) throws InsercaoException {
        GregorianCalendar data;
        try {
            if (dataString == null || dataString.equals("")){
                this.dataEncerramento = null;
            } else {
                data = Utils.getGregCalendarDeString(dataString);
                this.dataEncerramento = data;
            }
        } catch (ParseException ex) {
            throw new InsercaoException("Data inválida: formato inserido inválido" + ex.getMessage());
        }
    }
    
    
}
