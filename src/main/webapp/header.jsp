<%@page import="bank.logic.User"%>
<% User usuario=  (User) session.getAttribute("user");  %>

<header>
  <div class="banner">
    <img src="${pageContext.request.contextPath}/resources/images/logo.png">
  </div>
  <div class="bar_list">
    <ul class="nav">
      <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/about.jsp">About</a></li>
      <li><a href="">Accounts</a>
        <ul>
          <% if (usuario!=null){ %>
          <li><a href="${pageContext.request.contextPath}/accounts.jsp">My accounts</a>
          <li><a href="${pageContext.request.contextPath}/transfers.jsp">Transfer</a></li>
          <li><a href="${pageContext.request.contextPath}/movements.jsp">Account moves</a></li>
          <li><a href="${pageContext.request.contextPath}/linking.jsp">Account linking</a></li>
          <li><a href="${pageContext.request.contextPath}/register.jsp">Sign Up</a></li>
          <li><a href="${pageContext.request.contextPath}/retires.jsp">Retire/Deposit</a></li>
          <li><a href="${pageContext.request.contextPath}/transfers.jsp">Transfer</a></li>
          <% } %>
        </ul>
      </li>
      <% if (usuario!=null){ %>
      <li><a href="${pageContext.request.contextPath}/login/logout">Log out</a></li>
      <% }else{ %>
      <li><a href="${pageContext.request.contextPath}/login/view">Log in</a></li>
      <% } %>
    </ul>
  </div>
</header>
