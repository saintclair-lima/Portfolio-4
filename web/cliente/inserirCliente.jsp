<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Cliente - Inserir</title>
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
        
        <h1>Novo Cliente</h1>
        <form action="<%=request.getContextPath() %>/cliente/" method="post">
            <div class="container">
                <input type="hidden" name="acao" value="inserir"/>
                <label for="cliente_nome">Nome</label>
                <input type="text" name="cliente_nome" maxlength="35"/><br/>
                <label for="cliente_cpf">CPF</label>
                <input type="text" name="cliente_cpf" maxlength="11"/><br/>
                <label for="cliente_cnpj">CNPJ</label>
                <input type="text" name="cliente_cnpj" maxlength="15"/><br/>
                <label for="cliente_logradouro">Logradouro</label>
                <input type="text" name="cliente_logradouro" maxlength="50"/><br/>              
                <label for="cliente_numero_endereco">Número</label>
                <input type="text" name="cliente_numero_endereco"/><br/>
                <label for="cliente_bairro">Bairro</label>
                <input type="text" name="cliente_bairro" maxlength="50"/><br/>
                <label for="cliente_complemento">Complemento</label>
                <input type="text" name="cliente_complemento" maxlength="250"/><br/>
                <label for="cliente_cidade">Cidade</label>
                <input type="text" name="cliente_cidade" maxlength="50"/><br/>
                <label for="cliente_estado">Estado</label>
                <input type="text" name="cliente_estado" maxlength="50"/><br/>
                <label for="cliente_cep">CEP</label>
                <input type="text" name="cliente_cep" maxlength="8"/><br/>
              
                <label for="cliente_telefone">Telefone</label>
                <input type="text" name="cliente_telefone" maxlength="11"/><br/>
                <label for="cliente_email">Email</label>
                <input type="text" name="cliente_email" maxlength="40"/><br/>
                <label for="cliente_observacoes">Observações</label>
                <input type="text" name="cliente_observacoes"/><br/>
            </div>
            <input class="bot_envio" type="submit" value="Incluir Cliente"/> 
            <input class="bot_cancela" type="button" value ="Cancelar" onClick="window.location='<%=request.getContextPath() %>/home.jsp';"/><br/>
        </form>
    </body>
</html>
