package bank.presentation.cashier.deposit;

import bank.logic.Account;
import bank.logic.Movement;
import bank.logic.model.AccountModel;
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
    AccountModel dao = AccountModel.getInstance();
    String account = request.getParameter("account");
    if (account != null && !account.isEmpty()) {
      model.setAccount(dao.findById(Integer.valueOf(account)));
    }
    String url = "";
    switch (request.getServletPath()) {
      case "/cashier/deposit/view":
        url = this.view(request);
        break;
      case "/cashier/deposit/load":
        url = this.load(request);
        break;
      case "/cashier/deposit/validate-id":
        url = this.validateId(request);
        break;
      case "/cashier/deposit/validate-account":
        url = this.validateAccount(request);
        break;
      case "/cashier/deposit/clear":
        url = this.clear(request);
        break;
      case "/cashier/deposit/deposit":
        url = this.deposit(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
  }

  public static String isErroneous(String field, Map<String,String> mistakes) {
    if ((mistakes != null) && (mistakes.get(field) != null))
      return "is-invalid";
    else
      return "";
  }
  
  private String view(HttpServletRequest request) {
    return "/cashier/deposit/view.jsp";
  }

  private String load(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    model.setAccountList(dao.findByOwner(request.getParameter("id")));
    return "/cashier/deposit/view.jsp";
  }

  private String validateId(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    try {
      model.setAccount(dao.findById(Integer.valueOf(request.getParameter("account-select"))));
    } catch (Exception ex) { }
    return "/cashier/deposit/view.jsp";
  }

  private String validateAccount(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    try {
      model.setAccount(dao.findById(Integer.valueOf(request.getParameter("account-input"))));
    } catch (Exception ex) { }
    return "/cashier/deposit/view.jsp";
  }

  private String clear(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    model.setAccount(null);
    return "/cashier/deposit/view.jsp";
  }

  private String deposit(HttpServletRequest request) {
    Map<String, String> mistakes = this.validate(request);
    if (mistakes.isEmpty()) {
      return this.depositAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/deposit/view.jsp";
    }
  }

  private String depositAction(HttpServletRequest request) {
    Movement movement = new Movement();
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    try {
      Account account = dao.findById(Integer.valueOf(request.getParameter("account")));
      Double amount = Double.valueOf(request.getParameter("amount"))/account.getCurrency().getConversion();
      String name = request.getParameter("name");
      String description = request.getParameter("description");
      Date date = new Date();
      movement.setOrigin(null);
      movement.setDestination(account);
      movement.setAmount(amount);
      movement.setDescription(name + ": " + description);
      movement.setDate(date);
      
      bank.logic.model.MovementModel.getInstance().create(movement);
      account.setAmount(account.getAmount() + amount);
      dao.edit(account);
    } catch (Exception ex) {
      return "/error.jsp";
    }
    return "/cashier/deposit/done.jsp";
  }
  
  private Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    if (request.getParameter("account").isEmpty())
      mistakes.put("account", "The account is required");
    if (request.getParameter("amount").isEmpty())
      mistakes.put("amount", "The amount is required");
    if (request.getParameter("name").isEmpty())
      mistakes.put("name", "The name is required");
    if (request.getParameter("description").isEmpty())
      mistakes.put("description", "The description is required");
    Account account = dao.findById(Integer.valueOf(request.getParameter("account")));
    if (account == null)
      mistakes.put("destination", "The account is invalid");
    return mistakes;
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
