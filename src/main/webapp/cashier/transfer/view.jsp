<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bank.logic.Account"%>
<%@page import="java.util.List"%>
<%@page import="bank.presentation.cashier.transfer.Model"%>
<%@page import="java.util.Map"%>
<%
  Model model = (Model)request.getAttribute("model");
  List<Account> origin_id = model.getOrigin_id();
  List<Account> destination_id = model.getDestination_id();
  Account origin = model.getOrigin();
  Account destination = model.getDestination();
  
  Map<String,String> mistakes = (Map<String,String>) request.getAttribute("mistakes");
  bank.presentation.Controller verify = bank.presentation.Controller.getInstance();
%>

<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="transfer_display cashier_size">
      <form name="form" method="post" action="${pageContext.request.contextPath}/cashier/transfer/transfer" >
        <input type="hidden" name="origin" value="<%= (origin != null ? origin.getId() : "") %>">
        <input type="hidden" name="destination" value="<%= (destination != null ? destination.getId() : "") %>">
        <table>
          <tr>
            <th colspan="5"><center>Source account</center><th>
          </tr>
          <tr>
            <th></th>
            <th colspan="2">Search by ID number</th>
            <th colspan="2">Search by account number</th>
          </tr>
          <tr>
            <td rowspan="2">
              <button type"submit" formaction="${pageContext.request.contextPath}/cashier/transfer/clear-origin"
                <% if (origin == null) out.print("disabled"); %>>Clear</button>
            </td>
            <td class="<%= verify.getSpan(mistakes) %>">
              <input type="text" name="origin-id" placeholder="ID number"  class="<%= verify.validateMap(mistakes, "origin-id") %>">
              <% if (!verify.getTitle(mistakes, "origin-id").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "origin-id") %></span>
              <% } %>
            </td>
            <td>
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/transfer/load-origin-id"
                <% if (origin != null) out.print("disabled"); %>>Load</button>
            </td>
            <td rowspan="2" class="<%= verify.getSpan(mistakes) %>">
              <input type="text" name="origin-account" placeholder="Account number" class="<%= verify.validateMap(mistakes, "origin-account") %>"
              <%if (origin != null) out.print("value=\"" + origin.getId() + "\"" ); %> <% if (origin != null) out.print("disabled"); %>>
              <% if (!verify.getTitle(mistakes, "origin-account").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "origin-account") %></span>
              <% } %>
            </td>
            <td rowspan="2">
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/transfer/validate-origin-account"
                <% if (origin != null) out.print("disabled"); %>>Validate</button>
            </td>
          </tr>
          <tr>
            <td class="<%= verify.getSpan(mistakes) %>">
              <select name="origin-id-account" class="<%= verify.validateMap(mistakes, "origin-id-account") %>"
                <% if (origin != null) out.print("disabled"); %>>
                <% if (origin == null) { %>
                  <% if (origin_id.isEmpty()) { %>
                    <option>Please select the source account</option>
                  <% } %>
                  <% for (Account account:origin_id) { %>
                    <option><%= account.getId() %></option>               
                  <% } %>
                <% } else { %>
                  <option><%= origin.getId() %></option>
                <% } %>
              </select>
              <% if (!verify.getTitle(mistakes, "origin-id-account").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "origin-id-account") %></span>
              <% } %>
            </td>
            <td>
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/transfer/validate-origin-id"
                <% if (origin != null) out.print("disabled"); %>>Validate</button>
            </td>
          </tr>
          <tr>
            <th colspan="5"><center>Destination account</center><th>
          </tr>
          <tr>
            <th></th>
            <th colspan="2">Search by ID number</th>
            <th colspan="2">Search by account number</th>
          </tr>
          <tr>
            <td rowspan="2">
              <button type"submit" formaction="${pageContext.request.contextPath}/cashier/transfer/clear-destination"
                <% if (destination == null) out.print("disabled"); %>>Clear</button>
            </td>
            <td class="<%= verify.getSpan(mistakes) %>">
              <input type="text" name="destination-id" placeholder="ID number" class="<%= verify.validateMap(mistakes, "destination-id") %>">
              <% if (!verify.getTitle(mistakes, "destination-id").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "destination-id") %></span>
              <% } %>
            </td>
            <td>
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/transfer/load-destination-id"
                <% if (destination != null) out.print("disabled"); %>>Load</button> </td>
            <td rowspan="2" class="<%= verify.getSpan(mistakes) %>">
              <input type="text" name="destination-account" placeholder="Account number"
                class="<%= verify.validateMap(mistakes, "destination-account") %>" <%if (destination != null) 
                out.print("value=\"" + destination.getId()+ "\"" ); %> <% if (destination != null) out.print("disabled"); %>>
              <% if (!verify.getTitle(mistakes, "destination-account").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "destination-account") %></span>
              <% } %>
            </td>
            <td rowspan="2">
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/transfer/validate-destination-account"
                <% if (destination != null) out.print("disabled"); %>>Validate</button>
            </td>
          </tr>
          <tr>
            <td class="<%= verify.getSpan(mistakes) %>">
              <select name="destination-id-account" class="<%= verify.validateMap(mistakes, "destination-id-account") %>"
                <% if (destination != null) out.print("disabled"); %>>
                <% if (destination == null) { %>
                  <% if (destination_id.isEmpty()) { %>
                    <option>Please select the destination account</option>
                  <% } %>
                  <% for (Account account:destination_id) { %>
                    <option><%= account.getId() %></option>
                  <% } %>
                <% } else { %>
                  <option><%= destination.getId() %></option>
                <% } %>
              </select>
              <% if (!verify.getTitle(mistakes, "destination-id-account").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "destination-id-account") %></span>
              <% } %>
            </td>
            <td>
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/transfer/validate-destination-id"
                <% if (destination != null) out.print("disabled"); %>>Validate</button>
            </td>
          </tr>
          <tr>
            <th colspan="5"><center>Transfer information</center></th>
          </tr>
          <tr>
            <td></td>
            <td>Amount to transfer</td>
            <td colspan="2"  class="<%= verify.getSpan(mistakes) %>">
              <input name="amount" type="number" placeholder="10000" class="<%= verify.validateMap(mistakes, "amount") %>">
              <% if (!verify.getTitle(mistakes, "amount").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "amount") %></span>
              <% } %>
            </td>
            <td>
              <input name="currency" type="text" size="3" placeholder="$$$"
                value="<% if (origin != null) out.print(origin.getCurrency().getCode()); %>" readonly>
            </td>
          </tr>
          <tr>
            <td></td>
            <td>Description of the transfer</td>
            <td colspan="3" class="<%= verify.getSpan(mistakes) %>">
              <input name="description" type="text" placeholder="A very nice description" class="<%= verify.validateMap(mistakes, "amount") %>">
              <% if (!verify.getTitle(mistakes, "description").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "description") %></span>
              <% } %>
            </td>
          </tr>
          <tr>
            <th colspan="5"><center><button type="submit">Submit</button></center></th>
          </tr>
        </table>
      </form>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
