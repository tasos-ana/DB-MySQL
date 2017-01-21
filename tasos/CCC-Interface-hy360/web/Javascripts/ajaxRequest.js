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
                document.getElementById("main_container").setAttribute("data-type", xhr.getResponseHeader("dataType"));
                document.getElementById("main_container").setAttribute("data-userID", xhr.getResponseHeader("dataEmail"));
                document.getElementById("main_container").innerHTML = xhr.responseText;
                succeed_login_action();
                pageReady();
            } else {
                document.getElementById("main_container").removeAttribute("data-type");
                document.getElementById("main_container").removeAttribute("data-userID");
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
    pagePrepare();
    if (email === null && type === null) {
        xhr.send();
    } else {
        if (!document.getElementById("login_form").checkValidity()) {
            document.getElementById("usr_login_error").innerHTML = "Invalid email";
            document.getElementById("usr_login_error").style.color = "red";
            pageReady();
        } else {
            works = document.login.employee;
            if (type.value !== "company") {
                if (works.value === "yes") {
                    newType = "employee_" + type.value;
                } else {
                    newType = type.value;
                }
            } else {
                newType = type.value;
            }
            document.getElementById("main_container").setAttribute("data-type", newType);
            document.getElementById("main_container").setAttribute("data-userID", email.value);
            xhr.send('email=' + email.value + '&type=' + newType);
        }
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
                    document.getElementById("main_container").removeAttribute("data-type");
                    document.getElementById("main_container").removeAttribute("data-userID");
                } else {
                    var email = xhr.getResponseHeader("id");
                    setWelcomeMessage(email);
                    document.getElementById("main_container").innerHTML = xhr.responseText;
                    succeed_login_action();
                }
            } else if (xhr.status !== 200) {
                window.alert("Request failed. Returned status of " + xhr.status);
                document.getElementById("main_container").removeAttribute("data-type");
                document.getElementById("main_container").removeAttribute("data-userID");
            }
        };
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('action', 'open');
        document.getElementById("loadingModal").style.display = "block";

        type = document.getElementById("user_account_type");
        email = document.getElementById("usrEMAIL");
        name = document.getElementById("usrNAME");
        document.getElementById("main_container").setAttribute("data-type", type.value);
        document.getElementById("main_container").setAttribute("data-userID", email.value);
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
                        window.alert(xhr.getResponseHeader("error"));
                    } else {
                        window.alert("Your account close with succed!");
                        document.getElementById("page_message").innerHTML = "Credit Card Company";
                        logout_action();
                        document.getElementById("main_container").removeAttribute("data-type");
                        document.getElementById("main_container").removeAttribute("data-userID");
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
                document.getElementById("main_container").removeAttribute("data-type");
                document.getElementById("main_container").removeAttribute("data-userID");
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
        var companyID, accountID, accountName, accountType, action;
        companyID = document.getElementById("companyID").getAttribute("data-companyID");

        accountName = document.employee.accountName;
        accountType = document.employee.accountType;
        action = document.employee.action;
        if (action.value === "addEmployee") {
            accountID = document.employee.accountID;
        } else {
            accountID = document.employee.removeAccountId;
        }
        var xhr;
        xhr = new XMLHttpRequest();
        xhr.open('POST', 'CompanyServlet');
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                if (!cookieExist(xhr.getResponseHeader("fail"))) {
                    document.getElementById("home_but").click();
                } else {
                    if (xhr.getResponseHeader("error") !== null) {
                        window.alert(xhr.getResponseHeader("error"));
                    } else {
                        window.alert(xhr.getResponseHeader("succeed"));
                        document.employee.reset();
                        addEmployeeAction();
                        document.getElementById("usrNAME_err").innerHTML = "*";
                        document.getElementById("usrNAME_err").style.color = "red";

                        document.getElementById("usrEMAIL_err").innerHTML = "*";
                        document.getElementById("usrEMAIL_err").style.color = "red";

                        document.getElementById("removeUsrEMAIL_err").innerHTML = "*";
                        document.getElementById("removeUsrEMAIL_err").style.color = "red";
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
        xhr.send("companyID=" + companyID +
                "&accountID=" + accountID.value +
                "&accountName=" + accountName.value +
                "&accountType=" + accountType.value);
    } else {
        document.getElementById("form_alert").removeAttribute("hidden");
        document.getElementById("form_alert").addEventListener("mouseover", setTimeout(function () {
            document.getElementById("form_alert").setAttribute("hidden", "true");
        }, 2000));
        return;
    }
}

function ajaxMerchantsDropdownRequest() {
    var xhr;
    xhr = new XMLHttpRequest();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (!cookieExist(xhr.getResponseHeader("fail"))) {
                document.getElementById("home_but").click();
            } else {
                if (xhr.getResponseHeader("error") !== null) {
                    window.alert(xhr.getResponseHeader("error"));
                } else {
                    document.getElementById("merchantsDropdownContainer").innerHTML = xhr.responseText;
                }
            }
            pageReady();
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', 'merchantDropdown');
    xhr.send();
}

function ajaxMakeTransactionRequest() {
    "use strict";
    var xhr, civilianID, merchantID, civilianType, transType, value;
    civilianID = getUserID();
    merchantID = getMerchantID_buy();
    civilianType = getAccountType();
    transType = "charge";
    value = document.getElementById("buyGoods").value;
    if (value <= 0) {
        window.alert("Cant make transaction with negative or zero payoff.");
        document.getElementById("buyGoods").focus();
    } else {
        xhr = new XMLHttpRequest();
        xhr.open('POST', 'CompanyServlet');
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                if (xhr.getResponseHeader("error") !== null) {
                    window.alert(xhr.getResponseHeader("error"));
                } else {
                    window.alert("Succeed transaction");
                    document.getElementById("home_link").click();
                }
            } else if (xhr.status !== 200) {
                window.alert("Request failed. Returned status of " + xhr.status);
            }
            pageReady();
        };
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader("action", "makeTransaction");
        pagePrepare();
        xhr.send("civilianID=" + civilianID + "&merchantID=" + merchantID +
                "&civilianType=" + civilianType + "&transType=" + transType +
                "&value=" + value);
    }
}

function ajaxPayDebtRequest() {
    var value, debt;
    debt = document.getElementById("debt_amount").value;
    value = document.getElementById("payDebt").value;
    if (value <= 0 || value > debt) {
        window.alert("Can't make transaction with negative,zero or bigger value from your debt");
        document.getElementById("payDebt").focus();
    } else {
        var xhr, civilianID, civilianType;
        civilianID = getUserID();
        civilianType = getAccountType();

        xhr = new XMLHttpRequest();
        xhr.open('POST', 'CompanyServlet');
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                if (xhr.getResponseHeader("error") !== null) {
                    window.alert(xhr.getResponseHeader("error"));
                } else {
                    window.alert("Succeed debt payoff");
                    document.getElementById("home_link").click();
                }
            } else if (xhr.status !== 200) {
                window.alert("Request failed. Returned status of " + xhr.status);
            }
            pageReady();
        };
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader("action", "payDebt");
        pagePrepare();
        xhr.send("userID=" + civilianID + "&userType=" + civilianType +
                "&value=" + value);
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