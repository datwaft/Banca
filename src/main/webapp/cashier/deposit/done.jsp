<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <%@ include file="/head.jsp" %>
  <body>
    <%@ include file="/header.jsp" %>
    <form>
      <table>
        <tr>
          <th>Action successfull.</th>
        </tr>
        <tr>
          <th>Press <a href="${pageContext.request.contextPath}/cashier/deposit/view">this</a> to do another deposit.</th>
        </tr>
        <tr>
          <th>Press <a href="${pageContext.request.contextPath}/index.jsp">this</a> to return to the main page.</th>
        </tr>
      </table>
    </form>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
