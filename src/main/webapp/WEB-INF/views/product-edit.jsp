<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 07.08.2019
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Product</title>
</head>

<body>

<div align="center">
    <h2>Edit Product</h2>

    <form action="/spring.mvc.onlineshop/admin/product/edit" method="post">
        <table>
            <tr>
                <td>
                    <input value="${id}" name="id" type="hidden">
                </td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><input value="${lastEnteredName}" name="name" type="name"></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input value="${lastEnteredDescription}" name="description" type="description"></td>
            </tr>
            <tr>
                <td>Price:</td>
                <td><input value="${lastEnteredPrice}" name="price" type="number" step="0.01" min="0"></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <br>
                    <button type="submit">Confirm</button><br>
                    <br>
                    <input type="button" value="Back"
                           onclick="window.location='/spring.mvc.onlineshop/admin/product/all'">
                </td>
            </tr>
        </table>
    </form>

    <table>
        <tr>
            <td>
                <h3>${incompleteFormError}</h3>

                <h3>${illegalPriceError}</h3>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
