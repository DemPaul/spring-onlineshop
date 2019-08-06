<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 07.08.2019
  Time: 0:40
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
        ${successfulPurchase}
    </h3>

    <h2> Select what you want to buy </h2>
    <table border="2">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Buy</th>
        </tr>
        <c:forEach var="product" items="${allProductsToBuy}">
            <tr>
                <td> ${product.name}</td>
                <td> ${product.description}</td>
                <td> ${product.price}</td>
                <td>
                    <input type="button" value="Buy"
                           onclick="window.location='/spring.mvc.onlineshop/user/product/buy?id=${product.id}'">
                </td>
            </tr>
        </c:forEach>
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
                        <form action="/spring.mvc.onlineshop/user/basket" method="get">
                            <button type="submit">Basket</button>
                        </form>
                        <b>${productsInBasket}</b>
                    </h4>
                </center>
            </td>
        </tr>
        <tr>
            <td>
                <center>
                    <form action="/spring.mvc.onlineshop/user/basket/clear" method="get">
                        <button type="submit">Clear basket</button>
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

    <table>
        <tr>
            <td>
                <h3>${clearedBasket}</h3>

                <h3>${missingOrderError}</h3>

                <h3>${missingBasketError}</h3>

                <h3>${missingProductError}</h3>

                <h3>${emptyBasketError}</h3>
            </td>
        </tr>
    </table>

</div>

</body>
</html>
