// JavaScript Document MAP

var map;
var geocoder = null;


function initMap(id){
    var myOptions = {
        zoom: 11,
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