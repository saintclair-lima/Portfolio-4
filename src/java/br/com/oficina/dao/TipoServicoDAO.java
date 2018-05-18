package br.com.oficina.dao;

import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;
import br.com.oficina.modelo.TipoServico;
import br.com.oficina.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TipoServicoDAO extends DAO{
    
    public int inserir(String usuario, String senha, String endereco, TipoServico tipoServico){
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir tipo de serviço. TipoServicoDAO.inserir()") == null){
            return TipoServicoDAO.ERRO_CONEXAO;
        }
        
        try {
            String comandoSQL = "INSERT INTO tipo_servico (tipo_servico_codigo, tipo_servico_nome, tipo_servico_duracao_estimada, tipo_servico_valor, tipo_servico_ativo) VALUES(NEXTVAL('seq_tipo_servico_codigo'),?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement para inserir tipo de servico. TipoServicoDAO.inserir()");
            
            
            ps.setString(1, tipoServico.getNome());
            ps.setInt(2, tipoServico.getDuracao());
            ps.setDouble(3, tipoServico.getValor());
            ps.setBoolean(4, tipoServico.isAtivo());
            ps.execute();
            conexao.close();
            return TipoServicoDAO.SUCESSO;
            
        } catch(SQLException e) {
            System.err.print(e.getMessage());
        }
        return TipoServicoDAO.SUCESSO;
    }
    
    public int alterar(String usuario, String senha, String endereco, TipoServico tipoServico){
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para alterar tipo de servico. TipoServicoDAO.alterar()") == null){
            return TipoServicoDAO.ERRO_CONEXAO;
            //tipo_servico_codigo = ?, tipo_servico_nome = ?, tipo_servico_duracao_estimada = ?, tipo_servico_valor = ?, tipo_servico_ativo = ?,
        }
        
        try {
            String comandoSQL = "UPDATE tipo_servico SET tipo_servico_nome = ?, tipo_servico_duracao_estimada = ?, tipo_servico_valor = ?, tipo_servico_ativo = ? WHERE tipo_servico_codigo = ?";

            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setString(1, tipoServico.getNome());
            ps.setInt(2, tipoServico.getDuracao());
            ps.setDouble(3, tipoServico.getValor());
            ps.setBoolean(4, tipoServico.isAtivo());
            ps.setInt(5, tipoServico.getCodigo());
            ps.executeUpdate();
            conexao.close();
            
            return TipoServicoDAO.SUCESSO;
        } catch (org.postgresql.util.PSQLException e){
            System.err.println("ERRO: Falha ao alterar Tipo de Serviço de codigo " + tipoServico.getCodigo() + ". Violação de valor único\r\n " + e.getMessage());
            return TipoServicoDAO.ERRO_SQL;
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao alterar Tipo de Serviço de codigo " + tipoServico.getCodigo() + "\r\n " + e.getMessage());
            return ClienteDAO.ERRO_SQL;
        }
    }
    
    public int desativar(String usuario, String senha, String endereco, int codBusca){
        TipoServico tipoServico;
        try {
            tipoServico = this.buscar(usuario, senha, endereco, codBusca);
            tipoServico.setAtivo(false);
            int codResultado = this.alterar(usuario, senha, endereco, tipoServico);
            return codResultado;
        } catch (EntidadeNulaException ex) {
            Logger.getLogger(TipoServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return TipoServicoDAO.ERRO_CODIGO;
        }
    }
    
    public List<TipoServico> consultar(String usuario, String senha, String endereco) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para consultar Tipo de Serviço. TipoServicoDAO.consultar()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Tipo de Serviço. TipoServicoDAO.consultar()");
        }
        
        List listaTipoServicos = new ArrayList();
        TipoServico tipoServico = null;
        
        try {
            String comandoSQL = "SELECT tipo_servico_codigo, tipo_servico_nome, tipo_servico_duracao_estimada, tipo_servico_valor, tipo_servico_ativo FROM tipo_servico";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    tipoServico = new TipoServico(rs.getInt("tipo_servico_codigo"),
                                            rs.getString("tipo_servico_nome"),
                                            rs.getInt("tipo_servico_duracao_estimada"),
                                            rs.getDouble("tipo_servico_valor"),
                                            rs.getBoolean("tipo_servico_ativo"));
                    listaTipoServicos.add(tipoServico);
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por tipo de Serviço:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(tipoServico, "TipoServico nulo: TipoServicoDAO.buscarId()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(tipoServico, "Cliente") == null){
            throw new EntidadeNulaException("Cliente não encontrado no banco de dados");
        }
        return listaTipoServicos;
    }
    
    public List<TipoServico> consultar(String usuario, String senha, String endereco, boolean mostraInativos) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para consultar Tipo de Serviço. TipoServicoDAO.consultar()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Tipo de Serviço. TipoServicoDAO.buscarId()");
        }
        
        List listaTipoServicos = new ArrayList();
        TipoServico tipoServico = null;
        
        try {
            String comandoSQL;
            if (mostraInativos){
                comandoSQL = "SELECT tipo_servico_codigo, tipo_servico_nome, tipo_servico_duracao_estimada, tipo_servico_valor, tipo_servico_ativo FROM tipo_servico";
            } else {
                comandoSQL = "SELECT tipo_servico_codigo, tipo_servico_nome, tipo_servico_duracao_estimada, tipo_servico_valor, tipo_servico_ativo FROM tipo_servico WHERE tipo_servico_ativo IS TRUE";
            }
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    tipoServico = new TipoServico(rs.getInt("tipo_servico_codigo"),
                                            rs.getString("tipo_servico_nome"),
                                            rs.getInt("tipo_servico_duracao_estimada"),
                                            rs.getDouble("tipo_servico_valor"),
                                            rs.getBoolean("tipo_servico_ativo"));
                    listaTipoServicos.add(tipoServico);
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por tipo de Serviço:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(tipoServico, "TipoServico nulo: TipoServicoDAO.buscarId()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(tipoServico, "TipoServico") == null){
            throw new EntidadeNulaException("Tipo de Serviço não encontrado no banco de dados");
        }
        return listaTipoServicos;
    }
    
    public TipoServico buscar(String usuario, String senha, String endereco, int codBusca) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir Tipo de Serviço. TipoServicoDAO.buscarId()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Tipo de Serviço. TipoServicoDAO.buscarId()");
        }
        
        TipoServico tipoServico = null;
        
        try {
            String comandoSQL = "SELECT tipo_servico_codigo, tipo_servico_nome, tipo_servico_duracao_estimada, tipo_servico_valor, tipo_servico_ativo FROM tipo_servico WHERE tipo_servico_codigo = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, codBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    tipoServico = new TipoServico(rs.getInt("tipo_servico_codigo"),
                                            rs.getString("tipo_servico_nome"),
                                            rs.getInt("tipo_servico_duracao_estimada"),
                                            rs.getDouble("tipo_servico_valor"),
                                            rs.getBoolean("tipo_servico_ativo"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por tipo de Serviço:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(tipoServico, "TipoServico nulo: TipoServicoDAO.buscarId()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(tipoServico, "TipoServico") == null){
            throw new EntidadeNulaException("Tipo de Serviço não encontrado no banco de dados");
        }
        return tipoServico;
    }

}
