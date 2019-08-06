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

    <form action="/spring.mvc.onlineshop/login" method="post">
        <table>
            <tr>
                <td>Email:</td>
                <td><input value="${lastEnteredEmail}" name="email" type="email"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input value="${lastEnteredPassword}" name="password" type="password"/></td>
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
                <h3>${incompleteFormError}</h3>

                <h3>${LoginError}</h3>
            </td>
        </tr>
    </table>

</div>

</body>
</html>
