package servlets;

import cs359db.db.dbAPI;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Cookies;

/**
 *
 * @author Tasos
 */
@WebServlet(name = "CompanyServlet", urlPatterns = {"/CompanyServlet"})
public class CompanyServlet extends HttpServlet {

    Random rand = new Random(); // Seeded by current date/time

    private HashMap<Integer, String> servletCookies;

    @Override
    public void init() {
        this.servletCookies = new HashMap<>();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getHeader("action");
        if (missing(action)) {
            response.setHeader("fail", "Missing Parameters");
        } else {
            switch (action) {
                case "login":
                    loginAction(request, response);
                    break;
                case "open":
                    openAction(request, response);
                    break;
                case "close":
                    closeAction(request, response);
                    break;
                case "logout":
                    logoutAction(request, response);
                    break;
                default:
                    response.setHeader("fail", "Wrong Parameters");
            }
        }
    }

    private void loginAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, ClassNotFoundException {

        String email = request.getParameter("email");
        String type = request.getParameter("type");
        if (email == null && type == null) {
            email = Cookies.getCookieValue(Cookies.getRequestCookieValue(request, "cccCompanyServlet", null));//get email
            if (email == null) {
                response.setHeader("error", "we don't have cookie");
            } else {
                StringBuilder url = new StringBuilder();
                url.append("/WEB-INF/JSP/").append(type).append("Page.jsp");
                forwardToPage(request, response, url.toString());
            }
        } else {
            String exists = dbAPI.checkEmail(email);
            if (exists.equals("not exist")) {
                // TODO ena error h fail header
            } else {
                response.setHeader("id", "Hello, " + email);
                Cookie usrCookie = new Cookie("cccCompanyServlet", "" + Cookies.addCookie(email));//create and set cookies ,TODO rename addCookie
                usrCookie.setMaxAge(3600);
                response.addCookie(usrCookie);

                StringBuilder url = new StringBuilder();
                url.append("/WEB-INF/JSP/").append(type).append("Page.jsp");
                forwardToPage(request, response, url.toString());
            }
        }
    }

    private void openAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, ClassNotFoundException {

        String email = request.getParameter("email");
        String type = request.getParameter("type");
        if (missing(email)
                || missing(type)) {
            response.setHeader("fail", "Missing Parameters");
        } else {
            String exists = dbAPI.checkEmail(email);
            if (exists.equals("not exist")) {
                response.setHeader("id", "Welcome, " + email);
                Cookie usrCookie = new Cookie("cccCompanyServlet", "" + Cookies.addCookie(email));//create and set cookies
                usrCookie.setMaxAge(3600);
                response.addCookie(usrCookie);

                // add new account to database
                dbAPI.addEntity(type, email);

                StringBuilder url = new StringBuilder();
                url.append("/WEB-INF/JSP/").append(type).append("Page.jsp");
                forwardToPage(request, response, url.toString());
            } else {
                response.setHeader("error", "Email already exist at: " + exists);
            }
        }
    }

    private void closeAction(HttpServletRequest request, HttpServletResponse response) {
        logoutAction(request, response);
    }

    private void logoutAction(HttpServletRequest request, HttpServletResponse response) {
        Cookie userCookie = Cookies.getRequestCookie(request, "cccCompanyServlet");
        if (userCookie == null) { // cookie has expired
            response.setHeader("fail", "Missing Cookie");
        } else {
            userCookie.setValue(userCookie.getValue());
            userCookie.setMaxAge(0);
            response.addCookie(userCookie);

            Cookies.removeCookie(userCookie.getValue()); // from servlet cookies

            ServletContext context = getServletContext();
        }
    }

    private void forwardToPage(HttpServletRequest request,
            HttpServletResponse response,
            String url)
            throws IOException, ServletException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private boolean missing(String param) {
        return param == null || param.trim().isEmpty();
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CompanyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CompanyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
