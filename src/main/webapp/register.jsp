<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Register a new customer
      </div>
      
      <div class="register_left">
        
        <table class = "register">
          <tr>
            <td><label>ID:</label></td>
            <td><input type = "text" placeholder="Digite la cedula"><br></td>
            <td rowspan="2"><input type = "submit" value="Verify" align="center"><td><br>
          </tr>
          <tr>
            <td><label>Complete Name: </label></td>
            <td><input type="text" placeholder="Digite el nombre"><br></td>
          </tr>
          <tr>
            <td><label>Telephone:</label></td>
            <td><input type = "text" placeholder="Digite el telefono"><br></td> 
          </tr>
          <tr>
            <td><label>Transfer limit:</label></td>
            <td><input type = "text" placeholder="Limit"><br></td> 
          </tr>
          <tr>
            <td><label>Currency</label></td>
            <td><select id="coin">
                  <option value="USD">USD</option>
                  <option value="CRC">CRC</option>
                </select></td><br>    
          </tr>
        </table>
        <center><input type = "submit" value="Register"><br></center>
        
        
      </div>
      
      
      
      <div class="register_right"><img id="sign" src="${pageContext.request.contextPath}/resources/images/sign_up.png"></div>
        
    </div>
    
    
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
