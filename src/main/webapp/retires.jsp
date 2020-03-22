
<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Sistema de Depositos y retiros por cajeros
      </div>
      
      <div class="transfer_left">
        <table class="register">
          <tr>
            <td><label>Numero de cuenta: </label></td>
            <td><input type="text" placeholder="Numero de cuenta"></td>
            <td rowspan="2"><input type = "submit" value="verificar"></td>
          </tr>
          <tr>
            <td><label>Cedula: </label></td>
            <td><input type = "text" placeholder="Cedula" "></td>
          </tr>
          <tr>
            <td><label>Nombre: </label></td>
            <td><input type = "text" placeholder="Namae" ></td>
          </tr>
          <tr>
            <td><label>Saldo a retirar:</label></td>
            <td><input type = "text" placeholder="Cantidad"></td>
            <td><input type = "submit" value="Retirar"></td>
          </tr>
          <tr>
            <td><label>Description: </label></td>
            <td><input type = "text" placeholder="Description" ></td>
          </tr>
        </table>
      </div>
      
      
      <div class="transfer_right">
        <table class="register">
          <tr>
            <td><label>Numero de cuenta: </label></td>
            <td><input type="text" placeholder="Numero de cuenta"></td>
            <td rowspan="2"><input type = "submit" value="verificar"></td>
          </tr>
          <tr>
            <td><label>Cedula: </label></td>
            <td><input type = "text" placeholder="Cedula" "></td>
          </tr>
          <tr>
            <td><label>Nombre: </label></td>
            <td><input type = "text" value="Namae" disabled="disabled" readonly="readonly"></td>
          </tr>
          <tr>
            <td><label>Saldo a depositar:</label></td>
            <td><input type = "text" placeholder="Cantidad"></td>
            <td><input type = "submit" value="Depositar"></td>
          </tr>
        </table>
      </div>
    </div>
    
    
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
