<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %>
  <body>
    <%@ include file="/header.jsp" %>
    
    <form>
      <table>
        <tr>
          <th>Your action has been done.</th>
        </tr>
        <tr>
          <th>Press <a href="${pageContext.request.contextPath}/cashier/interesent/view">this</a> to credit interests again.</th>
        </tr>
        <tr>
          <th>Press <a href="${pageContext.request.contextPath}/index.jsp">this</a> to return to the main page.</th>
        </tr>
      </table>
    </form>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
