<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User registration</title>
    <link rel="stylesheet" href="resources\css\style.css">
</head>
<body>
<h2>User registration</h2>
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

    <label>Role</label><br>
          <input type="radio" name="role" value="ADMIN" checked/> Admin<br>
          <input type="radio" name="role" value="USER"/> User<br>
          <font color="red">
             <c:if test="${not empty isValidRole}">
                 <c:out value="${isValidRole}"/>
             </c:if>
          </font>
          <br><br>
    <input type="submit" value="registration" class="button">
</form>

</body>
</html>