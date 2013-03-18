// Request JS file
var trajectList;
	
var TrajectList = function(container) {
	this.container = container;
	
	this.Add = function(t) {
		this.container.innerHTML+= "<div class='choice' onclick='calcRoute(trajects["+t.id+"]);'>"+
			"Traject from <b>"+ t.fromName + "</b> to <b>" + t.toName + "</b> by " + t.driver + " (" + t.price + "€)" +
			"</div>";
	}
	
	this.AddAll = function(trajects) {
		for (i in trajects)
			this.Add(trajects[i]);
	}
}

var directionDisplay;
var directionsService;
var trajectCache = {};
function calcRoute(traject) {
	if (trajectCache[traject.id] != null) {
		directionsDisplay.setDirections(trajectCache[traject.id]);
		return;
	}
	var request = {
		origin:traject.from,
		destination:traject.to,
		travelMode: google.maps.DirectionsTravelMode.DRIVING
	};
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			trajectCache[traject.id] = response;
			directionsDisplay.setDirections(response);
		}
	});
}


window.onload = function() {
	initMap("map");
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsService = new google.maps.DirectionsService();
	map.setCenter(new google.maps.LatLng(center.Y, center.X));
	directionsDisplay.setMap(map);
	
	addMarker(from, "Selected origin point");
	addMarker(to, "Selected destination point");
	
	trajectList = new TrajectList(document.getElementById('choices'));
	trajectList.AddAll(trajects);
}