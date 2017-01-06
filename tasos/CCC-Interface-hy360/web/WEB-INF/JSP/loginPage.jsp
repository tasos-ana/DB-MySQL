<%-- 
    Document   : loginPage
    Created on : Dec 20, 2016, 10:43:47 PM
    Author     : Tasos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="animated_container">
    <h2 class="text-center">Login</h2>
    <form id="login_form" class="text-center" name="login">
        <div class="form-group">
            <!--EMAIL-->
            <label for="usr_email">Email:</label>
            <input type="email" id="usr_email" name="email" 
                   placeholder="Enter your email" 
                   autofocus required size="38"
                   onkeydown="enterPress('login', event)"><br>
            <select id="user_account_type" onchange="extraProperties()">
                <option value="company">Company</option>
                <option value="civilian">Customer</option>
                <option value="merchant">Merchant</option>
            </select>
            <div id="extraProperties" class="row">
                <div>Working on Company</div>
                <input type="radio" name="worksAtCompanyPropertie" value="yes">Yes
                <input type="radio" name="worksAtCompanyPropertie" value="no" checked="">No
            </div>
            <div id="usr_login_error"></div>
        </div>
        <button id="login" type="button" onclick="ajaxLoginRequest()" class="btn btn-default btn_style">Login</button>
    </form>
</div>