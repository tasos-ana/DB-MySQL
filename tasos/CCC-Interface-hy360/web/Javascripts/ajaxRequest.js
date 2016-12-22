/* global validationAPI */

function ajaxLoginRequest() {
    "use strict";
    var email, type, xhr;
    email = document.getElementById("usr_email");
    if (email !== null && email.value === "") {
        document.getElementById("usr_login_error").innerHTML = "Invalid email";
        document.getElementById("usr_login_error").style.color = "red";
    } else {
        xhr = new XMLHttpRequest();
        xhr.open('POST', 'CompanyServlet');
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                if (xhr.getResponseHeader("error") === null) {
                    var email = xhr.getResponseHeader("id");
                    setWelcomeMessage(email);
                    document.getElementById("main_container").innerHTML = xhr.responseText;
                    succeed_login_action();
                    pageReady();
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
                            document.getElementById("main_container").innerHTML = xhr.responseText;
                        }
                    }
                }
            } else if (xhr.status !== 200) {
                window.alert("Request failed. Returned status of " + xhr.status);
            }
        };
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('action', 'login');
        type = document.getElementById("user_account_type");
        email = document.getElementById("usr_email");
        pagePrepare();
        if (email === null && type === null) {
            xhr.send();
        } else {
            xhr.send('email=' + email.value + '&type=' + type.value);
        }
    }
}

function ajaxOpenAccountRequest() {
    "use strict";
    var xhr, email, type;

    if (validationAPI.form()) {
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
                    succeed_login_action();
                }
            } else if (xhr.status !== 200) {
                window.alert("Request failed. Returned status of " + xhr.status);
            }
        };
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('action', 'open');
        document.getElementById("loadingModal").style.display = "block";

        type = document.getElementById("user_account_type");
        email = document.getElementById("usr_email");
        xhr.send('email=' + email.value + '&type=' + type.value);
    } else {
        document.getElementById("form_alert").removeAttribute("hidden");
        document.getElementById("form_alert").addEventListener("mouseover", setTimeout(function () {
            document.getElementById("form_alert").setAttribute("hidden", "true");
        }, 2000));
        return;
    }
}

function ajaxCloseAccountRequest() {
    "use strict";
    var xhr;
    xhr = new XMLHttpRequest();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (!cookieExist(xhr.getResponseHeader("fail"))) {
                document.getElementById("home_but").click();
            } else {
                document.getElementById("page_message").innerHTML = "Credit Card Company";
                logout_action();
            }
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', 'close');
    xhr.send();
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
                document.getElementById("page_message").innerHTML = "Credit Card Company";
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

function ajaxEmployeeAction() {
    var accountNumber, accountName, accountType, action;
    accountNumber = document.employee.accountNumber;
    accountName = document.employee.accountName;
    accountType = document.employee.accountType;
    action = document.employee.action;
    if (accountName.checkValidity()) {
        var xhr;
        xhr = new XMLHttpRequest();
        xhr.open('POST', 'CompanyServlet');
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                if (!cookieExist(xhr.getResponseHeader("fail"))) {
                    document.getElementById("home_but").click();
                } else {
                    //TODO
                }
            } else if (xhr.status !== 200) {
                window.alert("Request failed. Returned status of " + xhr.status);
            }
        };
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('action', action.value);
        xhr.send("accountNumber=" + accountNumber.value + 
                 "&accountName="  + accountName.value   + 
                 "&accountType=" + accountType.value);
    }
}

function setWelcomeMessage(email) {
    if (email !== null) {
        document.getElementById("page_message").innerHTML = email;
        document.getElementById("page_message").setAttribute("data-email", email.split(" ")[1]);
    } else {
        document.getElementById("page_message").innerHTML = "Credit Card Company";
    }
}

function cookieExist(failError) {
    if (failError !== null && failError === "Missing Cookie") {
        return 0;
    } else {
        return 1;
    }
}