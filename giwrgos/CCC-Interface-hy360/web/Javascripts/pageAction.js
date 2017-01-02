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

function getAccountName() {
    return document.getElementById("cardHolder").value;
}

function getAccountNumber() {
    return document.getElementById("cardNumber").value;
}

function getAccountType() {
    return document.getElementById("main_container").getAttribute("data-type");
}

function getAccountCardLimit() {
    return document.getElementById("cardLimit").value;
}

function getAccountDebtValue() {
    return document.getElementById("debtValue").value;
}
