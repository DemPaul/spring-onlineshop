<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 07.08.2019
  Time: 0:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Basket</title>
</head>
<body>

<div align="center">

    <h2>Check your purchases</h2>
    <table border="2">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
        </tr>
        <c:forEach var="product" items="${allProductsInBasket}">
            <tr>
                <td> ${product.name}</td>
                <td> ${product.description}</td>
                <td> ${product.price}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>&#10004;</td>
            <td> Total price:</td>
            <td> ${totalPrice}</td>
        </tr>
    </table>

    <table>
        <tr>
            <td>
                <center></center>
            </td>
        </tr>
        <tr>
            <td>
                <center>
                    <h4>
                        <br>
                        <form action="/spring.mvc.onlineshop/user/order" method="get">
                            <button type="submit">Order</button>
                        </form>
                    </h4>
                </center>
            </td>
        </tr>
        <tr>
            <td>
                <center>
                    <form action="/spring.mvc.onlineshop/user/product/all" method="get">
                        <button type="submit">Back</button>
                    </form>
                </center>
            </td>
        </tr>
        <tr>
            <td>
                <center>
                    <form action="/spring.mvc.onlineshop/exit" method="get">
                        <button type="submit">Exit</button>
                    </form>
                </center>
            </td>
        </tr>
    </table>

</div>

</body>
</html>
