<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="retire_display">
      <div class="title">Retire system:</div>
      <div class="container">
        <table class="register">
          
          <tr>
            <td><label>Cedula: </label></td>
            <td><input type = "text" placeholder="Cedula" "></td>
            <td><input type = "submit" value="Search"></td>
          </tr>
             <tr>
            <td><label>Cuenta: </label></td>
            <td>
              <select>
                <option>hola</option>
              </select>
            </td>        
          </tr>    
          <tr>
            <td ><label>Numero de cuenta: </label></td>
            <td><input type="text" placeholder="Numero de cuenta"></td>
            <td><input type = "submit" value="Search"></td>
          </tr>            
          <br>
          <tr>
            <td><label>Owner:</label></td>
            <td><input type = "text" placeholder="owner" disabled></td>
          </tr>
          <tr>
            <td><label>Ammount to deposit:</label></td>
            <td><input type = "text" placeholder="Cantidad"></td>
          </tr>
          <tr>
            <td colspan="3" style="text-align: center;"><input type = "submit" value="Proceed"></td>
          </tr>
        </table>
      </div>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
