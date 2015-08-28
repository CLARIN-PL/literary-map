package pl.edu.pwr.litmap.objectrecognize;

import java.util.ArrayList;
import java.util.List;

import com.google.code.geocoder.model.GeocoderResult;

public class LocationRecognizeResult {
	private final List<GeocoderResult> list;
	private final String searchString;
	public static LocationRecognizeResult EMPTY = new LocationRecognizeResult("", new ArrayList<GeocoderResult>(0));
	
	public LocationRecognizeResult(String searchString, List<GeocoderResult> list) {
		this.searchString = searchString;
		this.list = list;
	}

	public List<GeocoderResult> getList() {
		return list;
	}
	
	public boolean hasResults() {
		return !list.isEmpty();
	}

	public String getSearchString() {
		return searchString;
	}

}
