/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import secure.SecureLogic;
import util.PageReturner;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "WelcomeController", urlPatterns = {
    "/welcome",

})
public class WelcomeController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        SecureLogic sl = new SecureLogic();
        User regUser = null;
        if(session != null){
            try {
                regUser = (User) session.getAttribute("regUser");
            } catch (Exception e) {
                regUser = null;
            }
        }
        if(regUser == null){
            request.setAttribute("info", "Войдите или зарегистрируйтесь");
            request.getRequestDispatcher(PageReturner.getPage("index"))
                    .forward(request, response);
            return;
        }
        if(sl.isRole(regUser, "ADMIN")){
            request.setAttribute("info", "Вы вошли как admin");
            request.getRequestDispatcher(PageReturner.getPage("welcomeAdmin"))
                    .forward(request, response);
            return;
        }else if(sl.isRole(regUser, "DIRECTOR")){
            request.setAttribute("info", "Вы вошли как директор");
            request.getRequestDispatcher(PageReturner.getPage("welcomeDirector"))
                    .forward(request, response);
            return;
        }else if(sl.isRole(regUser, "MANAGER")){
            request.setAttribute("info", "Вы вошли как manager");
            request.getRequestDispatcher(PageReturner.getPage("welcomeManager"))
                    .forward(request, response);
            return;
        }else if(sl.isRole(regUser, "USER")){
            request.setAttribute("info", "Вы вошли как user");
            request.getRequestDispatcher(PageReturner.getPage("welcomeUser"))
                    .forward(request, response);
            return;
        }else{
            request.setAttribute("info", "Вы должны войти для пользования библиотекой");
            request.getRequestDispatcher(PageReturner.getPage("index"))
                    .forward(request, response);
            return;
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
