<%@page import="br.com.oficina.modelo.RegistroAtendimento"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.util.List" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Registros de Atendimento - Listar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Atendimentos Registrados</h1>
        
        <table border="1">
            <tr>
                <th>Número</th>
                <th>Data de Abertura</th>
                <th>Cliente</th>
                <th>Estado</th>
                <th colspan="2">Ações</th>
            </tr>
            <%
                List<RegistroAtendimento> lista = (List<RegistroAtendimento>) request.getAttribute("listaRegistroAtendimento");
                for (RegistroAtendimento atendimento : lista) {%>
                    <tr>
                        <td>
                            <%= atendimento.getNumero()%>
                        <td>
                            <%= atendimento.getDataAberturaStringFormatada()%>
                        </td>
                        <td>
                            <a href="<%=request.getContextPath() %>/cliente/?acao=detalhar&cliente_codigo=<%= atendimento.getCliente().getId()%>">
                                <%= atendimento.getCliente().getNome()%>
                            </a>
                        </td>
                        <td>
                            <%= atendimento.getEstado()%>
                        </td>
                        <td>
                            <a href="<%=request.getContextPath() %>/registroAtendimento/?acao=detalhar&ra_numero=<%= atendimento.getNumero() %>">
                                Detalhar
                            </a>    
                        </td>
                        <td>
                            <a href="<%=request.getContextPath() %>/registroAtendimento/?acao=atualizar&ra_numero=<%= atendimento.getNumero() %>">
                                Atualizar
                            </a>
                        </td>
                        
                        
                    </tr>
                <%}
            %>
            </table>
               
    </body>
</html>
