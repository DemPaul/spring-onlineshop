<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello!</title>
</head>
<body>

<div align="center">
    <h1>Welcome!</h1>

    <form action="<spring:url value="/signin"/>" method="post">
        <table>
            <tr>
                <td>Email:</td>
                <td><input
                        <c:if test="${not empty sessionScope.lastEnteredEmail}">
                            value="${sessionScope.lastEnteredEmail}"
                            <c:remove var="lastEnteredEmail" scope="session"/>
                        </c:if>
                        name="email" type="email"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input
                        <c:if test="${not empty sessionScope.lastEnteredPassword}">
                            value="${sessionScope.lastEnteredPassword}"
                            <c:remove var="lastEnteredPassword" scope="session"/>
                        </c:if>
                        name="password" type="password"/></td>
            </tr>
        </table>
        <table>
            <tr>
                <td>
                    <button type="submit">Login</button>
                </td>
            </tr>
        </table>
    </form>

    <table>
        <tr>
            <td>
                <center>
                    <h3>
                        <form action="/spring.mvc.onlineshop/register" method="get">
                            <button type="submit">Registration</button>
                        </form>
                    </h3>
                </center>
            </td>
        </tr>
    </table>

    <table>
        <tr>
            <td>
                <c:if test="${not empty sessionScope.message}">
                    <span style="color:red"><c:out value="${sessionScope.message}"/></span>
                    <c:remove var="message" scope="session"/>
                </c:if>
            </td>
        </tr>
    </table>

</div>

</body>
</html>
