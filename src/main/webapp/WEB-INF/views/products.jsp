<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 07.08.2019
  Time: 0:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>

<body>

<div align="center">
    <h3>
        <form action="/spring.mvc.onlineshop/admin/user/all" method="get">
            <h3>
                <button>Users</button>
            </h3>
        </form>
    </h3>

    <h2> List of all products </h2>
    <table border="2">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="product" items="${allProducts}">
            <tr>
                <td> ${product.name}</td>
                <td> ${product.description}</td>
                <td> ${product.price}</td>
                <td>
                    <input type="button" value="Edit"
                           onclick="window.location='/spring.mvc.onlineshop/admin/product/edit?id=${product.id}'">
                </td>
                <td>
                    <input type="button" value="Delete"
                           onclick="window.location='/spring.mvc.onlineshop/admin/product/delete?id=${product.id}'">
                </td>
            </tr>
        </c:forEach>
    </table>

    <table>
        <tr></tr>
        <tr>
            <td>
                <h4>You can view all users if you want! </h4>
                <center>
                    <form action="/spring.mvc.onlineshop/admin/product/add" method="get">
                        <button>Add new product</button>
                    </form>
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
