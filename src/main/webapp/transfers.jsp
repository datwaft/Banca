
<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Sistema de tranferencias bancarias
      </div>
      
      <div class="transfer_left">
        <label>Cuenta de origen: </label>
        <select id="cars">
          <option value="Cuenta 1">Volvo</option>
          <option value="Cuenta 2">Saab</option>
        </select><br>
        <label>Nombre: </label>
        <input type = "text" value="Namae" disabled="disabled" readonly="readonly"><br>
        <label>Saldo a depositar:</label>
        <input type = "text" placeholder="Cantidad">
      </div>
      
      
      <div class="transfer_right">
        <label>Cuenta de destino : </label>
        <select id="cars">
          <option value="Cuenta 1">Volvo</option>
          <option value="Cuenta 2">Saab</option>
        </select><br>
        <label>Numero de cuenta destinataria: </label>
        <input type = "text" placeholder="No. Cuenta"> <input type = "submit" value="verificar"><br>
        <label>Nombre del receptor: </label>
        <input type = "text" value="Namae" disabled="disabled" readonly="readonly"><br>
        <label>Saldo a recibir:</label>
        <input type = "text" value="Namae" disabled="disabled" readonly="readonly">
      </div>
    </div>
    <div class="title">
        <input type = "submit" value="Tansferir">
    </div>
    
    
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
