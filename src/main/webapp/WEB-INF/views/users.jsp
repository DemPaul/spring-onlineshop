<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 06.08.2019
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>

<body>

<div align="center">

    <h3>
        <form action="/spring.mvc.onlineshop/admin/product/all" method="get">
            <h3>
                <button>Products</button>
            </h3>
        </form>
    </h3>

    <h2> List of all users </h2>
    <table border="2">
        <tr>
            <th>Email</th>
            <th>Password</th>
            <th>Role</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="user" items="${allUsers}">
            <tr>
                <td> ${user.email}</td>
                <td> ********</td>
                <td>
                    <c:if test="${user.role == 'ROLE_ADMIN'}"> admin </c:if>
                    <c:if test="${user.role == 'ROLE_USER'}"> user </c:if>
                </td>
                <td>
                    <input type="button" value="Edit"
                           onclick="window.location='/spring.mvc.onlineshop/admin/user/edit?id=${user.id}'">
                </td>
                <td>
                    <input type="button" value="Delete"
                           onclick="window.location='/spring.mvc.onlineshop/admin/user/delete?id=${user.id}'">
                </td>
            </tr>
        </c:forEach>
    </table>

    <table>
        <tr></tr>
        <tr>
            <td>
                <h4>You can view all products if you want! </h4>
                <center>
                    <center>
                        <form action="/spring.mvc.onlineshop/admin/user/add" method="get">
                            <button>Add new user</button>
                        </form>
                    </center>
                </center>
            </td>
        </tr>
        <tr>
            <td>
                <center>
                    <input type="button" value="Exit"
                           onclick="window.location='<spring:url value="/signout"/>'">
                </center>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
