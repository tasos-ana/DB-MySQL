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
                ajaxCccCustomerInfoRequest();
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

    type = document.getElementById("user_account_type");
    pagePrepare();
    if (email === null && type === null) {
        xhr.setRequestHeader('action', 'login');
        xhr.send();
    } else {
        if (email.value === "admin@ccc.gr") {
            xhr.setRequestHeader('action', 'adminPage');
            xhr.send();
        } else {
            xhr.setRequestHeader('action', 'login');
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

function ajaxUsersDropdownRequest(content, type = "") {
    var xhr, userID, userType;
    xhr = new XMLHttpRequest();
    userID = getUserID();
    userType = getAccountType();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (!cookieExist(xhr.getResponseHeader("fail"))) {
                document.getElementById("home_but").click();
            } else {
                if (xhr.getResponseHeader("error") !== null) {
                    window.alert(xhr.getResponseHeader("error"));
                } else {
                    document.getElementById(xhr.getResponseHeader("container")).innerHTML = xhr.responseText;
                }
            }
            pageReady();
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', content + 'UserDropdown');
    if (content === "refund" || content === "search" || content === "searchCompany"
            || content === "searchCivilian") {
        xhr.send("userID=" + userID + "&userType=" + userType);
    } else {
        xhr.send("customerType=" + type);
}
}

function ajaxSearchRequest() {
    var xhr, userType, searchPage;
    xhr = new XMLHttpRequest();
    userType = getAccountType();
    var type = userType.split("_");
    if (type[0] === "employee") {
        if (type[1] === "civilian") {
            searchPage = "civilianSearch";
        } else {
            searchPage = "merchantSearch";
        }
    } else {
        searchPage = userType + "Search";
    }
    xhr.open('POST', 'requestPagesServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (!cookieExist(xhr.getResponseHeader("fail"))) {
                document.getElementById("home_but").click();
            } else {
                if (xhr.getResponseHeader("error") !== null) {
                    window.alert(xhr.getResponseHeader("error"));
                } else {
                    document.getElementById("search").innerHTML = xhr.responseText;
                }
            }
            pageReady();
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send("page=" + searchPage);
}

function ajaxUpdateFieldRequest() {
    var oldUserID, oldUserType, employeeID, employeeType;
    oldUserID = getUserID();
    oldUserType = getAccountType();

    employeeID = document.getElementById("field0Value").value;
    employeeType = ajaxGetEmployeeType(employeeID, oldUserID);

    document.getElementById("main_container").setAttribute("data-userID", employeeID);
    document.getElementById("main_container").setAttribute("data-type", employeeType);

    if (employeeType.includes("civilian")) {
        ajaxUsersDropdownRequest('search');
    } else {
        ajaxUsersDropdownRequest('searchCivilian');
    }

    document.getElementById("main_container").setAttribute("data-userID", oldUserID);
    document.getElementById("main_container").setAttribute("data-type", oldUserType);
}

function ajaxUpdateUpdateFieldRequest() {
    var userType, userID;
    userType = getAccountType();
    userID = document.getElementById("field0Value").value;
    document.getElementById("searchButton").disabled = false;
    if (userID === "default") {
        document.getElementById("searchButton").disabled = true;
    }
    if (userType === "company") {
        ajaxUpdateFieldRequest();
    } else if (userType === "civilian") {
        document.getElementById("main_container").setAttribute("data-userID", userID);
        ajaxUsersDropdownRequest('search');
    } else if (userType === "merchant") {
        document.getElementById("main_container").setAttribute("data-userID", userID);
        ajaxUsersDropdownRequest('searchCivilian');
    } else {
        window.alert("error at ajaxUpdateAdminFieldRequest ~line 348");
    }
}

function ajaxUpdateAdminFieldRequest() {
    var companyID;
    if (document.getElementById("field0MinusValue").value === "default") {
        document.getElementById("field0Value").value = "default";
        document.getElementById("field1Value").value = "default";
        document.getElementById("searchButton").disabled = true;
    } else {
        companyID = document.getElementById("field0MinusValue").value;
        document.getElementById("main_container").setAttribute("data-userID", companyID);
        ajaxUsersDropdownRequest('searchCompany');
        document.getElementById("searchButton").disabled = false;
    }
}

function ajaxCccCustomerInfoRequest() {
    var xhr, userID, userType;
    xhr = new XMLHttpRequest();
    userID = getUserID();
    userType = getAccountType();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (!cookieExist(xhr.getResponseHeader("fail"))) {
                document.getElementById("home_but").click();
            } else {
                if (xhr.getResponseHeader("error") !== null) {
                    window.alert(xhr.getResponseHeader("error"));
                } else {
                    document.getElementById(xhr.getResponseHeader("container")).innerHTML = xhr.responseText;
                }
            }
            pageReady();
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('action', 'cccCustomerInfo');
    xhr.send();
}

function ajaxMakeTransactionRequest(transType) {
    "use strict";
    var xhr, civilianID, merchantID, civilianType, value;
    civilianID = getUserID();
    civilianType = getAccountType();
    if (transType === "charge") {
        value = document.getElementById("buyGoods").value;
        if (document.getElementById("buyMerchantsDropdownContainer").value === "default") {
            window.alert("Please select a merchant if exist");
            return;
        }
        merchantID = getMerchantID_buy();
    } else {
        value = document.getElementById("payRefund").value;
        if (document.getElementById("refundMerchantsDropdownContainer").value === "default") {
            window.alert("Please select a merchant if exist");
            return;
        }
        merchantID = getMerchantID_refund();
    }

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
    var value;
    value = document.getElementById("payDebt").value;
    if (value <= 0) {
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
                    window.alert("Succeed payoff");
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

function ajaxSearchExecuteRequest() {
    var query, subQuery1 = null, subQuery2 = null;
    var xhr, userID, userType;
    userID = getUserID();
    userType = getAccountType();
    if (userType === "company" && document.getElementById("field0Value").value !== "default") {
        var employeeID = document.getElementById("field0Value").value;
        userType = ajaxGetEmployeeType(employeeID, userID);
        userID = employeeID;
    }

    var table1, table2, merchantID1, civilianID1, merchantID2, civilianID2, compareID, thisID;
    if (userType.split("_")[0] === "employee") {
        if (userType.split("_")[1] === "civilian") {
            table1 = "emerchant_transaction_ecivilian";
            merchantID1 = "employee_merchant_id";
            civilianID1 = "employee_civilian_id";
            table2 = "merchant_transaction_ecivilian";
            merchantID2 = "merchant_id";
            civilianID2 = civilianID1;
            compareID = "merchantID";
            thisID = "civilianID";
        } else {
            table1 = "emerchant_transaction_ecivilian";
            merchantID1 = "employee_merchant_id";
            civilianID1 = "employee_civilian_id";
            table2 = "emerchant_transaction_civilian";
            merchantID2 = merchantID1;
            civilianID2 = "civilian_id";
            compareID = "civilianID";
            thisID = "merchantID";
        }
    } else {
        switch (userType.split("_")[0]) {
            case "civilian":
                table1 = "emerchant_transaction_civilian";
                merchantID1 = "employee_merchant_id";
                civilianID1 = "civilian_id";
                table2 = "merchant_transaction_civilian";
                merchantID2 = "merchant_id";
                civilianID2 = civilianID1;
                compareID = "merchantID";
                thisID = "civilianID";
                break;
            case "merchant":
                table1 = "merchant_transaction_ecivilian";
                merchantID1 = "merchant_id";
                civilianID1 = "employee_civilian_id";
                table2 = "merchant_transaction_civilian";
                merchantID2 = merchantID1;
                civilianID2 = "civilian_id";
                compareID = "civilianID";
                thisID = "merchantID";
                break;
            case "company":
                subQuery1 = "(SELECT employee_merchant_id as merchantID, civilian_id as civilianID, "
                        + " value, type, date FROM emerchant_transaction_civilian "
                        + " WHERE merchant_company_id = '" + userID + "'"
                        + " UNION"
                        + " SELECT merchant_id as merchantID, employee_civilian_id as civilianID, "
                        + " value, type, date FROM merchant_transaction_ecivilian "
                        + " WHERE civilian_company_id = '" + userID + "'"
                        + " UNION"
                        + " SELECT employee_merchant_id as merchantID, employee_civilian_id as civilianID, "
                        + " value, type, date FROM emerchant_transaction_ecivilian "
                        + " WHERE civilian_company_id = '" + userID + "' "
                        + " or merchant_company_id = '" + userID + "') a";
                break;
            default:
                window.alert("assert ajaxReqeust.js ~line 543");
        }
    }

    query = " SELECT * FROM ";

    if (subQuery1 === null) {
        subQuery2 = "(SELECT " + merchantID1 + " as merchantID, ";
        subQuery2 = subQuery2 + civilianID1 + " as civilianID, value, type, date FROM " + table1;
        subQuery2 = subQuery2 + " UNION ";
        subQuery2 = subQuery2 + "SELECT " + merchantID2 + " as merchantID, ";
        subQuery2 = subQuery2 + civilianID2 + " as civilianID, value, type, date FROM " + table2 + ") a";

        query = query + subQuery2;
        query = query + " WHERE 1=1 AND " + thisID + "= '" + userID + "'";
    } else {
        query = query + subQuery1;
        query = query + " WHERE 1=1 ";
    }

    var field1Query = null, field2Query, field3Query, field4Query = null, field5Query = null;
    if (document.getElementById("field1Value").value !== userID && document.getElementById("field1Value").value !== "") {
        field1Query = " AND " + compareID + " = '" + document.getElementById("field1Value").value + "' ";
    }
    field2Query = " AND type = '" + document.getElementById("field2Value").value + "' ";
    field3Query = " AND value " + document.getElementById("field3Operation").value + " " + document.getElementById("field3Value").value;

    if (document.getElementById("field4Value1").value !== "" && document.getElementById("field4Value2").value !== "") {
        field4Query = " AND value BETWEEN '" + document.getElementById("field4Value1").value + "' AND '" + document.getElementById("field4Value2").value + "'";
    }

    if (document.getElementById("field5Value1").value !== "" && document.getElementById("field5Value2").value !== "") {
        field5Query = " AND date BETWEEN '" + document.getElementById("field5Value1").value + "' AND '" + document.getElementById("field5Value2").value + "'";
    }

    if (field1Query !== null && !field1Query.includes("default")) {
        query = query + field1Query;
    }
    if (!field2Query.includes("default")) {
        query = query + field2Query;
    }
    if (!field3Query.includes("default") && document.getElementById("field3Value").value !== "") {
        query = query + field3Query;
    }
    if (field4Query !== null) {
        query = query + field4Query;
    }
    if (field5Query !== null) {
        query = query + field5Query;
    }

    xhr = new XMLHttpRequest();
    xhr.open('POST', 'CompanyServlet');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (xhr.getResponseHeader("error") !== null) {
                window.alert(xhr.getResponseHeader("error"));
            } else {
                document.getElementById("searchResults").innerHTML = xhr.responseText;
            }
        } else if (xhr.status !== 200) {
            window.alert("Request failed. Returned status of " + xhr.status);
        }
        pageReady();
    };
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader("action", "executeSearch");
    pagePrepare();
    xhr.send("insQuery=" + query);
}

function ajaxGetEmployeeType(userID, companyID) {
    xhr = new XMLHttpRequest();
    xhr.open('POST', 'CompanyServlet', false);

    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader("action", "checkEmployee");
    xhr.send("email=" + userID + "&companyID=" + companyID + "&employeeType=civilian");

    if (xhr.getResponseHeader("error") !== null) {
        return "employee_merchant";
    } else {
        return "employee_civilian";
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