
    <a:if spec="${message != ''}">
        <p class="notify">${message}</p>
    </a:if>

    <h1>${business.business.name}</h1>
    <h2>Edit Details</h2>

    <form action="/businesses/update" method="post">

        <input type="hidden" name="id" value="${business.id}"/>

        <label>Position</label>
        <span class="tiny">Are you a grocery clerk, waitress, bus boy or dry cleaner?</span><br/>
        <input type="text" name="position" value="${business.position}" placeholder="Grocery Clerk" style="width:70%"/>

        <label>Start Year</label>
        <span class="tiny">Please enter a valid year.</span><br/>
        <input type="number" name="dateStarted" value="${business.dateStarted}" style="width:30%;"/>

        <label>Years Worked</label>
        <span class="tiny">How many years have you worked at this fabulous institution?</span><br/>
        <input type="number" name="years" value="${business.years}" style="width:40%;"/>

        <label>Part Time</label>
        <a:set var="checked" value=""/>
        <a:if spec="${business.partTime}">
            <a:set var="checked" value="checked"/>
        </a:if>
        <input type="checkbox" name="partTime" ${checked}/>

        <div class="button-wrapper-lonely">
            <input type="submit" class="button green serious" value="Update Details"/>
        </div>
    </form>
</div>

