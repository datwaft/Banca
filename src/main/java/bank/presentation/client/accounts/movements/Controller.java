package bank.presentation.client.accounts.movements;

import bank.logic.model.MovementModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("model", new Model());
    String url = "";
    switch (request.getServletPath()) {
      case "/client/accounts/movements/view":
        url = this.view(request);
        break;
      case "/client/accounts/movements/update":
        url = this.update(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
  }

  private String view(HttpServletRequest request) {
    return viewAction(request);
  }

  private String viewAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    
    String account = request.getParameter("account");
    
    try {
      model.setMovements(MovementModel.getInstance().findByAccount(account, "", ""));
      return "/client/accounts/movements/view.jsp";
    } catch (Exception ex) {
      return "";
    }
  }
  
  private String update(HttpServletRequest request) {
    return updateAction(request);
  }

  private String updateAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    
    String account = request.getParameter("account");
    
    String from = request.getParameter("from");
    String to = request.getParameter("to");
    
    try {
      model.setMovements(MovementModel.getInstance().findByAccount(account, from, to));
      return "/client/accounts/movements/view.jsp";
    } catch (Exception ex) {
      return "";
    }
  }
  
  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
