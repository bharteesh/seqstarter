<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	out.println("Listing apps from controller:");
	
%>

<c:forEach items="${appsList}"  var="app">
	$app<br/> 
</c:forEach>
 
</body>
</html>
