<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ServletContext context = getServletContext();
    assert (context.getAttribute("data") instanceof ArrayList);
    ArrayList<String> merchants = (ArrayList<String>) context.getAttribute("data");
    context.removeAttribute("data"); // clear after use
%>


<%if (merchants.isEmpty()) {%>
<option value="default" selected>No merchants</option>
<%} else {%>
<option value="default" selected>Select Merchant...</option>
<% for (String merchant : merchants) {%>
<option value="<%=merchant%>"><%=merchant%></option>
<%}
        }%>
