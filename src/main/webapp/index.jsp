<%@page import="org.cirrostratus.samples.java.MainController"%>
<%@page import="org.cirrostratus.sequoia.serviceidentity.ServiceIdentity"%>
<%@page import="org.cirrostratus.sequoia.servicelocator.ServiceLocator"%>
<%@page import="java.util.List"%>
<html>
<style type="text/css">
h1{color:white}
body{color:white}
a:link{color:yellow}
a:visited{color:cyan}
</style>
<body  style="background-image:url(java-background.jpg)">
<h1>Sequoia Starter App</h1>
<%
	ServiceLocator sl = new ServiceLocator();
	
	List<String> list = sl.getAllServices();

	for(String s : list){
		out.println("<br/><h2>");
		out.println("App Name: "+s+"</h2>");
		List<String> hosts = sl.getAllHostsByService(s);
		for(String host : hosts){
			out.println("<br/>");
			out.println("<a href='http://" + host + ":8080'>" + host + "</a>");
		}
		out.println("<br/>");
	}
%>
</body>
</html>
