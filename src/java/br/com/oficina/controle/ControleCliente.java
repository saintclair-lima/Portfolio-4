package br.com.oficina.controle;

import br.com.oficina.dao.ClienteDAO;
import br.com.oficina.modelo.Cliente;
import br.com.oficina.modelo.EntidadeNulaException;
import br.com.oficina.modelo.InsercaoException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControleCliente extends Controle implements InterfaceControle{
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        
        
        int resultadoOperacao = 20;
        if (acao==null) acao = "";
        
        if (acao.equals("incluir")){
            resultadoOperacao = ControleCliente.INSERIR;


        } else if (acao.equals("inserir")){
            resultadoOperacao = inserir(request, response);


        } else if (acao.equals("alterar")){
            resultadoOperacao = alterar(request, response);


        }else if (acao.equals("atualizar")){
            try {
                Cliente cliente = buscar(request, response);
                request.setAttribute("cliente", cliente);
                resultadoOperacao = ControleCliente.ATUALIZAR;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = ClienteDAO.ERRO_CODIGO;
            }


        } else if (acao.equals("desativar")){
            resultadoOperacao = desativar(request, response);


        } else if (acao.equals("consultar")){
            try {
                List<Cliente> lista = consultar(request, response);
                request.setAttribute("listaClientes", lista);
                resultadoOperacao = this.LISTAR;
            } catch (EntidadeNulaException ex) {
                Logger.getLogger(ControleCliente.class.getName()).log(Level.SEVERE, null, ex);
                resultadoOperacao = ClienteDAO.ERRO_CONEXAO;
            }


        } else if (acao.equals("buscar")){
            resultadoOperacao = this.BUSCAR;
            
        } else if (acao.equals("detalhar")){
            try {
                Cliente cliente = buscar(request, response);
                request.setAttribute("cliente", cliente);
                resultadoOperacao = this.DETALHAR;
            } catch (EntidadeNulaException ex) {
                resultadoOperacao = ClienteDAO.ERRO_CODIGO;
            }
        }

        RequestDispatcher rd;
        request.setAttribute("title","Cliente - " + acao);
        switch (resultadoOperacao){
            case INSERIR:
                rd = request.getRequestDispatcher("inserirCliente.jsp");
                break;
            case ClienteDAO.SUCESSO:
                String processadas = " processadas ";
                if (acao.equals("inserir")){
                    processadas = " inseridas ";
                } else if (acao.equals("excluir")){
                    processadas = " excluídas ";
                } else {
                    processadas = " alteradas ";
                }
                request.setAttribute("conteudo","<h1>InformaÃ§Ãµes sobre o cliente" + processadas + "com sucesso</h1>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case ClienteDAO.ERRO_SQL:
                request.setAttribute("conteudo","<h1>Falha ao Acessar o Banco</h1>"
                                   + "<p>Erro durante o acesso (leitura ou escrita) ao banco de dados. Comando SQL malformado. Procure o Webmaster</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case ClienteDAO.ERRO_INSERCAO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>Os dados inseridos eram inválidos. Por favor verifcar e tentar outra vez</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case ClienteDAO.ERRO_CODIGO:
                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
                                              + "<p>O Código informado nao corresponde a um código de cliente presente no banco de dados</p>");
                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                break;
            case LISTAR:
                rd = request.getRequestDispatcher("listarClientes.jsp");
                break;
            case BUSCAR:
                rd = request.getRequestDispatcher("buscarCliente.jsp");
                break;
            case DETALHAR:
                rd = request.getRequestDispatcher("detalharCliente.jsp");
                break;
            case ATUALIZAR:
                rd = request.getRequestDispatcher("atualizarCliente.jsp");
                break;
            default:
//                request.setAttribute("conteudo","<h1>Falha na Operação</h1>"
//                                              + "<p>Por algum motivo nÃ£o identificado, a operaÃ§Ã£o foi cancelada. Entre em contato com o webmaster</p>");
//                rd = request.getRequestDispatcher("/resultadoOperacao.jsp");
                
                rd = request.getRequestDispatcher("buscarCliente.jsp");
        }

        rd.forward(request, response);
    }
    
    public int alterar(HttpServletRequest request, HttpServletResponse response){
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = new Cliente(Integer.parseInt(request.getParameter("cliente_codigo")),
                                          request.getParameter("cliente_nome"),
                                          request.getParameter("cliente_cpf"),
                                          request.getParameter("cliente_cnpj"),
                                          request.getParameter("cliente_logradouro"),
                                          Integer.parseInt(request.getParameter("cliente_numero_endereco")),
                                          request.getParameter("cliente_bairro"),
                                          request.getParameter("cliente_cidade"),
                                          request.getParameter("cliente_complemento"),
                                          request.getParameter("cliente_estado"),
                                          request.getParameter("cliente_cep"),
                                          request.getParameter("cliente_telefone"),
                                          request.getParameter("cliente_email"),
                                          request.getParameter("cliente_observacoes"),
                                          true);
            int resultadoOperacao = clienteDAO.alterar(this.usuario, this.senha, this.endereco, cliente);
            return resultadoOperacao;
        } catch (InsercaoException ex) {
            //tratamento de falha na insercao
            System.err.print(ex.getMessage() + ex.getStackTrace());
            return ClienteDAO.ERRO_INSERCAO;
        }
    }
    
    //VERIFICAR OS MÉTODOS de BUSCAR
    public Cliente buscar(HttpServletRequest request, HttpServletResponse response) throws EntidadeNulaException{
        return buscarId(request, response);
    }
    
    public Cliente buscarCPF(HttpServletRequest request, HttpServletResponse response){return null;}
    
    public Cliente buscarId(HttpServletRequest request, HttpServletResponse response) throws EntidadeNulaException{
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.buscarId(usuario, senha, endereco, Integer.parseInt(request.getParameter("cliente_codigo")));
    }
    
    public List<Cliente> consultar(HttpServletRequest request, HttpServletResponse response) throws EntidadeNulaException{
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.consultar(this.usuario, this.senha, this.endereco);
    }
    
    public int desativar(HttpServletRequest request, HttpServletResponse response){
        ClienteDAO clienteDAO = new ClienteDAO();
        int clienteCodigo = Integer.parseInt(request.getParameter("cliente_codigo"));
        int resultadoOperacao = clienteDAO.desativar(this.usuario, this.senha, this.endereco, clienteCodigo);
        return resultadoOperacao;
    }
    
    public int inserir(HttpServletRequest request, HttpServletResponse response){
        int resultadoOperacao = 0;
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = new Cliente(0,
                                          request.getParameter("cliente_nome"),
                                          request.getParameter("cliente_cpf"),
                                          request.getParameter("cliente_cnpj"),
                                          request.getParameter("cliente_logradouro"),
                                          Integer.parseInt(request.getParameter("cliente_numero_endereco")),
                                          request.getParameter("cliente_bairro"),
                                          request.getParameter("cliente_cidade"),
                                          request.getParameter("cliente_complemento"),
                                          request.getParameter("cliente_estado"),
                                          request.getParameter("cliente_cep"),
                                          request.getParameter("cliente_telefone"),
                                          request.getParameter("cliente_email"),
                                          request.getParameter("cliente_observacoes"),
                                          true);
            resultadoOperacao = clienteDAO.inserir(this.usuario, this.senha, this.endereco, cliente);
            return resultadoOperacao;
        } catch (InsercaoException ex) {
            System.err.println(ex.getMessage());
            return ClienteDAO.ERRO_INSERCAO;
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
