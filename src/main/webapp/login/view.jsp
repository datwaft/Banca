<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="bank.presentation.login.Model"%>
<%@page import="bank.presentation.login.Controller"%>

<% Model model= (Model)request.getAttribute("model");%>
<% 
  Map<String,String> errores = (Map<String,String>) request.getAttribute("mistakes");
  bank.presentation.Controller verify = bank.presentation.Controller.getInstance();
%>

<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="transfer_display login_size">
      <form name="form" action="${pageContext.request.contextPath}/login/login" method="post">
        <table >
          <tr>
            <th colspan="2">
              <center>Please, enter your login information</center>
            </th>
          </tr>
          <tr>
            <td>Username</td>
            <td class="<%= verify.getSpan(errores) %>">
              <input type="text" placeholder="User ID" name="id" class="<%= verify.validateMap(errores, "id") %>" autocomplete="off">
              <span>
                <%= verify.getTitle(errores,"id") %>
              </span>
            </td>
          </tr>
          <tr>
            <td>Password</td>
            <td class="<%= verify.getSpan(errores) %>">
              <input type="password" placeholder="User password" name="password" class="<%= verify.validateMap(errores, "password") %>" autocomplete="off">
              <span>
                <%= verify.getTitle(errores,"password") %>
              </span>
            </td>
          </tr>
          <tr>
             <td><button type="submit" name="first" value="FirstServlet">Log In as client</button></td>
             <td><button type="submit" name="second" value="SecondServlet">Log In as cashier</button></td>
          </tr>
        </table>
      </form>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>