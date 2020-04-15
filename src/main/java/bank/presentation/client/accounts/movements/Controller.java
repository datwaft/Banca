package bank.presentation.client.accounts.movements;

import bank.logic.User;
import bank.logic.model.MovementModel;
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
      case "/client/accounts/movements/view":
        url = this.view(request, user);
        break;
      case "/client/accounts/movements/update":
        url = this.update(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
  }

  private String view(HttpServletRequest request, User user) {
    return viewAction(request, user);
  }

  private String viewAction(HttpServletRequest request, User user) {
    Model model = (Model)request.getAttribute("model");
    
    String account = request.getParameter("account");
    
    try {
      
      model.setAccount(bank.logic.model.AccountModel.getInstance().findById(Integer.valueOf(account)));
      if(model.getAccount().getOwner().getId() == null ? user.getId() != null : !model.getAccount().getOwner().getId().equals(user.getId()))
      {
        System.out.println(model.getAccount().getOwner().getId());
        System.out.println(user.getId());
        throw new Exception("This is not one of your account");
      }
      model.setMovements(MovementModel.getInstance().findByAccount(account, "", ""));

      return "/client/accounts/movements/view.jsp";
    } catch (Exception ex) {

      return "/client/accounts/view";
    }
  }
  
  private String update(HttpServletRequest request) {
    return updateAction(request);
  }

  private String updateAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    
    String account = request.getParameter("account");
    //verificacion de si la cuenta pertenece al usuario logueado xdxdxdxd
    String from = request.getParameter("from");
    String to = request.getParameter("to");
    
    try {
      model.setMovements(MovementModel.getInstance().findByAccount(account, from, to));
      model.setAccount(bank.logic.model.AccountModel.getInstance().findById(Integer.valueOf(account)));
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
