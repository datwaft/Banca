package bank.presentation.cashier.interest;

import bank.logic.Account;
import bank.logic.Movement;
import bank.logic.User;
import bank.logic.model.AccountModel;
import bank.logic.model.MovementModel;
import java.io.IOException;
import java.util.Date;
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
    
    String url = "";
    switch (request.getServletPath()) {
      case "/cashier/interest/view":
        url = this.view(request);
        break;
      case "/cashier/interest/credit":
        System.out.println("llega");
        url = this.credit(request);
        break;
    }
  request.getRequestDispatcher(url).forward(request, response);
  } 
  
  private String view(HttpServletRequest request) {
    return "/cashier/interest/view.jsp";
  }

  private String credit(HttpServletRequest request) {
    AccountModel dao = AccountModel.getInstance();
    try {
      for (Account account : dao.findAll()) {
        Movement movement = new Movement();
        
        Double amount = account.getAmount() * account.getCurrency().getInterestRate();
        String description = "Interest credited";
        Date date = new Date();
        
        movement.setOrigin(null);
        movement.setDestination(account);
        movement.setAmount(amount);
        movement.setDescription(description);
        movement.setDate(date);
        movement.setType("A");
        
        MovementModel.getInstance().create(movement);
        account.setAmount(account.getAmount() + amount);
        dao.edit(account);
      }
    } catch (PersistenceException ex) {
      System.err.println(ex);
      return "/error.jsp";
    }
    request.setAttribute("last_path", "/cashier/register/view"); 
    return "/done/view";
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
