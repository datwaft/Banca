package bank.presentation.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet
{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setAttribute("model", new Model());
    String url = "";
    switch(request.getServletPath())
    {
      case "/login/view":
        url = this.view(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
  }
  
  public String view(HttpServletRequest request)
  {
    Model model = (Model)request.getAttribute("model");
    model.getCurrent().setId("");
    model.getCurrent().setPassword("");
    return "/login/view.jsp"; 
  }
  
  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   Handles the HTTP <code>GET</code> method.
   @param request servlet request
   @param response servlet response
   @throws ServletException if a servlet-specific error occurs
   @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }

  /**
   Handles the HTTP <code>POST</code> method.
   @param request servlet request
   @param response servlet response
   @throws ServletException if a servlet-specific error occurs
   @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }

  /**
   Returns a short description of the servlet.
   @return a String containing servlet description
   */
  @Override
  public String getServletInfo()
  {
    return "Short description";
  }// </editor-fold>

}
