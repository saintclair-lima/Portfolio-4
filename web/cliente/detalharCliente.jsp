<%@page import="br.com.oficina.modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Cliente - Detalhar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Detalhes do Cliente</h1>
        <%Cliente cliente = (Cliente) request.getAttribute("cliente");%>
        
        <p><b>C�digo: </b><%= cliente.getId() %></p>
        <p><b>Nome: </b><%= cliente.getNome() %></p>
        <p><b>Cpf: </b><%= cliente.getCpf() %></p>
        <p><b>Cnpj: </b><%= cliente.getCnpj() %></p>
        <p><b>Logradouro: </b><%= cliente.getLogradouro() %></p>
        <p><b>N�mero: </b><%= cliente.getNumeroCasa() %></p>
        <p><b>Bairro: </b><%= cliente.getBairro() %></p>
        <p><b>Complemento: </b><%= cliente.getComplemento() %></p>
        <p><b>Cidade: </b><%= cliente.getCidade() %></p>
        <p><b>Estado: </b><%= cliente.getEstado() %></p>
        <p><b>CEP: </b><%= cliente.getCep() %></p>
        <p><b>Telefone: </b><%= cliente.getTelefone() %></p>
        <p><b>Email: </b><%= cliente.getEmail() %></p>
        <p><b>Observa��es: </b><%= cliente.getObservacoes() %></p>
        
        <a class="bot_excluir" href="<%=request.getContextPath() %>/cliente/?acao=desativar&cliente_codigo=<%= cliente.getId() %>" onclick="return confirm('Deseja realmente realizar a desativa��o do registro?')">Desativar Cliente</a> 
        <a class="bot_detalhar" href="<%=request.getContextPath() %>/cliente/?acao=atualizar&cliente_codigo=<%= cliente.getId() %>">Atualizar Cliente</a><br/><br/>
        
    </body>
</html>