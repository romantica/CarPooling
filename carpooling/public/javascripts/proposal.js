function nextStep() {
    var idForm = "from_new";
    var fromAddress = document.getElementById(idForm+" fromadd").value;
    var toAddress = document.getElementById(idForm+" toadd").value;
    getGPScoord(fromAddress,
        function (fromCoordinate) {
            getGPScoord(toAddress,
                function (toCoordinate) {
                    document.getElementById(idForm+" fromcoord").value = fromCoordinate.lat() + "," + fromCoordinate.lng();
                    document.getElementById(idForm+" tocoord").value = toCoordinate.lat() + "," + toCoordinate.lng();
                    document.getElementById(idForm).submit();
                });
        });
}
function clickMarker(e){
    coord = e.latLng;
    coord_lat = Math.round(coord.lat()*10000000)/10000000;
    coord_lng = Math.round(coord.lng()*10000000)/10000000;
    //Search if this pp in selectedPP[]
    //Add or remove selection pp
    var toAdd = true;
    for (var i in selectedPP) {
        item = selectedPP[i];
        gps_lat = Math.round(item.gps_lat *10000000)/10000000;
        gps_long = Math.round(item.gps_long*10000000)/10000000;
        if (gps_lat == coord_lat && gps_long == coord_lng) { //remove
            removeFormPPSlected(selectedPP[i]);
            delete selectedPP[i];
            toAdd = false;
            break;
        }
    }
    //Add
    if (toAdd) {
        thispp = null;
        for (var i in pp) {
            item = pp[i];
            gps_lat = Math.round(item.gps_lat *10000000)/10000000;
            gps_long = Math.round(item.gps_long*10000000)/10000000;
            if (gps_lat == coord_lat && gps_long == coord_lng) {
                thispp = item;
            }
        }
        if(thispp) selectedPP.push(thispp);
        addFormPPSelected(thispp);
    }
}

function addFormPPSelected(pickuppoint){
    coord = pickuppoint.gps_lat+","+pickuppoint.gps_long;
    var html = '<div id="pp_'+coord+'">';
    html += '<input type="hidden" name="'+coord+'" value="'+coord+'" />';
    if(pickuppoint.name == null){
        html += '<input type="hidden" name="'+coord+'_new" />';
        html += '<dl>Name of Point:</dl><dd><input type="text" name="'+coord+'_name" /></dd>';
    }
    html += '<dl>Arrival Time:</dl><dd><input type="datetime-local" name="'+coord+'_arrivaltime" /></dd>';
    html += '<dl>Start Time:</dl><dd><input type="datetime-local" name="'+coord+'_starttime" /></dd>';
    html += "</div>";
    var form = document.getElementById("pp_form");
    form.innerHTML += html;
}

function removeFormPPSlected(pickuppoint){
    coord = pickuppoint.gps_lat+","+pickuppoint.gps_long;
    var form = document.getElementById("pp_form");
    var divF = document.getElementById("pp_"+coord);
    form.removeChild(divF)
}
