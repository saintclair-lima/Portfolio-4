<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Registro de Atendimento - Buscar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <h1>Buscar Cliente pelo Código</h1>
        <form action="<%=request.getContextPath() %>/registroAtendimento/" method="post">
            <input type="hidden" name="acao" value="detalhar"/>
            <label for="cliente_codigo"><b>Código do Registro de Atendimento </b></label>
            <input type="number" name="ra_numero" max="2147483648"/>
            <input class="bot_envio" type="submit" value="Buscar"/>
        </form>
    </body>
</html>
