/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.presentation.client.transfer;

import bank.logic.Account;
import bank.logic.Movement;
import bank.logic.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mario
 */
public class Controller extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
    request.setAttribute("model", new Model());
    Model model = (Model)request.getAttribute("model");
    
    HttpSession session = request.getSession(true);    
    User user = (User)session.getAttribute("user");
    
    model.origin_accounts = bank.logic.model.AccountModel.getInstance().findByOwner(user.getId());
   
    
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
      model.setOrigin(bank.logic.model.AccountModel.getInstance().findById(Integer.valueOf(request.getParameter("trans_origin_accounts"))));
      model.setSelected(model.getOrigin().getId());
      model.setDestination_accounts(bank.logic.model.LinkModel.getInstance().searchByLinked(Integer.valueOf(request.getParameter("trans_origin_accounts"))));
      
    }
    catch (Exception ex) { System.out.println(ex);}
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
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    try {
      Account origin = dao.findById(Integer.valueOf(request.getParameter("origin_hiden")));
      Account destination = dao.findById(Integer.valueOf(request.getParameter("trans_destination_accounts")));
      Double amount = Double.valueOf(request.getParameter("trans_ammount"))/origin.getCurrency().getConversion();
      String description = request.getParameter("trans_description");
      Date date = new Date();
      movement.setOrigin(origin);
      movement.setDestination(destination);
      movement.setAmount(amount);
      movement.setDescription(description);
      movement.setDate(date);
      
      bank.logic.model.MovementModel.getInstance().create(movement);
      origin.setAmount(origin.getAmount() - amount);
      destination.setAmount(destination.getAmount() + amount);
      dao.edit(origin);
      dao.edit(destination);
      
    } catch (Exception ex) {
      return "/error.jsp";
    }
    return "/client/transfer/view.jsp";
  }
  
  
  private Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    if (request.getParameter("trans_ammount").isEmpty())
      mistakes.put("origin", "The source is required");
    System.out.println(Integer.valueOf(request.getParameter("origin_hiden")));
    System.out.println(Integer.valueOf(request.getParameter("trans_ammount")));
    if (Integer.valueOf(request.getParameter("trans_ammount")) > dao.findById(Integer.valueOf(request.getParameter("origin_hiden"))).getAmount()){
      mistakes.put("ammount", "The ammount is insuficient");
    }
    return mistakes;
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
