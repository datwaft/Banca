<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Registrar a un nuevo cliente
      </div>
      
      <div class="register_left">
        
        <table class = "register">
          <tr>
            <td><label>Nombre Completo: </label></td>
            <td><input type="text" placeholder="Digite el nombre"><br></td>
          </tr>
          <tr>
            <td><label>Cedula:</label></td>
            <td><input type = "text" placeholder="Digite la cedula"><br></td>
          </tr>
          <tr>
            <td><label>Telefono:</label></td>
            <td><input type = "text" placeholder="Digite el telefono"><br></td> 
          </tr>
          <tr>
            <td><label>Moneda</label></td>
            <td><select id="coin">
                  <option value="USD">USD</option>
                  <option value="CRC">CRC</option>
                </select></td><br>    
          </tr>
        </table>
          <input type = "submit" value="Registrar"><br>
        
        
      </div>
      
      
      
      <div class="register_right"><img id="sign" src="${pageContext.request.contextPath}/resources/images/sign_up.png"></div>
        
    </div>
    
    
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
