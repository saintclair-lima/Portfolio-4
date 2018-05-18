<%@page import="br.com.oficina.modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Cliente - Atualizar</title>
        <style type="text/css">
            .container {
                width: 500px;
                clear: both;
            }
            .container input {
                width: 100%;
                clear: both;
            }
        </style>
   
        <jsp:include page="/cabecalho.jsp"/>
        
        <%Cliente cliente = (Cliente) request.getAttribute("cliente");%>
        <h1>Alterar Dados do Cliente</h1>
        <form action="<%=request.getContextPath() %>/cliente/" method="post">
            <div class="container">
                <input type="hidden" name="acao" value="alterar"/>
                <label for="cliente_codigo">Codigo</label>
                <input type="number" name="cliente_codigo" readonly="true" min="1" value="<%= cliente.getId() %>"/><br/>
                <label for="cliente_nome">Nome</label>
                <input type="text" name="cliente_nome" maxlength="35" value="<%= cliente.getNome() %>"/><br/>
                <label for="cliente_cpf">CPF</label>
                <input type="text" name="cliente_cpf" maxlength="11" value="<%= cliente.getCpf() %>"/><br/>
                <label for="cliente_cnpj">CNPJ</label>
                <input type="text" name="cliente_cnpj" maxlength="15" value="<%= cliente.getCnpj() %>"/><br/>
                <label for="cliente_logradouro">Logradouro</label>
                <input type="text" name="cliente_logradouro" maxlength="50" value="<%= cliente.getLogradouro() %>"/><br/>              
                <label for="cliente_numero_endereco">Número</label>
                <input type="number" name="cliente_numero_endereco" min="1" value="<%= cliente.getNumeroCasa() %>"/><br/>
                <label for="cliente_bairro">Bairro</label>
                <input type="text" name="cliente_bairro" maxlength="50" value="<%= cliente.getBairro() %>"/><br/>
                <label for="cliente_complemento">Complemento</label>
                <input type="text" name="cliente_complemento" maxlength="250" value="<%= cliente.getComplemento() %>"/><br/>
                <label for="cliente_cidade">Cidade</label>
                <input type="text" name="cliente_cidade" maxlength="50" value="<%= cliente.getCidade() %>"/><br/>
                <label for="cliente_estado">Estado</label>
                <input type="text" name="cliente_estado" maxlength="50" value="<%= cliente.getEstado() %>"/><br/>
                <label for="cliente_cep">CEP</label>
                <input type="text" name="cliente_cep" maxlength="8" value="<%= cliente.getCep() %>"/><br/>
                <label for="cliente_telefone">Telefone</label>
                <input type="tel" name="cliente_telefone" maxlength="11" value="<%= cliente.getTelefone() %>"/><br/>
                <label for="cliente_email">Email</label>
                <input type="email" name="cliente_email" value="<%= cliente.getEmail() %>"/><br/>
                <label for="cliente_observacoes">Observações</label>
                <input type="text" name="cliente_observacoes" maxlength="11" value="<%= cliente.getObservacoes() %>"/><br/>
                <input class="bot_envio" type="submit" value="Alterar Dados"/><br/><br/>
            </div>
        </form>
    </body>
</html>