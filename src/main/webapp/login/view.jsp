<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="bank.presentation.login.Model"%>
<%@page import="bank.presentation.login.Controller"%>

<% Model model= (Model)request.getAttribute("model");%>
<% Map<String,String> mistakes = (Map<String,String>)request.getAttribute("mistakes");%>

<html lang="es">
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="body">
      <form name="form" action="${pageContext.request.contextPath}/login/login" method="post">
        <table>
          <tr>
            <th colspan="2">
              Please, enter your login information
            </th>
          </tr>
          <tr>
            <td>username</td>
            <td>
              <input type="text" placeholder="User ID" name="id"
                class="<%=Controller.isErroneous("id", mistakes)%>" autocomplete="off">
            </td>
          </tr>
          <tr>
            <td>password</td>
            <td>
              <input type="password" placeholder="User password" name="password"
                class="<%=Controller.isErroneous("password", mistakes)%>" autocomplete="off">
            </td>
          </tr>
          <tr>
             <td><button type="submit" name="first" value="FirstServlet">Log In as client</button></td>
             <td><button type="submit" name="second" value="SecondServlet">Log In as cashier</button></td>
          </tr>
        </table>
      </form>
    </div>
    <div class="desc"><p>Creado por David Guevara y Mario Arguello. 2020 ©</p></div>
  </body>
</html>