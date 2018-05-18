<%-- 
    Document   : resultadoOperacao
    Created on : 27/10/2017, 04:38:12
    Author     : Samara C. Lima
--%>

<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>
            <%=request.getAttribute("title")%>
        </title>
        
        <jsp:include page="/cabecalho.jsp"/>
    
    
        <%=request.getAttribute("conteudo")%>
    </body>
</html>
