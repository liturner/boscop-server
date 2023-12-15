<%-- 
    Document   : tracker
    Created on : 15.12.2023, 20:32:39
    Author     : lukei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <a href="tracker" clase="active">Trackers</a>
                </li>
                <li>
                    <a href="api">API</a>
                </li>
            </ul>
        </nav>
        <main>
            <form action="/tracker" method="post">
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
                <%
                    // TODO: 
                    /**
                     * 
                     * for (Entry trackerKey : TrackerToken.entrySet()) {
                            out.writeStartElement("tr");
                                out.writeStartElement("td");
                                out.writeCharacters(trackerKey.getKey().toString());
                                out.writeEndElement();
                                out.writeStartElement("td");
                                out.writeCharacters(trackerKey.getValue().toString());
                                out.writeEndElement();
                            out.writeEndElement();
                        }
                     * 
                     */
                %>
            </table>
        </main>
        <footer>

        </footer>
    </body>
</html>
