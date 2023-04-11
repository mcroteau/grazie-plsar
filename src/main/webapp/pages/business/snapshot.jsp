
<style>
    .value.large{font-size:102px;}
</style>

<h1 style="margin:20px 0px 0px;">Snapshot!</h1>
<p><strong>${tips.size()}</strong> Tips Received!</p>

    <a:if spec="${tips.size() > 0}">
        <table>
            <tr>
                <th>Email</th>
                <th>Amount</th>
                <th>Monthly</th>
            </tr>
            <a:foreach items="${tips}" var="tip">
                <tr>
                    <td>${tip.email}</td>
                    <td style="text-align: center;"><strong>$${tip.amount}</strong></td>
                    <td style="text-align: center;">
                        <stargzr:if spec="${tip.subscriptionId != '' && tip.subscriptionId != 'null'}">
                            &check;
                        </stargzr:if>
                        <stargzr:if spec="${!tip.subscriptionId == '' || tip.subscriptionId == 'null'}">
                            -
                        </stargzr:if>
                    </td>
                </tr>
            </a:foreach>
        </table>
    </a:if>

<a:if spec="${tips.size() == 0}">
    <p class="notify">No tips yet, check back though and stay to it! Good luck!</p>
</a:if>

