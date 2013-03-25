function nextStep() {
    var idForm = "form_new";
    var fromAddress = document.getElementById(idForm+" fromadd").value;
    var toAddress = document.getElementById(idForm+" toadd").value;
    var error_div = document.getElementById(idForm+" error");
    error_div.innerHTML = "";
    if(fromAddress == ""){
        error_div.innerHTML = "<ul><li>Empty address from no permit!</li></ul>";
        return;
    }
    if(fromAddress == ""){
        error_div.innerHTML = "<ul><li>Empty address to no permit!</li></ul>";
        return;
    }
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
var count = 0;
function addFormPPSelected(pickuppoint){
    var coord = pickuppoint.gps_lat+","+pickuppoint.gps_long;
    var id = pickuppoint.id;
    if(id == null){
        id = "new#"+count;
        count++;
    }
    var html = "";
    html += '<input type="hidden" name="'+id+'" value="'+(pickuppoint.id == null ? "new" : id)+'" />';
    if(pickuppoint.Address != null){
        html += '<div class="address">'+pickuppoint.Address+'</div>';
        html += '<input type="hidden" name="'+id+'_address" value="'+(pickuppoint.Address)+'" />';
    }
    if(pickuppoint.name == null){
        html += '<input type="hidden" name="'+id+'_coord" value="'+coord+'" />';
        html += '<dl>Name of Point:</dl><dd><input type="text" name="'+id+'_name" /></dd>';
    }
    html += '<dd><span>Arrival Time:</span><input type="datetime-local" name="'+id+'_arrivaltime" /></dd>';
    html += '<dd><span>Start Time:</span><input type="datetime-local" name="'+id+'_starttime" /></dd>';
    var div = document.createElement('div');
    div.setAttribute("id","pp_"+id);
    div.setAttribute("class","field pp_item");
    div.setAttribute("style","height:175px; width:200px;");
    div.innerHTML = html;
    var form = document.getElementById("pp_form");
    form.appendChild(div);
}

function removeFormPPSlected(pickuppoint){
    var form = document.getElementById("pp_form");
    //TODO: bug: new pp have id null
    var divF = document.getElementById("pp_"+pickuppoint.id);
    form.removeChild(divF)
}
