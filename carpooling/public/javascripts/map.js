// JavaScript Document MAP

var map;
var geocoder = null;
var markersArray = [];


function initMap(id){
    var myOptions = {
        zoom: 8,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById(id),myOptions);
    if(!geocoder) geocoder = new google.maps.Geocoder();
}

function geolocationMe(fct){
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            function(position) {
                var myPosition = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
                fct(myPosition);
            },
            function() {
                handleNoGeolocation(true);
            });
    }else{
        // Browser doesn't support Geolocation
        handleNoGeolocation(false);
    }
}
function handleNoGeolocation(errorFlag) {
    if (errorFlag) {
        var content = 'The Geolocation service failed.';
    } else {
        var content = 'Your browser doesn\'t support geolocation.';
    }
    alert(content);
}

function getGPScoord(address,fct){
    geocoder = new google.maps.Geocoder();
    geocoder.geocode(
        { 'address': address+" Belgium"},
        function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                var position = results[0].geometry.location;
                if(fct) fct(position);
            }else{
                alert("Address no found !");
            }
        });
}



function getAroundMe(sandwicherie,myPosition,fct){
    var service = new google.maps.DistanceMatrixService();

    var dest = new Array();
    for(var i=0; i<sandwicherie.length; i++){
        var elem = sandwicherie[i];
        dest[i] = new google.maps.LatLng(elem.gps_lat, elem.gps_long);
    }

    service.getDistanceMatrix(
        {
            origins:[myPosition],
            destinations: dest,
            travelMode: google.maps.TravelMode.DRIVING,
            unitSystem: google.maps.UnitSystem.METRIC,
            avoidHighways: false,
            avoidTolls: false
        }, getAroundMe_callback);


    function getAroundMe_callback(response, status) {
        if (status == google.maps.DistanceMatrixStatus.OK) {
            var results = response.rows[0].elements;
            for (var j = 0; j < results.length; j++) {
                if(typeof results[j].distance != "undefined" && typeof results[j].duration != "undefined"){
                    var distance = results[j].distance.text;
                    var duration  = results[j].duration.text;
                    fct(distance,duration,j);
                }
            }

        }
    }
}

function deleteOverlays() {
    if (markersArray) {
        for (i in markersArray) {
            markersArray[i].setMap(null);
        }
        markersArray.length = 0;
    }
}

var iconURL = {
    "start":"../assets/images/map/map_cursor_green.png",
    "end":"../assets/images/map/map_cursor_green.png",
    "default":"../assets/images/map/map_cursor.png"
};
function addMarker(location, iconName) {
    var icon;
    if (iconURL[iconName])	icon = iconURL[iconName];
    else icon = iconURL["default"];
    var marker = new google.maps.Marker({
        map: map,
        position: location,
        icon: icon
    });
    markersArray.push(marker);
    if (listenerMarker) google.maps.event.addListener(marker, 'click', listenerMarker);
    return marker;
}

/**
 * Add a tab (json) of marker in maps
 * @param tab follow this format
 *     [{gps_lat:DOUBLE,gps_long:DOUBLE},{gps_lat: "50.6608938",gps_long: "4.56824410000001"},...]
 *     Information can be add inside each {}
 */
function addTabMarker(tab){
    for(var i=0; i<tab.length; i++){
        var elem = tab[i];
        var location = new google.maps.LatLng(elem.gps_lat, elem.gps_long);
        var marker = addMarker(location,null);
    }
}


function focusOver(lat,long,z){
    var myOptions = {
        zoom: z,
        center: new google.maps.LatLng(lat,long)
    };
    map.setOptions(myOptions);
}


function activeAddMarkerCick(fct){
    google.maps.event.addListener(map, 'click', function(e) {
        addMarker(e.latLng, null);
        if(fct) fct(e);
    });
}

var listenerMarker = null;
function addListenerMarker(listener) {
    /*
    for(var i=0; i<markersArray.length; i++){
        var marker = markersArray[i];
        google.maps.event.addListener(marker, 'click', listener);
    }
    */
    listenerMarker = listener;
}