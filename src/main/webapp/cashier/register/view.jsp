<%@page import="java.util.List"%>
<%@page import="bank.logic.Currency"%>
<%@page import="bank.presentation.cashier.register.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  Model model = (Model)request.getAttribute("model");
  User user = model.getExist();
  List<Currency> currency = model.getCurrency();
%>

<!DOCTYPE html>
<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="principal">
      <div class="title">
        Register a new account
      </div>
      
      <div class="register_left">
        <form name="form" action="${pageContext.request.contextPath}/cashier/register/register" method="post">
          <input type = "hidden" name="register_id_hidden" value="<%= user == null ? "" : model.getId()%>">
        <table class = "register">
          <tr>
            <td><label>ID:</label></td>
            <td><input type = "text" name="register_id" value="<%= user == null ? "" : model.getId()%>"  placeholder="ID"><br></td>
            <td><input type = "submit" value="Verify" formaction="${pageContext.request.contextPath}/cashier/register/verify" ><td><br>
          </tr>
          <tr>
            <td><label>Complete Name: </label></td>
            <td><input type="text" name="register_name" value="<%= user != null ? user.getName() : "" %>" placeholder="Insert the name" <%= user != null ? "disabled" : "" %>></td>
            <td><input type = "submit" value="Clear" formaction="${pageContext.request.contextPath}/cashier/register/clear" ><td>
          </tr>
          <tr>
            <td><label>Telephone:</label></td>
            <td><input type = "text" name="register_cellphone" value="<%= user != null ? user.getCellphone() : "" %>" placeholder="Digite el telefono" <%= user != null ? "disabled" : "" %>><br></td> 
          </tr>
          <tr>
            <td><label>Transfer limit:</label></td>
            <td><input type = "text" name="register_limit" placeholder="Limit"><br></td> 
          </tr>
          <tr>
            <td><label>Currency</label></td>
            <td><select id="coin" name="register_currency">
                  <option value="">Select a currency</option>
                    <%if(currency != null) {%>
                      <% for (Currency current : currency){%>
                        <option value="<%= current.getCode() %>"> <%= current.getCode() %>  </option>
                      <% } %>
                    <% } %>
                </select></td><br>    
          </tr>
          <tr>
            <td colspan="3"><center><input type = "submit" value="Register"><br></center></td>
          </tr>
        </table>
        </form>
        
        
        
      </div>
      
      
      
      <div class="register_right"><img id="sign" src="${pageContext.request.contextPath}/resources/images/sign_up.png"></div>
        
    </div>
    
    
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
