<%@page import="br.com.oficina.modelo.TipoServico"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Tipo de Serviço - Incluir</title>
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
        
        <%TipoServico servico = (TipoServico) request.getAttribute("tipoServico");%>
        <h1>Inserir Tipo de Serviço</h1>
        <form action="<%=request.getContextPath() %>/tipoServico/" method="post">
            <div class="container">
                <input type="hidden" name="acao" value="inserir"/>
                <label for="servico_nome">Nome</label>
                <input type="text" name="servico_nome" maxlength="50"/><br/>
                <label for="servico_duracao">Duração</label>
                <input type="number" name="servico_duracao" min="0.00" step="1"/><br/>
                <label for="servico_valor">Valor</label>
                <input type="number" name="servico_valor"min="0.00" step="0.01" /><br/>
            </div>
            <input class="bot_envio" type="submit" value="Inserir Dados"/><br/><br/>
        </form>
    </body>
</html>