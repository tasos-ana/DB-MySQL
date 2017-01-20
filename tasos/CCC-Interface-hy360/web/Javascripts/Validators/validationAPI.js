/* 
 *     Document      :validationAPI.js
 *     Project       :HY359-EX05
 *     Author        :Tasos198
 *     Created on    :Dec 9, 2016
 */

var validationAPI = function () {
    "use strict";
    var formValid = {
        usrEMAIL: false,
        usrNAME: false,
        employeeEMAIL: false
    };

    function usrEMAILValidation() {
        var usrEMAIL, pattern, xhr;
        usrEMAIL = document.getElementById("usrEMAIL");

        if (usrEMAIL.value.indexOf(" ") !== -1) {
            document.getElementById("usrEMAIL_err").style.color = "red";
            document.getElementById("usrEMAIL_err").innerHTML = "Space not allowed";
            formValid.usrID = false;
            return;
        }

        pattern = /(.+)@([A-Za-z]+)\.([A-Za-z]+)([\.A-Za-z]*)/;
        if (usrEMAIL.value.match(pattern)) {
            xhr = new XMLHttpRequest();
            xhr.open('POST', 'CompanyServlet');
            xhr.onload = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    if (xhr.getResponseHeader("error") !== null) {
                        document.getElementById("usrEMAIL_err").style.color = "red";
                        document.getElementById("usrEMAIL_err").innerHTML = xhr.getResponseHeader("error");
                        formValid.usrEMAIL = false;
                    } else {
                        document.getElementById("usrEMAIL_err").style.color = "green";
                        document.getElementById("usrEMAIL_err").innerHTML = "&#10004";
                        formValid.usrEMAIL = true;
                    }
                } else if (xhr.status !== 200) {
                    window.alert("Email check request failed. Returned status of " + xhr.status);
                    document.getElementById("usrEMAIL_err").style.color = "red";
                    document.getElementById("main_container").innerHTML = xhr.responseText;
                    formValid.usrEMAIL = false;
                }
            };
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.setRequestHeader('Action', 'check');
            xhr.send('email=' + usrEMAIL.value);

        } else {
            document.getElementById("usrEMAIL_err").style.color = "red";
            document.getElementById("usrEMAIL_err").innerHTML = "Invalid email";
            formValid.usrEMAIL = false;
        }
    }

    function usrNAMEValidation() {
        var usrNAME, nameLen, letter;

        usrNAME = document.getElementById("usrNAME");
        nameLen = usrNAME.value.length;

        letter = /[A-Za-z]/;
        if (nameLen >= 3 && nameLen <= 20 && usrNAME.value.match(letter)) {
            document.getElementById("usrNAME_err").style.color = "green";
            document.getElementById("usrNAME_err").innerHTML = "&#10004";
            formValid.usrNAME = true;
            return;
        }
        document.getElementById("usrNAME_err").style.color = "red";
        document.getElementById("usrNAME_err").innerHTML = "Name must contain at least 3 letters and less than 20";
        formValid.usrNAME = false;
    }

    function hasEmployeeValidation() {
        var companyID;
        companyID = document.getElementById("companyID").getAttribute("data-companyID");

        var usrEMAIL, pattern, xhr, type;
        usrEMAIL = document.getElementById("removeUsrEMAIL");
        type = document.employee.accountType;

        if (usrEMAIL.value.indexOf(" ") !== -1) {
            document.getElementById("removeUsrEMAIL_err").style.color = "red";
            document.getElementById("removeUsrEMAIL_err").innerHTML = "Space not allowed";
            formValid.employeeEMAIL = false;
            return;
        }

        pattern = /(.+)@([A-Za-z]+)\.([A-Za-z]+)([\.A-Za-z]*)/;
        if (usrEMAIL.value.match(pattern)) {
            xhr = new XMLHttpRequest();
            xhr.open('POST', 'CompanyServlet');
            xhr.onload = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    if (xhr.getResponseHeader("error") !== null) {
                        document.getElementById("removeUsrEMAIL_err").style.color = "red";
                        document.getElementById("removeUsrEMAIL_err").innerHTML = xhr.getResponseHeader("error");
                        formValid.employeeEMAIL = false;
                    } else {
                        document.getElementById("removeUsrEMAIL_err").style.color = "green";
                        document.getElementById("removeUsrEMAIL_err").innerHTML = "&#10004";
                        formValid.employeeEMAIL = true;
                    }
                } else if (xhr.status !== 200) {
                    window.alert("Email check request failed. Returned status of " + xhr.status);
                    document.getElementById("removeUsrEMAIL_err").style.color = "red";
                    document.getElementById("main_container").innerHTML = xhr.responseText;
                    formValid.employeeEMAIL = false;
                }
            };
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.setRequestHeader('Action', 'checkEmployee');
            xhr.send('email=' + usrEMAIL.value + "&companyID=" + companyID + "&employeeType=" + type.value);

        } else {
            document.getElementById("removeUsrEMAIL_err").style.color = "red";
            document.getElementById("removeUsrEMAIL_err").innerHTML = "Invalid email";
            formValid.employeeEMAIL = false;
        }
    }

    function validAll() {
        usrEMAILValidation();
        usrNAMEValidation();
    }

    function validForm() {
        try {
            var action = document.employee.action;
            if (action.value === "addEmployee") {
                return (formValid.usrEMAIL && formValid.usrNAME);
            } else {
                return formValid.employeeEMAIL;
            }
        } catch (e) {
            return (formValid.usrEMAIL && formValid.usrNAME);
        }
    }

    return {
        form: function () {
            return validForm();
        },
        validateAll: function () {
            validAll();
        },
        usrEMAIL: function () {
            usrEMAILValidation();
        },
        usrNAME: function () {
            usrNAMEValidation();
        },
        hasEmployee: hasEmployeeValidation
    };
}();