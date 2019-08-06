<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 06.08.2019
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>

<body>
<div align="center">
    <h2>Edit User</h2>

    <form action="/spring.mvc.onlineshop/admin/user/edit" method="post">
        <table>
            <tr>
                <td>
                    <input value="${id}" name="id" type="hidden">
                </td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input value="${lastEnteredEmail}" name="email" type="email"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input value="${lastEnteredPassword}" name="password" type="password"></td>
            </tr>
            <tr>
                <td>Role:</td>
                <td>
                    <p><select name="role">
                        <option selected hidden value="${lastEnteredRole}">${lastEnteredRole}</option>
                        <option value="user">user</option>
                        <option value="admin">admin</option>
                    </select></p>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <br>
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

                <h3>${userIsAlreadyPresentError}</h3>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
