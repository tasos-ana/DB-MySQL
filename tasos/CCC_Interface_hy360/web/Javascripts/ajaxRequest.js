function ajaxLoginRequest() {
    "use strict";
    var email, type;

    xhr = new XMLHttpRequest();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (xhr.getResponseHeader("error") === null) {
                var email = xhr.getResponseHeader("id");
                setWelcomeMessage(email);
                document.getElementById("main_container").innerHTML = xhr.responseText;
                succeed_login_action();
            } else {
                if (!cookieExist(xhr.getResponseHeader("fail"))) {
                    document.getElementById("login_but").click();
                } else {
                    renderPage();
                    try {
                        document.getElementById("usr_login_error").innerHTML = xhr.getResponseHeader("error");
                        document.getElementById("usr_login_error").style.color = "red";
                        pageReady();
                    } catch (err) {
                        document.getElementById("main_container").innerHTML = XSSValidator(xhr.responseText);
                    }
                }
            }
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', 'login');
    type = document.getElementById("user_account_type").value;
    xhr.setRequestHeader('type', type);

    email = document.getElementById("usr_email");
    pagePrepare();
    if (email === null) {
        xhr.send();
    } else {
        xhr.send('email=' + email);
    }
}

function ajaxCreateAccountRequest() {
    "use strict";
    var xhr, email;
    xhr = new XMLHttpRequest();
    //send data for register
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        pageReady();
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (xhr.getResponseHeader("error") !== null) {
                var err = xhr.getAllResponseHeader("error");
                document.getElementById("usrEMAIL_err").innerHTML = err;
            } else {
                document.getElementById("main_container").innerHTML = xhr.responseText;
            }
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', 'register');
    type = document.getElementById("user_account_type").value;
    xhr.setRequestHeader('type', type);
    document.getElementById("loadingModal").style.display = "block";

    email = document.getElementById("usr_email");
    xhr.send('email=' + email);
}

function ajaxCloseAccountRequest() {

}

function ajaxLogoutRequest() {
    "use strict";
    var xhr;
    xhr = new XMLHttpRequest();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (!cookieExist(xhr.getResponseHeader("fail"))) {
                document.getElementById("home_but").click();
            } else {
                document.getElementById("main_container").innerHTML = xhr.responseText;
                logout_action();
            }
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', 'logout');
    xhr.send();
}

function setWelcomeMessage(email) {
    if (email !== null) {
        document.getElementById("page_message").innerHTML = email;
        document.getElementById("page_message").setAttribute("data-email", email.split(" ")[1]);
    } else {
        document.getElementById("page_message").innerHTML = XSSValidator("Credit Card Company");
    }
}

function cookieExist(failError) {
    if (failError !== null && failError === "Missing Cookie") {
        return 0;
    } else {
        return 1;
    }
}