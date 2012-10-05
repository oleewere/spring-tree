<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Binary search tree</title>
</head>
<body>
	<h1>Binary search tree maker</h1>
	
	<form:form action="create" modelAttribute="tree" >
		<form:textarea path="numbers" cols="30" rows="10"/>

	  <input type="submit" value="OK"/>
	</form:form>

</body>
</html>
