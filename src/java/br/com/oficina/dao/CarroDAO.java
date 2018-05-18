package br.com.oficina.dao;

import br.com.oficina.modelo.Carro;
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

public class CarroDAO extends DAO{
    public Carro buscar(String usuario, String senha, String endereco, int idBusca) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir Tipo de Serviço. TipoServicoDAO.buscarId()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Tipo de Serviço. TipoServicoDAO.buscarId()");
        }
        
        Carro carro = null;
        
        try {
            String comandoSQL = "SELECT carro_id, carro_modelo, carro_marca, carro_ano, carro_placa, carro_cor, carro_cliente_id, carro_ativo FROM carro WHERE carro_id = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, idBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    ClienteDAO cd = new ClienteDAO();
                    Cliente cliente = cd.buscarId(usuario, senha, endereco, rs.getInt("carro_cliente_id"));
                    
                    carro = new Carro(rs.getInt("carro_id"),
                            rs.getString("carro_modelo"),
                            rs.getString("carro_marca"),
                            rs.getString("carro_ano"),
                            rs.getString("carro_placa"),
                            rs.getString("carro_cor"),
                            cliente,
                            rs.getBoolean("carro_ativo"));
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por Carro:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(carro, "Carro nulo: Carro.buscar()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(carro, "Carro") == null){
            throw new EntidadeNulaException("Carro não encontrado no banco de dados");
        }
        return carro;
    }
    
        public List <Carro> consultar(String usuario, String senha, String endereco) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir Tipo de Serviço. TipoServicoDAO.buscarId()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Tipo de Serviço. TipoServicoDAO.buscarId()");
        }
        
        List<Carro> listaCarro = new ArrayList<Carro>();
        Carro carro = null;
        
        try {
            String comandoSQL = "SELECT carro_id, carro_modelo, carro_marca, carro_ano, carro_placa, carro_cor, carro_cliente_id, carro_ativo FROM carro";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    ClienteDAO cd = new ClienteDAO();
                    Cliente cliente = cd.buscarId(usuario, senha, endereco, rs.getInt("carro_cliente_id"));
                    
                    carro = new Carro(rs.getInt("carro_id"),
                            rs.getString("carro_modelo"),
                            rs.getString("carro_marca"),
                            rs.getString("carro_ano"),
                            rs.getString("carro_placa"),
                            rs.getString("carro_cor"),
                            cliente,
                            rs.getBoolean("carro_ativo"));
                    listaCarro.add(carro);
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por Carro:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(carro, "Carro nulo: Carro.buscar()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(carro, "Carro") == null){
            throw new EntidadeNulaException("Carro não encontrado no banco de dados");
        }
        return listaCarro;
    }
}
