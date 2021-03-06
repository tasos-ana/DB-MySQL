<%-- 
    Document   : customerPage
    Created on : Dec 20, 2016, 10:28:51 PM
    Author     : Tasos
--%>

<%@page import="cs360db.model.Civilian"%>
<%@page import="cs360db.model.Company"%>
<%@page import="cs360db.model.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ServletContext context = getServletContext();
    assert (context.getAttribute("data") instanceof User);
    User user = (User) context.getAttribute("data");
    context.removeAttribute("data"); // clear after use
    String name, companyId, companyName, validThru;
    double debt, creditBalance, creditLimit;
    int accountNumber;

    assert (user.isCivilian() || user.isEmployeeCivilian());
    if (user.isCivilian()) {
        Civilian c = user.getCivilian();
        name = c.getName();
        companyId = null;
        companyName = null;
        debt = c.getDebt();
        creditBalance = c.getCreditBalance();
        creditLimit = c.getCreditLimit();
        accountNumber = c.getAccountNumber();
        validThru = c.getValidThru().toString();
    } else {
        Company c = user.getCompany();
        Civilian ci = user.getEmployeeCivilian();
        name = ci.getName();
        debt = ci.getDebt();
        companyId = c.getId();
        companyName = c.getName();
        creditBalance = c.getCreditBalance();
        creditLimit = c.getCreditLimit();
        accountNumber = c.getAccountNumber();
        validThru = c.getValidThru().toString();
    }
%>
<div class="container">
    <h2>Customer dashboard</h2>
    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor" 
                              onclick="document.getElementById('home_link').click();">Home</a></li>
        <li><a data-toggle="tab" href="#buy" class="darkcolor"
               onclick="ajaxUsersDropdownRequest('buy')"> Buy </a></li>
            <%if (companyId == null) {%>
        <li><a data-toggle="tab" href="#debt" class="darkcolor"> Debt </a></li>
            <%}%>
        <li><a data-toggle="tab" href="#refund" class="darkcolor"
               onclick="ajaxUsersDropdownRequest('refund')"> Refund </a></li >
        <li><a data-toggle="tab" href="#searchTab" class="darkcolor"
               onclick="ajaxSearchRequest(); ajaxUsersDropdownRequest('search');"> Search </a></li >
    </ul>


    <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
            <h3 class="text-center">Your account details</h3>
            <div class="container">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr class="text-left">
                                <th>#</th>
                                    <%if (companyId != null) {%>            
                                <th>Company name</th>
                                <th>Company ID</th>
                                    <%}%>
                                <th>Card number</th>
                                <th>Card holder</th>
                                <th>Valid Thru</th>
                                <th>Credit limit</th>
                                <th>Available credit balance </th>
                                <th>Debt</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="text-left">
                                <td id="accountNo">1</td>
                                <%if (companyId != null) {%>            
                                <td id="companyName"><%= companyName%></td>
                                <td id="companyId"><%= companyId%></td>
                                <%}%>
                                <td id="cardNumber"> <%= accountNumber%> </td>
                                <td id="cardHolder"> <%= name%></td>
                                <td id="cardExpired"><%= validThru%></td>
                                <td><span id="cardLimit"><%= creditLimit%></span> &#8364</td>
                                <td><span id="availableCreditBalance"><%= creditBalance%></span> &#8364</td>
                                <td><span id="debtValue"><%= debt%></span> &#8364</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="buy" class="tab-pane fade">
            <form>
                <fieldset>
                    <legend class="legend_text">Buy goods</legend>
                    <div class="title_text">Merchant:</div>
                    <select id="buyMerchantsDropdownContainer"></select>
                    <div class="title_text">Items Cost:</div>
                    <input type="text" class="text-center" id="buyGoods"
                           placeholder="e.g 50.0" size="30" pattern="\d+(.\d+)?"><br><br>
                    <button type="button" class="btn btn-default btn_style"  
                            onclick="ajaxMakeTransactionRequest('charge')">
                        Order now
                    </button>
                </fieldset>
            </form>
        </div>
        <%if (companyId == null) {%>
        <div id="debt" class="tab-pane fade">
            <form>
                <fieldset>
                    <legend class="legend_text">Pay your debt</legend>
                    <div class="title_text">Your debt is: </div>
                    <input type="text" class="text-center" id="debt_amount" 
                           size="30" readonly value="<%= debt%> &#8364">
                    <div class="title_text">Payoff:</div>
                    <input type="text" class="text-center" id ="payDebt"
                           placeholder="e.g 50.0" size="30" pattern="\d+(.\d+)?"><br><br>
                    <button type="button" class="btn btn-default btn_style"  
                            onclick="ajaxPayDebtRequest()">
                        Pay now
                    </button>
                </fieldset>
            </form>
        </div>
        <%}%>
        <div id="refund" class="tab-pane fade">
            <form>
                <fieldset>
                    <legend class="legend_text">Refund</legend>
                    <div class="title_text">Merchant:</div>
                    <select id="refundMerchantsDropdownContainer"></select>
                    <div class="title_text">Items Cost:</div>
                    <input type="text" class="text-center" id="payRefund"
                           placeholder="e.g 50.0" size="30" pattern="\d+(.\d+)?"><br><br>
                    <button type="button" class="btn btn-default btn_style"  
                            onclick="ajaxMakeTransactionRequest('credit')">
                        Refund now
                    </button>
                </fieldset>
            </form>
        </div>
        <div id="searchTab" class="tab-pane fade">
            <div id="searchResults"></div>
            <div id="search">
            </div>
        </div>

    </div>
    <div class="row" id="cccCustomersInfoContainer"></div>
</div>
