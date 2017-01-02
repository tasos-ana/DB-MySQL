<%-- 
    Document   : customerPage
    Created on : Dec 20, 2016, 10:28:51 PM
    Author     : Tasos
--%>

<%@page import="cs360db.model.Civilian"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ServletContext context = getServletContext();

    if (context.getAttribute("data") instanceof Civilian) {
        Civilian user = (Civilian) context.getAttribute("data");
        context.removeAttribute("data"); // clear after use
%>
<script>
    $(function () {
        $("#datepickerfrom").datepicker();
    });
    $(function () {
        $("#datepickerbefore").datepicker();
    });
</script>
<div class="container">
    <h2>Customer dashboard</h2>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor" 
                              onclick="ajaxRefreshCivilian()">Home</a></li>
        <li><a data-toggle="tab" href="#buy" class="darkcolor">Buy</a></li>
        <li><a data-toggle="tab" href="#debt" class="darkcolor">Debt</a></li>
        <li><a data-toggle="tab" href="#refund" class="darkcolor">Refund</a></li>
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
        <div id="buy" class="tab-pane fade">
            <form>
                <fieldset class="fieldset-auto-width">
                    <legend class="legend_text">Buy goods</legend>
                    <div class="title_text">Merchant:</div>
                    <input type="text" class="text-center" placeholder="Required" size="30" pattern="[a-zA-Z0-9]+"><br>
                    <div class="title_text">Items Cost:</div>
                    <input type="text" class="text-center" placeholder="e.g 50,00" size="30" pattern="\d+(,\d{2})?"><br><br>
                    <button type="button" class="btn btn-default btn_style"  
                            onclick="">
                        Order now
                    </button>
                </fieldset>
            </form>
        </div>
        <div id="debt" class="tab-pane fade in">
            <form>
                <fieldset>
                    <legend class="legend_text">Pay your debt</legend>
                    <div class="title_text">Your debt is: </div>
                    <input type="text" class="text-center" id="debt_amount" size="30" readonly value=" 0,00 &#8364">
                    <div class="title_text">Payoff:</div>
                    <input type="text" class="text-center" placeholder="e.g 50,00" size="30" pattern="\d+(,\d{2})?"><br><br>
                    <button type="button" class="btn btn-default btn_style"  
                            onclick="">
                        Pay now
                    </button>
                </fieldset>
            </form>
        </div>
        <div id="refund" class="tab-pane fade">
            <form>
                <fieldset>
                    <legend>Refund</legend>
                    <div class="title_text">Merchant:</div>
                    <input type="text" class="text-center" placeholder="Required" size="30" pattern="[a-zA-Z0-9]+"><br>
                    <div class="title_text">Items Cost:</div>
                    <input type="text" class="text-center" placeholder="e.g 50,00" size="30" pattern="\d+(,\d{2})?"><br><br>
                    <button type="button" class="btn btn-default btn_style"  
                            onclick="">
                        Refund now
                    </button>
                </fieldset>
            </form>
        </div>
        <div id="search" class="tab-pane fade">
            <form>
                <!--TODO na mpei ena tab me diafora search-->
                <fieldset>
                    <legend>Based on Dates</legend>
                    <div>Date from: <input type="text" id="datepickerfrom"></div>
                    <div>Date before: <input type="text" id="datepickerbefore"></div><br>
                    <button type="button" class="btn btn-default btn_style"  
                            onclick="">
                        Search now
                    </button>
                </fieldset>
            </form>
        </div>
    </div>
</div>
<%  } else {
        System.out.println("customerPage.jsp: attribute \"data\" should contain a 'Civilian' object");
    }
%>