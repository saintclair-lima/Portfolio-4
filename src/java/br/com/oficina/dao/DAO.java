package br.com.oficina.dao;

public abstract class DAO {
    public static final int SUCESSO = 0;
    public static final int ERRO_CONEXAO = 1;
    public static final int ERRO_SQL = 2;
    public static final int ERRO_INSERCAO = 3;
    public static final int ERRO_CODIGO = 4;
    public static final int ERRO_SQL_VIOLACAO = 5;
}
