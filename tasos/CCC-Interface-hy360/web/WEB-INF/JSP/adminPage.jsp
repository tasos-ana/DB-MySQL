<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">
    <h2>Admin dashboard</h2>
    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home" class="darkcolor" 
                              onclick="document.getElementById('home_link').click();">Home</a></li>
        <li><a data-toggle="tab" href="#moftm" class="darkcolor"
               onclick="ajaxUsersDropdownRequest('buy')">Merchant of the month</a></li>
        <li><a data-toggle="tab" href="#searchTab" class="darkcolor"
               onclick="ajaxSearchRequest()">Search</a></li>
    </ul>

    <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
        </div>
        <div id="moftm" class="tab-pane fade in">
            <p></p>
            <button class = "btn_style" onclick="ajaxBestMerchantsDiscountRequest()">Make discount on best Merchant</button>
        </div>
        <div id="searchTab" class="tab-pane fade">
            <div id="searchResults"></div>
            <div id="search">
            </div>
        </div>
    </div>
    <div class="row" id="cccCustomersInfoContainer"></div>
</div>
