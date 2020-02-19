<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ServletContext context = getServletContext();
    assert (context.getAttribute("result") instanceof ArrayList);
    ArrayList<ArrayList<String>> data = (ArrayList<ArrayList<String>>) context.getAttribute("result");
    ArrayList<String> head;
    context.removeAttribute("result"); // clear after use
    int dataCnt = 1;
%>
<div class="form-group">
    <h3> Search Results </h3>
    <table class="table table-hover text-left table-responsive" id="searchResults">
        <thead>
        <th class="text-left">#</th>
        <th class="text-left">Merchant ID</th>
        <th class="text-left">Civilian ID</th>
        <th class="text-left">Transaction Value</th>
        <th class="text-left">Transaction Type</th>
        <th class="text-left">Transaction Date</th>
        </thead>
        <tbody>
            <%for (int i = 0; i < data.size(); ++i) {
                    head = data.get(i);%>
            <tr>
                <td><%= dataCnt%></td>
                    <%for (int j = 0; j < head.size(); ++j) {%>
                    <td class="text-left"><%= head.get(j)%></td>
                    <%}%>
            </tr>
            <%dataCnt++;
                }%>
    </table>
</div>  