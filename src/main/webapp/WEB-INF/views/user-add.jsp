<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 06.08.2019
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>

<div align="center">
    <h2>Add User</h2>

    <form action="/spring.mvc.onlineshop/admin/user/add" method="post">
        <table>
            <tr>
                <td>Email:</td>
                <td><input value="${lastEnteredEmail}" name="email" type="email"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input value="${lastEnteredPassword}" name="password" type="password"/></td>
            </tr>
            <tr>
                <td>Repeat password:</td>
                <td><input value="${lastEnteredRepeatPassword}" name="repeatPassword" type="password"/></td>
            </tr>
            <tr>
                <td>Role:</td>
                <td>
                    <input name="role" type="radio" value="ROLE_ADMIN"
                    <c:if test="${lastEnteredRole == 'ROLE_ADMIN'}"> checked </c:if>> Admin
                    <input name="role" type="radio" value="ROLE_USER"
                    <c:if test="${lastEnteredRole == 'ROLE_USER'}"> checked </c:if>> User
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit">Confirm</button><br>
                    <br>
                    <input type="button" value="Back"
                           onclick="window.location='/spring.mvc.onlineshop/admin/user/all'">
                </td>
            </tr>
        </table>
    </form>

    <table>
        <tr>
            <td>
                <h3>${incompleteFormError}</h3>

                <h3>${passwordEquivalenceError}</h3>

                <h3>${userIsAlreadyPresentError}</h3>
            </td>
        </tr>
    </table>

</div>

</body>
</html>
