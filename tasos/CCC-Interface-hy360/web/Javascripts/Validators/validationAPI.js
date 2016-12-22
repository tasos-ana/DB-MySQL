/* 
 *     Document      :validationAPI.js
 *     Project       :HY359-EX05
 *     Author        :Tasos198
 *     Created on    :Dec 9, 2016
 */

var validationAPI = function () {
    "use strict";
    var formValid = {
        usrEMAIL: false
    };

    function usrEMAILValidation() {
        var usrEMAIL, current, pattern;
        usrEMAIL = document.getElementById("usrEMAIL");
        try {
            current = document.getElementById("email_label").getAttribute("data-current");
        } catch (e) {

        }

        if (usrEMAIL.value.indexOf(" ") !== -1) {
            document.getElementById("usrEMAIL_err").style.color = "red";
            document.getElementById("usrEMAIL_err").innerHTML = "Space not allowed";
            formValid.usrID = false;
            return;
        }

        pattern = /(.+)@([A-Za-z]+)\.([A-Za-z]+)([\.A-Za-z]*)/;
        if (usrEMAIL.value.match(pattern)) {
            document.getElementById("usrEMAIL_err").style.color = "green";
            document.getElementById("usrEMAIL_err").innerHTML = "&#10004";
            formValid.usrEMAIL = true;
        } else {
            document.getElementById("usrEMAIL_err").style.color = "red";
            document.getElementById("usrEMAIL_err").innerHTML = "Invalid email";
            formValid.usrEMAIL = false;
        }
    }
    
    function validAll(){
       usrEMAILValidation();
    }

    return {
        form: function () {
            return (formValid.usrEMAIL);
        },
        validateAll: function () {
                validAll();
        },
        usrEMAIL: function () {
            usrEMAILValidation();
        }
    };
}();