<%@page import="java.util.GregorianCalendar"%>
<%@page import="br.com.oficina.utils.Utils"%>
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
                for (var i=tamanho-1; i>0; i--){
                    console.log("removendo " + carroSelect.options[i]);
                    carroSelect.options.remove(i);
                }
                
                carroSelect.options[0] = new Option("Selecione o Carro", "");
                
                for (var carroId in todosCarros){
                    if (todosCarros[carroId].clienteCodigo == codigoCliente){
                        console.log("Adicionando "+todosCarros[carroId].descricao)
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
                if (document.getElementById("ra_esta_encerrado").checked===true){
                    document.getElementById("campo_encerramento").style.visibility = "visible";
                } else {
                    document.getElementById("campo_encerramento").style.visibility = "collapse";
                    document.getElementById("descricao_encerramento").value=null;
                    document.getElementById("label_data_encerramento").innerHTML="<b>Data do Encerramento: </b><%=Utils.getDataStringFormatada(new GregorianCalendar())%>";
                    document.getElementById("data_encerramento").value="<%=Utils.getDataString(new GregorianCalendar())%>";
                }
                console.log("toggleEncerrado");
            }
            
            function confirmarCancelaEncerramento(){
                if (document.getElementById("ra_esta_encerrado").checked === true) {
                    toggleEncerrado();
                    document.getElementById("ra_esta_encerrado").value = true;
                } else {
                    var confirmacao = confirm("O estado do RA será alterado para ABERTO e os dados de encerramento serão apagados.\nConfirma encerramento?");
                    if (confirmacao===true){
                        toggleEncerrado();
                        document.getElementById("ra_esta_encerrado").value = false;
                    }else{
                        document.getElementById("ra_esta_encerrado").checked = true;
                        document.getElementById("ra_esta_encerrado").value = true;
                    }
                }
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
        <h1>Atualizar Registro de Atendimento</h1>
        
        <form action="<%=request.getContextPath() %>/registroAtendimento/" method="post">
            <input type="hidden" name="acao" value="alterar"/>
            <input type="hidden" name="numero_ra" id="numero_ra" value="<%=ra.getNumero()%>"/>
            
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
            <input type="hidden" value="<%=ra.getAtendente().getId()%>" name="atendente_id" id="atendente_id"/>
            <br/>
            
            <label for="cliente"><b>Cliente: </b></label>
            <select name="cliente" id="cliente" onchange="setCliente()">
                <option value="<%=ra.getCliente().getId()%>" selected><%=ra.getCliente().getNome() + " | CPF: " + ra.getCliente().getCpf()%></option>
                <%
                    List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
                    for (Cliente cliente:listaClientes){
                        if(cliente.getId() != ra.getCliente().getId()){
                %>
                        <option value ="<%=cliente.getId()%>">
                            <%=cliente.getNome() + " | CPF: " + cliente.getCpf()%>
                        </option>
                <%
                        }
                    }
                %>
            </select>            
            <input type="hidden" value="<%=ra.getCliente().getId()%>" name="cliente_id" id="cliente_id"/>
            <br/>
            
            <label for="carro"><b>Carro: </b></label>
            <select name="carro" id="carro" disabled onchange="setCarro()">
                <option value="" >Selecione o Carro></option>
                <option value="<%=ra.getCarro().getId()%>" selected><%=ra.getCarro().getModelo() + "|" +ra.getCarro().getPlaca()%></option>                
            </select>            
            <input type="hidden" value="<%=ra.getCarro().getId()%>" name="carro_codigo" id="carro_codigo"/>
            <br/>          
            <br/>
            <hr/>
            <label for="data_abertura"><b>Data do Atendimento: </b><%=Utils.getDataStringFormatada(ra.getDataAbertura())%></label><br/>
            <input type="hidden" id="data_abertura" name="data_abertura" value="<%=Utils.getDataString(ra.getDataAbertura())%>"/><br/>
            <label for="descricao_abertura"><b>Descrição do Atendimento: </b></label><br/>
            <textarea name="descricao_abertura" id="descricao_abertura"><%=ra.getDescricaoAbertura()%></textarea>
            <hr/>
            
            <br/><br/>
            <%
                if (!ra.getEstado().equals("Aberto")){
            %>
                    <input type="checkbox" name="ra_esta_encerrado" id="ra_esta_encerrado" checked="checked" value="true" onchange="confirmarCancelaEncerramento()">
                    <label for="ra_esta_encerrado">Registro de Atendimento encerrado?</label>

                    <div id="campo_encerramento" name="campo_encerramento" style="visibility:visible">
                        <hr/>
                        <p id = "label_data_encerramento"><b>Data do Encerramento: </b><%=Utils.getDataStringFormatada(ra.getDataEncerramento())%></p>
                        <input type="hidden" id="data_encerramento" value="<%=Utils.getDataString(ra.getDataEncerramento())%>"/>
                        <select id="estado" name="estado">
                            <option disabled value="">Selecione motivo do enceramento</option>
                            <option selected value="Concluído">Concluído</option>
                            <option value="Cancelado">Cancelado</option>
                        </select>
                        <label for="descricao_encerramento"><b>Descrição do Encerramento: </b></label><br/>
                        <textarea name="descricao_encerramento" id="descricao_encerramento"> <%=ra.getDescricaoEncerramento()%> </textarea>
                        <hr/>
                    </div>
            <%
                } else {
            %>
                    <input type="checkbox" name="ra_esta_encerrado" id="ra_esta_encerrado" value="false" onchange="confirmarCancelaEncerramento()">
                    <label for="ra_esta_encerrado">Registro de Atendimento encerrado?</label>

                    <div id="campo_encerramento" name="campo_encerramento" style="visibility:collapse">
                        <hr/>
                        <p id="label_data_encerramento"><b>Data do Encerramento: </b><%=Utils.getDataStringFormatada(new GregorianCalendar())%></p>
                        <input type="hidden" id="data_encerramento" value="<%=Utils.getDataStringFormatada(new GregorianCalendar())%>"/>
                        <select id="estado" name="estado">
                            <option disabled selected value="">Selecione motivo do encerramento</option>
                            <option value="Concluído">Concluído</option>
                            <option value="Cancelado">Cancelado</option>
                        </select>
                        <label for="descricao_encerramento"><b>Descrição do Encerramento: </b></label><br/>
                        <textarea name="descricao_encerramento" id="descricao_encerramento"></textarea>
                        <hr/>
                    </div>
            
            <%
                }
            %>
            
            
            
            
            <br/>
            <input class="bot_envio" type="submit" value="Alterar Registro de Atendimento">
            <input class="bot_cancela" type="button" value ="Cancelar" onClick="window.location='<%=request.getContextPath() %>/registroAtendimento/?acao=consultar';"/><br/>
        </form>
        
    </body>
</html>