<!DOCTYPE html>
<html> 
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div>
      <p>
        Esta es la p�gina de about, una p�gina de prueba, help me!. <br>
        1: "${pageContext.servletContext.contextPath}". <br>
        2: "${pageContext.request.contextPath}". <br>
        Java version: "<%out.print(System.getProperty("java.version"));%>" <br>
      </p>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
