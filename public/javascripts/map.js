var map;
function initialize_map() {
	var mapOptions = {
		zoom : 6,
		center : new google.maps.LatLng(52, 19.3)
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
}

google.maps.event.addDomListener(window, 'load', initialize_map);
