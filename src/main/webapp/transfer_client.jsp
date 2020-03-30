<!DOCTYPE html>
<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Sistema de tranferencias bancarias
      </div>
      
      <div class="transfer_left">
        <table class = "register">
          <tr>
            <td><label>Cuenta de origen: </label></td>
            <td><select id="cars">
              <option value="Cuenta 1">Volvo</option>
              <option value="Cuenta 2">Saab</option>
            </select></td>
          </tr>
          <tr>
            <td><label>Nombre: </label></td>
            <td><input type = "text" value="Namae" disabled="disabled" readonly="readonly"></td>
          </tr>
          <tr>
            <td><label>Saldo a depositar:</label></td>
            <td><input type = "text" placeholder="Cantidad"></td>
          </tr>
        </table>
      </div>
      
      
      <div class="transfer_right">
        <table class = "register">
          <tr>
            <td><label>Cuenta de destino : </label></td>
            <td><select id="cars">
             <option value="Cuenta 1">Volvo</option>
              <option value="Cuenta 2">Saab</option>
            </select></td>
          </tr>
          <tr>
            <td><label>Numero de cuenta destinataria: </label></td>
            <td><input type = "text" placeholder="No. Cuenta"> <input type = "submit" value="verificar"></td>
          </tr>
          <tr>
            <td><label>Nombre del receptor: </label></td>
            <td><input type = "text" value="Namae" disabled="disabled" readonly="readonly"></td>
          </tr>
          <tr>
            <td><label>Saldo a recibir:</label></td>
            <td><input type = "text" value="Namae" disabled="disabled" readonly="readonly"></td>
          </tr>
        </table>
      </div>
    </div>
    <div class="title">
        <input type = "submit" value="Tansferir">
    </div>
    
    
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
