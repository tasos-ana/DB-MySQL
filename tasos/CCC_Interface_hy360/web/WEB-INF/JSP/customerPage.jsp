<%-- 
    Document   : customerPage
    Created on : Dec 20, 2016, 10:28:51 PM
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
    <form class="col-sm-3">
        <fieldset>
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
    <div class="col-sm-1"></div>
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
    <div class="col-sm-2"></div>
    <form class="col-sm-3">
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
<form>
    <fieldset>
        <legend>Search Dealings</legend>
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


    </fieldset>
</form>
