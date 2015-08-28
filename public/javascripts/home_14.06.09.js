var litmap = {
	MarkerData: function (attrs){
	   this.locationLat = attrs.locationLat;
	   this.locationLng = attrs.locationLng;
	   this.title = attrs.title;
	   this.info = attrs.info;
	   this.locationText = attrs.locationText;
	   this.nameClass = attrs.nameClass;
	}
}
  

$(function() {
	
	var markers = [];
	
	var map_max_auto_zoom = 15;
	
	var open_infowindow;
	
	var textobjects;
	  
    $.get("/dokumenty", function(data) {
  	  $("#documents").empty();
      $.each(data, function(index, document) {
        var item = $("<a>");
        item.text(document.name);
        item.attr("data-document_id", document.id);
        item.attr("class", "list-group-item document-item");

        $("#documents").append(item);
      });
      
    });
    
    var load_document = function(id) {
    	$.get("/dokument/"+id, function(data) {
    		$("#document_plainText").text(data.plainText);
    	});
    };
    
    $("#documents").on("click", ".document-item", function(e){
    	e.preventDefault();
    	$(this).siblings(".document-item").removeClass("active");
    	$(this).addClass("active");
    	load_document($(this).attr("data-document_id"));
    	
    	$(".show_when_document_choosen").show();
    	return false;
    });
    
    var visualize_markers = function(map_markers_data) {
    	// remove old markers
    	/*google.maps.Map.prototype.clearOverlays = function() {
    		  for (var i = 0; i < markersArray.length; i++ ) {
    		    markersArray[i].setMap(null);
    		  }
    		  markersArray.length = 0;
    		}*/
    	initialize_map();
    	// add new
    	if (map_markers_data.length > 0) {
    		markers = [];
    		var marker_index = 0;
	    	var iterator = 0;
	
	    	function drop() {
	    	  for (var i = 0; i < map_markers_data.length; i++) {
	    	    setTimeout(function() {
	    	      addMarker();
	    	    }, i * 200);
	    	  }
	    	}

	    	function addMarker() {
	    		  var infoContentString = '<div class="map_info_content">'+
	    	      '<h4 title="'+map_markers_data[iterator].nameClass+'">'+map_markers_data[iterator].title+'</h4>'+
	    	      '<p class="map_info_content_underHeader">Nazwa lokalizacji: '+map_markers_data[iterator].locationText+'</p>'+
	    	      '<p class="map_info_content_underHeader">Współrzędne: '+map_markers_data[iterator].locationLat+','+map_markers_data[iterator].locationLng+'</p>'+
	    	      '<p class="map_info_content_sentence">'+map_markers_data[iterator].info+'</p>'+
	    	      '</div>';
	    		  var infowindow = new google.maps.InfoWindow({
	    		      content: infoContentString
	    		  });
	    		
		    	  var marker = new google.maps.Marker({
	  	    	    position: new google.maps.LatLng(map_markers_data[iterator].locationLat, map_markers_data[iterator].locationLng),
		    	    map: map,
		    	    title: map_markers_data[iterator].title,
		    	    draggable: false,
		    	    animation: google.maps.Animation.DROP
	    	      });
		    	  markers.push(marker);
		    	  
		    	  google.maps.event.addListener(marker, 'click', function() {
		    		if (open_infowindow) {
		    			open_infowindow.close();
		    		}
	    		    infowindow.open(map,marker);
		    		open_infowindow = infowindow;
	    		  });

		    	  iterator++;
	    	}
	    	map_zoom_to_all_markers_data(map, map_markers_data); 
	    	drop();
    	}
    }
    
    var map_zoom_to_all_markers_data = function(map, markers_data) {
    	var bounds = new google.maps.LatLngBounds();
    	for(i=0;i<markers_data.length;i++) {
    	 bounds.extend(new google.maps.LatLng(markers_data[i].locationLat, markers_data[i].locationLng));
    	}

    	map.fitBounds(bounds);
    	if (map.getZoom() > map_max_auto_zoom) {
    		map.setZoom(map_max_auto_zoom);
    	}
    }
    
    $("#btn_process").on("click", function(e){
    	e.preventDefault();
    	var btn = $(this)
        btn.button('loading')
        var btn_ladda = Ladda.create(this);
    	btn_ladda.start();
        var document_id = $("#documents .active").first().attr("data-document_id");
    	var map_markers_data = [];
		$("#document_objects").empty();

        $.ajax({
        	url: "/dokument/"+document_id+"/obiekty",
        	success: function(data) {
        		var marker_index = 0;
        		var textobject_index = 0;
        		textobjects = data;
        		$.each(data, function(index, documentObject) {
	                var item = $("<a>");
	                item.text(documentObject.baseName);
	                item.attr("data-baseName", documentObject.baseName);
	                item.attr("data-nameClass", documentObject.nameClass);
	                item.attr("data-text", documentObject.rawText);
	                item.attr("data-sentenceText", documentObject.rawSentenceText);
	                item.attr("data-sentenceTextWithSpan", documentObject.sentenceTextWithSpan);
	                item.attr("data-locationText", documentObject.locationRecognizeResult.searchString);
	                item.attr("title", "Klasa: "+documentObject.nameClass+"\n"+
	                		"W tekście: (...)"+documentObject.rawText+"(...)\n"+
	                		"Forma podstawowa (wymuszona): "+documentObject.forceBaseName+"\n"+
	                		"Zdanie: (...)"+documentObject.rawSentenceText+"(...)\n"+
	                		"Lokalizacja: "+(documentObject.locationText == "" ? "brak" : documentObject.locationText)+"\n"+
	                		"Nazwa wyszukanej lokalizacji: "+(documentObject.locationRecognizeResult.searchString == "" ? "brak" : documentObject.locationRecognizeResult.searchString)+"\n"+
	                		"Status geolokalizacji: "+documentObject.locationGeocodeStatus.toLowerCase());
	                item.attr("class", "list-group-item document-item");
                	item.attr("data-textobjectIndex", textobject_index);
	                if (documentObject.locationText != "") {
	                	item.addClass("list-group-item-success");
		                item.attr("data-markerIndex", marker_index);
	                	var marker_data = new litmap.MarkerData({
	                		locationLat: parseFloat(documentObject.locationLat), 
	                		locationLng: parseFloat(documentObject.locationLng),
	                		title: documentObject.baseName,
	                		info: documentObject.sentenceTextWithSpan,
	                		locationText: documentObject.locationRecognizeResult.searchString,
	                		nameClass: documentObject.nameClass
        	    	    });
	                	
	                	map_markers_data.push(marker_data);
	                	textobjects[textobject_index].marker_index = marker_index;
		                marker_index++;
	                } else if (documentObject.toRelations.length > 0) {
//	                	item.addClass("list-group-item-info"); //
	                	var to_indexes = [];
	                	for (index = 0; index < documentObject.toRelations.length; ++index) {
	                		to_indexes.push(documentObject.toRelations[index].toIndex);
	                	}
	                	item.attr("data-toRelations", to_indexes.join(","));
	                }
	                
	                $("#document_objects").append(item);
	                textobject_index++;
	            });
        		$(".show_when_text_processed").show();
        		visualize_markers(map_markers_data);
        	},
        	error: function(xhr, status, error) {
        		$("#document_objects").empty();
        		alert(xhr.responseText);
        		$(".show_when_text_processed").hide();
        	}
        	}).always(function () {
          btn.button('reset');
          btn_ladda.stop();
        });
    	return false;
    });
    
    $("#document_objects").on("click", "a", function() {
    	var marker_index = $(this).attr("data-markerIndex");
    	if (marker_index === undefined) {
    		/* Pobranie relacji "to" z markerem i wybranie pierwszej
    		 * - daje to błędnie interpretowane i niepełne informacje.
    		 * Można ew. przypisać do obiektów z markerem (geolokalizowalnych).
    		 * Aczkolwiek w obecniej wersji połączenia z Serelem (2014.05.14)
    		 * nadal daje kiepskie rezultaty.
    		 */
    		/*
    		var to_indexes = $(this).attr("data-toRelations");
    		if (to_indexes !== undefined) {
    			to_indexes_arr = to_indexes.split(",");
    			for (index = 0; index < to_indexes_arr.length; ++index) {
            		if (textobjects[to_indexes_arr[index]].locationText != "") {
            			marker_index = textobjects[to_indexes_arr[index]].marker_index;
            		}
            	}
    		}
    		*/
    	}
    	if (marker_index !== undefined) {
    		google.maps.event.trigger(markers[marker_index], 'click');
    	}
    });

    
  });
