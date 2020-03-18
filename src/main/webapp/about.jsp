<html lang="es"> 
  <%@ include file="/head.jsp" %> 
  <body>
    <%@ include file="/header.jsp" %>
    <div class="body">
      <p>
        Esta es la página de about, una página de prueba, help me!.
        <br>
        1: "${pageContext.servletContext.contextPath}".
        2: "${pageContext.request.contextPath}".
      </p>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
