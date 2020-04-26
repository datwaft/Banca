<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bank.logic.Link"%>
<%@page import="java.util.List"%>
<%@page import="bank.logic.Account"%>
<%@page import="bank.presentation.client.transfer.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
  Model model = (Model) request.getAttribute("model");
  List<Account> origin_accs = model.getOrigin_accounts();
  List<Link> destination_accs = model.getDestination_accounts();
  Map<String,String> errores = (Map<String,String>) request.getAttribute("mistakes");
  bank.presentation.Controller verify = bank.presentation.Controller.getInstance();
%>

<!DOCTYPE html>
<html>
    <%@ include file="/head.jsp" %> 
    <body>
    <%@ include file="/header.jsp" %>
    <div class="transfer_display client_size">
      <form name="form" method="post" action="${pageContext.request.contextPath}/client/transfer/transfer">
        <input name="origin_hiden" type="hidden" value="<%= model.getSelected() %>">
        <table>
          <tr>
            <th colspan="3">
              <center>Origin account information</center>
            </th>
          </tr><br>
          <tr>
            <td><label>Origin account: </label></td>
            <td class="<%= verify.getSpan(errores) %>">
              <select name="trans_origin_accounts" class="<%= verify.validateMap(errores, "trans_origin_accounts") %>"  <%= destination_accs == null ? "" : "disabled" %> value="<%= destination_accs == null ? "" : model.getSelected() %>" >
                <% if (origin_accs != null) {%>
                  <option value="">Select a origin account</option>
                  <% for (Account account:origin_accs){ %>
                    <option value="<%= account.getId()%>" <%= account.getId() == model.getSelected() ? "selected" : "" %> ><%= account.getId()%></option>
                  <% } %>
                <% } %>
              </select>
              <span>
                <%= verify.getTitle(errores,"trans_origin_accounts") %>
              </span>
            </td>
            <td>
              <input type = "submit" value="Search" <%= destination_accs == null ? "" : "disabled" %> formaction="${pageContext.request.contextPath}/client/transfer/search">
              <input type = "submit" value="Clear" <%= destination_accs == null ? "disabled" : "" %> formaction="${pageContext.request.contextPath}/client/transfer/clear">
            </td>
          </tr>
          <tr>
            <th colspan="3">
              <center>Destination account information</center>
            </th>
          </tr>
          <tr>
            <td><label>Destination Account: </label></td>
           <td class="<%= verify.getSpan(errores) %>">
              <select name="trans_destination_accounts" class="<%= verify.validateMap(errores, "trans_destination_accounts") %>" <%= destination_accs == null ? "disabled" : "" %>>
              <% if (destination_accs != null) {%>
                <option value="">Select a destination account</option>
                  <% for (Link link:destination_accs){ %>
                  <option value="<%= link.getLinkedAccount().getId() %>"><%= link.getLinkedAccount().getId() %> <%= link.getLinkedAccount().getOwner().getName()  %></option>
                <% } %>
              <% } %>
            </select>
            <span>
                <%= verify.getTitle(errores,"trans_destination_accounts") %>
            </span>
           </td>
          </tr>
          <tr>
            <th colspan="3">
              <center>Transfer information</center>
            </th>
          </tr>
          <tr>
            <td><label>Ammount to deposit:</label></td>
            <td class="<%= verify.getSpan(errores) %>">
              <input name="trans_ammount" class="<%= verify.validateMap(errores, "trans_ammount") %>" type = "text" placeholder="Amount">
              <input name="currency" type="text" size="3" placeholder="$$$"
                value="<% if (model.getOrigin() != null) out.print(model.getOrigin().getCurrency().getCode()); %>" readonly>
              <span>
                <%= verify.getTitle(errores,"trans_ammount") %>
              </span>
            </td>
          </tr>
          <tr>
            <td><label>Description:</label></td>
            <td class="<%= verify.getSpan(errores) %>">
              <input name="trans_description" class="<%= verify.validateMap(errores, "trans_description") %>" type = "text" placeholder="Description">
              <span>
                <%= verify.getTitle(errores,"trans_description") %>
              </span>
            </td>
          </tr>
          <tr>
            <th colspan="3">
          <center><input type = "submit" value="Transfer" <%= destination_accs == null || destination_accs.isEmpty() ? "disabled":"" %>></center>
            </th>
          </tr>
        </table>
      </form>
    </div>
    <%@ include file="/footer.jsp" %>
    </body>
</html> 
