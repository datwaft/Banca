<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="link_display">
      <div class="title">Accounts linking:</div>
      <div class="container">
        <table>
          <tr>
            <td><label>Account number:</label></td>
            <td><input type = "text" placeholder="Digite la cedula"></td>
            <td><input type = "submit" value="Verificar" align="center"><td>
          </tr>
          <tr>
            <td><label>Complete name: </label></td>
            <td><input type="text" placeholder="Name"></td>
          </tr>
          <tr>
            <td><label>Id:</label></td>
            <td><input type = "text" placeholder="Cedula"></td> 
          </tr>
          <tr>
            <td colspan="2"><center><input type = "submit" value="Vincular" align="center"></center></td>
          </tr>
        </table>
      </div>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
