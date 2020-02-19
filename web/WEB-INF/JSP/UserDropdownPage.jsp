<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ServletContext context = getServletContext();
    assert (context.getAttribute("data") instanceof ArrayList);
    ArrayList<String> users = (ArrayList<String>) context.getAttribute("data");
    context.removeAttribute("data"); // clear after use
%>


<%if (users.isEmpty()) {%>
<option value="default" selected>No Users</option>
<%} else {%>
<option value="default" selected>Select User...</option>
<% for (String merchant : users) {%>
<option value="<%=merchant%>"><%=merchant%></option>
<%}
    }%>
