﻿<html lang="es"> 
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
    <div class="body"></div>
    <div class="desc"><p>Creado por David Guevara y Mario Arguello. 2020 ©</p></div>
  </body>
</html>
