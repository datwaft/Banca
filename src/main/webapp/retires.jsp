
<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Sistema de Depositos y retiros por cajeros
      </div>
      
      <div class="transfer_left">
        <label>Numero de cuenta: </label>
        <input type="text" placeholder="Numero de cuenta"><input type = "submit" value="verificar"><br><br>
        <label>Cedula: </label>
        <input type = "text" placeholder="Cedula" "><br>
        <label>Nombre: </label>
        <input type = "text" value="Namae" disabled="disabled" readonly="readonly"><br>
        <label>Saldo a retirar:</label>
        <input type = "text" placeholder="Cantidad"><br>
        <input type = "submit" value="Retirar"><br>
      </div>
      
      
      <div class="transfer_right">
        <label>Numero de cuenta: </label>
        <input type="text" placeholder="Numero de cuenta"><input type = "submit" value="verificar"><br>
        <label>Cedula: </label>
        <input type = "text" placeholder="Cedula" "><br>
        <label>Nombre: </label>
        <input type = "text" value="Namae" disabled="disabled" readonly="readonly"><br>
        <label>Saldo a depositar:</label>
        <input type = "text" placeholder="Cantidad"><br>
        <input type = "submit" value="Depositar"><br>
      </div>
    </div>
    
    
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
