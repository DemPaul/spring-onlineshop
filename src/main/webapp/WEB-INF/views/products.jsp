<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 07.08.2019
  Time: 0:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>

<body>

<div align="center">
    <h3>
        <form action="/spring.mvc.onlineshop/admin/product/add" method="get">
            <button>Add new product</button>
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
                <td> ${product.getName()}</td>
                <td> ${product.getDescription()}</td>
                <td> ${product.getPrice()}</td>
                <td>
                    <input type="button" value="Edit"
                           onclick="window.location='/spring.mvc.onlineshop/admin/product/edit?id=${product.getId()}'">
                </td>
                <td>
                    <input type="button" value="Delete"
                           onclick="window.location='/spring.mvc.onlineshop/admin/product/delete?id=${product.getId()}'">
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
                    <form action="/spring.mvc.onlineshop/admin/user/all" method="get">
                        <h3>
                            <button>Users</button>
                        </h3>
                    </form>
                </center>
            </td>
        </tr>
        <tr>
            <td>
                <center>
                    <form action="/spring.mvc.onlineshop/exit" method="get">
                        <button>Exit</button>
                    </form>
                </center>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
