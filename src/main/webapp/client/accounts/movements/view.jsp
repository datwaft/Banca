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
%>

<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div id="move_display">
      <form name="form" method="post"
        action="${pageContext.request.contextPath}/client/accounts/movements/update?account=${pageContext.request.getParameter("account")}">
        <label>From: </label><input type="date" name="from">
        <label>To: </label><input type="date" name="to">
        <button type="submit">Filter by date range</button>
      </form>
      <table class="move_table">
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
                <td><%= (movement.getOrigin() == null ? "-" : movement.getOrigin().getId()) %></td>
                <td><%= (movement.getDestination() == null ? "-" : movement.getDestination().getId()) %></td>
                <td><%= movement.getAmount() %></td>
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
