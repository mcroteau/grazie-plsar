<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <stargzr:if spec="${message != ''}">
        <p class="notify">${message}</p>
    </stargzr:if>

    <h2>location requests</h2>
    <table>
        <stargzr:foreach items="${requests}" var="req">
            <tr>
                <td>${req.name}<br/>
                    ${req.address}</td>
                <td>
                    <form action="/signup_request/delete/${req.id}" method="post">
                        <input type="submit" class="button remove serious bubble" value="Acknowledged & Ingested!"/>
                    </form>
                </td>
            </tr>
        </stargzr:foreach>
    </table>

    <h2 style="margin-top:20px;">users</h2>
    <table>
        <stargzr:foreach items="${users}" var="userCredential">
            <tr>
                <td>${userCredential.guid}</td>
                <td>${userCredential.email}</td>
            </tr>
        </stargzr:foreach>
    </table>