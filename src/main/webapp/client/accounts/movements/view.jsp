<%@page import="org.decimal4j.util.DoubleRounder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bank.logic.Account"%>
<%@page import="bank.presentation.client.accounts.movements.Model"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bank.logic.Movement"%>

<%
  Model model = (Model)request.getAttribute("model");
  List<Movement> movements = model.getMovements();
  Account account = model.getAccount();
%>

<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div id="date_display">
      <form name="form" method="post"
        action="${pageContext.request.contextPath}/client/accounts/movements/update?account=${pageContext.request.getParameter("account")}">
        <table>
          <tr>
            <td><label>From: </label><input type="date" name="from"></td>
            <td><label>To: </label><input type="date" name="to"></td>
            <td><button type="submit">Filter by date range</button></td>
          </tr>
        </table>
      </form>
      <table class="Acc_tables">
        <thead>
          <tr>
            <th>Time</th>
            <th>Type</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>Amount</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          <%
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
          %>
          <% if (movements.isEmpty()) { %>
            <td colspan="6">There are no movements for this account</td>
          <% } else { %>
            <% for (Movement movement:movements) { %>
              <tr>
                <td><%= formatter.format(movement.getDate()) %></td>
                <td><%= (movement.getOrigin() == null ? "Deposit" : (movement.getDestination() == null ? "Withdrawal" : "Movement")) %></td>
                <td><%= (movement.getOrigin() == null ? "-" : 
                        (movement.getOrigin().equals(account) ? "This account" : movement.getOrigin().getId())) %></td>
                <td><%= (movement.getDestination() == null ? "-" : 
                        (movement.getDestination().equals(account) ? "This account" : movement.getDestination().getId())) %></td>
                <% if ((movement.getOrigin() != null && movement.getDestination() != null) 
                      && !movement.getOrigin().getCurrency().equals(account.getCurrency())) {%>
                  <td><%= DoubleRounder.round(movement.getAmount()/movement.getOrigin().getCurrency().getConversion()
                    *movement.getDestination().getCurrency().getConversion(), 3) %> <%= account.getCurrency().getCode() %></td>
                <% } else { %>
                  <td><%= DoubleRounder.round(movement.getAmount(), 3) %> <%= account.getCurrency().getCode() %></td>
                <% }%>
                <td><%= movement.getDescription() %></td>
              </tr>
            <% } %>
          <% } %>
        </tbody>
      </table>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
