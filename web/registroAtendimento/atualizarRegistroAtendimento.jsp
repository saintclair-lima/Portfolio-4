<%@page import="br.com.oficina.modelo.Cliente"%>
<%@page import="br.com.oficina.modelo.TipoServico"%>
<%@page import="br.com.oficina.modelo.RegistroAtendimento"%>
<%@page import="br.com.oficina.modelo.Carro"%>
<%@page import="br.com.oficina.modelo.Atendente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Registro de Atendimento - Atualizar</title>
        
        <jsp:include page="/cabecalho.jsp"/>
        
        <script type="text/javascript">
            var todosCarros ={
                <%
                    List<Carro> listaCarros = (List<Carro>) request.getAttribute("listaCarros");
                    for (Carro carro:listaCarros){
                %>
                        <%=carro.getId()%> :{descricao:"<%=carro.getModelo() + "|" +carro.getPlaca()%>",
                                             clienteCodigo:<%=carro.getCliente().getId()%>},
                <%
                    }
                %>
            };
            
            
    

            
            function setCliente(){
                var cliente = document.getElementById("cliente");
                var codigoCliente = cliente.options[cliente.selectedIndex].value;
                
                document.getElementById("cliente_id").value=codigoCliente;
                
                carroSelect = document.getElementById('carro');
                carroSelect.disabled = false;
                
                var tamanho = carroSelect.options.length;
                for (i=tamanho-1; i>0; i--){
                    carroSelect.options.remove(i)
                }
                
                for (var carroId in todosCarros){
                    if (todosCarros[carroId].clienteCodigo == codigoCliente){
                        carroSelect.options[carroSelect.options.length] = new Option(todosCarros[carroId].descricao, carroId);
                    }
                }
                console.log("setCliente");
            }
            
            
            
            function setCarro(){
                var carro = document.getElementById("carro");
                var codigoCarro = carro.options[carro.selectedIndex].value;
                
                document.getElementById("carro_codigo").value=codigoCarro;
                console.log("setCarro");
            }
            
            function setAtendente(){
                var atendente = document.getElementById("atendente");
                var atendenteId = atendente.options[atendente.selectedIndex].value;
                document.getElementById("atendente_id").value=atendenteId;
                console.log("setAtendente")
            }

            function setupZero(){
                carroSelect = document.getElementById('carro');
                carroSelect.options[0] = new Option("Selecione o Carro", "");
                carroSelect.options[0].style.disabled = true;
                console.log("setupZero");
            }
            
            function toggleEncerrado(){
                if (document.getElementById("ra_esta_encerrado").checked==true){
                    document.getElementById("campo_encerramento").style.display = "block";
                    document.getElementById("descricao_encerramento").value=null;
                } else {
                    document.getElementById("campo_encerramento").style.display = "none";
                }
                console.log("toggleEncerrado");
            }
        </script>
        <%RegistroAtendimento ra = (RegistroAtendimento) request.getAttribute("registroAtendimento");
        String atendenteNome = ra.getAtendente().getNome();
        int atendenteId = ra.getAtendente().getId();
        String clienteNome = ra.getCliente().getNome();
        int clienteId = ra.getCliente().getId();
        String carroDescricao = ra.getCarro().getModelo() + "|" +ra.getCarro().getPlaca();
        int carroCodigo = ra.getCarro().getId();
        
        %>
        <h1>Gerar Registro de Atendimento</h1>
        
        <form action="<%=request.getContextPath() %>/registroAtendimento/" method="post">
            <input type="hidden" name="acao" value="alterar"/>
            
            <label for="atendente"><b>Atendente: </b></label>
            <select name="atendente" id="atendente" onblur="setAtendente()">
                <option value="<%=ra.getAtendente().getId()%>" selected><%=ra.getAtendente().getNome()%></option>
                <%
                    List<Atendente> listaAtendentes = (List<Atendente>) request.getAttribute("listaAtendentes");
                    for (Atendente atendente:listaAtendentes){
                %>
                        <option value ="<%=atendente.getId()%>">
                            <%=atendente.getNome()%>
                        </option>
                <%
                    }
                %>
            </select>            
            <input type="hidden" value="0" name="atendente_id" id="atendente_id"/>
            <br/>
            
            <label for="cliente"><b>Cliente: </b></label>
            <select name="cliente" id="cliente" onblur="setCliente()" onchange="setCliente()">
                <option value="<%=ra.getCliente().getId()%>" selected><%=ra.getCliente().getNome()%></option>
                <%
                    List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
                    for (Cliente cliente:listaClientes){
                %>
                        <option value ="<%=cliente.getId()%>">
                            <%=cliente.getNome() + " | CPF: " + cliente.getCpf()%>
                        </option>
                <%
                    }
                %>
            </select>            
            <input type="hidden" value="<%=ra.getCliente().getId()%>" name="cliente_id" id="cliente_id"/>
            <br/>
            
            <label for="carro"><b>Carro: </b></label>
            <select name="carro" id="carro" disabled onblur="setCarro()">
                <option value="<%=ra.getCarro().getId()%>" selected><%=ra.getCarro().getModelo() + "|" +ra.getCarro().getPlaca()%></option>
                
            </select>            
            <input type="hidden" value="<%=ra.getCarro().getId()%>" name="carro_codigo" id="carro_codigo"/>
            <br/>          
            <br/>

            <label for="descricao_Abertura"><b>Descrição do Atendimento: </b></label><br/>
            <textarea name="descricao_abertura" id="descricao_abertura" ><%=ra.getDescricaoAbertura()%></textarea>

            <br/>
            <input type="checkbox" name="ra_esta_encerrado" id="ra_esta_encerrado" value="false">
            <label for="ra_esta_encerrado">Registro de Atendimento encerrado?</label>
            <div id="campo_encerramento" style="visibility:collapse">
                <textarea name="descricao_encerramento" id="descricao_encerramento"></textarea>
            </div>
            <br/>
            <input class="bot_envio" type="submit" value="Inserir Registro de Atendimento">
            <input class="bot_cancela" type="button" value ="Cancelar" onClick="window.location='<%=request.getContextPath() %>/home.jsp';"/><br/>
        </form>
        
    </body>
</html>