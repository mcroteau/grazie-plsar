
    <h2>Employment</h2>
    <a href="/employment/create" class="button retro serious bubble">Add New Employer</a>

    <table style="width:100%">
        <tr>
            <th>Business</th>
            <th>Status</th>
        </tr>
        <a:foreach items="${businesses}" var="business">
            <tr>
                <td>
                        ${business.name}<br/>
                    <a:if spec="${business.userBusiness.partTime}">
                        Part Time.<br/>
                    </a:if>
                    <a:if spec="${!business.userBusiness.partTime}">
                        Full Time! <br/>
                    </a:if>
                    Years worked : ${business.userBusiness.years}
                    <a href="/businesses/edit/${business.userBusiness.id}" class="href-dotted-black">Edit Info!</a>
                </td>
                <td>
                    <a:if spec="${business.userBusiness.active}">
                        <form action="/businesses/disable/${business.userBusiness.id}">
                            <input type="submit" value="I No Longer Work Here" class="button remove serious bubble"/>
                        </form>
                    </a:if>
                    <a:if spec="${!business.userBusiness.active}">
                        <form action="/businesses/enable/${business.userBusiness.id}">
                            <input type="submit" value="I Work Here Again!" class="button green serious bubble"/>
                        </form>
                    </a:if>
                </td>
            </tr>
        </a:foreach>
    </table>