var navBut = ["home_but", "login_but", "logout_but", "create_account_but", "close_account_but"];

var elemVisibility = ["login_but", "logout_but", "create_account_but", "close_account_but"];

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
    document.getElementById("create_account_but").setAttribute("data-visible", "block");
    document.getElementById("close_account_but").setAttribute("data-visible", "none");
    document.getElementById("logout_but").setAttribute("data-visible", "none");

    activeNavButton("login_but");
    renderPage();
}

function createAccount_action() {
    document.getElementById("login_but").setAttribute("data-visible", "block");
    document.getElementById("create_account_but").setAttribute("data-visible", "block");
    document.getElementById("close_account_but").setAttribute("data-visible", "none");
    document.getElementById("logout_but").setAttribute("data-visible", "none");

    activeNavButton("createAccount_but");
    renderPage();
}

function succeed_login_action() {
    document.getElementById("login_but").setAttribute("data-visible", "none");
    document.getElementById("create_account_but").setAttribute("data-visible", "none");
    document.getElementById("close_account_but").setAttribute("data-visible", "block");
    document.getElementById("logout_but").setAttribute("data-visible", "block");

    activeNavButton("home_but");
    renderPage();
}

function logout_action() {
    document.getElementById("login_but").setAttribute("data-visible", "block");
    document.getElementById("create_account_but").setAttribute("data-visible", "block");
    document.getElementById("close_account_but").setAttribute("data-visible", "none");
    document.getElementById("logout_but").setAttribute("data-visible", "none");

    activeNavButton("logout_but");
    renderPage();
}

function pageReady() {
    document.getElementById("loadingModal").style.display = "none";
    document.getElementById("main_container").style.display = "block";
}

function pagePrepare() {
    document.getElementById("loadingModal").style.display = "block";
    document.getElementById("main_container").style.display = "none";
}