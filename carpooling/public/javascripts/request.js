// Request JS file
var trajectList;
	
var TrajectList = function(container) {
	this.container = container;
	
	this.Add = function(t) {
		this.container.innerHTML+= "<div class='choice' id='choice_"+t.id+"' onclick='calcRoute(trajects["+t.id+"]);'>"+
			"Traject from <b>"+ t.fromName + "</b> to <b>" + t.toName + "</b> by " + t.driver + " (" + t.price + "€, mean rating: "+t.driverRate+"/5)" +
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
var selected = -1;
function calcRoute(traject) {
	if (selected != -1)
		document.getElementById('choice_'+selected).style.border = '1px solid transparent';
	selected = traject.id;
	document.getElementById('choice_'+selected).style.border = '1px dotted black';
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

function selectTraject() {
	if (selected == -1)
		alert("You must first choose a traject !");
	else location.href = "/requestselectedtraject?selected="+selected;
}

function moreInfos(i) {
	alert("test");
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