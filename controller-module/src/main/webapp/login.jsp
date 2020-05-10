<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="resources\css\style.css">
</head>
<body>
<h2>Login</h2>
<c:if test="${not empty successfullyLogout}">
  <c:out value="${successfullyLogout}"/>
</c:if>
<br><br>
<form method="post">
    <label>Name</label><br>
        <input name="username" type="text" pattern="[A-Za-z]+" placeholder="Only English letters" maxlength="40" required/>
            <c:if test="${not empty isValidName}">
               <c:out value="${isValidName}"/>
            </c:if>
        </font>
        <br><br>

    <label>Password</label><br>
        <input name="password" type="text" pattern=".{6,}" placeholder="Six or more characters" maxlength="40" required/>
        <font color="red">
            <c:if test="${not empty isValidPassword}">
               <c:out value="${isValidPassword}"/>
            </c:if>
        </font>
        <br><br>

    <input type="submit" value="login" class="button">
</form>
<br><br>
<p><a class="addLink" href='<c:url value="/registration" />'>New user registration</a></p>
<p><a class="addLink" href='<c:url value="/logout" />'>Sign out</a></p>
</body>
</html>