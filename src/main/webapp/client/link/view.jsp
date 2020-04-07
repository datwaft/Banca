<%@page import="java.util.Map"%>
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
  
  Map<String,String> errores = (Map<String,String>) request.getAttribute("mistakes");
  bank.presentation.Controller verify = bank.presentation.Controller.getInstance();
%>
<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="transfer_display link_size">
        <form name="form" action="${pageContext.request.contextPath}/client/link/link" method="post">
          <table>
            <tr>
              <th colspan="3"><center>Account linking system</center></th>
            </tr>
            <tr>
              <td>
                <label>Origin Account to link: </label>
              </td>           
              <td class="<%= verify.getSpan(errores) %>">
                <select id="cars" name="origin_link" class="<%= verify.validateMap(errores, "origin_link") %>">
                  <option value="">Select the origin account</option>
                  <%for (Account account : accounts) {%>
                  <option value="<%= account.getId()%>" <%= account.getId() == model.getSelected() ? "selected" : "" %>> <%= account.getId()%></option>
                  <% }%>
                </select>
                <span>
                <%= verify.getTitle(errores,"origin_link") %>
                </span>
              </td>   
            </tr>
            <tr>
              <td><label>Account number to link:</label></td>
              <td class="<%= verify.getSpan(errores) %>">
                  <input type = "text"  name="destination_link" class="<%= verify.validateMap(errores, "destination_link") %>" value=<%= (model.getTo_link() == null ? "" : model.getTo_link().getId())%>>
              <span>
                <%= verify.getTitle(errores,"destination_link") %>
              </span>
              </td>
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
    <%@ include file="/footer.jsp" %>
  </body>
</html>
