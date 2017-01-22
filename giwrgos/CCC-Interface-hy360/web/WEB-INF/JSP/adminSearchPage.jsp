<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form>
    <fieldset>
        <legend class="legend_text">Transaction search</legend>
        Choose Customer Type: <select id="customerType" onchange="adminAction()">
            <option value="default">Choose a customer...</option>
            <option value="company">Company</option>
            <option value="civilian">Civilian</option>
            <option value="merchant">Merchant</option>
        </select>
        <p></p>
        <span id="allExtra">Transactions between: <span id="companyExtra">
                <select id="field0MinusValue" onchange="ajaxUpdateAdminFieldRequest()"></select> with </span>
            <select id="field0Value" onchange="ajaxUpdateUpdateFieldRequest()"></select> with 
            <select id="field1Value"></select>
            <p></p>
            Transaction type: <select id="field2Value">
                <option value="default">Both</option>
                <option value="charge">Charge</option>
                <option value="credit">Credit</option>
            </select>
            <p></p>
            Value: <select id="field3Operation">
                <option value="default">None</option>
                <option value="<"> < </option>
                <option value="<="> <= </option>
                <option value=">"> > </option>
                <option value=">="> >= </option>
                <option value="="> = </option>
                <option value="<>"> != </option>
            </select>
            <input id="field3Value" type="number">
            <p></p>
            Value between: <input type="number" id="field4Value1" size="11" placeholder=" min"> - <input type="number" id="field4Value2" size ="11" placeholder=" max">
            <p></p> 
            Date between: <input type="date" id="field5Value1" size="11" placeholder="from"> - <input type="date" id="field5Value2" size="11" placeholder="to">
            <p></p>
        </span>
    </fieldset>
    <p></p>
    <button type="button" id="searchButton" class="btn btn-default btn_style"  
            onclick="ajaxSearchExecuteRequest()">
        Search now
    </button>
</form>