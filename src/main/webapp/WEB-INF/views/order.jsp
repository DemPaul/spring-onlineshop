<%--
  Created by IntelliJ IDEA.
  User: dps46
  Date: 07.08.2019
  Time: 1:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your order</title>
</head>
<body>
<div align="center">
    <h2>To confirm the order, enter delivery data and contact details</h2>

    <form action="/spring.mvc.onlineshop/user/order" method="post">
        <table>
            <tr>
                <td>Name:</td>
                <td>
                    <input value="${lastEnteredName}" name="name" type="text"/>
                </td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input value="${lastEnteredEmail}" name="email" type="email"/></td>
            </tr>
            <tr>
                <td>Phone number:</td>
                <td><input value="${lastEnteredPhoneNumber}" name="phoneNumber" type="number"
                           min="0"/></td>
            </tr>
            <tr>
                <td>Country:</td>
                <td><input value="${lastEnteredCountry}" name="country" type="text"/></td>
            </tr>
            <tr>
                <td>City:</td>
                <td><input value="${lastEnteredCity}" name="city" type="text"/></td>
            </tr>
            <tr>
                <td>Street & house:</td>
                <td>
                    <input value="${lastEnteredStreet}" name="street" type="text" size="12"/>
                    <input value="${lastEnteredHouseNumber}" name="houseNumber" type="text"
                           size="3"/>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <div align="center">
                    <button type="submit">Confirm</button><br>
                    <br>
                    <input type="button" value="Back"
                           onclick="window.location='/spring.mvc.onlineshop/user/basket'"><br>
                    <br>
                    <input type="button" value="Exit"
                           onclick="window.location='/spring.mvc.onlineshop/exit'">
                    </td>
                </div>
            </tr>
        </table>

        <table>
            <tr>
                <td>
                    <h3>
                        ${incompleteFormError}
                    </h3>
                </td>
            </tr>
        </table>
    </form>

</div>

</body>
</html>
