<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="icon" type="image/x-icon" href="favicon.ico" />
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" />
        <link rel="stylesheet" href="boscop/style.css" />
        <title>BOSCOP Trackers</title>
    </head>
    <body>
        <nav>
            <ul>
                <li>
                    <a href="/">Map</a>
                </li>
                <li>
                    <a href="tracker" class="active">Trackers</a>
                </li>
                <li>
                    <a href="api">API</a>
                </li>
            </ul>
        </nav>
        <main>
            <form action="./api/v1/tracker" method="post">
                <label for="fopta">OPTA: (e.g. BUTHWND ONEB 2110)</label><br />
                <input type="text" id="fopta" name="opta" />
                <input type="hidden" name="lat" value="0.0" />
                <input type="hidden" name="lon" value="0.0" />
                <input type="submit" value="Submit" />
            </form>
            <table id="tracker-table" class="table">
                <tr>
                    <th>OPTA</th>
                    <th>Key</th>
                </tr>
                <c:forEach var="tracker" items="${trackers}">
                    <tr>
                        <td>${tracker.opta}</td>
                        <td>${tracker.token}</td>
                    </tr>
                </c:forEach>
            </table>
        </main>
        <footer>

        </footer>
    </body>
</html>
