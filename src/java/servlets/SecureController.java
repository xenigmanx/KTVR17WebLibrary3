/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.User;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import secure.Role;
import secure.SecureLogic;
import secure.UserRoles;
import session.UserFacade;
import session.RoleFacade;
import session.UserRolesFacade;
import util.EncriptPass;
import util.PageReturner;

/**
 *
 * @author Melnikov
 */
@WebServlet(loadOnStartup = 1,name = "SecureController", urlPatterns = {
    "/login",
    "/logout",
    "/showLogin",
    "/newUser",
    "/addUser",
    
})
public class SecureController extends HttpServlet {
   
    @EJB RoleFacade roleFacade;
    @EJB UserFacade userFacade;
    @EJB UserRolesFacade userRolesFacade;

    @Override
    public void init() throws ServletException {
        List<User> listUsers = userFacade.findAll();
        if(listUsers.isEmpty()){
            EncriptPass ep = new EncriptPass();
            String salts = ep.createSalts();
            String encriptPass = ep.setEncriptPass("admin", salts);
            User user = new User("Сидор", "Сидоров", 
                 "454545454", "К-Ярве", "admin", encriptPass, salts);
            userFacade.create(user);
            Role role = new Role();
            role.setName("ADMIN");
            roleFacade.create(role);
            UserRoles ur = new UserRoles();
            ur.setUser(user);
            ur.setRole(role);
            userRolesFacade.create(ur);
            role.setName("DIRECTOR");
            roleFacade.create(role);
            ur.setUser(user);
            ur.setRole(role);
            userRolesFacade.create(ur);
            role.setName("MANAGER");
            roleFacade.create(role);
            ur.setUser(user);
            ur.setRole(role);
            userRolesFacade.create(ur);
            role.setName("USER");
            roleFacade.create(role);
            ur.setUser(user);
            ur.setRole(role);
            userRolesFacade.create(ur);
        }
    }
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF8");
        HttpSession session = request.getSession(false);
        User regUser = null;
        if(session != null){
            try {
                regUser = (User) session.getAttribute("regUser");
            } catch (Exception e) {
                regUser = null;
            }
        }
        EncriptPass ep = new EncriptPass(); 
        String salts="";
        SecureLogic sl = new SecureLogic();
        String path = request.getServletPath();
        
        switch (path) {
            case "/login":
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                request.setAttribute("info", "Нет такого пользователя!");
                regUser = userFacade.findByLogin(login);
                if(regUser == null){
                    request.getRequestDispatcher(PageReturner.getPage("showLogin"))
                        .forward(request, response);
                    break;
                }
                salts = regUser.getSalts();
                String encriptPass = ep.setEncriptPass(password, salts);
                if(encriptPass.equals(regUser.getPassword())){
                    session = request.getSession(true);
                    session.setAttribute("regUser", regUser);
                    request.setAttribute("info", "Привет "+regUser.getName()
                            +"! Вы вошли в систему.");
                    request.getRequestDispatcher("/welcome").forward(request, response);
                    break;
                }
                request.getRequestDispatcher(PageReturner.getPage("showLogin"))
                        .forward(request, response);
                break;
            case "/showLogin":
                request.getRequestDispatcher(PageReturner.getPage("showLogin"))
                        .forward(request, response);
                break;
            case "/logout":
                if(session != null){
                    session.invalidate();
                    request.setAttribute("info", "Вы вышли из системы");
                }
                request.getRequestDispatcher(PageReturner.getPage("index"))
                        .forward(request, response);
                break;
            case "/newUser":
                request.getRequestDispatcher(PageReturner.getPage("newUser")).forward(request, response);
                break;
            case "/addUser":
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String phone = request.getParameter("phone");
                String city = request.getParameter("city");
                login = request.getParameter("login");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
                if(!password1.equals(password2)){
                    request.setAttribute("info", "Неправильно введен логин или пароль");  
                    request.getRequestDispatcher(PageReturner.getPage("newUser"))
                            .forward(request, response);
                    break;
                }
                salts = ep.createSalts();
                encriptPass = ep.setEncriptPass(password1, salts);
                User user = new User(name, surname, phone, city, login, 
                        encriptPass,salts);
                userFacade.create(user);
                Role role = roleFacade.findRoleByName("USER");
                UserRoles ur = new UserRoles(user, role);
                userRolesFacade.create(ur);
                session = request.getSession(true);
                session.setAttribute("regUser", user);
                request.setAttribute("user", user);
                request.getRequestDispatcher("/welcome").forward(request, response);
                break;
             default:
                request.setAttribute("info", "Нет такой станицы!");
                request.getRequestDispatcher("/welcome").forward(request, response);
                break;
            
        }
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
