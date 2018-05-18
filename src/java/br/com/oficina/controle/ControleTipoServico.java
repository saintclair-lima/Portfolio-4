/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.oficina.controle;

import br.com.oficina.dao.TipoServicoDAO;
import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;
import br.com.oficina.modelo.TipoServico;
import java.io.IOException;
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
public class ControleTipoServico extends Controle implements InterfaceControle{
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        int resultadoOperacao = 20;
        if (acao==null) acao = "";
        
        if (acao.equals("incluir")){
            resultadoOperacao = ControleTipoServico.INSERIR;


        } else if (acao.equals("inserir")){
            resultadoOperacao = inserir(request, response);


        } else if (acao.equals("alterar")){
            resultadoOperacao = alterar(request, response);


        }else if (acao.equals("atualizar")){
            try {
                TipoServico tipoServico = buscar(request, response);
                request.setAttribute("tipoServico", tipoServico);
                resultadoOperacao = ControleTipoServico.ATUALIZAR;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = TipoServicoDAO.ERRO_CODIGO;
            }


        } else if (acao.equals("desativar")){
            resultadoOperacao = desativar(request, response);


        } else if (acao.equals("consultar")){
            try {
                List<TipoServico> lista = consultar(request, response);
                request.setAttribute("listaTipoServico", lista);
                resultadoOperacao = ControleTipoServico.LISTAR;
            } catch (EntidadeNulaException ex) {
                Logger.getLogger(ControleCliente.class.getName()).log(Level.SEVERE, null, ex);
                resultadoOperacao = TipoServicoDAO.ERRO_CONEXAO;
            }


        } else if (acao.equals("buscar")){
            resultadoOperacao = ControleTipoServico.BUSCAR;


        } else if (acao.equals("detalhar")){
            try {
                TipoServico tipoServico = buscar(request, response);
                request.setAttribute("tipoServico", tipoServico);
                resultadoOperacao = ControleTipoServico.DETALHAR;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = TipoServicoDAO.ERRO_CODIGO;
            }
        }

        RequestDispatcher rd;
        request.setAttribute("title","Tipo de Serviço - " + acao);
        switch (resultadoOperacao){
            case INSERIR:
                rd = request.getRequestDispatcher("inserirTipoServico.jsp");
                break;
            case TipoServicoDAO.SUCESSO:
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
            case TipoServicoDAO.ERRO_SQL:
                request.setAttribute("conteudo","<h1>Falha ao Acessar o Banco</h1>"
                                   + "<p>Erro durante o acesso (leitura ou escrita) ao banco de dados. Comando SQL malformado. Procure o Webmaster</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case TipoServicoDAO.ERRO_INSERCAO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>Os dados inseridos eram inválidos. Por favor verifcar e tentar outra vez</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case TipoServicoDAO.ERRO_CODIGO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>O Código informado nao corresponde a um código de tipo de serviço presente no banco de dados</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case LISTAR:
                rd = request.getRequestDispatcher("listarTipoServico.jsp");
                break;
            case BUSCAR:
                rd = request.getRequestDispatcher("buscarTipoServico.jsp");
                break;
            case DETALHAR:
                rd = request.getRequestDispatcher("detalharTipoServico.jsp");
                break;
            case ATUALIZAR:
                rd = request.getRequestDispatcher("atualizarTipoServico.jsp");
                break;
            default:                
                rd = request.getRequestDispatcher("buscarTipoServico.jsp");
        }
        rd.forward(request, response);
    }
    
    public int alterar(HttpServletRequest request, HttpServletResponse response){
        try {
            TipoServicoDAO servicoDAO = new TipoServicoDAO();
            TipoServico servico = new TipoServico(Integer.parseInt(request.getParameter("servico_codigo")),
                                          request.getParameter("servico_nome"),
                                          Integer.parseInt(request.getParameter("servico_duracao")),
                                          Double.parseDouble(request.getParameter("servico_valor")),
                                          true);
            int resultadoOperacao = servicoDAO.alterar(this.usuario, this.senha, this.endereco, servico);
            return resultadoOperacao;
        } catch (InsercaoException ex) {
            //tratamento de falha na insercao
            return TipoServicoDAO.ERRO_INSERCAO;
        }
    }
    
    //VERIFICAR OS MÉTODOS de BUSCAR
    public TipoServico buscar(HttpServletRequest request, HttpServletResponse response) throws EntidadeNulaException{
        TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();
        TipoServico tipoServico = tipoServicoDAO.buscar(usuario, senha, endereco, Integer.parseInt(request.getParameter("servico_codigo")));
        return tipoServico;
                //new TipoServicoDAO().buscar(usuario, senha, endereco, Integer.parseInt(request.getParameter("servico_codigo")));
    }
        
    public List<TipoServico> consultar(HttpServletRequest request, HttpServletResponse response) throws EntidadeNulaException{
        TipoServicoDAO clienteDAO = new TipoServicoDAO();
        return clienteDAO.consultar(this.usuario, this.senha, this.endereco);
        
    }
    
    public int desativar(HttpServletRequest request, HttpServletResponse response){
        TipoServicoDAO tipoServico = new TipoServicoDAO();
        int tipoServicoCodigo = Integer.parseInt(request.getParameter("servico_codigo"));
        int resultadoOperacao = tipoServico.desativar(this.usuario, this.senha, this.endereco, tipoServicoCodigo);
        return resultadoOperacao;
    }
    
    public int inserir(HttpServletRequest request, HttpServletResponse response){
        int resultadoOperacao = 0;
        try {
            TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();
            TipoServico tipoServico = new TipoServico(0,
                                          request.getParameter("servico_nome"),
                                          Integer.parseInt(request.getParameter("servico_duracao")),
                                          Double.parseDouble(request.getParameter("servico_valor")),
                                          true);
            resultadoOperacao = tipoServicoDAO.inserir(this.usuario, this.senha, this.endereco, tipoServico);
            return resultadoOperacao;
        } catch (InsercaoException ex) {
            System.err.println(ex.getMessage() + "\n" + ex.getStackTrace().toString());
            return TipoServicoDAO.ERRO_INSERCAO;
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
