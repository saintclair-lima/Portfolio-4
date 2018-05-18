<%-- 
    Document   : cabecalho
    Created on : 09/05/2018, 03:52:15
    Author     : saintclair
--%>


        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" type="text/css" href="">
        
        
    </head>
    <body>
        <div>CHAVE DE RODA</div>
        <div><span>Centro Automotivo</span></div>
        <div>
            <a href="../index.html">Home</a>
        </div>
        <div>
            <h2>Clientes</h2>
            <div>
                <a href="<%=request.getContextPath() %>/cliente/?acao=consultar">Listar Clientes</a>
                <a href="<%=request.getContextPath() %>/cliente/?acao=buscar">Buscar Clientes</a>
                <a href="<%=request.getContextPath() %>/cliente/?acao=incluir">Incluir Clientes</a>
            </div>
        </div>
        <div>
            <h2>Serviços</h2>
            <div>
                <a href="<%=request.getContextPath() %>/tipoServico/?acao=consultar">Listar Serviços</a>
                <a href="<%=request.getContextPath() %>/tipoServico/?acao=buscar">Buscar Serviços</a>
                <a href="<%=request.getContextPath() %>/tipoServico/?acao=incluir">Incluir Serviços</a>
            </div>
        </div>
        <div>
            <h2>Registros de Atendimento</h2>
            <div>
                <a href="<%=request.getContextPath() %>/registroAtendimento/?acao=consultar">Listar Registros de Atendimento</a>
                <a href="<%=request.getContextPath() %>/registroAtendimento/?acao=buscar">Buscar Registros de Atendimento</a>
                <a href="<%=request.getContextPath() %>/registroAtendimento/?acao=incluir">Incluir Registros de Atendimento</a>
            </div>
        </div>
        
        <div>
            <a href="">Sobre Nós</a>
        </div>