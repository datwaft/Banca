<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="transfer_display interest_size">
      <table>
        <tr>
          <th>Press this button to credit interest to every account in the system.</th>
        </tr>
        <tr>
          <td><center><button onclick="window.location.href='${pageContext.request.contextPath}/cashier/interest/credit'">Credit Interest</button></center></td>
        </tr>
      </table>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
