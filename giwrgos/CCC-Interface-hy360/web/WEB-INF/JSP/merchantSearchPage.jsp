<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form>
    <fieldset>
        <legend class="legend_text">Transaction search</legend>
        <br>Transactions with: <select id="field1Value"></select>
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
        <input id="field3Value" type="text">
        <p></p>
        Value between: <input type="text" id="field4Value1" size="11" placeholder=" min"> - <input type="text" id="field4Value2" size ="11" placeholder=" max">
        <p></p> 
        Date between: <input type="date" id="field5Value1" size="11" placeholder="from"> - <input type="date" id="field5Value2" size="11" placeholder="to">
        <p></p>
    </fieldset>
    <p></p>
    <button type="button" class="btn btn-default btn_style"  
            onclick="ajaxSearchExecuteRequest()">
        Search now
    </button>
</form>