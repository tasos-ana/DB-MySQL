<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ServletContext context = getServletContext();

    assert (context.getAttribute("good") instanceof ArrayList);
    ArrayList<String> good = (ArrayList) context.getAttribute("good");
    int goodCnt = 1;
    context.removeAttribute("good"); // clear after use

    assert (context.getAttribute("bad") instanceof ArrayList);
    ArrayList<String> bad = (ArrayList) context.getAttribute("bad");
    int badCnt = 1;
    context.removeAttribute("bad"); // clear after use

    assert (context.getAttribute("month") instanceof ArrayList);
    ArrayList<String> month = (ArrayList) context.getAttribute("month");
    int monthCnt = 1;
    context.removeAttribute("month"); // clear after use
%>
<h3>CCC Info about customers</h3>
<div class="form-group col-sm-4">
    <labe for="goodCustomer">The Good CCC Customers</labe>
    <table class="table table-hover text-left table-responsive" id="goodCustomer">
        <thead>
            <tr>
                <th class="text-right">#</th>
                <th>ID</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (!good.isEmpty()) {
                    for (String id : good) {
            %>
            <tr>
                <td class="text-right"><%= goodCnt%></td>
                <td><%= id%></td>
            </tr>
            <%goodCnt++;
                }
            } else {%>
            <tr>
                <td class="text-right">1</td>
                <td>Nobody</td>
            </tr>
            <%}
            %>  
        </tbody>
    </table>
</div>
<div class="form-group col-sm-4">
    <labe for="badCustomer">The Bad CCC Customers</labe>
    <table class="table table-hover text-left table-responsive" id="badCustomer">
        <thead>
            <tr>
                <th class="text-right">#</th>
                <th>ID</th>
                <th>Debt</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (!bad.isEmpty()) {
                    String userID = null;
                    for (String id : bad) {
                        if (userID == null) {
                            userID = id;
                        } else {%>
            <tr>
                <td class="text-right"><%= badCnt%></td>
                <td><%= userID%></td>
                <td><%= id%></td>
            </tr>
            <%
                        userID = null;
                        badCnt++;
                    }
                }
            } else {%>
            <tr>
                <td class="text-right">1</td>
                <td>Nobody</td>
                <td>0</td>
            </tr>
            <%}
            %>  
        </tbody>
    </table>
</div>
<div class="form-group col-sm-4">
    <labe for="monthMerchant">Merchant of the previous month</labe>
    <table class="table table-hover text-left table-responsive" id="monthMerchant">
        <thead>
            <tr>
                <th class="text-right">#</th>
                <th>ID</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (!month.isEmpty()) {
                    for (String id : month) {
            %>
            <tr>
                <td class="text-right"><%= monthCnt%></td>
                <td><%= id%></td>
            </tr>
            <%monthCnt++;
                }
            } else {%>
            <tr>
                <td class="text-right">1</td>
                <td>Nobody</td>
            </tr>
            <%}
            %>  
        </tbody>
    </table>
</div>  