<%-- 
    Document   : createAccountPage
    Created on : Dec 20, 2016, 10:44:26 PM
    Author     : Tasos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="animated_container">
    <h2 id="register_form_title" class="text-center">Registration form</h2>
    <form id="register_form" name="registration" class="form-horizontal">
        <!--PERSONAL INFO FORM-->
        <!--NAME-->
        <div class="form-group">
            <label for="usrNAME" class="control-label col-sm-3">Name:</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="usrNAME" name="usrNAME" placeholder="Enter Name" 
                       required size="38" onchange="validationAPI.usrNAME()">
            </div>
            <div id="usrNAME_err" class="col-sm-3 text-left" style="color: red;">*</div>
        </div>
        <!--LOGIN CREDENTIAL FORM-->
        <!--EMAIL-->
        <div class="form-group">
            <label for="usrEMAIL" class="control-label col-sm-3">Email:</label>
            <div class="col-sm-6">
                <input type="email" class="form-control" id="usrEMAIL" name="usrEMAIL" placeholder="Enter Email" 
                       size="38" required onchange="validationAPI.usrEMAIL()">
            </div>
            <div id="usrEMAIL_err" class="col-sm-3 text-left" style="color: red;">*</div>
        </div>
        <select id="user_account_type">
            <option value="company">Company</option>
            <option value="civilian">Customer</option>
            <option value="merchant">Merchant</option>
        </select>
        <div id="form_alert" class="alert alert-danger" hidden>
            <strong>Incomplete form!</strong> You should check it again.
        </div>
        <div id="new_usr_action" class="form-group">
            <div class="col-sm-5"></div>
            <button type="button" onclick="ajaxOpenAccountRequest()" 
                    class="btn btn-default btn_style col-sm-2">Open Account</button>
            <div></div>
        </div>
    </form>
</div>
