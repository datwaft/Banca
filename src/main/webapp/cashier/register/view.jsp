<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="bank.logic.Currency"%>
<%@page import="bank.presentation.cashier.register.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
  Model model = (Model)request.getAttribute("model");
  User user = model.getExist();
  List<Currency> currency = model.getCurrency();
  Map<String,String> errores = (Map<String,String>) request.getAttribute("mistakes");
  bank.presentation.Controller verify = bank.presentation.Controller.getInstance();
%>

<!DOCTYPE html>
<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
     <div class="principal_div">
      <div class="transfer_display register_size">
        <form name="form" action="${pageContext.request.contextPath}/cashier/register/register" method="post">
          <input type = "hidden"  name="register_id_hidden" value="<%= user == null ? "" : model.getId()%>">
            <table>
            <tr>
              <th colspan="3"><center>Register a new account</center><br></th>
            </tr>
            <tr>
              <td>
                <label>ID:</label>
              </td>
              <td class="<%= verify.getSpan(errores) %>">
                <input type = "text" name="register_id" class="<%= verify.validateMap(errores, "register_id") %>" value="<%= model.getId() == "" ? "" : model.getId()%>"  placeholder="ID">
                <span>
                  <%= verify.getTitle(errores,"register_id") %>
                </span>
              </td>
              <td>
              <input type = "submit" value="Verify" formaction="${pageContext.request.contextPath}/cashier/register/verify" >
              <td>
            </tr>
            <tr>
              <td >
                <label>Complete Name: </label>
              </td>
              <td class="<%= verify.getSpan(errores) %>">
                <input type="text"  name="register_name" class="<%= verify.validateMap(errores, "register_name") %>" value="<%= user != null ? user.getName() : "" %>" placeholder="Insert the name" <%= user != null ? "disabled" : "" %>>
                <span><%= verify.getTitle(errores,"register_name") %>
                </span>
              </td>
              <td>
                <input type = "submit" value="Clear" formaction="${pageContext.request.contextPath}/cashier/register/clear" >
              <td>
            </tr>
            <tr>
              <td><label>Telephone:</label></td>
              <td class="<%= verify.getSpan(errores) %>">
                <input type = "text" name="register_cellphone" class="<%= verify.validateMap(errores, "register_cellphone") %>" value="<%= user != null ? user.getCellphone() : "" %>" placeholder="Digite el telefono" <%= user != null ? "disabled" : "" %>>
                <span><%= verify.getTitle(errores,"register_cellphone") %>
                </span>
              </td> 
            </tr>
            <tr>
              <td><label>Transfer limit:</label></td>
              <td class="<%= verify.getSpan(errores) %>"><input type = "text" name="register_limit" class="<%= verify.validateMap(errores, "register_limit") %>" placeholder="Limit"><span><%= verify.getTitle(errores,"register_limit") %></span></td> 
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
       <div class="transfer_display register_size_img"><img id="sign" src="${pageContext.request.contextPath}/resources/images/sign_up.png"></div>
     </div>
    
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
