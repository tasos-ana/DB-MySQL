<%-- 
    Document   : companyPage
    Created on : Dec 20, 2016, 10:29:17 PM
    Author     : Tasos
--%>

<%@page import="cs360db.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--TODO oi etairies na mporoun na kanoun add enan ergazomeno me ton logariasmo tous-->

<%
    ServletContext context = getServletContext();

    if (context.getAttribute("data") instanceof User) {
        User user = (User) context.getAttribute("data");
        context.removeAttribute("data"); // clear after use    
%>
<div class="container">
    <h2>Company dashboard</h2>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor"
                              onclick="ajaxRefreshUser()">Home</a></li>
        <li><a data-toggle="tab" href="#addEmployee" class="darkcolor">Add employee</a></li>
        <li><a data-toggle="tab" href="#search" class="darkcolor">Search</a></li>
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
                                <td id="cardNumber"> <%= user.getCard().getAccountNumber()%> </td>
                                <td id="cardHolder"> <%= user.getName()%></td>
                                <td id="cardExpired"><%= user.getCard().getValidThru()%></td>
                                <td><span id="cardLimit"><%= user.getCard().getCreditLimit()%></span> &#8364</td>
                                <td><span id="availableCreditBalance"><%= user.getCard().getAvailableCreditBalance()%></span> &#8364</td>
                                <td><span id="debtValue"><%= user.getCard().getCurrentDebt()%></span> &#8364</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="addEmployee" class="tab-pane fade">
            <form name="employee">
                <div class='row col-sm-8'>
                    <div class='col-sm-1'></div>
                    <div class="title_text col-sm-4">Account number:</div>
                    <div class='col-sm-1'></div>
                    <input class="col-sm-5" type="text" name="accountNumber" value="<%=user.getCard().getAccountNumber()%>"  readonly>
                    <div class='col-sm-1'></div>
                </div>

                <div class="row col-sm-8">
                    <div class='col-sm-1'></div>
                    <div class="title_text col-sm-4">Employee id:</div>
                    <div class='col-sm-1'></div>
                    <input class='col-sm-5' type="text" name="accountID" id="usrEMAIL"
                           placeholder="Type the employee login email" onchange="validationAPI.usrEMAIL()">
                    <div id="usrEMAIL_err" class="col-sm-1 text-left" style="color: red;">*</div>
                </div>

                <div class="row col-sm-8">
                    <div class='col-sm-1'></div>
                    <div class="title_text col-sm-4">Employee name:</div>
                    <div class='col-sm-1'></div>
                    <input class='col-sm-5' type="text" name="accountName" id='usrNAME'
                           placeholder="e.g Barrack" required size="30" onchange="validationAPI.usrNAME()">
                    <div id="usrNAME_err" class="col-sm-1 text-left" style="color: red;">*</div>
                </div>

                <div class="row col-sm-8">
                    <div class='col-sm-1'></div>
                    <div class="title_text col-sm-4">Account type:</div>
                    <div class='col-sm-1'></div>
                    <div class='col-sm-5'>
                        <input type="radio" name="accountType" value="civilian" checked>Customer
                        <input type="radio" name="accountType" value="merchant">Merchant
                    </div>
                    <div class='col-sm-1'></div>
                </div>

                <div class="row col-sm-8">
                    <div class='col-sm-1'></div>
                    <div class="title_text col-sm-4">Action:</div>
                    <div class='col-sm-1'></div>
                    <div class='col-sm-5'>
                        <input type="radio" name="action" value="addEmployee" checked>Add
                        <input type="radio" name="action" value="removeEmployee">Remove
                    </div>
                    <div class='col-sm-1'></div>
                </div>
                <div id="form_alert" class="alert alert-danger" hidden>
                    <strong>Incomplete form!</strong> You should check it again.
                </div>
                <button type="button" class="btn_style" onclick="ajaxEmployeeAction()">Submit</button>
            </form>
        </div>
        <div id="search" class="tab-pane fade">

        </div>
    </div>
</div>
<%  } else {
        System.out.println("companyPage.jsp: attribute \"data\" should contain a 'User' object");
    }
%>