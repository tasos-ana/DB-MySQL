<%-- 
    Document   : merchantPage
    Created on : Dec 20, 2016, 10:29:06 PM
    Author     : Tasos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    $(function () {
        $("#datepickerfrom").datepicker();
    });
    $(function () {
        $("#datepickerbefore").datepicker();
    });
</script>
<div class="row">

    <div class="col-sm-1"></div>

</div>

<div class="container">
    <h2>Merchant dashboard</h2>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor">Home</a></li>
        <li><a data-toggle="tab" href="#debt" class="darkcolor">Debt</a></li>
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
        <div id="debt" class="tab-pane fade">
            <form class="col-sm-3">
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
        <div id="search" class="tab-pane fade">
            <form class="col-sm-8">
                <fieldset>
                    <legend>Search</legend>
                    <fieldset>
                        <legend>Dealings</legend>
                        <div>Date from: <input type="text" id="datepickerfrom"></div>
                        <div>Date before: <input type="text" id="datepickerbefore"></div><br>
                        <button type="button" class="btn btn-default btn_style"  
                                onclick="">
                            Refund now
                        </button>
                    </fieldset>
                </fieldset>
            </form>
        </div>
    </div>
</div>
