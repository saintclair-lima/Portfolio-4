package br.com.oficina.dao;

import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;
import br.com.oficina.modelo.ItemEstoque;
import br.com.oficina.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ItemEstoqueDAO extends DAO {
    public int inserir(String usuario, String senha, String endereco, ItemEstoque itemEstoque){
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir Item de Estoque. ItemEstoqueDAO.inserir()") == null){
            return TipoServicoDAO.ERRO_CONEXAO;
        }
        
        try {
            String comandoSQL = "INSERT INTO item_estoque (item_estoque_codigo, item_estoque_nome, item_estoque_descricao, item_estoque_preco, item_estoque_quantidade_estoque, item_estoque_ativo) VALUES(NEXTVAL('seq_item_estoque_codigo'),?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            Utils.checaNull(ps,"PreparedStatement para inserir Item de Estoque. ItemEstoqueDAO.inserir()");
            
            
            ps.setString(1, itemEstoque.getNome());
            ps.setString(2, itemEstoque.getDescricao());
            ps.setDouble(3, itemEstoque.getPreco());
            ps.setDouble(4, itemEstoque.getQuantidadeEstoque());
            ps.setBoolean(5, itemEstoque.isAtivo());
            ps.execute();
            conexao.close();
            return ItemEstoqueDAO.SUCESSO;
            
        } catch(SQLException e) {
            System.err.print(e.getMessage());
        }
        return TipoServicoDAO.SUCESSO;
    }
    
    public ItemEstoque buscar(String usuario, String senha, String endereco, int codBusca) throws InsercaoException, EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para inserir Item de Estoque. ItemEstoqueDAO.buscarId()") == null){
            throw new InsercaoException("Connection nula para inserir Item de Estoque. ItemEstoqueDAO.buscar()");
        }
        
        ItemEstoque itemEstoque = null;
        
        try {
            String comandoSQL = "SELECT item_estoque_codigo, item_estoque_nome, item_estoque_descricao, item_estoque_preco, item_estoque_quantidade_estoque, item_estoque_ativo FROM item_estoque WHERE item_estoque_codigo = ?";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setInt(1, codBusca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemEstoque = new ItemEstoque(rs.getInt("item_estoque_codigo"),
                        rs.getString("item_estoque_nome"),
                        rs.getString("item_estoque_descricao"),
                        rs.getDouble("item_estoque_preco"),
                        rs.getDouble("item_estoque_quantidade_estoque"),
                        rs.getBoolean("item_estoque_ativo"));
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(itemEstoque, "ItemEstoque nulo: ItemEstoqueDAO.buscar()") == null){
            throw new EntidadeNulaException("Item de Estoque não encontrado no banco de dados");
        }
        return itemEstoque;
    }

    public int alterar(String usuario, String senha, String endereco, ItemEstoque itemEstoque){
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para alterar Item de Estoque. ItemEstoqueDAO.alterar()") == null){
            return ItemEstoqueDAO.ERRO_CONEXAO;
        }
        
        try {
            String comandoSQL = "UPDATE item_estoque SET item_estoque_nome = ?, item_estoque_descricao = ?, item_estoque_preco = ?, item_estoque_quantidade_estoque = ?, item_estoque_ativo = ? WHERE item_estoque_codigo = ?";

            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ps.setString(1, itemEstoque.getNome());
            ps.setString(2, itemEstoque.getDescricao());
            ps.setDouble(3, itemEstoque.getPreco());
            ps.setDouble(4, itemEstoque.getQuantidadeEstoque());
            ps.setBoolean(5, itemEstoque.isAtivo());
            ps.setInt(6, itemEstoque.getCodigo());
            ps.executeUpdate();
            conexao.close();
            
            return ItemEstoqueDAO.SUCESSO;
        } catch (org.postgresql.util.PSQLException e){
            System.err.println("ERRO: Falha ao alterar Item de Estoque de codigo " + itemEstoque.getCodigo() + ". Violação de valor único\r\n " + e.getMessage());
            return TipoServicoDAO.ERRO_SQL;
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao alterar Item de Estoque de codigo " + itemEstoque.getCodigo() + "\r\n " + e.getMessage());
            return ClienteDAO.ERRO_SQL;
        }
    }    

    public int desativar(String usuario, String senha, String endereco, int codBusca) throws InsercaoException{
        ItemEstoque itemEstoque;
        try {
            itemEstoque = this.buscar(usuario, senha, endereco, codBusca);
            itemEstoque.setAtivo(false);
            int codResultado = this.alterar(usuario, senha, endereco, itemEstoque);
            return codResultado;
        } catch (EntidadeNulaException ex) {
            Logger.getLogger(TipoServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return TipoServicoDAO.ERRO_CODIGO;
        }
    }
    
    public List<ItemEstoque> consultar(String usuario, String senha, String endereco) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para consultar Item de Estoque. ItemEstoqueDAO.consultar()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Item de Estoque. ItemEstoqueDAO.consultar()");
        }
        
        List listaItemEstoque = new ArrayList();
        ItemEstoque itemEstoque = null;
        
        try {
            String comandoSQL = "SELECT item_estoque_codigo, item_estoque_nome, item_estoque_descricao, item_estoque_preco, item_estoque_quantidade_estoque, item_estoque_ativo FROM item_estoque";
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    itemEstoque = new ItemEstoque(rs.getInt("item_estoque_codigo"),
                        rs.getString("item_estoque_nome"),
                        rs.getString("item_estoque_descricao"),
                        rs.getDouble("item_estoque_preco"),
                        rs.getDouble("item_estoque_quantidade_estoque"),
                        rs.getBoolean("item_estoque_ativo"));
                    listaItemEstoque.add(itemEstoque);
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por Item de Estoque:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(itemEstoque, "ItemEstoque nulo: ItemEstoqueDAO.buscarId()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(itemEstoque, "ItemEstoque") == null){
            throw new EntidadeNulaException("ItemEstoque não encontrado no banco de dados");
        }
        return listaItemEstoque;
    }
    
    public List<ItemEstoque> consultar(String usuario, String senha, String endereco, boolean mostraInativos) throws EntidadeNulaException{
        Connection conexao = ConectaBD.conectarBanco(usuario, senha, endereco);
        if (Utils.checaNull(conexao, "Connection para consultar Item de Estoque. ItemEstoqueDAO.consultar()") == null){
            throw new EntidadeNulaException("Connection nula para inserir Item de Estoque. ItemEstoqueDAO.consultar()");
        }
        
        List listaItemEstoque = new ArrayList();
        ItemEstoque itemEstoque = null;
        
        try {
            String comandoSQL;
            if (mostraInativos){
                comandoSQL = "SELECT item_estoque_codigo, item_estoque_nome, item_estoque_descricao, item_estoque_preco, item_estoque_quantidade_estoque, item_estoque_ativo FROM item_estoque";
            } else {
                comandoSQL = "SELECT item_estoque_codigo, item_estoque_nome, item_estoque_descricao, item_estoque_preco, item_estoque_quantidade_estoque, item_estoque_ativo FROM item_estoque WHERE item_estoque_ativo IS TRUE";
            }
            PreparedStatement ps = conexao.prepareStatement(comandoSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try{
                    itemEstoque = new ItemEstoque(rs.getInt("item_estoque_codigo"),
                        rs.getString("item_estoque_nome"),
                        rs.getString("item_estoque_descricao"),
                        rs.getDouble("item_estoque_preco"),
                        rs.getDouble("item_estoque_quantidade_estoque"),
                        rs.getBoolean("item_estoque_ativo"));
                    listaItemEstoque.add(itemEstoque);
                }catch(InsercaoException e){
                    System.err.println("ERRO: Falha na busca por Item de Estoque:" + "\r\n" + e.getMessage());
                }
                Utils.checaNull(itemEstoque, "ItemEstoque nulo: ItemEstoqueDAO.buscarId()");
            }
            
            conexao.close();
        } catch (SQLException e) {
             System.err.println("Consulta inválida!" + "\nErro : " + e.getMessage());
        }
        if (Utils.checaNull(itemEstoque, "ItemEstoque") == null){
            throw new EntidadeNulaException("ItemEstoque não encontrado no banco de dados");
        }
        return listaItemEstoque;
    }
}
