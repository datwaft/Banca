<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bank.logic.Account"%>
<%@page import="java.util.List"%>
<%@page import="bank.presentation.cashier.deposit.Model"%>
<%
  Model model = (Model)request.getAttribute("model");
  List<Account> accountlist = model.getAccountList();
  Account account = model.getAccount();
%>

<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div>
      <form name="form" method="post" action="${pageContext.request.contextPath}/cashier/deposit/deposit" >
        <input type="hidden" name="account" value="<%= (account != null ? account.getId() : "") %>">
        <table>
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
              <button type"submit" formaction="${pageContext.request.contextPath}/cashier/deposit/clear"
                <% if (account == null) out.print("disabled"); %>>Clear</button>
            </td>
            <td><input type="text" name="id" placeholder="ID number"></td>
            <td>
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/deposit/load"
                <% if (account != null) out.print("disabled"); %>>Load</button>
            </td>
            <td rowspan="2"><input type="text" name="account-input" placeholder="Account number"
              <%if (account != null) out.print("value=\"" + account.getId() + "\"" ); %> <% if (account != null) out.print("disabled"); %>></td>
            <td rowspan="2">
              <button type=submit formaction="${pageContext.request.contextPath}/cashier/deposit/validate-account"
                <% if (account != null) out.print("disabled"); %>>Validate</button>
            </td>
          </tr>
          <tr>
            <td>
              <select name="account-select" <% if (account != null) out.print("disabled"); %>>
                <% if (account == null) { %>
                  <option>Please select the source account</option>
                  <% for (Account a : accountlist) { %>
                    <option><%= a.getId() %></option>               
                  <% } %>
                <% } else { %>
                  <option><%= account.getId() %></option>
                <% } %>
              </select>
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
            <td colspan="3">
              <input name="amount" type="number" placeholder="10000">
              <input name="currency" type="text" size="3" placeholder="$$$"
                value="<% if (account != null) out.print(account.getCurrency().getCode()); %>" readonly>
            </td>
          </tr>
          <tr>
            <td></td>
            <td>Name of the bailor</td>
            <td colspan="3"><input name="name" type="text" placeholder="The name please"></td>
          </tr>
          <tr>
            <td></td>
            <td>Description of the deposit</td>
            <td colspan="3"><input name="description" type="text" placeholder="A very nice description"></td>
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
