package br.com.oficina.dao;

import br.com.oficina.modelo.Atendente;
import br.com.oficina.modelo.Carro;
import br.com.oficina.modelo.Cliente;
import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;
import br.com.oficina.modelo.RegistroAtendimento;
import br.com.oficina.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistroAtendimentoDAO extends DAO{
    public int inserir(String usuario, String senha, String endereco, RegistroAtendimento registroAtendimento){
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir cliente. TipoServicoDAO.inserir()") == null){
            return TipoServicoDAO.ERRO_CONEXAO;
        }
        
        try {
            String comandoSQL = "INSERT INTO registro_atendimento (ra_numero, ra_data_abertura, ra_data_encerramento, ra_descricao_abertura, ra_descricao_encerramento, ra_estado, ra_atendente_matricula, ra_cliente_id, ra_carro_id, ra_ativo) VALUES(NEXTVAL('seq_registro_atendimento_numero'),?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement para inserir tipo de servico. TipoServicoDAO.inserir()");
            
            ps.setDate(1, registroAtendimento.getDataAberturaAsSQLDate());
            ps.setDate(2, registroAtendimento.getDataEncerramentoAsSQLDate());
            ps.setString(3, registroAtendimento.getDescricaoAbertura());
            ps.setString(4, registroAtendimento.getDescricaoEncerramento());
            ps.setString(5, registroAtendimento.getEstado());
            ps.setInt(6, registroAtendimento.getAtendente().getId());
            ps.setInt(7, registroAtendimento.getCliente().getId());
            ps.setInt(8, registroAtendimento.getCarro().getId());
            ps.setBoolean(9, registroAtendimento.isAtivo());
            
            ps.execute();
            conexao.close();
            return RegistroAtendimentoDAO.SUCESSO;
            
        } catch(SQLException e) {
            System.err.print(e.getMessage());
        }
        return RegistroAtendimentoDAO.SUCESSO;
    }
    
    public int alterar(String usuario, String senha, String endereco, RegistroAtendimento registroAtendimento){
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para alterar tipo de servico. TipoServicoDAO.alterar()") == null){
            return TipoServicoDAO.ERRO_CONEXAO;
        }
        
        try {
            String comandoSQL = "UPDATE registro_atendimento SET ra_data_abertura = ?, ra_data_encerramento = ?, ra_descricao_abertura = ?, ra_descricao_encerramento = ?, ra_estado = ?, ra_atendente_matricula = ?, ra_cliente_id = ?, ra_carro_id = ?, ra_ativo = ?  WHERE ra_numero = ?";

            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setDate(1, registroAtendimento.getDataAberturaAsSQLDate());
            ps.setDate(2, registroAtendimento.getDataEncerramentoAsSQLDate());
            ps.setString(3, registroAtendimento.getDescricaoAbertura());
            ps.setString(4, registroAtendimento.getDescricaoEncerramento());
            ps.setString(5, registroAtendimento.getEstado());
            ps.setInt(6, registroAtendimento.getAtendente().getId());
            ps.setInt(7, registroAtendimento.getCliente().getId());
            ps.setInt(8, registroAtendimento.getCarro().getId());
            ps.setBoolean(9, registroAtendimento.isAtivo());
            ps.setInt(10, registroAtendimento.getNumero());
            
            ps.executeUpdate();
            conexao.close();
            
            return TipoServicoDAO.SUCESSO;
        } catch (org.postgresql.util.PSQLException e){
            System.err.println("ERRO: Falha ao alterar Registro de Atendimento de número " + registroAtendimento.getNumero() + ". Violação de valor único\r\n " + e.getMessage());
            return TipoServicoDAO.ERRO_SQL;
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao alterar Registro de Atendimento de número " + registroAtendimento.getNumero() + "\r\n " + e.getMessage());
            return ClienteDAO.ERRO_SQL;
        }
    }
    
    public int desativar(String usuario, String senha, String endereco, int codigoBusca){
        try {
            RegistroAtendimento ra = this.buscar(usuario, senha, endereco, codigoBusca);
            ra.setAtivo(false);
            return this.alterar(usuario, senha, endereco, ra);
        } catch (EntidadeNulaException ex) {
            Logger.getLogger(RegistroAtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return RegistroAtendimentoDAO.ERRO_CODIGO;
        }
    }
    
    public List<RegistroAtendimento> consultar(String usuario, String senha, String endereco) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir Registro de Atendimento. RegistroAtendimentoDAO.buscar()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Registro de Atendimento. RegistroAtendimentoDAO.buscar()");
        }
        
        List<RegistroAtendimento> listaRegistroAtendimento = new ArrayList<RegistroAtendimento>();
        RegistroAtendimento registroAtendimento = null;
        
        try {
            String comandoSQL = "SELECT ra_numero, ra_data_abertura, ra_data_encerramento, ra_descricao_abertura, ra_descricao_encerramento, ra_estado, ra_atendente_matricula, ra_cliente_id, ra_carro_id, ra_ativo FROM registro_atendimento";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    /*TODO acessar banco para criar atendente*/
                    AtendenteDAO ad = new AtendenteDAO();
                    Atendente atendente = ad.buscarId(usuario, senha, endereco, rs.getInt("ra_atendente_matricula"));                    
                    ClienteDAO cd = new ClienteDAO();
                    Cliente cliente = cd.buscarId(usuario, senha, endereco, rs.getInt("ra_cliente_id"));
                    CarroDAO carDAO = new CarroDAO();
                    Carro carro = carDAO.buscar(usuario, senha, endereco, rs.getInt("ra_carro_id"));
                    
                    GregorianCalendar dataAbertura = new GregorianCalendar();
                    dataAbertura.setTime(rs.getDate("ra_data_abertura"));
                    GregorianCalendar dataEncerramento = null;
                    
                    if (rs.getDate("ra_data_encerramento") != null){                        
                        dataEncerramento = new GregorianCalendar();
                        dataEncerramento.setTime(rs.getDate("ra_data_encerramento"));
                    }
                    
                    registroAtendimento = new RegistroAtendimento(rs.getInt("ra_numero"),
                            dataAbertura, rs.getString("ra_descricao_abertura"), dataEncerramento,
                            rs.getString("ra_descricao_encerramento"), rs.getString("ra_estado"),
                            atendente, cliente, carro, rs.getBoolean("ra_estado"));
                    
                    listaRegistroAtendimento.add(registroAtendimento);
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por Registro de Atendimento:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(registroAtendimento, "RegistroAtendimento nulo: RegistroAtendimentoDAO.buscar()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (listaRegistroAtendimento.isEmpty()){
            throw new EntidadeNulaException("Registr de Atendimento não encontrado no banco de dados");
        }
        return listaRegistroAtendimento;
    }
    
    public List<RegistroAtendimento> consultar(String usuario, String senha, String endereco, boolean mostraInativos) throws EntidadeNulaException{
                Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir Registro de Atendimento. RegistroAtendimentoDAO.buscar()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Registro de Atendimento. RegistroAtendimentoDAO.buscar()");
        }
        
        List<RegistroAtendimento> listaRegistroAtendimento = new ArrayList<RegistroAtendimento>();
        RegistroAtendimento registroAtendimento = null;
            String comandoSQL;
        
        try {
            if (mostraInativos){
                comandoSQL = "SELECT ra_numero, ra_data_abertura, ra_data_encerramento, ra_descricao_abertura, ra_descricao_encerramento, ra_estado, ra_atendente_matricula, ra_cliente_id, ra_carro_id, ra_ativo FROM registro_atendimento";
            } else {
                comandoSQL = "SELECT ra_numero, ra_data_abertura, ra_data_encerramento, ra_descricao_abertura, ra_descricao_encerramento, ra_estado, ra_atendente_matricula, ra_cliente_id, ra_carro_id, ra_ativo FROM registro_atendimento WHERE ra_ativo IS TRUE";
            }
            
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    /*TODO acessar banco para criar atendente*/
                    AtendenteDAO ad = new AtendenteDAO();
                    Atendente atendente = ad.buscarId(usuario, senha, endereco, rs.getInt("ra_atendente_matricula"));                    
                    ClienteDAO cd = new ClienteDAO();
                    Cliente cliente = cd.buscarId(usuario, senha, endereco, rs.getInt("ra_cliente_id"));
                    CarroDAO carDAO = new CarroDAO();
                    Carro carro = carDAO.buscar(usuario, senha, endereco, rs.getInt("ra_carro_id"));
                    
                    GregorianCalendar dataAbertura = new GregorianCalendar();
                    dataAbertura.setTime(rs.getDate("ra_data_abertura"));
                    GregorianCalendar dataEncerramento = new GregorianCalendar();
                    dataEncerramento.setTime(rs.getDate("ra_data_encerramento"));
                    
                    registroAtendimento = new RegistroAtendimento(rs.getInt("ra_numero"),
                            dataAbertura, rs.getString("ra_descricao_abertura"), dataEncerramento,
                            rs.getString("ra_descricao_encerramento"), rs.getString("ra_estado"),
                            atendente, cliente, carro, rs.getBoolean("ra_estado"));
                    
                    listaRegistroAtendimento.add(registroAtendimento);
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por Registro de Atendimento:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(registroAtendimento, "RegistroAtendimento nulo: RegistroAtendimentoDAO.buscar()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (    listaRegistroAtendimento.isEmpty()){
            throw new EntidadeNulaException("Registr de Atendimento não encontrado no banco de dados");
        }
        return listaRegistroAtendimento;
    }
    
    public RegistroAtendimento buscar(String usuario, String senha, String endereco, int codBusca) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir Registro de Atendimento. RegistroAtendimentoDAO.buscar()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Registro de Atendimento. RegistroAtendimentoDAO.buscar()");
        }
        
        RegistroAtendimento registroAtendimento = null;
        
        try {
            String comandoSQL = "SELECT ra_numero, ra_data_abertura, ra_data_encerramento, ra_descricao_abertura, ra_descricao_encerramento, ra_estado, ra_atendente_matricula, ra_cliente_id, ra_carro_id, ra_ativo FROM registro_atendimento WHERE ra_numero = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, codBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    /*TODO acessar banco para criar atendente*/
                    AtendenteDAO ad = new AtendenteDAO();
                    Atendente atendente = ad.buscarId(usuario, senha, endereco, rs.getInt("ra_atendente_matricula"));                    
                    ClienteDAO cd = new ClienteDAO();
                    Cliente cliente = cd.buscarId(usuario, senha, endereco, rs.getInt("ra_cliente_id"));
                    CarroDAO carDAO = new CarroDAO();
                    Carro carro = carDAO.buscar(usuario, senha, endereco, rs.getInt("ra_carro_id"));
                    
                    GregorianCalendar dataAbertura = new GregorianCalendar();
                    dataAbertura.setTime(rs.getDate("ra_data_abertura"));
                    
                    String estado = rs.getString("ra_estado");
                    
                    System.err.println(estado + " " + atendente.getNome());
                    //Se o RA não estiver concluído, faz uma construção de um RA simples:
                    if (!estado.equals("Concluído")){
                        registroAtendimento = new RegistroAtendimento(rs.getInt("ra_numero"),
                            dataAbertura, rs.getString("ra_descricao_abertura"), rs.getString("ra_estado"),
                            atendente, cliente, carro);
                        
                    //Caso esreja concluído:
                    } else {
                        GregorianCalendar dataEncerramento = new GregorianCalendar();
                        dataEncerramento.setTime(rs.getDate("ra_data_encerramento"));
                        registroAtendimento = new RegistroAtendimento(rs.getInt("ra_numero"),
                            dataAbertura, rs.getString("ra_descricao_abertura"), dataEncerramento,
                            rs.getString("ra_descricao_encerramento"), rs.getString("ra_estado"),
                            atendente, cliente, carro, rs.getBoolean("ra_ativo"));
                    }
                    
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por Registro de Atendimento:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(registroAtendimento, "RegistroAtendimento nulo: RegistroAtendimentoDAO.buscar()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(registroAtendimento, "RegistroAtendimento") == null){
            throw new EntidadeNulaException("Registr de Atendimento não encontrado no banco de dados");
        }
        return registroAtendimento;
    }
}
