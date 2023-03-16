<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<link rel="stylesheet" type="text/css" href="webjars/bootstrap/5.2.3/css/bootstrap.min.css"/>
<script type="text/javascript" src="webjars/jquery/3.6.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/5.2.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/init.js"></script>

<body>
	<c:url value="/resources/text.txt" var="url"/>
	<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
	Spring URL: ${springUrl} at ${time}
	<br>
	JSTL URL: ${url}
	<br>
	Message: ${message}
</body>

</html>
