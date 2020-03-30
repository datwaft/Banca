<!DOCTYPE html>
<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Bank transfer system
      </div>
      
      <div class="transfer_left">
        <table class = "register">
          <tr>
            <td><label>Search by: </label></td>
            <td><select id="trans_options" onchange='hideOptions()'>
                <option  value="id" selected>ID</option>
                <option  value="account_number">Account number</option>
            </select></td>
          </tr>
          
          <tr id='box_acc'>
            <td><label>Account number:</label></td>
            <td><input type = "text" placeholder="Account number"></td>
          </tr>
          
          <tr id='id_box'>
            <td><label>ID: </label></td>
            <td><input type = "text" placeholder="ID" ><input type = "submit" value="verify"></td>
          </tr>
          <tr id='id_ac_box'>
            <td><label>Origin account: </label></td>
            <td><select>
              <option value="Cuenta 1">Volvo</option>
              <option value="Cuenta 2">Saab</option>
            </select></td>
          </tr>
          <tr>
            <td><label>Complete Name: </label></td>
            <td><input type = "text" value="Namae" disabled="disabled" readonly="readonly"></td>
          </tr>
          <tr>
            <td><label>Amount to deposit:</label></td>
            <td><input type = "text" placeholder="Cantidad"></td>
          </tr>
        </table>
      </div>
      <div class="transfer_right">
        <table class = "register">         
          <tr>
            <td><label>Destination account: </label></td>
            <td><input type = "text" placeholder="No. Cuenta"> <input type = "submit" value="verify"></td>
          </tr>
          <tr>
            <td><label>Receptor name: </label></td>
            <td><input type = "text" value="Namae" disabled="disabled" readonly="readonly"></td>
          </tr>
          <tr>
            <td><label>Amount to receive:</label></td>
            <td><input type = "text" value="Namae" disabled="disabled" readonly="readonly"></td>
          </tr>
        </table>
      </div>
    </div>
    <div class="title">
        <input type = "submit" value="Tansfer">
    </div>
    
    
    <%@ include file="/footer.jsp" %>
    
    </body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/functions.js"></script>
</html> 
