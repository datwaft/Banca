package bank.presentation.cashier.transfer;

import bank.logic.Account;
import bank.logic.Movement;
import bank.logic.User;
import bank.logic.model.AccountModel;
import bank.logic.model.MovementModel;
import bank.logic.model.UserModel;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Controller extends HttpServlet {
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    User user = (User)session.getAttribute("user");
    
    if (user == null || !user.getCashier())
    {
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    request.setAttribute("model", new Model());
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
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
    Map<String, String> mistakes = this.mistakesLoadOrigin(request);
    if (mistakes.isEmpty()) {
      return this.loadOriginAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/transfer/view.jsp";
    }
  }
  
  private String loadOriginAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    model.setOrigin_id(dao.findByOwner(request.getParameter("origin-id")));
    return "/cashier/transfer/view.jsp";
  }
    
  private Map<String, String> mistakesLoadOrigin(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    UserModel dao = UserModel.getInstance();
    if (request.getParameter("origin-id").isEmpty()) {
      mistakes.put("origin-id", "The source ID cannot be empty");
    } else {
      if (dao.find(request.getParameter("origin-id")) == null) {
        mistakes.put("origin-id", "The source ID is invalid");
      }
    }
    return mistakes;
  }
  
  private String loadDestination(HttpServletRequest request) {
    Map<String, String> mistakes = this.mistakesLoadDestination(request);
    if (mistakes.isEmpty()) {
      return this.loadDestinationAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/transfer/view.jsp";
    }
  }
  
  private String loadDestinationAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    model.setDestination_id(dao.findByOwner(request.getParameter("destination-id")));
    return "/cashier/transfer/view.jsp";
  }
  
  private Map<String, String> mistakesLoadDestination(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    UserModel dao = UserModel.getInstance();
    if (request.getParameter("destination-id").isEmpty()) {
      mistakes.put("destination-id", "The destination ID cannot be empty");
    } else {
      if (dao.find(request.getParameter("destination-id")) == null) {
        mistakes.put("destination-id", "The destination ID is invalid");
      }
    }
    return mistakes;
  }
  
  private String validateOriginId(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    try {
      model.setOrigin(dao.findById(Integer.valueOf(request.getParameter("origin-id-account"))));
    } catch (Exception ex) { }
    return "/cashier/transfer/view.jsp";
  }
  
  private String validateDestinationId(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    try {
      model.setDestination(dao.findById(Integer.valueOf(request.getParameter("destination-id-account"))));
    } catch (Exception ex) { }
    return "/cashier/transfer/view.jsp";
  }
  
  private String validateOriginAccount(HttpServletRequest request) {
    Map<String, String> mistakes = this.mistakesOriginAccount(request);
    if (mistakes.isEmpty()) {
      return this.validateOriginAccountAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/transfer/view.jsp";
    }
  }
  
  private String validateOriginAccountAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    model.setOrigin(dao.findById(Integer.valueOf(request.getParameter("origin-account"))));
    return "/cashier/transfer/view.jsp";
  }
  
  private Map<String, String> mistakesOriginAccount(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    AccountModel dao = AccountModel.getInstance();
    if (request.getParameter("origin-account").isEmpty()) {
      mistakes.put("origin-account", "The source account cannot be empty");
    } else {
      try {
        if (dao.find(Integer.valueOf(request.getParameter("origin-account"))) == null) {
          mistakes.put("origin-account", "The source account is invalid");
        }
      } catch (NumberFormatException | PersistenceException ex) {
        mistakes.put("origin-account", "The source account must be a number");
      }
    }
    return mistakes;
  }
  
  private String validateDestinationAccount(HttpServletRequest request) {
    Map<String, String> mistakes = this.mistakesDestinationAccount(request);
    if (mistakes.isEmpty()) {
      return this.validateDestinationAccountAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/transfer/view.jsp";
    }
  }
  
  private String validateDestinationAccountAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    model.setDestination(dao.findById(Integer.valueOf(request.getParameter("destination-account"))));
    return "/cashier/transfer/view.jsp";
  }
  
  private Map<String, String> mistakesDestinationAccount(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    AccountModel dao = AccountModel.getInstance();
    if (request.getParameter("destination-account").isEmpty()) {
      mistakes.put("destination-account", "The destination account cannot be empty");
    } else {
      try {
        if (dao.find(Integer.valueOf(request.getParameter("destination-account"))) == null) {
          mistakes.put("destination-account", "The destination account is invalid");
        }
      } catch (NumberFormatException | PersistenceException ex) {
        mistakes.put("destination-account", "The destination account must be a number");
      }
    }
    return mistakes;
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
    Map<String, String> mistakes = this.mistakes(request);
    if (mistakes.isEmpty()) {
      return this.transferAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/transfer/view.jsp";
    }
  }
  
  private String transferAction(HttpServletRequest request) {
    Movement movement = new Movement();
    AccountModel dao = AccountModel.getInstance();
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
      movement.setType("A");
      
      MovementModel.getInstance().create(movement);
      origin.setAmount(origin.getAmount() - amount);
      destination.setAmount(destination.getAmount() + amount/origin.getCurrency().getConversion()*destination.getCurrency().getConversion());
      dao.edit(origin);
      dao.edit(destination);
    } catch (Exception ex) {
      return "/error.jsp";
    }
    
    request.setAttribute("last_path", "/cashier/transfer/view"); 
    return "/done/view";

  }
  
  private Map<String, String> mistakes(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    AccountModel dao = AccountModel.getInstance();
    if (request.getParameter("amount").isEmpty())
      mistakes.put("amount", "The amount is required");
    if (request.getParameter("description").isEmpty())
      mistakes.put("description", "The description is required");
    if (!request.getParameter("origin").isEmpty()) {
      Account origin = dao.findById(Integer.valueOf(request.getParameter("origin")));
      if (origin == null) {
        mistakes.put("origin-account", "The source account is invalid");
        mistakes.put("origin-id-account", "The source account is invalid");
      } else if (origin != null && !request.getParameter("amount").isEmpty()) {
        Double amount = Double.valueOf(request.getParameter("amount"))/origin.getCurrency().getConversion();
        if (origin.getAmount() < amount)
          mistakes.put("amount", "The source account doesn't have enough money");
      }
    } else {
      mistakes.put("origin-account", "The source account is required");
      mistakes.put("origin-id-account", "The source account is required");
    }
    if (!request.getParameter("destination").isEmpty()) {
      Account destination = dao.findById(Integer.valueOf(request.getParameter("destination")));
      if (destination == null) {
        mistakes.put("destination-account", "The destination account is invalid");
        mistakes.put("destination-id-account", "The destination account is invalid");
      }
    } else {
      mistakes.put("destination-account", "The destination account is required");
      mistakes.put("destination-id-account", "The destination account is required");
    }
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
