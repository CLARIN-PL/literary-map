package pl.edu.pwr.litmap.objectrecognize;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pl.edu.pwr.litmap.exceptions.OverQueryLimitException;
import pl.edu.pwr.litmap.exceptions.UnrecognizedWebserviceResponseException;
import pl.edu.pwr.litmap.exceptions.ZeroResultsException;
import pl.edu.pwr.litmap.textobjects.NameClass;
import pl.edu.pwr.litmap.textobjects.NamedTextobject;
import play.cache.Cache;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

public class LocationUtil {

	private static boolean DEBUG_PRINT = false;		
	private static boolean DEBUG_OVER_LIMIT_PRINT = true;			

	public static final String URL_GOOGLE_API_GEOCODE = "http://maps.googleapis.com/maps/api/geocode/json?sensor=true&address=";
	public static final int OVER_QUERY_LIMIT_DELAY = 1100; // in ms
	public static final int OVER_QUERY_LIMIT_TRY_NUMBER = 3;
	
	public static String getGoogleApiGeocodeUrl(String address) {
	    String addressEncoded = null;
		try {
			addressEncoded = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    
	    return URL_GOOGLE_API_GEOCODE+addressEncoded;
	}

	public static LatLng getFirstLocation(String address) throws IOException, OverQueryLimitException, ZeroResultsException, UnrecognizedWebserviceResponseException {
		LatLng result = null;
		List<GeocoderResult> gresults = getResults(address).getList();
		if (!gresults.isEmpty()) {
			GeocoderGeometry ggeometry = gresults.get(0).getGeometry();
			result = ggeometry.getLocation();
		} else {
			if (DEBUG_PRINT) {
				System.out.println("empty results");
			}
		}
		return result;
	}
	
	private static List<GeocoderResult> removePartialMatchResultsOLD(List<GeocoderResult> results) {
		List<GeocoderResult> newList = new ArrayList<>(results.size());
		for (GeocoderResult gr : results) {
			if (!gr.isPartialMatch()) {
				newList.add(gr);
			}
		}
		return newList;
	}
	
	// TODO: zmienić nazwę po rozwiąznaiu problemu z partial match (patrz uwaga w metodzie getResults(String address))
	private static List<GeocoderResult> removePartialMatchResults(List<GeocoderResult> results) {
		List<GeocoderResult> newList = new ArrayList<>(results.size());
		for (GeocoderResult gr : results) {
			if (!gr.isPartialMatch()) {
				newList.add(gr);
			}
		}
		return newList;
	}
	
	public static List<GeocoderResult> filterResults(List<GeocoderResult> results, NameClass leaveNameClass) {
		List<GeocodingResultType> leaveTypes = leaveNameClass.getGeocodingResultTypes();
		
		List<GeocoderResult> newList = new ArrayList<>(results.size());
		for (GeocoderResult gr : results) {
			boolean leave = false;
			for(String type : gr.getTypes()) {
				GeocodingResultType grt = GeocodingResultType.fromValue(type);
				if (leaveTypes.contains(grt)) {
					leave = true;
					break;
				}
			}
			if (leave) {
				newList.add(gr);
			}
		}
		return newList;
	}
	
	public static List<GeocoderResult> filterResultsByLevels(List<GeocoderResult> results, List<NamedTextobject> textobjects) {
//		System.out.println("################################################");
//		String debugMethodId = UUID.randomUUID().toString();
//		System.out.println("## method filterResultsByLevels - start id = "+debugMethodId);
		int levels = textobjects.size();
		List<GeocoderResult> newList = new ArrayList<>(results.size());

//		System.out.println("results<GeocoderResult>.size() = "+results.size());
//		System.out.println("textobjects<NamedTextobject>.size() = "+textobjects.size());
//		System.out.println("levels (textobjects.size()) = "+levels);
		
		int debug_1st_loop = -1;
		for (GeocoderResult gr : results) {
//			String debugTabs_1 = "\t";
//			debug_1st_loop++;
			
			int addressComponentsTotalSize = gr.getAddressComponents().size();

//			System.out.println(debugTabs_1+"1st lvl loop ->  results["+debug_1st_loop+"].getAddressComponents().size() = "+addressComponentsTotalSize);
			
			int addressComponentsFilteredSize = 0;
			
			/**
			 * textobject zawiera nazwy obszarów uszeregowane malejąco pod względem powierzchni (Polska, dolnośląskie, Wrocław)
			 * GeocoderAddressComponent comp i ogólnie wyniki z Google Maps API zawierają 
			 *      nazwy obszarów uszeregowane rosnąco pod względem powierzchni (Wrocław, województwo dolnośląskie, Polska)
			 * dlatego textobject będzie sprawdzany od końca
			 * TODO: zmienić zapytania na szeregowane rosnąco, tak jak wyniki Google Maps API
			 */
			int textobjectIndex;
			int textobjectIndexLastFound = textobjects.size();
			
//			int debug_2nd_loop = -1;
			
			for (GeocoderAddressComponent comp : gr.getAddressComponents()) {
//				String debugTabs_2 = debugTabs_1+"\t";
//				debug_2nd_loop++;
//				System.out.println(debugTabs_2+"2nd lvl loop ->  gr.getAddressComponents()["+debug_2nd_loop+"].comp.getLongName() = "+comp.getLongName());
//				System.out.println(debugTabs_2+"             ->  gr.getAddressComponents()["+debug_2nd_loop+"].comp.getTypes() = "+String.join(", ", comp.getTypes()));
				
				textobjectIndex = textobjectIndexLastFound-1;
				
				
				boolean typeFound = false;
//				System.out.println(debugTabs_2+"Sprawdzanie występowania w treści zapytania obszaru z treści wyniku: "+comp.getLongName()+" ("+String.join(", ", comp.getTypes())+")");
//				System.out.println(debugTabs_2+"textobjectIndex: "+textobjectIndex);
				
				int debug_3th_loop = -1;
				
				while (textobjectIndex >= 0) {
//					String debugTabs_3 = debugTabs_2+"\t";
//					debug_3th_loop++;
//					System.out.println(debugTabs_3+"3st lvl loop ->  textobjectIndex = "+textobjectIndex);
//					
//					System.out.println(debugTabs_3+"Sprawdzanie dla textobject z zapytania: "+textobjects.get(textobjectIndex).getName());
//					System.out.println(debugTabs_3+"tzn. czy jednym z typów [Google Maps API] jakie posiada obiekt z treści zapytania określany jest jakiś fragment wyniku.");
					for (String type : comp.getTypes()) {
//						System.out.print(debugTabs_3+"\tSprawdzanie typu [Google Maps API]: "+type+" -> ");
						if (textobjects.get(textobjectIndex).getTextobject().getNameClass().getGeocodingResultTypes().contains(GeocodingResultType.fromValue(type))) {
							typeFound = true;
//							System.out.println("tak");
						} else {
//							System.out.println("nie");
						}
						if (typeFound) {
							break;
						}
					}
					if (typeFound) {
						textobjectIndexLastFound = textobjectIndex;
						addressComponentsFilteredSize++;
						break;
					} 
					textobjectIndex--;		
				}
			}
			
//			System.out.println(debugTabs_1+"addressComponentsTotalSize = "+addressComponentsTotalSize+"; "+"addressComponentsFilteredSize = "+addressComponentsFilteredSize+"; levels = "+levels);

			if (addressComponentsFilteredSize == levels) {
				newList.add(gr);
			}
		}
//		System.out.println("## END OF method filterResultsByLevels id = "+debugMethodId);
//		System.out.println("################################################");
		return newList;
	}

	/**
	 * 
	 * @param address
	 * @param leaveNameClass
	 * @param levels - ex. "Polska, Wrocław, Wybrzeże Wyspiańskiego" -> 3 levels
	 * @return
	 * @throws IOException
	 * @throws OverQueryLimitException
	 * @throws UnrecognizedWebserviceResponseException
	 */
	public static LocationRecognizeResult getFilteredResults(String address, NameClass leaveNameClass, List<NamedTextobject> textobjects) throws IOException, OverQueryLimitException, UnrecognizedWebserviceResponseException {
		if (DEBUG_PRINT) {
			System.out.print("Filter results for "+leaveNameClass+": ");
		}
		List<GeocoderResult> results = getResults(address).getList();
		List<GeocoderResult> filteredResults_1 = filterResults(results, leaveNameClass);
		List<GeocoderResult> filteredResults_2 = filterResultsByLevels(filteredResults_1, textobjects);
		if (DEBUG_PRINT) {
			System.out.print("allResultsSize="+results.size()+", ");
			System.out.println("nameClassTranslatedTypes="+leaveNameClass.getGeocodingResultTypes().size()+", filtered_1_Size="+filteredResults_1.size()+", filtered_2_Size="+filteredResults_2.size());
		}
		return new LocationRecognizeResult(address, filteredResults_2);
	}

	private static LocationRecognizeResult getResults(String address) throws IOException, OverQueryLimitException, UnrecognizedWebserviceResponseException {
		String cacheAddressEncode = String.valueOf(address);
		String cacheKey = "litmap.objectrecognize.LocationUtil.getLocationByAddress."+cacheAddressEncode;
		
		@SuppressWarnings("unchecked")
		List<GeocoderResult> gresults = (List<GeocoderResult>) ((List<?>) Cache.get(cacheKey));
		
		if (DEBUG_PRINT) {
			System.out.println("Search cache for \""+address+"\", found: "+(gresults == null ? "no" : "yes"));
		}
		if (gresults == null) {
			if (address == null ||address.trim().isEmpty()) {
				throw new IllegalArgumentException("address is "+(address == null ? "null" : "empty"));
			}
			
			final Geocoder geocoder = new Geocoder();
			if (DEBUG_PRINT) {
				System.out.print("Send request to Geocoder: \""+address+"\"... ");
			}
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("pl").getGeocoderRequest();
			
			int tryCounter = 1;
			GeocodeResponse geocoderResponse = null;
			while ((geocoderResponse == null || geocoderResponse.getStatus().name().equals(OverQueryLimitException.GOOGLE_MSG)) && tryCounter <= LocationUtil.OVER_QUERY_LIMIT_TRY_NUMBER) {
				geocoderResponse = geocoder.geocode(geocoderRequest);
				if (geocoderResponse == null) {
					throw new UnrecognizedWebserviceResponseException("Problem with Google webservice or geocoder library: geocoderResponse is null.");
				}
				if (geocoderResponse.getStatus() == GeocoderStatus.OK) {
					if (DEBUG_PRINT) {
						System.out.print("OK: ");
					}

					gresults = geocoderResponse.getResults();
					//	TODO: Poniższe daje dobre wyniki dla np.:
					//        "Europa, Francja, Szczawnica" zwraca 0 wyników, a z Partial więcej wyników
					//		  Daje błędne dane dla
					// 	      "dolnoślaskie" zwraca 0 wyników (zwraca 1 wynik dla "województwo dolnośląskie")
					//        "Wrocław, Wybrzeże Wyspiańskiego" zwraca 0 wyników (zwraca 1 wynik najprawdopodniej dla "Wrocław, Wybrzeże Stanisława Wyspiańskiego"
					//    Możliwe rozwiązania:
					//    1. Wyjątki: np. dla województw i ew. innych.
					//    2. Sprawdzenie liczby elementów
					// 	     np. dla wejścia "Polska, dolnośląskie, Wrocław" -> 3 poziomy
					//       w wyniku da: "Polska, Wrocław" -> 2 poziomy (obliczyć jaki results."address_components".size()
					//       Uwaga: API Google automatycznie dodaje pośrednie poziomy (i wyższe). Można spróbować je przefiltrować po typach.
					//       przykładowy url: https://maps.googleapis.com/maps/api/geocode/json?address=Wroc%C5%82aw,%20Wybrze%C5%BCe%20Wyspia%C5%84skiego&language=pl
			        //
					//
					// TODO: 2014.11.18 wgawel: testowo wprowadziłem 2. rozwiązaniem (sprawdzanie liczby poziomów ("levels"))
//					gresults = LocationUtil.removePartialMatchResults(geocoderResponse.getResults());
					gresults = geocoderResponse.getResults();
					
					if (DEBUG_PRINT) {
//						System.out.println("count="+geocoderResponse.getResults().size()+"; withoutPartialMatch="+gresults.size());
						System.out.println("count="+geocoderResponse.getResults().size());
						if (gresults.size() > 0) {
							System.out.println("first_location = "+gresults.get(0).getGeometry().getLocation().toString());
						}
					}
				} else {
					if (geocoderResponse.getStatus().name().equals(OverQueryLimitException.GOOGLE_MSG)) {
						if (DEBUG_OVER_LIMIT_PRINT) {
							System.out.println("OverQueryLimitException ("+tryCounter+". try)");
						}
						try {
							Thread.sleep(LocationUtil.OVER_QUERY_LIMIT_DELAY);
						} catch (InterruptedException e1) {
						}
					} else if (geocoderResponse.getStatus().name().equals(ZeroResultsException.GOOGLE_MSG)) {
						if (DEBUG_PRINT) {
							System.out.println("ZeroResultsException");
						}
						gresults = new ArrayList<GeocoderResult>(0);
					} else {
						if (DEBUG_PRINT) {
							System.out.println("Unknown exception: "+geocoderResponse.getStatus().name());
						}
						throw new UnrecognizedWebserviceResponseException("Problem with Google webservice, status: "+geocoderResponse.getStatus().name());
					}
				}
				tryCounter++;
			}
			if (geocoderResponse != null && geocoderResponse.getStatus().name().equals(OverQueryLimitException.GOOGLE_MSG)) {
				if (DEBUG_PRINT) {
					System.out.println("OverQueryLimitException");
				}
				throw new OverQueryLimitException();
			}
			Cache.set(cacheKey, gresults);
		}
		return new LocationRecognizeResult(address, gresults);
	}
	
}
