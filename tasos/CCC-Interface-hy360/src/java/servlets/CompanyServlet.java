package servlets;

import cs360db.db.dbAPI;
import cs360db.model.MerchantCreditCard;
import cs360db.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
            throws ServletException, IOException, ClassNotFoundException, ParseException {
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
                case "check":
                    checkAction(request, response);
                    break;
                case "refresh":
                    refreshAction(request, response);
                    break;
                case "addEmployee":
                    addEmployeeAction(request, response);
                    break;
                case "removeEmployee":
                    removeEmployeeAction(request, response);
                    break;
                default:
                    response.setHeader("fail", "Wrong Parameters");
            }
        }
    }

    private void loginAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, ClassNotFoundException, ParseException {

        String email = request.getParameter("email");
        String type = request.getParameter("type");
        if (email == null && type == null) {
            email = Cookies.getCookieValue(Cookies.getRequestCookieValue(request, "cccCompanyServlet", null));//get email
            type = Cookies.getCookieType(Cookies.getRequestCookieValue(request, "cccCompanyServlet", null));//get type
            if (email == null) {
                response.setHeader("error", "we don't have cookie");
            } else {
                response.setHeader("id", "Hello, " + email);
                StringBuilder url = new StringBuilder();
                url.append("/WEB-INF/JSP/").append(convertType2Page(type));
                ServletContext context = getServletContext();
                context.setAttribute("data", dbAPI.getUser(type, email));
                context.setAttribute("dataType", type);
                forwardToPage(request, response, url.toString());
            }
        } else {
            boolean exists = dbAPI.existID(email, type);
            if (!exists) {
                response.setHeader("error", "User not exist!");//return error
            } else {
                response.setHeader("id", "Hello, " + email);
                Cookie usrCookie = new Cookie("cccCompanyServlet", "" + Cookies.addCookie(email, type));//create and set cookies ,TODO rename addCookie
                usrCookie.setMaxAge(3600);
                response.addCookie(usrCookie);

                StringBuilder url = new StringBuilder();
                url.append("/WEB-INF/JSP/").append(convertType2Page(type));
                ServletContext context = getServletContext();
                context.setAttribute("data", dbAPI.getUser(type, email));
                context.setAttribute("dataType", type);
                forwardToPage(request, response, url.toString());
            }
        }
    }

    private void openAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, ClassNotFoundException, ParseException {

        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        if (missing(email)
                || missing(type)) {
            response.setHeader("fail", "Missing Parameters");
        } else {
            boolean exists = dbAPI.existID(email);
            if (!exists) {
                response.setHeader("id", "Welcome, " + email);
                Cookie usrCookie = new Cookie("cccCompanyServlet", "" + Cookies.addCookie(email, type));//create and set cookies
                usrCookie.setMaxAge(3600);
                response.addCookie(usrCookie);

                // add new account to database
                dbAPI.addEntity(type, email, name);

                StringBuilder url = new StringBuilder();

                url.append("/WEB-INF/JSP/").append(convertType2Page(type));
                ServletContext context = getServletContext();
                context.setAttribute("data", dbAPI.getUser(type, email));
                forwardToPage(request, response, url.toString());
            } else {
                response.setHeader("error", "Email already exists");
            }
        }
    }

    private void closeAction(HttpServletRequest request, HttpServletResponse response) throws ParseException, ClassNotFoundException {
        String email = Cookies.getCookieValue(Cookies.getRequestCookieValue(request, "cccCompanyServlet", null));//get email
        String type = Cookies.getCookieType(Cookies.getRequestCookieValue(request, "cccCompanyServlet", null));//get type
        if (dbAPI.deleteUser(type, email)) {
            logoutAction(request, response);
        } else {
            response.setHeader("error", "Something goes wrong please re-login and try again");
        }
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
        }
    }

    private void checkAction(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException {
        String email = request.getParameter("email");
        if (!missing(email)) {
            if (dbAPI.existID(email)) {
                response.setHeader("error", "Email Already Exist");
            }
        } else {
            response.setHeader("fail", "Missing Parameters");
        }
    }

    private void refreshAction(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, ParseException, IOException {
        String email, type;
        email = Cookies.getCookieValue(Cookies.getRequestCookieValue(request, "cccCompanyServlet", null));//get email
        type = Cookies.getCookieType(Cookies.getRequestCookieValue(request, "cccCompanyServlet", null));//get type

        if (email == null && type == null) {
            response.setHeader("error", "we don't have cookie");
        } else {
            CharSequence str = "merchant";
            User user = dbAPI.getUser(type, email);
            if (type.contains(str)) {
                if (user.getCard() instanceof MerchantCreditCard) {
                    MerchantCreditCard cc = (MerchantCreditCard) user.getCard();
                    try (PrintWriter out = response.getWriter()) { // construct JSON object
                        out.append("{\"cardNumber\":\"").append("" + cc.getAccountNumber()).append("\"");
                        out.append(",\"cardHolder\":\"").append(user.getName()).append("\"");
                        out.append(",\"totalProfit\":\"").append("" + cc.getTotalProfit()).append("\"");
                        out.append(",\"debtToCCC\":\"").append("" + cc.getCurrentDebt()).append("\"");
                        out.append(",\"supply\":\"").append("" + cc.getSupply()).append("\"");
                        out.append(",\"type\":\"").append("" + "merchant").append("\"");
                        out.append("}");
                    }
                } else {
                    assert (false);
                }
            } else {
                try (PrintWriter out = response.getWriter()) { // construct JSON object
                    out.append("{\"cardNumber\":\"").append("" + user.getCard().getAccountNumber()).append("\"");
                    out.append(",\"cardHolder\":\"").append(user.getName()).append("\"");
                    out.append(",\"expiredThru\":\"").append(user.getCard().getValidThru()).append("\"");
                    out.append(",\"creditLimit\":\"").append("" + user.getCard().getCreditLimit()).append("\"");
                    out.append(",\"availableCreditBalance\":\"").append("" + user.getCard().getAvailableCreditBalance()).append("\"");
                    out.append(",\"currentDebt\":\"").append("" + user.getCard().getCurrentDebt()).append("\"");
                    out.append(",\"type\":\"").append("customer").append("\"");
                    out.append("}");
                }
            }
        }
    }

    private void addEmployeeAction(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, ParseException, IOException {
        String email, name, accountNumber, type;
        email = request.getParameter("accountID");
        name = request.getParameter("accountName");
        accountNumber = request.getParameter("accountNumber");
        type = "employee_" + request.getParameter("accountType");
        if (missing(email)
                || missing(name)
                || missing(accountNumber)
                || missing(type)) {
            response.setHeader("fail", "Missing Parameters");
        } else {
            boolean exists = dbAPI.existID(email);
            if (!exists) {
                // add new account to database
                dbAPI.addEntity(type, email, name, accountNumber);
            } else {
                response.setHeader("error", "Email already exists");
            }
        }
    }

    private void removeEmployeeAction(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, ParseException, IOException {

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

    private String convertType2Page(String type) {
        String str = "";
        switch (type) {
            case "civilian":
            case "employee_civilian":
                str = "customerPage.jsp";
                break;
            case "merchant":
            case "employee_merchant":
                str = "merchantPage.jsp";
                break;
            case "company":
                str = "companyPage.jsp";
                break;
            default:
                assert (false);
        }

        return str;
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
        } catch (ClassNotFoundException | ParseException ex) {
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
        } catch (ClassNotFoundException | ParseException ex) {
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
