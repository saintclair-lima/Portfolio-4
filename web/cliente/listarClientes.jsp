<%@page import="br.com.oficina.modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.util.List" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Cliente - Listar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Lista de Clientes Registrados</h1>
        
        <table border="1">
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>CNPJ</th>
                <th colspan="2">Ações</th>
            </tr>
            <%
                List<Cliente> lista = (List<Cliente>) request.getAttribute("listaClientes");
                for (Cliente cliente : lista) {%>
                    <tr>
                        <td><%= cliente.getId() %></td>
                        <td>
                            <a href="<%=request.getContextPath() %>/cliente/?acao=detalhar&cliente_codigo=<%= cliente.getId() %>">
                                <%= cliente.getNome() %>
                            </a>
                        </td>
                        <td><%= cliente.getCpf() %></td>
                        <td><%= cliente.getCnpj() %></td>
                        <td>
                            <a href="<%=request.getContextPath() %>/cliente/?acao=atualizar&cliente_codigo=<%= cliente.getId() %>">
                                Atualizar
                            </a>
                        </td>
                        <td>
                            <a href="C<%=request.getContextPath() %>/cliente/?acao=desativar&cliente_codigo=<%= cliente.getId() %>" onclick="return confirm('Deseja realmente realizar a desativação do registro do registro?')">
                                Desativar
                            </a>
                        </td>
                    </tr>
                <%}
            %>
            </table>
               
    </body>
</html>
