<%@page import="br.com.oficina.modelo.RegistroAtendimento"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Registro de Atendimento - Detalhar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Detalhes do Registro de Atendimento</h1>
        <%RegistroAtendimento atendimento = (RegistroAtendimento) request.getAttribute("registroAtendimento");%>
        
        <p><b>Número: </b><%= atendimento.getNumero()%></p>
        <p><b>Estado: </b><%= atendimento.getEstado()%></p>
        <p><b>Atendente: </b><%= atendimento.getAtendente().getNome() %></p>
        <p><b>Cliente: </b>
            <a href="<%=request.getContextPath() %>/cliente/?acao=detalhar&cliente_codigo=<%= atendimento.getCliente().getId() %>">
                <%= atendimento.getCliente().getNome() %>
            </a>
        </p>
        <p><b>Carro: </b></p>
        <ul>
            <li>Identificador: <%=atendimento.getCarro().getId()%></li>
            <li>Placa: <%=atendimento.getCarro().getPlaca()%></li>
            <li>Modelo: <%=atendimento.getCarro().getModelo()%></li>
            <li>Cor: <%=atendimento.getCarro().getCor()%></li>
            <li>Ano: <%=atendimento.getCarro().getAno()%></li>
        </ul>
        <p><b>Data de Abertura: </b><%= atendimento.getDataAberturaStringFormatada()%></p>
        <p><b>Descrição de Abertura: </b><%= atendimento.getDescricaoAbertura()%></p>
        
        <p><b>Data de Encerramento: </b><%= atendimento.getDataEncerramentoStringFormatada()%></p>
        <p><b>Descrição de Encerramento: </b><%= atendimento.getDescricaoEncerramento()%></p>
        
        
        <a class="bot_excluir" href="<%=request.getContextPath() %>/registroAtendimento/?acao=desativar&ra_numero=<%= atendimento.getNumero()%>" onclick="return confirm('Deseja realmente realizar a alteração do registro?')">Desativar Atendimento</a> 
        <a class="bot_detalhar" href="<%=request.getContextPath() %>/registroAtendimento/?acao=atualizar&ra_numero=<%=atendimento.getNumero()%>">Atualizar Atendimento</a><br/><br/>
        
    </body>
</html>