<%@page import="org.decimal4j.util.DoubleRounder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bank.presentation.client.accounts.Model"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bank.logic.Account"%>

<%
  Model model = (Model)request.getAttribute("model");
  List<Account> accounts = model.getAccounts();
%>

<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div id="acc_table">
      <table class="Acc_tables">
        <thead>
          <tr>
            <th>No.</th><
            <th>Currency</th>
            <th>Balance</th>
            <th>Daily Limit</th>
          </tr>
        </thead>
        <tbody>
          <% for (Account account:accounts) { %>
            <tr onclick="window.location='${pageContext.request.contextPath}/client/accounts/movements/view?account=' + <%= account.getId() %>;">
              <td><%= account.getId() %></td>
              <td><%= account.getCurrency().getName() %></td>
              <td><%= DoubleRounder.round(account.getAmount(), 3) %> <%= account.getCurrency().getCode() %></td>
              <td><%= account.getDailylimit() %> <%= account.getCurrency().getCode() %></td>
            </tr>
          <% } %>
        </tbody>
      </table>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
