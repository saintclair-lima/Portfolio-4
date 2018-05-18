package br.com.oficina.dao;

import br.com.oficina.modelo.Atendente;
import br.com.oficina.modelo.Cliente;
import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;
import br.com.oficina.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AtendenteDAO extends DAO{
    public Atendente buscarId(String usuario, String senha, String endereco, int matriculaBusca) throws EntidadeNulaException {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. ClienteDAO.buscarId()") == null){
            throw new EntidadeNulaException("Connection nula para inserir cliente. ClienteDAO.buscar()");
        }
        Atendente atendente = null;
        
        try {
            String comandoSQL = "SELECT atendente_matricula, atendente_nome, atendente_login, atendente_senha, atendente_cpf, atendente_logradouro, atendente_numero, atendente_bairro, atendente_cidade, atendente_complemento, atendente_estado, atendente_cep, atendente_telefone, atendente_email, atendente_observacoes, atendente_ativo FROM atendente WHERE atendente_matricula = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, matriculaBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    atendente = new Atendente(rs.getInt("atendente_matricula"),
                                            rs.getString("atendente_nome"),
                                            rs.getString("atendente_login"),
                                            rs.getString("atendente_senha"),
                                            rs.getString("atendente_cpf"),
                                            rs.getString("atendente_logradouro"),
                                            rs.getInt("atendente_numero"),
                                            rs.getString("atendente_bairro"),
                                            rs.getString("atendente_cidade"),
                                            rs.getString("atendente_complemento"),
                                            rs.getString("atendente_estado"),
                                            rs.getString("atendente_cep"),
                                            rs.getString("atendente_telefone"),
                                            rs.getString("atendente_email"),
                                            rs.getString("atendente_observacoes"),
                                            rs.getBoolean("atendente_ativo"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por atendente:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(atendente, "Atendente nulo: AtendenteDAO.buscarId()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(atendente, "Atendente") == null){
            throw new EntidadeNulaException("Atendnete não encontrado no banco de dados");
        }
        return atendente;
    }
    
    public List<Atendente> consultar(String usuario, String senha, String endereco) throws EntidadeNulaException {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. ClienteDAO.buscarId()") == null){
            throw new EntidadeNulaException("Connection nula para inserir cliente. ClienteDAO.buscar()");
        }
        List<Atendente> listaAtendente = new ArrayList<Atendente>();
        Atendente atendente = null;
        
        try {
            String comandoSQL = "SELECT atendente_matricula, atendente_nome, atendente_login, atendente_senha, atendente_cpf, atendente_logradouro, atendente_numero, atendente_bairro, atendente_cidade, atendente_complemento, atendente_estado, atendente_cep, atendente_telefone, atendente_email, atendente_observacoes, atendente_ativo FROM atendente";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    atendente = new Atendente(rs.getInt("atendente_matricula"),
                                            rs.getString("atendente_nome"),
                                            rs.getString("atendente_login"),
                                            rs.getString("atendente_senha"),
                                            rs.getString("atendente_cpf"),
                                            rs.getString("atendente_logradouro"),
                                            rs.getInt("atendente_numero"),
                                            rs.getString("atendente_bairro"),
                                            rs.getString("atendente_cidade"),
                                            rs.getString("atendente_complemento"),
                                            rs.getString("atendente_estado"),
                                            rs.getString("atendente_cep"),
                                            rs.getString("atendente_telefone"),
                                            rs.getString("atendente_email"),
                                            rs.getString("atendente_observacoes"),
                                            rs.getBoolean("atendente_ativo"));
                    listaAtendente.add(atendente);
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por atendente:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(atendente, "Atendente nulo: AtendenteDAO.buscarId()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(atendente, "Atendente") == null){
            throw new EntidadeNulaException("Atendnete não encontrado no banco de dados");
        }
        return listaAtendente;
    }
}
