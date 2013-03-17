// Request JS file
var trajectList;
	
var TrajectList = function(container) {
	this.container = container;
	
	this.Add = function(t) {
		this.container.innerHTML+= "<div>"+
			"There is a traject !!!" +
			"</div>";
	}
	
	this.AddAll = function(trajects) {
		for (t in trajects)
			this.Add(t);
	}
}

window.onload = function() {
	initMap("map");
	map.setCenter(new google.maps.LatLng(center.Y, center.X));
	
	trajectList = new TrajectList(document.getElementById('choices'));
	trajectList.AddAll(trajects);
}