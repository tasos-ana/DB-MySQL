/* global validationAPI */

function ajaxLoginRequest() {
    "use strict";
    var email, type, xhr, works, newType;
    email = document.getElementById("usr_email");

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
                        pageReady();
                        document.getElementById("main_container").innerHTML = xhr.responseText;
                    }
                }
            }
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
            document.getElementById("main_container").innerHTML = xhr.responseText;
            pageReady();
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', 'login');
    type = document.getElementById("user_account_type");
    works = document.getElementById("extraProperties");
    pagePrepare();
    if (email === null && type === null) {
        xhr.send();
    } else {
        if (type.value !== "company") {
            if (works.value === "yes") {
                newType = "employee_" + type.value;
            } else {
                newType = type.value;
            }
        } else {
            newType = type.value;
        }
        document.getElementById("main_container").setAttribute("data-type", type.value);
        xhr.send('email=' + email.value + '&type=' + newType);
    }

}

function ajaxOpenAccountRequest() {
    "use strict";
    var xhr, email, type, name;

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
        email = document.getElementById("usrEMAIL");
        name = document.getElementById("usrNAME");
        xhr.send('email=' + email.value + '&name=' + name.value + '&type=' + type.value);
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
    var xhr, currentDebt;
    currentDebt = document.getElementById("debtValue").innerHTML;
    if (currentDebt !== '0.0') {
        window.alert("Can't delete your account while you have debt's to other people");
    } else {
        xhr = new XMLHttpRequest();
        xhr.open('POST', 'CompanyServlet');
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                if (!cookieExist(xhr.getResponseHeader("fail"))) {
                    document.getElementById("home_but").click();
                } else {
                    if (xhr.getResponseHeader("error") !== null) {
                        window.alert(xhr.responseText);
                    } else {
                        window.alert("Your account close with succed!");
                        document.getElementById("page_message").innerHTML = "Credit Card Company";
                        logout_action();
                    }
                }
            } else if (xhr.status !== 200) {
                window.alert("Request failed. Returned status of " + xhr.status);
            }
        };
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('action', 'close');
        xhr.send();
    }

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
    if (validationAPI.form()) {
        var accountNumber, accountID, accountName, accountType, action;
    accountNumber = document.employee.accountNumber;
    accountID = document.employee.accountID;
    accountName = document.employee.accountName;
    accountType = document.employee.accountType;
    action = document.employee.action;
    var xhr;
    xhr = new XMLHttpRequest();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (!cookieExist(xhr.getResponseHeader("fail"))) {
                document.getElementById("home_but").click();
            } else {
                if (xhr.getResponseHeader("error") !== null) {
                    window.alert(xhr.responseText);
                } else {
                    window.alert("New Employee added successful");
                    document.employee.reset();
                    document.getElementById("usrNAME_err").innerHTML = "*";
                    document.getElementById("usrNAME_err").style.color = "red";
                    
                    document.getElementById("usrEMAIL_err").innerHTML = "*";
                    document.getElementById("usrEMAIL_err").style.color = "red";
                }
            }
            pageReady();
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', action.value);
    pagePrepare();
    xhr.send("accountNumber=" + accountNumber.value +
            "&accountID=" + accountID.value +
            "&accountName=" + accountName.value +
            "&accountType=" + accountType.value);
    }else{
        document.getElementById("form_alert").removeAttribute("hidden");
        document.getElementById("form_alert").addEventListener("mouseover", setTimeout(function () {
            document.getElementById("form_alert").setAttribute("hidden", "true");
        }, 2000));
        return;
    }
}

function ajaxRefreshUser() {
    "use strict";
    var xhr;
    xhr = new XMLHttpRequest();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (!cookieExist(xhr.getResponseHeader("fail"))) {
                document.getElementById("home_but").click();
            } else {
                var user;
                user = JSON.parse(xhr.responseText);
                if (user.type === "merchant") {
                    document.getElementById("cardNumber").innerHTML = user.cardNumber;
                    document.getElementById("cardHolder").innerHTML = user.cardHolder;
                    document.getElementById("totalProfit").innerHTML = user.totalProfit;
                    document.getElementById("debtValue").innerHTML = user.debtToCCC;
                    document.getElementById("supply").innerHTML = user.supply;
                } else {
                    document.getElementById("cardNumber").innerHTML = user.cardNumber;
                    document.getElementById("cardHolder").innerHTML = user.cardHolder;
                    document.getElementById("cardExpired").innerHTML = user.expiredThru;
                    document.getElementById("cardLimit").innerHTML = user.creditLimit;
                    document.getElementById("availableCreditBalance").innerHTML = user.availableCreditBalance;
                    document.getElementById("debtValue").innerHTML = user.currentDebt;
                }
            }
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
        pageReady();
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', 'refresh');
    pagePrepare();
    xhr.send();
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