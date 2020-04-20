/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.presentation.client.link;

import bank.logic.Account;
import bank.logic.Link;
import bank.logic.User;
import bank.logic.model.AccountModel;
import bank.logic.model.LinkModel;
import bank.logic.model.UserModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class controller extends HttpServlet {

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
      case "/client/link/view":
        url = this.view(request);
        break;
      case "/client/link/verify":
        url = this.verify(request);
        break;
      case "/client/link/link":
        url=this.link(request);
        break;
      case "/client/link/clear":
        url=this.clear(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
    
  }
  
  private String view(HttpServletRequest request) {
    return viewAction(request);

  }
  
  private String verify(HttpServletRequest request) {
    if(!request.getParameter("destination_link").isEmpty())
      return verifyAction(request);
    else
    {
      Map<String, String> mistakes = new HashMap<>();
        mistakes.put("destination_link","Destination link was empty");  //estas 3 lineas quedaron como pruebas finales xdxdxdxd borrar en caso de error xd
        request.setAttribute("mistakes", mistakes);
        return "/client/link/view.jsp";
    }
  }
  
  private String link( HttpServletRequest request )
  {
    Map<String, String> mistakes = this.validate(request);
    if (mistakes.isEmpty()) {
      return linkAction(request);
    } else {
      request.setAttribute("mistakes", mistakes);
      return "/client/link/view.jsp";
    }
  }
  
  private String clear( HttpServletRequest request)
  { 
    HttpSession session = request.getSession(true);      
    User user = (User)session.getAttribute("user");
    bank.presentation.client.link.Model model = (bank.presentation.client.link.Model)request.getAttribute("model");
    model.setAccounts(AccountModel.getInstance().findByOwner(user.getId()));
    model.setTo_link(null);
    return "/client/link/view.jsp";
  }
  
   private String viewAction(HttpServletRequest request) {    
     
    HttpSession session = request.getSession(true);    
    User user = (User)session.getAttribute("user");
    bank.presentation.client.link.Model model = (bank.presentation.client.link.Model)request.getAttribute("model");
    try {
      model.setAccounts(AccountModel.getInstance().findByOwner(user.getId()));
      return "/client/link/view.jsp";
    } catch (Exception ex) {
      return "/client/link/view.jsp";
    }
       
  }
   
   private String verifyAction( HttpServletRequest request )
   {
     
      bank.presentation.client.link.Model model = (bank.presentation.client.link.Model)request.getAttribute("model");
      HttpSession session = request.getSession(true);      
      User user = (User)session.getAttribute("user");;
 
      try{
  
        if (request.getParameter("destination_link").isEmpty()){
          throw new Exception("Empty acc field");
        }
        else{

          Account destination = AccountModel.getInstance().findById(Integer.valueOf(request.getParameter("destination_link")));
  
          model.setTo_link(destination);
      
          model.setAccounts(AccountModel.getInstance().findByOwner(user.getId()));
         
          model.setSelected(Integer.valueOf(request.getParameter("origin_link")));
       
          } 
        }
      catch(Exception ex)
      {
        
        return "/client/link/view.jsp";
      }
     return "/client/link/view.jsp";
   }
   
   private String linkAction(HttpServletRequest request){

    bank.presentation.client.link.Model model = (bank.presentation.client.link.Model)request.getAttribute("model");
      
    try {
      
      Account origin = AccountModel.getInstance().findById(Integer.valueOf(request.getParameter("origin_link")));
      model.setOrigin(origin);     

      Link link = new Link();
      link.setLinkedAccount(AccountModel.getInstance().findById(Integer.valueOf(request.getParameter("destination_link"))));
      link.setOwner(origin);
      LinkModel.getInstance().create(link);

    } catch (Exception ex) {
          return "/client/link/view.jsp";
      } 
    
    request.setAttribute("last_path", "/client/link/view"); 
    return "/done/view";
    }
   
   
   private Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    bank.logic.model.AccountModel dao = bank.logic.model.AccountModel.getInstance();
    if ("".equals(request.getParameter("origin_link")))
      mistakes.put("origin_link", "invalid value in origin link, press clear!");
    
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
