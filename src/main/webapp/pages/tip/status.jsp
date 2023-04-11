
    <h1>$${tip.amount} tipped</h1>
    <h3>${tip.recipient.name}</h3>

    <p>Successfully processed your donation!</p>
    <a:if spec="${tip.recipient.description != ''}">
        <p>${tip.recipient.name} thanks you!</p>
    </a:if>

    <a:if spec="${tip.recipient.description != ''}">
        <h3>Here is a word from ${tip.recipient.name}</h3>
        <p style="margin:10px 0px 10px !important">"${tip.recipient.description}"</p>
    </a:if>