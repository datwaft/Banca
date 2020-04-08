package bank.presentation.cashier.register;

import bank.logic.Account;
import bank.logic.Link;
import bank.logic.User;
import bank.logic.model.AccountModel;
import bank.logic.model.CurrencyModel;
import bank.logic.model.LinkModel;
import bank.logic.model.UserModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Controller extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    User user = (User)session.getAttribute("user");
    
    if (user == null || !user.getCashier())
    {
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    request.setAttribute("model",new Model());
    Model model = (Model) request.getAttribute("model");
    model.setCurrency(CurrencyModel.getInstance().findAll());
    String url = "";
    switch (request.getServletPath()) {
      case "/cashier/register/view":
        url = this.view(request);
        break;
      case "/cashier/register/verify":
        url = this.verify(request);
        break;
      case "/cashier/register/register":
        url = this.register(request);
        break;
      case "/cashier/register/clear":
        url = this.clear(request);
    }
    request.getRequestDispatcher(url).forward(request, response);   
  }
  
  private String view(HttpServletRequest request) {
    return viewAction(request);
  }
  
  private String verify(HttpServletRequest request)
  {
    Map<String, String> mistakes = null;
    if (!request.getParameter("register_id").isEmpty()) {
      return verifyAction(request);
    } else {
      mistakes = new HashMap<>();
      mistakes.put("register_id","The id field was empty");
      request.setAttribute("mistakes", mistakes);
      return "/cashier/register/view.jsp";
    }
  }
  
  private String register(HttpServletRequest request)
  {
    Map<String, String> mistakes = this.validate(request);
    if (mistakes.isEmpty()) {
      return this.registerAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/cashier/register/view.jsp";
    }
  }
  
  private String clear(HttpServletRequest request)
  {
    Model model = (Model)request.getAttribute("model");
    model.setExist(null);
    return "/cashier/register/view.jsp";
  }
  
  private String viewAction(HttpServletRequest request) {
//    HttpSession session = request.getSession(true);
    try {
      
      return "/cashier/register/view.jsp";
    } catch (Exception ex) {
      return "";
    }
  }
  
  private String verifyAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    model.setId(request.getParameter("register_id"));
    User user = null; //quitar null cualquier wea  //falta el clear 
    try {
      
      user = UserModel.getInstance().find(request.getParameter("register_id"));
      if(user != null)
      {
        if (user.getCashier() == true && user.getClient() == false)
        {
          model.setIs(true);
        }
      }
      model.setExist(user);
      model.setId(user.getId());
      
    } catch (Exception ex) {
      return "/cashier/register/view.jsp";
    }
    return "/cashier/register/view.jsp";
  }
  
  private String registerAction(HttpServletRequest request)
  {
    User user = UserModel.getInstance().find(request.getParameter("register_id_hidden"));
    try
    {
      if (user == null)
      {
        
        User new_user = new User();
        new_user.setId(request.getParameter("register_id"));
        new_user.setCashier(false);
        new_user.setClient(true);
        new_user.setName(request.getParameter("register_name"));
        new_user.setCellphone(request.getParameter("register_cellphone"));
        
        bank.presentation.Controller controller = bank.presentation.Controller.getInstance();
        
        new_user.setPassword(controller.generatePassword(8));
        
        UserModel.getInstance().create(new_user);
        
        Account new_account = new Account();
        
        new_account.setAmount(0);
        new_account.setCurrency(CurrencyModel.getInstance().find(request.getParameter("register_currency")));
        new_account.setDailylimit(Integer.valueOf(request.getParameter("register_limit")));
        new_account.setOwner(new_user);
        
        
        AccountModel.getInstance().create(new_account);
      }
      else
      {
        if (user.getCashier() == true && user.getClient() == false)
        {
          user.setClient(true);
        }
        Account new_account = new Account();
        new_account.setAmount(0);
        new_account.setCurrency(CurrencyModel.getInstance().find(request.getParameter("register_currency")));
        new_account.setDailylimit(Integer.valueOf(request.getParameter("register_limit")));
        new_account.setOwner(user);
        UserModel.getInstance().edit(user);
        
        AccountModel.getInstance().create(new_account);
        
        List<Account> acclist = AccountModel.getInstance().findByOwner(user.getId());
        Account owner = acclist.get(acclist.size()-1);
        
        for (Account acc : acclist)
        {
          if(owner.getId() != acc.getId())
          {
            Link to_link = new Link();
            to_link.setOwner(owner);
            to_link.setLinkedAccount(acc);
            LinkModel.getInstance().create(to_link);
          }
        }
      }
    }
    catch(Exception ex)
    {
      
    }
    
    return "/cashier/register/view.jsp";
  }
  
  private Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    UserModel dao = UserModel.getInstance();
    if (request.getParameter("register_id").isEmpty())
      mistakes.put("register_id", "id field was empty");
    
    if ( request.getParameter("register_name") != null && request.getParameter("register_name").isEmpty())
      mistakes.put("register_name", "name was empty");
    
    if (request.getParameter("register_cellphone") != null  && request.getParameter("register_cellphone").isEmpty())
      mistakes.put("register_cellphone", "cellphone was  empty");
    
    
    if (request.getParameter("register_limit").isEmpty())
      mistakes.put("register_limit", "limit was empty");
    if ("".equals(request.getParameter("register_currency")))
      mistakes.put("currency", "Invalid currency choosed");
    
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
