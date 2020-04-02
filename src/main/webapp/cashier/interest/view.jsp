<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div>
      <table>
        <tr>
          <th>Press this button to credit interest to every account in the system.</th>
        </tr>
        <tr>
          <th><button onclick="window.location.href='${pageContext.request.contextPath}/cashier/interest/credit'">Credit Interest</button></th>
        </tr>
      </table>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
