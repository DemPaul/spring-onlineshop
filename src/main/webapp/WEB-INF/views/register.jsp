<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 06.08.2019
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

<div align="center">
    <h2>Registration</h2>

    <form action="/spring.mvc.onlineshop/register" method="post">
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
                <td></td>
                <td>
                    <button type="submit">Register</button>
                </td>
            </tr>
        </table>
    </form>

    <table>
        <tr>
            <td>
                <center>
                    <form action="/spring.mvc.onlineshop/exit" method="get">
                        <button>Exit</button>
                    </form>
                </center>
            </td>
        </tr>
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
