/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.oficina.controle;

import br.com.oficina.dao.AtendenteDAO;
import br.com.oficina.dao.CarroDAO;
import br.com.oficina.dao.ClienteDAO;
import br.com.oficina.dao.RegistroAtendimentoDAO;
import br.com.oficina.dao.TipoServicoDAO;
import br.com.oficina.modelo.Atendente;
import br.com.oficina.modelo.Carro;
import br.com.oficina.modelo.Cliente;
import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;
import br.com.oficina.modelo.RegistroAtendimento;
import br.com.oficina.modelo.TipoServico;
import br.com.oficina.utils.Utils;
import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author saintclair
 */
public class ControleRegistroAtendimento extends Controle implements InterfaceControle{
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        int resultadoOperacao = 20;
        if (acao==null) acao = "";
        
        if (acao.equals("incluir")){
            try {
                resultadoOperacao = ControleRegistroAtendimento.INSERIR;
                List<Cliente> listaClientes = new ClienteDAO().consultar(usuario, senha, endereco);
                List<Carro> listaCarros = new CarroDAO().consultar(usuario, senha, endereco);
                List<Atendente> listaAtendentes = new AtendenteDAO().consultar(usuario, senha, endereco);
                List<TipoServico> listaTiposServico = new TipoServicoDAO().consultar(usuario, senha, endereco);
                
                request.setAttribute("listaClientes", listaClientes);
                request.setAttribute("listaCarros", listaCarros);
                request.setAttribute("listaAtendentes", listaAtendentes);
                request.setAttribute("listaTiposServico", listaTiposServico);
            } catch (EntidadeNulaException ex) {
                Logger.getLogger(ControleRegistroAtendimento.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else if (acao.equals("inserir")){
            try {
                resultadoOperacao = inserir(request, response);
            } catch (ParseException ex) {
                Logger.getLogger(ControleRegistroAtendimento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EntidadeNulaException ex) {
                Logger.getLogger(ControleRegistroAtendimento.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else if (acao.equals("alterar")){
            try {
                resultadoOperacao = alterar(request, response);
            } catch (ParseException ex) {
                Logger.getLogger(ControleRegistroAtendimento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EntidadeNulaException ex) {
                Logger.getLogger(ControleRegistroAtendimento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InsercaoException ex) {
                Logger.getLogger(ControleRegistroAtendimento.class.getName()).log(Level.SEVERE, null, ex);
            }


        }else if (acao.equals("atualizar")){
            try {
                RegistroAtendimento ra = buscar(request, response);
                request.setAttribute("registroAtendimento", ra);
                List<Cliente> listaClientes = new ClienteDAO().consultar(usuario, senha, endereco);
                List<Carro> listaCarros = new CarroDAO().consultar(usuario, senha, endereco);
                List<Atendente> listaAtendentes = new AtendenteDAO().consultar(usuario, senha, endereco);
                List<TipoServico> listaTiposServico = new TipoServicoDAO().consultar(usuario, senha, endereco);
                
                request.setAttribute("listaClientes", listaClientes);
                request.setAttribute("listaCarros", listaCarros);
                request.setAttribute("listaAtendentes", listaAtendentes);
                request.setAttribute("listaTiposServico", listaTiposServico);
                resultadoOperacao = ControleTipoServico.ATUALIZAR;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = RegistroAtendimentoDAO.ERRO_CODIGO;
            }


        } else if (acao.equals("desativar")){
            resultadoOperacao = desativar(request, response);


        } else if (acao.equals("consultar")){
            try {
                List<RegistroAtendimento> lista = consultar(request, response);
                request.setAttribute("listaRegistroAtendimento", lista);
                resultadoOperacao = ControleTipoServico.LISTAR;
            } catch (EntidadeNulaException ex) {
                Logger.getLogger(ControleCliente.class.getName()).log(Level.SEVERE, null, ex);
                resultadoOperacao = RegistroAtendimentoDAO.ERRO_CONEXAO;
            }


        } else if (acao.equals("buscar")){
            resultadoOperacao = ControleRegistroAtendimento.BUSCAR;


        } else if (acao.equals("detalhar")){
            try {
                RegistroAtendimento ra = buscar(request, response);
                request.setAttribute("registroAtendimento", ra);
                resultadoOperacao = ControleTipoServico.DETALHAR;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = RegistroAtendimentoDAO.ERRO_CODIGO;
            }
        }

        RequestDispatcher rd;
        request.setAttribute("title","Registro de Atendimento - " + acao);
        switch (resultadoOperacao){
            case INSERIR:
                rd = request.getRequestDispatcher("inserirRegistroAtendimento.jsp");
                break;
            case RegistroAtendimentoDAO.SUCESSO:
                String processadas = " processadas ";
                if (acao.equals("inserir")){
                    processadas = " inseridas ";
                } else if (acao.equals("excluir")){
                    processadas = " excluídas ";
                } else {
                    processadas = " alteradas ";
                }
                request.setAttribute("conteudo","<h1>Informações sobre o tipo de serviço" + processadas + "com sucesso</h1>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case RegistroAtendimentoDAO.ERRO_SQL:
                request.setAttribute("conteudo","<h1>Falha ao Acessar o Banco</h1>"
                                   + "<p>Erro durante o acesso (leitura ou escrita) ao banco de dados. Comando SQL malformado. Procure o Webmaster</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case RegistroAtendimentoDAO.ERRO_INSERCAO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>Os dados inseridos eram inválidos. Por favor verifcar e tentar outra vez</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case RegistroAtendimentoDAO.ERRO_CODIGO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>O Código informado nao corresponde a um código de tipo de serviço presente no banco de dados</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case LISTAR:
                rd = request.getRequestDispatcher("listarRegistroAtendimento.jsp");
                break;
            case BUSCAR:
                rd = request.getRequestDispatcher("buscarRegistroAtendimento.jsp");
                break;
            case DETALHAR:
                rd = request.getRequestDispatcher("detalharRegistroAtendimento.jsp");
                break;
            case ATUALIZAR:
                rd = request.getRequestDispatcher("atualizarRegistroAtendimento.jsp");
                break;
            default:
//                request.setAttribute("conteudo","<h1>Falha na OperaÃ§Ã£o</h1>"
//                                              + "<p>Por algum motivo nÃ£o identificado, a operaÃ§Ã£o foi cancelada. Entre em contato com o webmaster</p>");
//                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                
                rd = request.getRequestDispatcher("buscarRegistroAtendimento.jsp");
        }

        rd.forward(request, response);
    }
    
    public int alterar(HttpServletRequest request, HttpServletResponse response) throws ParseException, EntidadeNulaException, InsercaoException{
        //tratamento de falha na insercao
            AtendenteDAO atendenteDAO = new AtendenteDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            CarroDAO carroDAO = new CarroDAO();
            RegistroAtendimentoDAO raDAO = new RegistroAtendimentoDAO();
            
            
            
            GregorianCalendar dataAbertura = Utils.getGregCalendarDeString(request.getParameter("data_abertura"));
            String descricaoAbertura = request.getParameter("descricao_abertura");
            Atendente atendente = atendenteDAO.buscarId(usuario, senha, endereco, Integer.parseInt(request.getParameter("id_atendente")));
            Cliente cliente = clienteDAO.buscarId(usuario, senha, endereco, Integer.parseInt(request.getParameter("id_cliente")));
            Carro carro = carroDAO.buscar(usuario, senha, endereco, Integer.parseInt(request.getParameter("cod_cliente")));
            
            RegistroAtendimento ra = new RegistroAtendimento(0, dataAbertura, descricaoAbertura, atendente, cliente, carro, true);
        int resultadoOperacao = raDAO.alterar(this.usuario, this.senha, this.endereco, ra);
        return resultadoOperacao;
    }
    
    //VERIFICAR OS MÉTODOS de BUSCAR
    public RegistroAtendimento buscar(HttpServletRequest request, HttpServletResponse response) throws EntidadeNulaException{
        return new RegistroAtendimentoDAO().buscar(usuario, senha, endereco, Integer.parseInt(request.getParameter("ra_numero")));
    }
        
    public List<RegistroAtendimento> consultar(HttpServletRequest request, HttpServletResponse response) throws EntidadeNulaException{
        RegistroAtendimentoDAO raDAO = new RegistroAtendimentoDAO();
        return raDAO.consultar(this.usuario, this.senha, this.endereco);
        
    }
    
    public int desativar(HttpServletRequest request, HttpServletResponse response){
        RegistroAtendimentoDAO raDAO = new RegistroAtendimentoDAO();
        int raNumero = Integer.parseInt(request.getParameter("ra_numero"));
        int resultadoOperacao = raDAO.desativar(this.usuario, this.senha, this.endereco, raNumero);
        return resultadoOperacao;
    }
    
    public int inserir(HttpServletRequest request, HttpServletResponse response) throws ParseException, EntidadeNulaException{
        int resultadoOperacao = 0;
        try {
            AtendenteDAO atendenteDAO = new AtendenteDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            CarroDAO carroDAO = new CarroDAO();
            RegistroAtendimentoDAO raDAO = new RegistroAtendimentoDAO();
            
            
            
            GregorianCalendar dataAbertura = new GregorianCalendar();
            String descricaoAbertura = request.getParameter("descricao_abertura");
            Atendente atendente = atendenteDAO.buscarId(usuario, senha, endereco, Integer.parseInt(request.getParameter("atendente_id")));
            Cliente cliente = clienteDAO.buscarId(usuario, senha, endereco, Integer.parseInt(request.getParameter("cliente_id")));
            Carro carro = carroDAO.buscar(usuario, senha, endereco, Integer.parseInt(request.getParameter("carro_codigo")));
            
            RegistroAtendimento ra = new RegistroAtendimento(0, dataAbertura, descricaoAbertura, atendente, cliente, carro, true);
            resultadoOperacao = raDAO.inserir(this.usuario, this.senha, this.endereco, ra);
            return resultadoOperacao;
        } catch (InsercaoException ex) {
            //tratamento de falha na insercao
            System.err.println(ex.getMessage() + "\n" + ex.getStackTrace());
            return RegistroAtendimentoDAO.ERRO_INSERCAO;
        }
    }

 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    //</editor-fold>
}
