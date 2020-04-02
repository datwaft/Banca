<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bank.logic.Account"%>
<%@page import="java.util.List"%>
<%@page import="bank.presentation.client.link.Model"%>
<%
  Model model = (Model)request.getAttribute("model");
  List<Account> accounts = model.getAccounts();
  
  User destination = null;
  if(model.getTo_link() != null )
  {
  destination = model.getTo_link().getOwner();
  }
  System.out.print("la wea");
%>

<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="link_display">
      <div class="title">Accounts linking:</div>
      <div class="container">
        <form name="form" action="${pageContext.request.contextPath}/client/link/link" method="post">
          <table>
            <td><label>Origin Account to link: </label></td>           
            <td><select id="cars" name="origin_link">
                <%for (Account account : accounts) {%>
                <option value="<%= account.getId()%>" <%= account.getId() == model.getSelected() ? "selected" : "" %>> <%= account.getId()%></option>
                <% }%>
              </select></td>           
            <tr>
              <td><label>Account number to link:</label></td>
              <td><input name="destination_link" type = "text" value=<%= (model.getTo_link() == null ? "" : model.getTo_link().getId())%> ></td>
              <td><input name="verify" type = "submit" value="Verify" align="center" formaction="${pageContext.request.contextPath}/client/link/verify"><td>
            </tr>
            <tr>
              <td><label>Complete name: </label></td>
              <td><input type="text" disabled value="<%= (destination == null ? "" : destination.getName())%>"></td>
            </tr>
            <tr>
              <td><label>Id:</label></td>
              <td><input type = "text" placeholder="Cedula" disabled value="<%= (destination == null ? "" : destination.getId())%>"></td> 
            </tr>
            <tr>
              <td colspan="3" style="text-align: center;">
                <input name="link" type = "submit" value="Vincular" <%= (model.getTo_link()== null ? "disabled" : "")%> >
                <input name="clear" type = "submit" value="clear" align="center" formaction="${pageContext.request.contextPath}/client/link/clear">
              </td>
            </tr>
          </table>
        </form>
      </div>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
