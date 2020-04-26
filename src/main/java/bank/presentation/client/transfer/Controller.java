package bank.presentation.client.transfer;

import bank.logic.Account;
import bank.logic.Movement;
import bank.logic.User;
import bank.logic.model.AccountModel;
import bank.logic.model.LinkModel;
import bank.logic.model.MovementModel;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Controller extends HttpServlet {
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    User user = (User)session.getAttribute("user");
    
    if (user == null || !user.getClient()) {
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    request.setAttribute("model", new Model());
    Model model = (Model)request.getAttribute("model");
    
    model.origin_accounts = AccountModel.getInstance().findByOwner(user.getId());
   
    String url = "";
    switch (request.getServletPath()) {
      case "/client/transfer/view":
        url = this.view(request);
        break;
      case "/client/transfer/search":
        url = this.search(request);
        break;  
      case "/client/transfer/clear":
        url = this.clear(request);
        break;  
      case "/client/transfer/transfer":
        url = this.transfer(request);
        break;     
    }
    request.getRequestDispatcher(url).forward(request, response); 
  }
  
  private String view(HttpServletRequest request) {
    return "/client/transfer/view.jsp";
  }
  
  private String search(HttpServletRequest request) {
    Model model = (Model) request.getAttribute("model");
    try
    {
      if("".equals(request.getParameter("trans_origin_accounts"))) {
        throw new Exception("Empty field");
      }
      model.setOrigin(AccountModel.getInstance().findById(Integer.valueOf(request.getParameter("trans_origin_accounts"))));
      model.setSelected(model.getOrigin().getId());
      model.setDestination_accounts(LinkModel.getInstance().searchByLinked(Integer.valueOf(request.getParameter("trans_origin_accounts"))));
    }
    catch (Exception ex) {
      Map<String, String> mistakes = new HashMap<>();
      mistakes.put("trans_origin_accounts", "Origin account was empty or invalid");  //estas 3 lineas quedaron como pruebas finales xdxdxdxd borrar en caso de error xd
      request.setAttribute("mistakes", mistakes);
    }
    return "/client/transfer/view.jsp";
  }
  
  private String clear(HttpServletRequest request) {
    Model model = (Model) request.getAttribute("model");
    model.setDestination_accounts(null);
    return "/client/transfer/view.jsp";
  }
  
  private String transfer(HttpServletRequest request) {
    Map<String, String> mistakes = this.validate(request);
    if (mistakes.isEmpty()) {
      return this.transferAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/client/transfer/view.jsp";
    }
  }
  
  private String transferAction(HttpServletRequest request) {
    
    Movement movement = new Movement();
    AccountModel dao = AccountModel.getInstance();
    try {
      Account origin = dao.findById(Integer.valueOf(request.getParameter("origin_hiden")));
      Account destination = dao.findById(Integer.valueOf(request.getParameter("trans_destination_accounts")));
      Double amount = Double.valueOf(request.getParameter("trans_ammount"));
      String description = request.getParameter("trans_description");
      Date date = new Date();
      movement.setOrigin(origin);
      movement.setDestination(destination);
      movement.setAmount(amount);
      movement.setDescription(description);
      movement.setDate(date);
      movement.setType("C");
      
      MovementModel.getInstance().create(movement);
      origin.setAmount(origin.getAmount() - amount);
      destination.setAmount(destination.getAmount() + amount/origin.getCurrency().getConversion()*destination.getCurrency().getConversion()); //cambio que puede da;ar la transfer de cliente
      dao.edit(origin);
      dao.edit(destination);
      
    } catch (Exception ex) {
      return "/error.jsp";
    }
    
    request.setAttribute("last_path", "/client/transfer/view"); 
    return "/done/view";
  }
  
  private Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    AccountModel dao = AccountModel.getInstance();
    
    if (request.getParameter("origin_hiden").isEmpty()) {
      mistakes.put("trans_origin_accounts", "The origin account is invalid");
    } 
    if (request.getParameter("trans_ammount").isEmpty()) {
      mistakes.put("trans_ammount", "Invalid transfer amount");
    } else if (!request.getParameter("origin_hiden").isEmpty()) {
      Double amount = Double.valueOf(request.getParameter("trans_ammount"));
      Account origin = dao.findById(Integer.valueOf(request.getParameter("origin_hiden")));
      Double account_amount = origin.getAmount();
      Double acc_today = dailyLimitBalance(request.getParameter("origin_hiden"));
      if (account_amount < amount) {
        mistakes.put("trans_ammount", "The origin account doesn't have enough balance");
      } else if (amount + acc_today > origin.getDailylimit()) {
        mistakes.put("trans_ammount", "That amount exceeds the daily limit");
      }
    }
    if (request.getParameter("trans_description").isEmpty()) {
      mistakes.put("trans_description", "The description field was empty");
    }
    if(request.getParameter("trans_destination_accounts").isEmpty()) {
      mistakes.put("trans_destination_accounts","The destination account is invalid");
    }
    return mistakes;
  }

  private Double dailyLimitBalance(String account) {
    MovementModel dao = MovementModel.getInstance();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDate today = LocalDate.now(ZoneOffset.UTC) ;
    OffsetDateTime start = today.atTime(OffsetTime.MIN) ;
    OffsetDateTime end = today.plusDays(1).atTime(OffsetTime.MIN) ;
    String from = fmt.format(start);
    String to = fmt.format(end);
    List<Movement> movements = dao.findByOrigin(account, from, to);
    movements = movements.stream().filter(x -> x.getType().equals("C")).collect(Collectors.toList());
    
    Double acc = 0d;
    for (Movement movement : movements) {
      acc += movement.getAmount();
    }
    return acc;
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
