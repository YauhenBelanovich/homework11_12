<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="resources\css\style.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Users List</h1>
</div>
<table class="myTable">
    <tr>
        <th>Name</th>
        <th>Password</th>
        <th>Create Date</th>
        <th>Role</th>

    </tr>
    <c:forEach var="userDTO" items="${userDtoList}">
        <tr>
            <td>${userDTO.userId}</td>
            <td>${userDTO.userName}</td>
            <td>${userDTO.createdBy}</td>
            <td>${userDTO.role}</td>
        </tr>
    </c:forEach>
</table>
<p><a class="addLink" href='<c:url value="/logout" />'>Sign out</a></p>
</body>
</html>
