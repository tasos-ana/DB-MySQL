<%-- 
    Document   : merchantPage
    Created on : Dec 20, 2016, 10:29:06 PM
    Author     : Tasos
--%>

<%@page import="cs360db.model.MerchantCreditCard"%>
<%@page import="cs360db.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ServletContext context = getServletContext();

    if (context.getAttribute("data") instanceof User) {
        User user = (User) context.getAttribute("data");
        MerchantCreditCard cc;
        if (user.getCard() instanceof MerchantCreditCard) {
            cc = (MerchantCreditCard) user.getCard();
        } else {
            cc = null;
            assert (false);
        }
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
    <h2>Merchant dashboard</h2>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor"
                                onclick="ajaxRefreshUser()">Home</a></li>
        <li><a data-toggle="tab" href="#debt" class="darkcolor"
                                onclick="updateMerchantDebt()">Debt</a></li>
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
                                <th>Total profit</th>
                                <th>Debt to CCC</th>
                                <th>Supply</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="text-left">
                                <td id="accountNo">1</td>
                                <td id="cardNumber"> <%= cc.getAccountNumber()%> </td>
                                <td id="cardHolder"> <%= user.getName() %> </td>
                                <td><span id="totalProfit"> <%=cc.getTotalProfit()%> </span> &#8364</td>
                                <td><span id="debtValue"> <%=cc.getCurrentDebt()%> </span> &#8364</td>
                                <td><span id="supply"> <%=cc.getSupply()%> </span> &#8364</td>
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
                    <span><input type="text" class="text-center" id="debt_amount" size="30" readonly value="<%=cc.getCurrentDebt()%>">&#8364</span>
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
            <form>
                <fieldset>
                    <legend class="legend_text">Search</legend>
                    <fieldset>
                        <legend class="legend_text">Dealings</legend>
                        <span>Date from: <input type="text" id="datepickerfrom" size='11'></span>
                        <span>Date before: <input type="text" id="datepickerbefore" size='11'></span><br>
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
<%  } else {
        System.out.println("merchantPage.jsp: attribute \"data\" should contain a 'User' object");
    }
%>