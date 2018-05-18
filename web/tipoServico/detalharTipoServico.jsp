<%@page import="br.com.oficina.modelo.TipoServico"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Tipo de Servico - Detalhar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Detalhes do Tipo de Servico</h1>
        <%TipoServico servico = (TipoServico) request.getAttribute("tipoServico");%>
        
        <p><b>Código: </b><%= servico.getCodigo() %></p>
        <p><b>Nome: </b><%= servico.getNome() %></p>
        <p><b>Duração: </b><%= servico.getDuracao() %></p>
        <p><b>Valor: </b><%= servico.getValor() %></p>
        
        <a class="bot_excluir" href="<%=request.getContextPath() %>/tipoServico/?acao=desativar&servico_codigo=<%= servico.getCodigo()%>" onclick="return confirm('Deseja realmente realizar a atualizar do registro?')">Desativar Tipo de Serviço</a> 
        <a class="bot_detalhar" href="<%=request.getContextPath() %>/tipoServico/?acao=atualizar&servico_codigo=<%= servico.getCodigo()%>">Atualizar Tipo de Serviço</a><br/><br/>
        
    </body>
</html>