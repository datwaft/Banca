<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%  
  System.out.println(request.getAttribute("last_path"));    
 %>
        
<html>
  <%@ include file="/head.jsp" %>
  <body>
    <%@ include file="/header.jsp" %>
    <div class="transfer_display done_size">
      <form>
        <table>
          <tr>
            <th colspan="2"><IMG src="${pageContext.request.contextPath}/resources/images/isDone.gif" height="320" witdh="320"></th>
          </tr>
          <tr>
            <th colspan="2"><center>¡SUCCESS!</center></th>
          </tr>
          <tr>
            <td colspan="2"> 
              <input type = "hidden" name="path" value="<%= request.getAttribute("last_path") %>">
          <center>
              <button type = "submit" formaction="${pageContext.request.contextPath}/done/go-back">Go back</button>
              <button type = "submit" formaction="${pageContext.request.contextPath}/index.jsp" >Go index</button>
          </center>
          </td>
            <td></td>
          </tr>
        </table>
      </form>
    </div>
    <%@ include file="/footer.jsp" %>
  </body>
</html>
