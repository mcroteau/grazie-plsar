
<h1>Person Locator!</h1>
<p>Find the person you would like to tip!</p>

<label>Town/City</label>

<select name="townId" id="town" style="width:100%;">
    <option>Select a Town/City</option>
    <a:foreach items="${towns}" var="town">
        <a:set var="selected" val=""/>
        <a:if spec="${town.id == townId}">
            <a:set var="selected" val="selected"/>
        </a:if>
        <option value="${town.id}"
                data-lat="${town.latitude}"
                data-lon="${town.longitude}" ${selected}>${town.name}</option>
    </a:foreach>
</select><br/>

<a:if spec="${businesses.size() > 0}">
    <label>Business where the Person Worked!</label>
    <select name="businessId" id="business" style="width:100%;text-align:center">
        <option>Select One</option>
        <a:foreach items="${businesses}" var="busy">

            <a:set var="selected" val=""/>
            <a:if spec="${busy.id == business.id}">
                <a:set var="selected" val="selected"/>
            </a:if>

            <option value="${busy.id}"
                    data-lat="${busy.latitude}"
                    data-lon="${busy.longitude}"
                ${selected}>${busy.address}</option>
        </a:foreach>
    </select><br/>

</a:if>

<h1 id="options">${business.name}</h1>

<a:if spec="${people.size() > 0}">
    <p>Here are the fine people who are employed at ${business.name}</p>
    <table>
        <a:foreach items="${people}" var="person">
            <a:if spec="${person.name != ''}">
                <tr>
                    <td>
                        <a:if spec="${person.photo != ''}">
                            <img src="${person.photo}" style="width:110px;border-radius:60px;padding:7px;border:solid 1px #deeaea"/>
                        </a:if>
                        <a:if spec="${person.photo == ''}">
                            <div class="image-placeholder" style="height:60px;width:60px;color:#fff;padding:20px 0px;border-radius: 62px;background:#3979E4;text-align: center;">${person.initials}</div>
                        </a:if>
                    </td>
                    <td>
                        <strong>${person.name}</strong><br/>
                        <a:if spec="${person.userBusiness.position != ''}">
                            Position : ${person.userBusiness.position}<br/>
                        </a:if>
                        <a:if spec="${person.userBusiness.dateStarted != 0}">
                            <span class="tiny">Date Started : ${person.userBusiness.dateStarted}</span>
                        </a:if>
                        <a:if spec="${person.userBusiness.years != 0}">
                            <span class="tiny">Years Worked : ${person.userBusiness.years}</span><br/>
                        </a:if>
                        <a:if spec="${person.userBusiness.years == 0}">
                            <span class="tiny">Years Worked : Just Started</span><br/>
                        </a:if>
                        <a:if spec="${person.userBusiness.partTime}">
                            <span class="tiny">Part-time</span>
                        </a:if>
                        <a:if spec="${!person.userBusiness.partTime}">
                            <span class="tiny">Full-time</span>
                        </a:if>
                    </td>
                    <td><a href="/${person.guid}" class="button green">Tip $</a></td>
                </tr>
            </a:if>
        </a:foreach>
    </table>
</a:if>
<a:if spec="${people.size() == 0}">
    <p class="notify">No one signed up at that location yet. Tell them about this,
        you may help them turn a crappy job into an awesome job!</p>
</a:if>


<div id="map" style="width:100%;height:300px;margin:40px 0px 0px;"></div>






<script
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCQulbBR1VkrQsKwisM1mLEyEMRUoT2GCI&callback=initMap&libraries=&v=weekly"
    async
></script>

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
        const uri = '/discover?z=' + id + coordinates;
        window.location.href = uri;
    })

    $business.change(function(){
        const town = $town.val();
        const business = $business.val();
        const selected = $business.find(":selected");
        const lat = selected.data('lat');
        const lon = selected.data('lon');
        const coordinates = '&lat=' + lat + '&lon=' + lon;
        const uri = '/discover?z=' + town + '&zq=' + business + coordinates + '#options';
        window.location.href = uri;
    });

});


function initMap() {

    let uluru = { lat: parseFloat('${lat}'), lng: parseFloat('${lon}') };
    zoom = 10;
    console.log(uluru)
    if(isNaN(uluru.lat) && isNaN(uluru.lng)){
        uluru.lat = 42.7432082;
        uluru.lng = -89.5296815;
        zoom = 2;
    }

    // The location of Uluru
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: zoom,
        center: uluru,
    });
    // The marker, positioned at Uluru
    const marker = new google.maps.Marker({
        position: uluru,
        map: map,
    });
}
</script>
