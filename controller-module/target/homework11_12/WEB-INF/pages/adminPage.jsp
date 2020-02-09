<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Roles</title>
    <link rel="stylesheet" href="resources\css\style.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Roles List</h1>
</div>

<table class="myTable">
    <tr>
        <th>Name</th>
        <th>Description</th>

    </tr>
    <c:forEach var="roleDTO" items="${roleDtoList}">
        <tr>
            <td>${roleDTO.name}</td>
            <td>${roleDTO.description}</td>
        </tr>
    </c:forEach>
</table>
<p><a class="addLink" href='<c:url value="/logout" />'>Sign out</a></p>
</body>
</html>
