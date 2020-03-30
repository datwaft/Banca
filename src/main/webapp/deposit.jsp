<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="deposit_display">
      <div class="title">Deposit system:</div>
      <div class="container">
        <table class="register">
          
          <tr id='ced_table'>
            <td><label>Cedula: </label></td>
            <td><input type = "text" placeholder="Cedula" "></td>
            <td><input type = "submit" value="Search"></td>
          </tr>
           <tr id='number_table_2'>
            <td ><label>Numero de cuenta: </label></td>
            <td><input type="text" placeholder="Numero de cuenta"></td>
            <td><input type = "submit" value="Search"></td>
          </tr>
          <tr id='number_table'>
            <td><label>Cuenta: </label></td>
            <td>
              <select>
                <option>hola</option>
              </select>
            </td>        
          </tr>         
          <br>
          <tr>
            <td><label>Ammount to deposit:</label></td>
            <td><input type = "text" placeholder="Cantidad"></td>
          </tr>
          <tr>
            <td><label>Full name: </label></td>
            <td><input id='name' type = "text" placeholder="Namae" ></td>
          </tr>       
          <tr>
            <td><label>Description: </label></td>
            <td><input id='description' type = "text" placeholder="Description" ></td>
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
