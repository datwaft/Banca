package bank.presentation.login;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bank.logic.User;
import bank.logic.model.UserModel;

public class Controller extends HttpServlet {
  public static String isErroneous(String field, Map<String,String> mistakes) {
    if((mistakes != null) && (mistakes.get(field) != null))
      return "is-invalid";
    else
      return "";
  }
  
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("model", new Model());
    String url = "";
    switch(request.getServletPath()) {
      case "/login/view":
        url = this.view(request);
        break;
      case "/login/login":
        url = this.login(request);
        break;
      case "/login/logout":
        url = this.logout(request);
        break;
    }
    request.getRequestDispatcher(url).forward(request, response);
  }
  
  private String view(HttpServletRequest request) {
    return viewAction(request);
  }

  private String viewAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    model.getCurrent().setId("");
    model.getCurrent().setPassword("");
    return "/login/view.jsp"; 
  }

  private String login(HttpServletRequest request) {
    try {
      Map<String, String> mistakes = this.validate(request);
      if(mistakes.isEmpty()) {
        this.updateModel(request);
        return this.loginAction(request);
      } else {
        request.setAttribute("mistakes", mistakes);
        return "/login/view.jsp";
      }
    } catch(Exception ex) {
      return "/error.jsp";
    }
  }

  private String loginAction(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    bank.logic.model.UserModel dao = UserModel.getInstance();
    HttpSession session = request.getSession(true);
    try {
      User match = dao.find(model.getCurrent().getId());
      if(!match.getPassword().equals(model.getCurrent().getPassword()))
        throw new Exception("Invalid password");
      session.setAttribute("user", match);
      String url = "/index.jsp";
      // Aquí es donde ponemos la URL dependiendo si es cajero o si es usuario.
      return url;
    } catch(Exception ex) {
      Map<String, String> mistakes = new HashMap<>();
      request.setAttribute("mistakes", mistakes);
      mistakes.put("id", "ID or password is incorrect");
      mistakes.put("password", "ID or password is incorrect");
      return "/login/view.jsp";
    }
  }

  private void updateModel(HttpServletRequest request) {
    Model model = (Model)request.getAttribute("model");
    model.getCurrent().setId(request.getParameter("id"));
    model.getCurrent().setPassword(request.getParameter("password"));
  }

  private Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> mistakes = new HashMap<>();
    if(request.getParameter("id").isEmpty())
      mistakes.put("id", "The ID is required");
    if(request.getParameter("password").isEmpty())
      mistakes.put("password", "The password is required");
    return mistakes;
  }
  
  public String logout(HttpServletRequest request) {
    return this.logoutAction(request);
  }
    
  public String logoutAction(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    session.removeAttribute("user");
    session.invalidate();
    return "/index.jsp";   
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   Handles the HTTP <code>GET</code> method.
   @param request servlet request
   @param response servlet response
   @throws ServletException if a servlet-specific error occurs
   @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   Handles the HTTP <code>POST</code> method.
   @param request servlet request
   @param response servlet response
   @throws ServletException if a servlet-specific error occurs
   @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   Returns a short description of the servlet.
   @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
