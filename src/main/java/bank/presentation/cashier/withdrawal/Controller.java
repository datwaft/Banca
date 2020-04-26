package bank.presentation.cashier.withdrawal;

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
    String account = request.getParameter("account");
    if (account != null && !account.isEmpty()) {
      model.setAccount(dao.findById(Integer.valueOf(account)));
    }
    String url = "";
    switch (request.getServletPath()) {
      case "/cashier/withdrawal/view":
        url = this.view(request);
        break;
      case "/cashier/withdrawal/load":
        url = this.load(request);
        break;
      case "/cashier/withdrawal/validate-id":
        url = this.validateId(request);
        break;
      case "/cashier/withdrawal/validate-account":
        url = this.validateAccount(request);
        break;
      case "/cashier/withdrawal/clear":
        url = this.clear(request);
        break;
      case "/cashier/withdrawal/withdrawal":
        url = this.withdrawal(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
  } 
  
  private String view(HttpServletRequest request) {
    return "/cashier/withdrawal/view.jsp";
  }

  private String load(HttpServletRequest request) {
    Map<String, String> mistakes = this.mistakesLoad(request);
    if (mistakes.isEmpty()) {
      return this.loadAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/withdrawal/view.jsp";
    }
  }
  
  private String loadAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    model.setAccountList(dao.findByOwner(request.getParameter("id")));
    return "/cashier/withdrawal/view.jsp";
  }
  
  private Map<String, String> mistakesLoad(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    UserModel dao = UserModel.getInstance();
    if (request.getParameter("id").isEmpty()) {
      mistakes.put("id", "The ID cannot be empty");
    } else {
      if (dao.find(request.getParameter("id")) == null) {
        mistakes.put("id", "The ID is invalid");
      }
    }
    return mistakes;
  }

  private String validateId(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    try {
      model.setAccount(dao.findById(Integer.valueOf(request.getParameter("account-select"))));
    } catch (Exception ex) { }
    return "/cashier/withdrawal/view.jsp";
  }

  private String validateAccount(HttpServletRequest request) {
    Map<String, String> mistakes = this.mistakesAccount(request);
    if (mistakes.isEmpty()) {
      return this.validateAccountAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/withdrawal/view.jsp";
    }
  }
  
  private String validateAccountAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    AccountModel dao = AccountModel.getInstance();
    model.setAccount(dao.findById(Integer.valueOf(request.getParameter("account-input"))));
    return "/cashier/withdrawal/view.jsp";
  }
  
  private Map<String, String> mistakesAccount(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    AccountModel dao = AccountModel.getInstance();
    if (request.getParameter("account-input").isEmpty()) {
      mistakes.put("account-input", "The account cannot be empty");
    } else {
      try {
        if (dao.find(Integer.valueOf(request.getParameter("account-input"))) == null) {
          mistakes.put("account-input", "The account is invalid");
        }
      } catch (NumberFormatException | PersistenceException ex) {
        mistakes.put("account-input", "The account must be a number");
      }
    }
    return mistakes;
  }

  private String clear(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    model.setAccount(null);
    return "/cashier/withdrawal/view.jsp";
  }

  private String withdrawal(HttpServletRequest request) {
    Map<String, String> mistakes = this.mistakes(request);
    if (mistakes.isEmpty()) {
      return this.withdrawalAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/withdrawal/view.jsp";
    }
  }
  
  private String withdrawalAction(HttpServletRequest request) {
    Movement movement = new Movement();
    AccountModel dao = AccountModel.getInstance();
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    try {
      Account account = dao.findById(Integer.valueOf(request.getParameter("account")));
      Double amount = Double.valueOf(request.getParameter("amount"));
      String description = request.getParameter("description");
      Date date = new Date();
      movement.setOrigin(account);
      movement.setDestination(null);
      movement.setAmount(amount);
      movement.setDescription(description);
      movement.setDate(date);
      movement.setType("A");
      
      MovementModel.getInstance().create(movement);
      account.setAmount(account.getAmount() - amount);
      dao.edit(account);
    } catch (Exception ex) {
      return "/error.jsp";
    }
    
    request.setAttribute("last_path", "/cashier/withdrawal/view"); 
    return "/done/view";

  }
  
  private Map<String, String> mistakes(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    AccountModel dao = AccountModel.getInstance();
    if (request.getParameter("amount").isEmpty())
      mistakes.put("amount", "The amount is required");
    if (request.getParameter("description").isEmpty())
      mistakes.put("description", "The description is required");
    if (!request.getParameter("account").isEmpty()) {
      Account account = dao.findById(Integer.valueOf(request.getParameter("account")));
      if (account == null) {
        mistakes.put("account-input", "The account is invalid");
        mistakes.put("account-select", "The account is invalid");
      } else if (!request.getParameter("amount").isEmpty()) {
        Double amount = Double.valueOf(request.getParameter("amount"));
        if (account.getAmount() < amount)
          mistakes.put("amount", "The account doesn't have enough money");
      }
    } else {
      mistakes.put("account-input", "The account is required");
      mistakes.put("account-select", "The account is required");
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
