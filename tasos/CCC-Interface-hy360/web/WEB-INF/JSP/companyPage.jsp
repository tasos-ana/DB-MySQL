<%-- 
    Document   : companyPage
    Created on : Dec 20, 2016, 10:29:17 PM
    Author     : Tasos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--TODO oi etairies na mporoun na kanoun add enan ergazomeno me ton logariasmo tous-->
<div class="container">
    <h2>Company dashboard</h2>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor">Home</a></li>
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
                                <th>Debt's</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="text-left">
                                <td id="accountNo">1</td>
                                <td id="cardNumber">1234 5678 9123 4567</td>
                                <td id="cardHolder">Akakies Ta makaronia</td>
                                <td id="cardExpired">2018</td>
                                <td><span id="cardLimit">100,00</span> &#8364</td>
                                <td><span id="debtValue">0,00</span> &#8364</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="addEmployee" class="tab-pane fade">
            <form name="employee">
                <div class="row">
                    <div class="col-sm-3"></div>
                    <div class="title_text col-sm-2">
                        Account number:
                        <input type="text" name="accountNumber" value="1234 5678 9123 4567"  readonly>
                    </div>
                    <div class="col-sm-2"></div>
                    <div class="title_text col-sm-2">
                        Employee name:
                        <input type="text" name="accountName" placeholder="e.g Tasos" required size="30" pattern="[a-zA-Z0-9]+">
                    </div>
                    <div class="col-sm-3"></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"></div>
                    <div class="col-sm-2">
                        <span class="title_text">Account type:</span><br>
                        <input type="radio" name="accountType" value="customer" checked>Customer
                        <input type="radio" name="accountType" value="merchant">Merchant
                    </div>
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">
                        <span class="title_text">Action:</span><br>
                        <input type="radio" name="action" value="addEmployee" checked>Add
                        <input type="radio" name="action" value="removeEmployee">Remove
                    </div>
                    <div class="col-sm-3"></div>
                </div>
                
                <button type="button" class="btn_style" onclick="ajaxEmployeeAction()">Submit</button>
            </form>
        </div>
        <div id="search" class="tab-pane fade">

        </div>
    </div>
</div>