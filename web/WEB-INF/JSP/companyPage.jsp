<%-- 
    Document   : companyPage
    Created on : Dec 20, 2016, 10:29:17 PM
    Author     : Tasos
--%>

<%@page import="cs360db.model.Company"%>
<%@page import="cs360db.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    ServletContext context = getServletContext();
    assert (context.getAttribute("data") instanceof User);
    User user = (User) context.getAttribute("data");
    context.removeAttribute("data"); // clear after use
    String name, ID, validThru;
    double debt, creditBalance, creditLimit;
    int accountNumber;

    assert (user.isCompany());
    Company c = user.getCompany();
    ID = c.getId();
    name = c.getName();
    debt = c.getDebt();
    creditBalance = c.getCreditBalance();
    creditLimit = c.getCreditLimit();
    accountNumber = c.getAccountNumber();
    validThru = c.getValidThru().toString();

%>
<div class="container">
    <h2>Company dashboard</h2>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor"
                              onclick="document.getElementById('home_link').click();">Home</a></li>
        <li><a data-toggle="tab" href="#addEmployee" class="darkcolor">Employee Actions</a></li>
        <li><a data-toggle="tab" href="#debt" class="darkcolor"> Debt </a></li>
        <li><a data-toggle="tab" href="#searchTab" class="darkcolor" 
               onclick="ajaxSearchRequest(); ajaxUsersDropdownRequest('searchCompany')">Search</a></li>
    </ul>

    <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
            <h2 class="text-center" id="companyID" data-companyID ="<%=ID%>">Your account details</h2>
            <div class="container">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr class="text-left">
                                <th>#</th>
                                <th>Card number</th>
                                <th>Card holder</th>
                                <th>Expired thru</th>
                                <th>Credit limit</th>
                                <th>Available credit balance </th>
                                <th>Debt's</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="text-left">
                                <td id="accountNo">1</td>
                                <td id="cardNumber"><%= accountNumber%> </td>
                                <td id="cardHolder"><%= name%></td>
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
        <div id="addEmployee" class="tab-pane fade">
            <form name="employee">
                <div id="addEmployeeContainer">
                    <p></p>
                    <div class='row'>
                        <div class='col-sm-3'></div>
                        <div class="title_text col-sm-2">Account number:</div>
                        <div class='col-sm-1'></div>
                        <input class="col-sm-3" type="text" name="accountNumber" value="<%= accountNumber%>"  readonly>
                        <div class='col-sm-3'></div>
                    </div>
                    <p></p>
                    <div class="row">
                        <div class='col-sm-3'></div>
                        <div class="title_text col-sm-2">Employee id:</div>
                        <div class='col-sm-1'></div>
                        <input class='col-sm-3' type="text" name="accountID" id="usrEMAIL"
                               placeholder="Type the employee login email" onchange="validationAPI.usrEMAIL()">
                        <div id="usrEMAIL_err" class="col-sm-3 text-left" style="color: red;">*</div>
                    </div>
                    <p></p>
                    <div class="row">
                        <div class='col-sm-3'></div>
                        <div class="title_text col-sm-2">Employee name:</div>
                        <div class='col-sm-1'></div>
                        <input class='col-sm-3' type="text" name="accountName" id='usrNAME'
                               placeholder="e.g Barrack" required size="30" onchange="validationAPI.usrNAME()">
                        <div id="usrNAME_err" class="col-sm-3 text-left" style="color: red;">*</div>
                    </div>
                </div>
                <div id="removeEmployeeContainer">
                    <p></p>
                    <div class="row">
                        <div class='col-sm-3'></div>
                        <div class="title_text col-sm-2">Employee id:</div>
                        <div class='col-sm-1'></div>
                        <select id="employeeName" class='col-sm-3'></select>
                    </div>
                </div>
                <p></p>
                <div class="row">
                    <div class='col-sm-3'></div>
                    <div class="title_text col-sm-2">Account type:</div>
                    <div class='col-sm-1'></div>
                    <div class='col-sm-3'>
                        <input type="radio" name="accountType" value="civilian" checked  
                               onclick="ajaxUsersDropdownRequest('halfCompany', 'civilian')">Civilian
                        <input type="radio" name="accountType" value="merchant" 
                               onclick="ajaxUsersDropdownRequest('halfCompany', 'merchant')">Merchant
                    </div>
                    <div class='col-sm-3'></div>
                </div>
                <p></p>
                <div class="row">
                    <div class='col-sm-3'></div>
                    <div class="title_text col-sm-2">Action:</div>
                    <div class='col-sm-1'></div>
                    <div class='col-sm-3'>
                        <input type="radio" name="action" value="addEmployee" checked onclick="addEmployeeAction()">Add
                        <input type="radio" name="action" value="removeEmployee" onclick="removeEmployeeAction()">Remove
                    </div>
                    <div class='col-sm-3'></div>
                </div>
                <div class="row">       
                    <div id="form_alert" class="alert alert-danger" hidden>
                        <strong>Incomplete form!</strong> You should check it again.
                    </div>
                    <button type="button" class="btn_style" onclick="ajaxEmployeeAction()">Submit</button>
                </div>
            </form>
        </div>
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
        <div id="searchTab" class="tab-pane fade">
            <div id="searchResults"></div>
            <div id="search">
            </div>
        </div>
    </div>
    <div class="row" id="cccCustomersInfoContainer"></div>
</div>