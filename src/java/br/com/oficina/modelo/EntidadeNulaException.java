package br.com.oficina.modelo;

public class EntidadeNulaException extends Exception{
    public EntidadeNulaException() { super(); }
    public EntidadeNulaException(String message) { super(message); }
    public EntidadeNulaException(String message, Throwable cause) { super(message, cause); }
}
