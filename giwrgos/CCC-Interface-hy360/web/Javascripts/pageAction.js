var navBut = ["home_but", "login_but", "logout_but", "open_account_but", "close_account_but"];

var elemVisibility = ["login_but", "logout_but", "open_account_but", "close_account_but"];

function renderPage() {
    "use strict";
    var i, elem, attr;
    for (i = 0; i < elemVisibility.length; i += 1) {
        elem = document.getElementById(elemVisibility[i]);
        attr = elem.getAttribute("data-visible");
        elem.style.display = attr;
    }
    toggleNav();
}

function activeNavButton(target) {
    "use strict";
    var i;
    for (i = 0; i < navBut.length; i += 1) {
        if (target === navBut[i]) {
            document.getElementById(navBut[i]).setAttribute("class", "active");
        } else {
            document.getElementById(navBut[i]).removeAttribute("class");
        }
    }
}

function toggleNav() {
    "use strict";
    var status;

    status = document.getElementById("toggle_but").getAttribute("class");
    if (status !== "navbar-toggle collapsed") {
        document.getElementById("toggle_but").click();
    }
}

function login_action() {
    document.getElementById("login_but").setAttribute("data-visible", "block");
    document.getElementById("open_account_but").setAttribute("data-visible", "block");
    document.getElementById("close_account_but").setAttribute("data-visible", "none");
    document.getElementById("logout_but").setAttribute("data-visible", "none");

    document.getElementById("welcome_container").style.display = "none";
    document.getElementById("main_container").style.display = "block";

    activeNavButton("login_but");
    renderPage();
}

function openAccount_action() {
    document.getElementById("login_but").setAttribute("data-visible", "block");
    document.getElementById("open_account_but").setAttribute("data-visible", "block");
    document.getElementById("close_account_but").setAttribute("data-visible", "none");
    document.getElementById("logout_but").setAttribute("data-visible", "none");

    document.getElementById("welcome_container").style.display = "none";
    document.getElementById("main_container").style.display = "block";

    activeNavButton("open_account_but");
    renderPage();
}

function userPage_action() {
    succeed_login_action();
}

function succeed_login_action() {
    document.getElementById("login_but").setAttribute("data-visible", "none");
    document.getElementById("open_account_but").setAttribute("data-visible", "none");
    document.getElementById("close_account_but").setAttribute("data-visible", "block");
    document.getElementById("logout_but").setAttribute("data-visible", "block");

    document.getElementById("welcome_container").style.display = "none";
    document.getElementById("main_container").style.display = "block";

    activeNavButton("home_but");
    renderPage();
}

function logout_action() {
    document.getElementById("login_but").setAttribute("data-visible", "block");
    document.getElementById("open_account_but").setAttribute("data-visible", "block");
    document.getElementById("close_account_but").setAttribute("data-visible", "none");
    document.getElementById("logout_but").setAttribute("data-visible", "none");

    document.getElementById("welcome_container").style.display = "block";
    document.getElementById("main_container").style.display = "none";

    activeNavButton("home_but");
    renderPage();
}

function enterPress(elem, event) {
    var x = event.keyCode;
    if (x === 13) {
        document.getElementById(elem).click();
    }
}

function pageReady() {
    document.getElementById("loadingModal").style.display = "none";
    document.getElementById("main_container").style.display = "block";
}

function pagePrepare() {
    document.getElementById("loadingModal").style.display = "block";
    document.getElementById("main_container").style.display = "none";
}

function getAccountType() {
    return document.getElementById("main_container").getAttribute("data-type");
}

function getAccountDebtValue() {
    return document.getElementById("debtValue").value;
}

function getMerchantID_buy() {
    return document.getElementById("buyMerchantsDropdownContainer").value;
}

function getMerchantID_refund() {
    return document.getElementById("refundMerchantsDropdownContainer").value;
}

function getUserID() {
    return document.getElementById("main_container").getAttribute("data-userID");
}

function updateMerchantDebt() {
    var value;
    value = document.getElementById("debtValue").innerHTML;
    document.getElementById("debt_amount").value = value;
}

function extraProperties() {
    var type = document.getElementById("user_account_type").value;
    if (type === "company") {
        document.getElementById("extraProperties").style.display = "none";
    } else {
        document.getElementById("extraProperties").style.display = "block";
    }
}

function addEmployeeAction() {
    document.getElementById("removeEmployeeContainer").style.display = "none";
    document.getElementById("addEmployeeContainer").style.display = "block";
}

function removeEmployeeAction() {
    document.getElementById("removeEmployeeContainer").style.display = "block";
    document.getElementById("addEmployeeContainer").style.display = "none";
    
    var type = document.employee.accountType.value;
    ajaxUsersDropdownRequest("halfCompany", type);
}

function emailCheckValidity() {
    if (!document.getElementById('login_form').checkValidity()) {
        document.getElementById('usr_login_error').innerHTML = 'Invalid email';
        document.getElementById('usr_login_error').style.color = 'red';
    } else {
        document.getElementById('usr_login_error').innerHTML = 'Valid email';
        document.getElementById('usr_login_error').style.color = 'green';
    }
}

function adminAction() {
    var customer;
    customer = document.getElementById("customerType").value;
    switch (customer) {
        case "company":
            document.getElementById("companyExtra").style.display = "inline-block";
            document.getElementById("searchButton").style.display = "inline-block";
            document.getElementById("allExtra").style.display = "inline-block";
            document.getElementById("main_container").setAttribute("data-type", "company");
            ajaxUsersDropdownRequest('allCompany', "company");
            document.getElementById("searchButton").disabled = true;
            break;
        case "civilian":
            document.getElementById("companyExtra").style.display = "none";
            document.getElementById("searchButton").style.display = "inline-block";
            document.getElementById("allExtra").style.display = "inline-block";
            document.getElementById("main_container").setAttribute("data-type", "civilian");
            ajaxUsersDropdownRequest('allCustomers', "civilian");
            document.getElementById("searchButton").disabled = true;
            document.getElementById("field1Value").value = "default";
            break;
        case "merchant":
            document.getElementById("companyExtra").style.display = "none";
            document.getElementById("searchButton").style.display = "inline-block";
            document.getElementById("allExtra").style.display = "inline-block";
            document.getElementById("main_container").setAttribute("data-type", "merchant");
            ajaxUsersDropdownRequest('allCustomers', "merchant");
            document.getElementById("searchButton").disabled = true;
            document.getElementById("field1Value").value = "default";
            break;
        default:
            document.getElementById("searchButton").style.display = "none";
            document.getElementById("allExtra").style.display = "none";
            document.getElementById("main_container").setAttribute("data-userID", "admin@ccc.gr");
            document.getElementById("main_container").setAttribute("data-type", "admin");
    }
}