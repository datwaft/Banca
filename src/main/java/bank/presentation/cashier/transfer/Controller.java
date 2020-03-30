package bank.presentation.cashier.transfer;

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
      case "/cashier/transfer/view":
        url = this.view(request);
        break;
        case "/cashier/transfer/load-origin-id":
        url = this.loadOrigin(request);
        break;
      case "/cashier/transfer/load-destination-id":
        url = this.loadDestination(request);
        break;
      case "/cashier/transfer/validate-origin-id":
        url = this.validateOriginId(request);
        break;
      case "/cashier/transfer/validate-destination-id":
        url = this.validateDestinationId(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
  } 

  private String view(HttpServletRequest request) {
    return "/cashier/transfer/view.jsp";
  }
  
  private String loadOrigin(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    model.setOrigin_id(dao.findByOwner(request.getParameter("origin-id")));
    return "/cashier/transfer/view.jsp";
  }
  
  private String loadDestination(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    model.setDestination_id(dao.findByOwner(request.getParameter("destination-id")));
    return "/cashier/transfer/view.jsp";
  }
  
  private String validateOriginId(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    try {
      model.setOrigin(dao.findById(Integer.valueOf(request.getParameter("origin-id-account"))));
    } catch (Exception ex) { }
    return "/cashier/transfer/view.jsp";
  }
  
  private String validateDestinationId(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    try {
      model.setDestination(dao.findById(Integer.valueOf(request.getParameter("destination-id-account"))));
    } catch (Exception ex) { }
    return "/cashier/transfer/view.jsp";
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /** 
   * Handles the HTTP <code>GET</code> method.
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
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
      return "Short description";
  }// </editor-fold>

}
