package bank.presentation.cashier.transfer;

import bank.logic.Account;
import bank.logic.Movement;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("model", new Model());
    Model model = (Model)request.getAttribute("model");
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    String origin = request.getParameter("origin");
    String destination = request.getParameter("destination");
    if (origin != null && !request.getParameter("origin").isEmpty()) {
      model.setOrigin(dao.findById(Integer.valueOf(request.getParameter("origin"))));
    }
    if (destination != null && !request.getParameter("destination").isEmpty()) {
      model.setDestination(dao.findById(Integer.valueOf(request.getParameter("destination"))));
    }
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
      case "/cashier/transfer/validate-origin-account":
        url = this.validateOriginAccount(request);
        break;
      case "/cashier/transfer/validate-destination-account":
        url = this.validateDestinationAccount(request);
        break;
      case "/cashier/transfer/clear-origin":
        url = this.clearOrigin(request);
        break;
      case "/cashier/transfer/clear-destination":
        url = this.clearDestination(request);
        break;
      case "/cashier/transfer/transfer":
        url = this.transfer(request);
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
  
  private String validateOriginAccount(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    try {
      model.setOrigin(dao.findById(Integer.valueOf(request.getParameter("origin-account"))));
    } catch (Exception ex) { }
    return "/cashier/transfer/view.jsp";
  }
  
  private String validateDestinationAccount(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    try {
      model.setDestination(dao.findById(Integer.valueOf(request.getParameter("destination-account"))));
    } catch (Exception ex) { }
    return "/cashier/transfer/view.jsp";
  }
  
  private String clearOrigin(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    model.setOrigin(null);
    return "/cashier/transfer/view.jsp";
  }

  private String clearDestination(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    model.setDestination(null);
    return "/cashier/transfer/view.jsp";
  }
  
  private String transfer(HttpServletRequest request) {
    Map<String, String> mistakes = this.validate(request);
    if (mistakes.isEmpty()) {
      return this.transferAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/transfer/view.jsp";
    }
  }
  
  private String transferAction(HttpServletRequest request) {
    Movement movement = new Movement();
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    try {
      Account origin = dao.findById(Integer.valueOf(request.getParameter("origin")));
      Account destination = dao.findById(Integer.valueOf(request.getParameter("destination")));
      Double amount = Double.valueOf(request.getParameter("amount"));
      String description = request.getParameter("description");
      Date date = new Date();
      movement.setOrigin(origin);
      movement.setDestination(destination);
      movement.setAmount(amount);
      movement.setDescription(description);
      movement.setDate(date);
      
      bank.logic.model.MovementModel.getInstance().create(movement);
      origin.setAmount(origin.getAmount() - amount);
      destination.setAmount(destination.getAmount() + amount/origin.getCurrency().getConversion()*destination.getCurrency().getConversion());
      dao.edit(origin);
      dao.edit(destination);
    } catch (Exception ex) {
      return "/error.jsp";
    }
    return "/cashier/transfer/done.jsp";
  }
  
  private Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    if (request.getParameter("origin").isEmpty())
      mistakes.put("origin", "The source is required");
    if (request.getParameter("destination").isEmpty())
      mistakes.put("destination", "The destination is required");
    if (request.getParameter("amount").isEmpty())
      mistakes.put("amount", "The amount is required");
    if (request.getParameter("description").isEmpty())
      mistakes.put("description", "The description is required");
    Account origin = dao.findById(Integer.valueOf(request.getParameter("origin")));
    Account destination = dao.findById(Integer.valueOf(request.getParameter("destination")));
    if (origin == null)
      mistakes.put("origin", "The source is invalid");
    if (destination == null)
      mistakes.put("destination", "The destination is invalid");
    if (origin != null) {
      Double amount = Double.valueOf(request.getParameter("amount"))/origin.getCurrency().getConversion();
      if (origin.getAmount() < amount)
        mistakes.put("amount", "The source account doesn't have enough money");
    }
    return mistakes;
  }
          
  public static String isErroneous(String field, Map<String,String> mistakes) {
    if ((mistakes != null) && (mistakes.get(field) != null))
      return "is-invalid";
    else
      return "";
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
