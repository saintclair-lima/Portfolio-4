<%@page import="br.com.oficina.modelo.TipoServico"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.util.List" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Tipos de Servi�os - Listar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Lista de Tipos de Servi�os Registrados</h1>
        
        <table border="1">
            <tr>
                <th>C�digo</th>
                <th>Nome</th>
                <th colspan="2">A��es</th>
            </tr>
            <%
                List<TipoServico> lista = (List<TipoServico>) request.getAttribute("listaTipoServico");
                for (TipoServico servico : lista) {%>
                    <tr>
                        <td><%= servico.getCodigo()%></td>
                        <td>
                            <a href="<%=request.getContextPath() %>/tipoServico/?acao=detalhar&servico_codigo=<%= servico.getCodigo() %>">
                                <%= servico.getNome() %>
                            </a>
                        </td>
                        <td>
                            <a href="<%=request.getContextPath() %>/tipoServico/?acao=atualizar&servico_codigo=<%= servico.getCodigo() %>">
                                Atualizar
                            </a>
                        </td>
                        <td>
                            <a href="<%=request.getContextPath() %>/tipoServico/?acao=desativar&servico_codigo=<%= servico.getCodigo() %>" onclick="return confirm('Deseja realmente realizar a desativa��o do registro?')">
                                Desativar
                            </a>
                        </td>
                    </tr>
                <%}
            %>
            </table>
               
    </body>
</html>
