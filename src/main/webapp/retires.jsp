
<html>
    <script type="text/javascript" src="js/functions.js"></script>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Withdrawal and deposit system
      </div>
      
      <div class="transfer_left">
        
        <p>Select the option corresponding to your needs:</p>
        <table class="register">
          <tr>
            <td><p>Deposit:</p></td>
            <td><input id='rad1' type="Radio" onclick="change()" name="radio"  value="depo" checked></td>
          </tr>            
          <tr>
            <td><p>Retire:</p></td>
            <td><input id='rad2' type="Radio" onclick="change()" name="radio" " value="reti"></td>
          </tr>         
          <tr>
            <td><p>Search account by:</p></td>
          </tr>
          <tr>
            <td><p>ID:</p></td>
            <td><input id='id' type = "Radio" name='search_t' onclick="hide()" value='cedula' checked></td>
          </tr>
          <tr>
            <td><p>Account number:</p></td>
            <td><input id='num' type = "Radio" name='search_t' onclick="hide()" value='number'></td>
          </tr>
        </table>    
      </div>   
      
      <div class="transfer_right">
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
            <td><label>Balance to retire/deposit:</label></td>
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
            <td colspan="2"><input type = "submit" value="Proceed"></td>
          </tr>
        </table>
        
      </div>
    </div>
       
    <%@ include file="/footer.jsp" %>
    </body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/functions.js"></script>
</html> 
