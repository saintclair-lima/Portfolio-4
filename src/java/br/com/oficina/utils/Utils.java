/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.oficina.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Utils {
    public static String checaNull(String string){
        if (string == null){
            return "";
        } else {
            return string;
        }
    }
    
    public static Object checaNull(Object objeto){
        if (objeto == null){
            System.err.println("ALERTA: Objeto com valor nulo.");
        }
        
        return objeto;
    }
    
    public static Object checaNull(Object entidade, String objeto){
        if (entidade == null){
            System.err.println("ALERTA: " + objeto + " com valor nulo. Classe: " + objeto);
        }
        return entidade;
    }
    
    
    public static String getDataString(GregorianCalendar data) {
        if (data == null){
            return null;
        } else {
            Date dataString = (Date) data.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(dataString);
        }
    }
    
    public static String getDataStringFormatada(GregorianCalendar data) {
        if (data == null){
            return null;
        } else {
            Date dataString = (Date) data.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(dataString);
        }
    }
    
    public static Date getDataAsDate(GregorianCalendar data){
        if (data == null){
            return null;
        } else {
            return new Date(data.getTimeInMillis());
        }
    }
    
    public static java.sql.Date getDataAsSQLDate(GregorianCalendar data){
        if (data == null){
            return null;
        } else {
            return new java.sql.Date(data.getTimeInMillis());
        }
    }
    
    public static GregorianCalendar getGregCalendarDeString(String dataString) throws ParseException {
        if (dataString == null){
            return null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date) df.parse(dataString);
            GregorianCalendar gregCal = new GregorianCalendar();
            gregCal.setTime(date);
            return gregCal;
        }
    }
}
