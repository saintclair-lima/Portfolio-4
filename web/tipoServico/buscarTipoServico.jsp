<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Tipo de Serviço - Buscar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Buscar Tipode Serviço pelo Código</h1>
        <form action="<%=request.getContextPath() %>/tipoServico/" method="post">
            <input type="hidden" name="acao" value="detalhar"/>
            <label for="servico_codigo"><b>Código do Tipo de Serviço: </b></label>
            <input type="number" name="servico_codigo" max="2147483648"/>
            <input class="bot_envio" type="submit" value="Buscar"/>
        </form>
    </body>
</html>
