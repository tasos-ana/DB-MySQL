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
        <li><a data-toggle="tab" href="#removeEmployee" class="darkcolor">Remove employee</a></li>
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

        </div>

        <div id="removeEmployee" class="tab-pane fade">

        </div>

        <div id="search" class="tab-pane fade">

        </div>
    </div>
</div>