<%@page import="bank.logic.User"%>
<% User usuario = (User)session.getAttribute("user"); %>

<header>
  <div class="banner">
    <img src="${pageContext.request.contextPath}/resources/images/logo.png">
  </div>
  <div class="bar_list">
    <ul class="nav">
      <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/about.jsp">About</a></li>
      <% if (usuario!=null) { %>
        <li><a>Accounts</a>
          <ul>
            <% if (usuario.getClient()) { %>
              <li><a href="${pageContext.request.contextPath}/client/accounts/view">My accounts</a></li>
              <li><a href="${pageContext.request.contextPath}/transfer_client.jsp">Transfer</a></li>
              <li><a href="${pageContext.request.contextPath}/client/link/view">Account linking</a></li>
            <% } else { %>
              <li><a href="${pageContext.request.contextPath}/register.jsp">Sign Up</a></li>
              <li><a href="${pageContext.request.contextPath}/deposit.jsp">Deposit</a></li>
              <li><a href="${pageContext.request.contextPath}/retires_c.jsp">Retire</a></li>
              <li><a href="${pageContext.request.contextPath}/cashier/transfer/view">Transfer</a></li>
            <% } %>
          </ul>
        </li>
      <% } %>
      <% if (usuario!=null) { %>
        <li><a href="${pageContext.request.contextPath}/login/logout">Log Out</a></li>
      <% } else { %>
        <li><a href="${pageContext.request.contextPath}/login/view">Log In</a></li>
      <% } %>
    </ul>
  </div>
</header>