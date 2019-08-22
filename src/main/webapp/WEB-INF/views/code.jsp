<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 07.08.2019
  Time: 1:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order confirmation</title>
</head>
<body>

<div align="center">
    <h1>Enter your personal confirmation code</h1>

    <form action="/spring.mvc.onlineshop/user/code" method="post">
        <table>
            <tr>
                <td>Your code:</td>
                <td><input value="${lastEnteredCode}" name="code" type="text"/></td>
            </tr>
        </table>
        <table>
            <tr>
                <td>
                    <button type="submit">Confirm</button>
                </td>
            </tr>
        </table>
    </form>

    <table>
        <tr>
            <td>
                <center>
                    <input type="button" value="Cancel"
                           onclick="window.location='/spring.mvc.onlineshop/user/product/all'"><br>
                    <br>
                    <input type="button" value="Exit"
                           onclick="window.location='<spring:url value="/signout"/>'">
                </center>
            </td>
        </tr>
    </table>

    <table>
        <tr>
            <td>
                <h3>${codeEquivalenceError}</h3>
            </td>
        </tr>
    </table>

</div>

</body>
</html>
