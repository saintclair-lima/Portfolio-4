package br.com.oficina.dao;

import br.com.oficina.modelo.Cliente;
import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;
import br.com.oficina.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO extends DAO{

    public int inserir(String usuario, String senha, String endereco, Cliente cliente) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. ClienteDAO.inserir()") == null){
            return ClienteDAO.ERRO_CONEXAO;
        }
        
        try {
            String comandoSQL = "INSERT INTO cliente (cliente_id, cliente_nome, cliente_cpf, cliente_cnpj, cliente_logradouro, cliente_numero, cliente_bairro, cliente_cidade, cliente_complemento, cliente_estado, cliente_cep, cliente_telefone, cliente_email, cliente_observacoes, cliente_ativo) VALUES(NEXTVAL('seq_cliente_codigo'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatementpara inserir cliente. ClienteDAO.inserir()");
            
            
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            if (cliente.getCnpj().equals("") || cliente.getCnpj() == null){
                ps.setNull(3, java.sql.Types.VARCHAR);
            } else {
                ps.setString(3, cliente.getCnpj());
            }
            ps.setString(4, cliente.getLogradouro());
            ps.setInt(5, cliente.getNumeroCasa());
            ps.setString(6, cliente.getBairro());
            ps.setString(7, cliente.getCidade());
            if (cliente.getComplemento().equals("") || cliente.getComplemento() == null){
                ps.setNull(8, java.sql.Types.LONGVARCHAR);
            } else {
                ps.setString(8, cliente.getComplemento());
            }
            ps.setString(9, cliente.getEstado());
            ps.setString(10, cliente.getCep());
            ps.setString(11, cliente.getTelefone());
            if (cliente.getEmail().equals("") || cliente.getEmail() == null){
                ps.setNull(12, java.sql.Types.VARCHAR);
            } else {
                ps.setString(12, cliente.getEmail());
            }
            ps.setString(13, cliente.getObservacoes(true));
            ps.setBoolean(14, cliente.isAtivo());
            ps.execute();
            conexao.close();
            return ClienteDAO.SUCESSO;
            
        } catch(SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    public int alterar(String usuario, String senha, String endereco, Cliente cliente) {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. ClienteDAO.alterar()") == null){
            return ClienteDAO.ERRO_CONEXAO;
        }
        
        try {
            String comandoSQL = "UPDATE cliente SET cliente_nome = ?, cliente_cpf = ?, cliente_cnpj = ?, cliente_logradouro = ?, cliente_numero = ?, cliente_bairro = ?, cliente_cidade = ?, cliente_complemento = ?, cliente_estado = ?, cliente_cep = ?, cliente_telefone = ?, cliente_email = ?, cliente_observacoes = ?, cliente_ativo = ? WHERE cliente_id = ?";

            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            if (cliente.getCnpj().equals("") || cliente.getCnpj() == null){
                ps.setNull(3, java.sql.Types.VARCHAR);
            } else {
                ps.setString(3, cliente.getCnpj());
            }
            ps.setString(4, cliente.getLogradouro());
            ps.setInt(5, cliente.getNumeroCasa());
            ps.setString(6, cliente.getBairro());
            ps.setString(7, cliente.getCidade());
            if (cliente.getComplemento().equals("") || cliente.getComplemento() == null){
                ps.setNull(8, java.sql.Types.LONGVARCHAR);
            } else {
                ps.setString(8, cliente.getComplemento());
            }
            ps.setString(9, cliente.getEstado());
            ps.setString(10, cliente.getCep());
            ps.setString(11, cliente.getTelefone());
            if (cliente.getEmail().equals("") || cliente.getEmail() == null){
                ps.setNull(12, java.sql.Types.VARCHAR);
            } else {
                ps.setString(12, cliente.getEmail());
            }
            ps.setString(13, cliente.getObservacoes(true));
            ps.setBoolean(14, cliente.isAtivo());
            ps.setInt(15, cliente.getId());
            ps.executeUpdate();
            conexao.close();
            
            return ClienteDAO.SUCESSO;
        } catch (org.postgresql.util.PSQLException e){
            System.err.println("ERRO: Falha ao alterar cliente de codigo " + cliente.getId() + ". Violação de valor único\r\n " + e.getMessage());
            return ClienteDAO.ERRO_SQL;
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao alterar cliente de codigo " + cliente.getId() + "\r\n " + e.getMessage());
            return ClienteDAO.ERRO_SQL;
        }
    }

    public int desativar(String usuario, String senha, String endereco, int idBusca) {
        Cliente cliente;
        try {
            cliente = this.buscarId(usuario, senha, endereco, idBusca);
            cliente.setAtivo(false);
            int codigoResultado = this.alterar(usuario, senha, endereco, cliente);
            return codigoResultado;
            
        } catch (EntidadeNulaException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return ClienteDAO.ERRO_CODIGO;
        }
    }

    public List<Cliente> consultar(String usuario, String senha, String endereco) throws EntidadeNulaException {
        List<Cliente> listaCliente = new ArrayList<Cliente>();
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. ClienteDAO.consultar()") == null){
            throw new EntidadeNulaException("Connection nula consultar lista de clientes. ClienteDAO.consultar()");
        }
        Cliente cliente = null;
        
        try {
            String comandoSQL = "SELECT cliente_id, cliente_nome, cliente_cpf, cliente_cnpj, cliente_logradouro, cliente_numero, cliente_bairro, cliente_cidade, cliente_complemento, cliente_estado, cliente_cep, cliente_telefone, cliente_email, cliente_observacoes, cliente_ativo FROM cliente";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    cliente = new Cliente(rs.getInt("cliente_id"),
                                            rs.getString("cliente_nome"),
                                            rs.getString("cliente_cpf"),
                                            rs.getString("cliente_cnpj"),
                                            rs.getString("cliente_logradouro"),
                                            rs.getInt("cliente_numero"),
                                            rs.getString("cliente_bairro"),
                                            rs.getString("cliente_cidade"),
                                            rs.getString("cliente_complemento"),
                                            rs.getString("cliente_estado"),
                                            rs.getString("cliente_cep"),
                                            rs.getString("cliente_telefone"),
                                            rs.getString("cliente_email"),
                                            rs.getString("cliente_observacoes"),
                                            rs.getBoolean("cliente_ativo"));
                    listaCliente.add(cliente);
                }catch(InsercaoException e){
                    System.err.println("InsercaoException: Falha na busca por cliente:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(cliente, "Cliente nulo: ClienteDAO.consultar()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (listaCliente.size() == 0){
            throw new EntidadeNulaException("Clientes não encontrados no banco de dados");
        }
        return listaCliente;
    }
    
    public List<Cliente> consultar(String usuario, String senha, String endereco, boolean mostraInativos) throws EntidadeNulaException {
        List<Cliente> listaCliente = new ArrayList<Cliente>();
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. ClienteDAO.consultar()") == null){
            throw new EntidadeNulaException("Connection nula consultar lista de clientes. ClienteDAO.consultar()");
        }
        Cliente cliente = null;
        
        try {
            String comandoSQL = "";
            if (mostraInativos){
                comandoSQL = "SELECT cliente_id, cliente_nome, cliente_cpf, cliente_cnpj, cliente_logradouro, cliente_numero, cliente_bairro, cliente_cidade, cliente_complemento, cliente_estado, cliente_cep, cliente_telefone, cliente_email, cliente_observacoes, cliente_ativo FROM cliente";
            } else {
                comandoSQL = "SELECT cliente_id, cliente_nome, cliente_cpf, cliente_cnpj, cliente_logradouro, cliente_numero, cliente_bairro, cliente_cidade, cliente_complemento, cliente_estado, cliente_cep, cliente_telefone, cliente_email, cliente_observacoes, cliente_ativo FROM cliente WHERE cliente_ativo IS TRUE";
            }
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    cliente = new Cliente(rs.getInt("cliente_id"),
                                            rs.getString("cliente_nome"),
                                            rs.getString("cliente_cpf"),
                                            rs.getString("cliente_cnpj"),
                                            rs.getString("cliente_logradouro"),
                                            rs.getInt("cliente_numero"),
                                            rs.getString("cliente_bairro"),
                                            rs.getString("cliente_cidade"),
                                            rs.getString("cliente_complemento"),
                                            rs.getString("cliente_estado"),
                                            rs.getString("cliente_cep"),
                                            rs.getString("cliente_telefone"),
                                            rs.getString("cliente_email"),
                                            rs.getString("cliente_observacoes"),
                                            rs.getBoolean("cliente_ativo"));
                    listaCliente.add(cliente);
                }catch(InsercaoException e){
                    System.err.println("InsercaoException: Falha na busca por cliente:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(cliente, "Cliente nulo: ClienteDAO.consultar()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(cliente, "Cliente") == null){
            throw new EntidadeNulaException("Cliente não encontrado no banco de dados");
        }
        return listaCliente;
    }
    
    public Cliente buscarId(String usuario, String senha, String endereco, int idBusca) throws EntidadeNulaException {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. ClienteDAO.buscarId()") == null){
            throw new EntidadeNulaException("Connection nula para inserir cliente. ClienteDAO.buscar()");
        }
        Cliente cliente = null;
        
        try {
            String comandoSQL = "SELECT cliente_id, cliente_nome, cliente_cpf, cliente_cnpj, cliente_logradouro, cliente_numero, cliente_bairro, cliente_cidade, cliente_complemento, cliente_estado, cliente_cep, cliente_telefone, cliente_email, cliente_observacoes, cliente_ativo FROM cliente WHERE cliente_id = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    cliente = new Cliente(rs.getInt("cliente_id"),
                                            rs.getString("cliente_nome"),
                                            rs.getString("cliente_cpf"),
                                            rs.getString("cliente_cnpj"),
                                            rs.getString("cliente_logradouro"),
                                            rs.getInt("cliente_numero"),
                                            rs.getString("cliente_bairro"),
                                            rs.getString("cliente_cidade"),
                                            rs.getString("cliente_complemento"),
                                            rs.getString("cliente_estado"),
                                            rs.getString("cliente_cep"),
                                            rs.getString("cliente_telefone"),
                                            rs.getString("cliente_email"),
                                            rs.getString("cliente_observacoes"),
                                            rs.getBoolean("cliente_ativo"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por cliente:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(cliente, "Cliente nulo: ClienteDAO.buscarId()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(cliente, "Cliente") == null){
            throw new EntidadeNulaException("Cliente não encontrado no banco de dados");
        }
        return cliente;
    }
    
    public Cliente buscarCPF(String usuario, String senha, String endereco, String cpf) throws EntidadeNulaException {
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. ClienteDAO.buscarCPF()") == null){
            throw new EntidadeNulaException("Connection nula para inserir cliente. ClienteDAO.buscar()");
        }
        Cliente cliente = null;
        
        try {
            String comandoSQL = "SELECT cliente_id, cliente_nome, cliente_cpf, cliente_cnpj, cliente_logradouro, cliente_numero, cliente_bairro, cliente_cidade, cliente_complemento, cliente_estado, cliente_cep, cliente_telefone, cliente_email, cliente_observacoes, cliente_ativo FROM cliente WHERE cliente_cpf = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    cliente = new Cliente(rs.getInt("cliente_id"),
                                            rs.getString("cliente_nome"),
                                            rs.getString("cliente_cpf"),
                                            rs.getString("cliente_cnpj"),
                                            rs.getString("cliente_logradouro"),
                                            rs.getInt("cliente_numero"),
                                            rs.getString("cliente_bairro"),
                                            rs.getString("cliente_cidade"),
                                            rs.getString("cliente_complemento"),
                                            rs.getString("cliente_estado"),
                                            rs.getString("cliente_cep"),
                                            rs.getString("cliente_telefone"),
                                            rs.getString("cliente_email"),
                                            rs.getString("cliente_observacoes"),
                                            rs.getBoolean("cliente_ativo"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por cliente:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(cliente, "Cliente nulo: ClienteDAO.buscarCPF()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(cliente, "Cliente") == null){
            throw new EntidadeNulaException("Cliente não encontrado no banco de dados");
        }
        return cliente;
    }
    
}
