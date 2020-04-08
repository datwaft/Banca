<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bank.logic.Account"%>
<%@page import="java.util.List"%>
<%@page import="bank.presentation.cashier.deposit.Model"%>
<%@page import="java.util.Map"%>
<%
  Model model = (Model)request.getAttribute("model");
  List<Account> accountlist = model.getAccountList();
  Account account = model.getAccount();
  
  Map<String,String> mistakes = (Map<String,String>) request.getAttribute("mistakes");
  bank.presentation.Controller verify = bank.presentation.Controller.getInstance();
%>

<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="transfer_display deposit_size">
      <form name="form" method="post" action="${pageContext.request.contextPath}/cashier/deposit/deposit" >
        <input type="hidden" name="account" value="<%= (account != null ? account.getId() : "") %>">
        <table>
          <tr>
            <th colspan="5"><center>Destination account</center></th>
          </tr>
          <tr>
            <th></th>
            <th colspan="2">Search by ID number</th>
            <th colspan="2">Search by account number</th>
          </tr>
          <tr>
            <td rowspan="2">
              <button type"submit" formaction="${pageContext.request.contextPath}/cashier/deposit/clear"
                <% if (account == null) out.print("disabled"); %>>Clear</button>
            </td>
            <td class="<%= verify.getSpan(mistakes) %>">
              <input type="text" name="id" placeholder="ID number" class="<%= verify.validateMap(mistakes, "id") %>">
              <% if (!verify.getTitle(mistakes, "id").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "id") %></span>
              <% } %>
            </td>
            <td>
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/deposit/load"
                <% if (account != null) out.print("disabled"); %>>Load</button>
            </td>
            <td rowspan="2" class="<%= verify.getSpan(mistakes) %>">
              <input type="text" name="account-input" placeholder="Account number" class="<%= verify.validateMap(mistakes, "account-input") %>"
                <%if (account != null) out.print("value=\"" + account.getId() + "\"" ); %> <% if (account != null) out.print("disabled"); %>>
              <% if (!verify.getTitle(mistakes, "account-input").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "account-input") %></span>
              <% } %>
            </td>
            <td rowspan="2">
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/deposit/validate-account"
                <% if (account != null) out.print("disabled"); %>>Validate</button>
            </td>
          </tr>
          <tr>
            <td class="<%= verify.getSpan(mistakes) %>">
              <select name="account-select" class="<%= verify.validateMap(mistakes, "account-select") %>"
                <% if (account != null) out.print("disabled"); %>>
                <% if (account == null) { %>
                  <% if (accountlist.isEmpty()) { %>
                    <option>Please select the destination account</option>
                  <% } %>
                  <% for (Account a : accountlist) { %>
                    <option><%= a.getId() %></option>               
                  <% } %>
                <% } else { %>
                  <option><%= account.getId() %></option>
                <% } %>
              </select>
              <% if (!verify.getTitle(mistakes, "account-select").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "account-select") %></span>
              <% } %>
            </td>
            <td>
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/deposit/validate-id"
                <% if (account != null) out.print("disabled"); %>>Validate</button>
            </td>
          </tr>
          <tr>
            <th colspan="5"><center>Deposit information</center></th>
          </tr>
          <tr>
            <td></td>
            <td>Amount to deposit</td>
            <td colspan="2"  class="<%= verify.getSpan(mistakes) %>">
              <input name="amount" type="number" placeholder="10000" class="<%= verify.validateMap(mistakes, "amount") %>">
              <% if (!verify.getTitle(mistakes, "amount").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "amount") %></span>
              <% } %>
            </td>
            <td>
              <input name="currency" type="text" size="3" placeholder="$$$"
                value="<% if (account != null) out.print(account.getCurrency().getCode()); %>" readonly>
            </td>
          </tr>
          <tr>
            <td></td>
            <td>Name of the bailor</td>
            <td colspan="3" class="<%= verify.getSpan(mistakes) %>">
              <input name="name" type="text" placeholder="The name please"  class="<%= verify.validateMap(mistakes, "name") %>">
              <% if (!verify.getTitle(mistakes, "name").isEmpty()) { %>
                <span><%= verify.getTitle(mistakes, "name") %></span>
              <% } %>
            </td>
          </tr>
          <tr>
            <td></td>
            <td>Description of the deposit</td>
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
