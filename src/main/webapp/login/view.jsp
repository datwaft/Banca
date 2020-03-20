<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="bank.presentation.login.Model"%>
<%@page import="bank.presentation.login.Controller"%>

<% Model model= (Model)request.getAttribute("model");%>
<% Map<String,String> mistakes = (Map<String,String>)request.getAttribute("mistakes");%>
<% Map<String,String[]> form = (mistakes == null) ? Controller.getForm(model) : request.getParameterMap();%>

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
              <input type="text" placeholder="User ID" name="id" value="<%=form.get("id")%>"
                title="<%=Controller.title("id", mistakes)%>" class="<%=Controller.isErroneous("id", mistakes)%>">
            </td>
          </tr>
          <tr>
            <td>password</td>
            <td>
              <input type="password" placeholder="User password" name="password"
                value="<%=form.get("password")%>" title="<%=Controller.title("password", mistakes)%>"
                class="<%=Controller.isErroneous("password", mistakes)%>">
            </td>
          </tr>
          <tr>
            <th colspan="2">
              <button>Log In</button>
            </th>
          </tr>
        </table>
      </form>
    </div>
    <div class="desc"><p>Creado por David Guevara y Mario Arguello. 2020 ©</p></div>
  </body>
</html>