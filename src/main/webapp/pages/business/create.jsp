
<stargzr:if spec="${message != ''}">
    <p class="notify">${message}</p>
</stargzr:if>

<h2>Add Employer</h2>
<span class="tiny">Add an employer where you may receive tips.</span>
<form action="/employment/save" method="post">

    <input type="hidden" name="userId" value="${sessionScope.userId}"/>

    <label>Town/City</label>
    <select name="townId" id="town" style="width:100%;">
        <option>Select a Town/City</option>
        <stargzr:foreach items="${towns}" var="town">
            <c:set var="selected" value=""/>
            <stargzr:if spec="${town.id == townId}">
                <c:set var="selected" value="selected"/>
            </stargzr:if>
            <option value="${town.id}"
                    data-lat="${town.latitude}"
                    data-lon="${town.longitude}" ${selected}>${town.name}</option>
        </stargzr:foreach>
    </select><br/>

    <stargzr:if spec="${businesses.size() > 0}">
        <label>Business where you Work!</label>
        <select name="businessId" id="business" style="width:100%;text-align:center">
        <option>Select One</option>
        <stargzr:foreach items="${businesses}" var="busy">

            <stargzr:set var="selected" value=""/>
            <stargzr:if spec="${busy.id == business.id}">
                <stargzr:set var="selected" value="selected"/>
            </stargzr:if>

            <option value="${busy.id}"
                    data-lat="${busy.latitude}"
                    data-lon="${busy.longitude}"
                ${selected}>${busy.address}</option>
        </stargzr:foreach>
        </select><br/>
    </stargzr:if>

    <div class="button-wrapper-lonely">
        <input type="submit" class="button green" value="Save Employment"/>
    </div>
</form>

<script>
$(document).ready(function(){
    let $town = $('#town');
    let $business = $('#business');

    $town.change(function(){
        const id = $town.val();
        const selected = $town.find(":selected");
        const lat = selected.data('lat');
        const lon = selected.data('lon');
        const coordinates = '&lat=' + lat + '&lon=' + lon;
        const uri = '/employment/create?z=' + id + coordinates + "#business";
        window.location.href = uri;
    })

    // $business.change(function(){
    //     const town = $town.val();
    //     const business = $business.val();
    //     const selected = $business.find(":selected");
    //     const lat = selected.data('lat');
    //     const lon = selected.data('lon');
    //     const coordinates = '&lat=' + lat + '&lon=' + lon;
    //     const uri = '/discover?z=' + town + '&zq=' + business + coordinates + '#business';
    //     window.location.href = uri;
    // });

});


<%--function initMap() {--%>

<%--    let uluru = { lat: parseFloat('${lat}'), lng: parseFloat('${lon}') };--%>
<%--    // The location of Uluru--%>
<%--    // The map, centered at Uluru--%>
<%--    const map = new google.maps.Map(document.getElementById("map"), {--%>
<%--        zoom: 4,--%>
<%--        center: uluru,--%>
<%--    });--%>
<%--    // The marker, positioned at Uluru--%>
<%--    const marker = new google.maps.Marker({--%>
<%--        position: uluru,--%>
<%--        map: map,--%>
<%--    });--%>
// }
</script>
