package bank.presentation.client.accounts;

import bank.logic.User;
import bank.logic.model.AccountModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Controller extends HttpServlet {
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    HttpSession session = request.getSession(true);
    User user = (User)session.getAttribute("user");
    
    if (user == null || !user.getClient())
    {
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    request.setAttribute("model", new Model());
    String url = "";
    switch (request.getServletPath()) {
      case "/client/accounts/view":
        url = this.view(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
  }

  private String view(HttpServletRequest request) {
    return viewAction(request);
  }

  private String viewAction(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Model model = (Model)request.getAttribute("model");
    
    User user = (User)session.getAttribute("user");
    
    try {
      model.setAccounts(AccountModel.getInstance().findByOwner(user.getId()));
      return "/client/accounts/view.jsp";
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
