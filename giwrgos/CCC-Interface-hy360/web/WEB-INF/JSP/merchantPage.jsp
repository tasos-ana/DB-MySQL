<%-- 
    Document   : merchantPage
    Created on : Dec 20, 2016, 10:29:06 PM
    Author     : Tasos
--%>

<%@page import="cs360db.model.Merchant"%>
<%@page import="cs360db.model.Company"%>
<%@page import="cs360db.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ServletContext context = getServletContext();
    assert (context.getAttribute("data") instanceof User);
    User user = (User) context.getAttribute("data");
    context.removeAttribute("data"); // clear after use
    String name, companyId, companyName;
    double debt, commission, totalProfit;
    int accountNumber;

    assert (user.isMerchant() || user.isEmployeeMerchant());
    if (user.isMerchant()) {
        Merchant c = user.getMerchant();
        name = c.getName();
        commission = c.getCommission();
        totalProfit = c.getTotalProfit();
        companyId = null;
        companyName = null;
        debt = c.getDebt();
        accountNumber = c.getAccountNumber();
    } else {
        Company c = user.getCompany();
        Merchant m = user.getEmployeeMerchant();
        debt = m.getDebt();
        name = m.getName();
        commission = m.getCommission();
        totalProfit = m.getTotalProfit();
        companyId = c.getId();
        companyName = c.getName();
        accountNumber = c.getAccountNumber();
    }
%>
<div class="container">
    <h2>Merchant dashboard</h2>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor"
                              onclick="document.getElementById('home_link').click();">Home</a></li>
        <li><a data-toggle="tab" href="#debt" class="darkcolor"
               onclick="updateMerchantDebt()">Debt</a></li>
        <li><a data-toggle="tab" href="#searchTab" class="darkcolor"
               onclick="ajaxSearchRequest(); ajaxUsersDropdownRequest('searchCivilian')">Search</a></li>
    </ul>
    <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
            <h2 class="text-center">Your account details</h2>
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
                                <th>Total profit</th>
                                <th>Debt to CCC</th>
                                <th>Commission</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="text-left">
                                <td id="accountNo">1</td>
                                <%if (companyId != null) {%>            
                                <td id="companyName"><%= companyName%></td>
                                <td id="companyId"><%= companyId%></td>
                                <%}%>
                                <td id="cardNumber"><%= accountNumber%></td>
                                <td id="cardHolder"><%= name%></td>
                                <td><span id="totalProfit"><%= totalProfit%></span> &#8364</td>
                                <td><span id="debtValue"><%= debt%></span> &#8364</td>
                                <td><span id="supply"><%= commission%></span>%</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="debt" class="tab-pane fade">
            <form>
                <fieldset>
                    <legend class="legend_text">Pay your debt</legend>
                    <div class="title_text">Your debt is: </div>
                    <span><input type="text" class="text-center" id="debt_amount"
                                 size="30" readonly value="<%= debt%>&#8364"></span>
                    <div class="title_text">Payoff:</div>
                    <input type="number" class="text-center" id="payDebt"
                           placeholder="e.g 50.0" size="30" pattern="\d+(.\d+)?"><br><br>
                    <button type="button" class="btn btn-default btn_style"  
                            onclick="ajaxPayDebtRequest()">
                        Pay now
                    </button>
                </fieldset>
            </form>
        </div>
        <div id="searchTab" class="tab-pane fade">
            <div id="searchResults"></div>
            <div id="search"></div>
        </div>
        <div class="row" id="cccCustomersInfoContainer"></div>
    </div>