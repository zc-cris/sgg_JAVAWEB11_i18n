<%@page import="java.util.Locale"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Date date = new Date();
		double salary = 12323.232;
		request.setAttribute("date", date);
		request.setAttribute("salary", salary);
	%>
	<c:if test="${param.locale !=null }">
		<%
			String locale = request.getParameter("locale");
			if("en".equals(locale)){
				session.setAttribute("locale", Locale.US);
			}else if("zh".equals(locale)){
				session.setAttribute("locale", Locale.CHINA);
			}
		%>
	</c:if>
	
	<c:if test="${sessionScope.locale != null}">
		<fmt:setLocale value="${sessionScope.locale }"/>
	</c:if>
	
	<fmt:setBundle basename="i18n"/>
	<fmt:message key="date"></fmt:message>:
	<fmt:formatDate value="${date }" dateStyle="FULL" timeStyle="FULL"/>
	<br>
	<fmt:message key="salary"></fmt:message>:
	<fmt:formatNumber value="${salary }" type="currency"></fmt:formatNumber>

	<a href="index.jsp?locale=zh">中文</a>
	<a href="index.jsp?locale=en">English</a>
</body>
</html>