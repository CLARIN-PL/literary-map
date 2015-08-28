package pl.edu.pwr.litmap.objectrecognize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.avaje.ebeaninternal.server.lib.sql.Prefix;
import com.google.code.geocoder.model.GeocoderResult;

import pl.edu.pwr.litmap.exceptions.OverQueryLimitException;
import pl.edu.pwr.litmap.exceptions.UnrecognizedWebserviceResponseException;
import pl.edu.pwr.litmap.heuristics.LocationNameHeuristicResult;
import pl.edu.pwr.litmap.textobjects.NameClass;
import pl.edu.pwr.litmap.textobjects.NamedTextobject;
import pl.edu.pwr.litmap.textobjects.Textobject;
import scala.collection.mutable.StringBuilder;

public class LocationRecognize {
	
	private final LocationNameHeuristicResult locationName;
	private LocationRecognizeResult result = null;
	public static final String LOCATION_TEXT_SEARCH_SEPARATOR = ", ";
	
	public LocationRecognize(LocationNameHeuristicResult locationName) {
		this.locationName = locationName;
	}
	
//	String[][] toponimy = {
//		    {Polski, Polska},
//		    {Wrocławia, Wrocław},
//		    {Suchą, Sucha}
//		};

//	private LocationRecognizeResult processSearch_BAC1(List<NamedTextobject> names, List<NamedTextobject> prefix, List<List<NamedTextobject>> postfixes) throws OverQueryLimitException, UnrecognizedWebserviceResponseException {
//	    for (int name_i=0; name_i<names.size(); name_i++) {
//	    	NamedTextobject name = names.get(name_i);
//	    	LocationRecognizeResult result;
//	        if (!(result = queryForLocationNameList(prefix, name)).getList().isEmpty()) {
//	            if (postfixes.isEmpty()) {
//	                return result;
//	            } else {
//	                for (List<NamedTextobject> postifx_names : postfixes) {
//	                    List<NamedTextobject> new_prefix = new ArrayList<>(prefix);
//	                    new_prefix.add(name);
//	                    List<List<NamedTextobject>> new_postfixes = new ArrayList<>(postfixes);
//	                    new_postfixes.remove(postifx_names);
//	                    return processSearch_BAC1(postifx_names, new_prefix, new_postfixes);
//	                }
//	            }
//	        }
//	    }
//	    return LocationRecognizeResult.EMPTY;
//	}

	/**
	 * NEW Start args:
	 * List<NamedTextobject> names = {Suchą, Sucha}
	 * List<List<NamedTextobject>> prefix = {{Polski, Polska}, {dolnośląskie, dolnośląski}, {Wrocławia, Wrocław}} 
	 * @throws UnrecognizedWebserviceResponseException 
	 * @throws OverQueryLimitException 
	 * 
	**/
	public LocationRecognizeResult searchLocations(List<NamedTextobject> names, List<List<NamedTextobject>> prefix) throws IOException, OverQueryLimitException, UnrecognizedWebserviceResponseException {

        for (int i=prefix.size(); i>=0; i--) {
            List<List<List<NamedTextobject>>> subsequences_of_prefix = subsequences_k_elem(prefix, i);

            for (List<List<NamedTextobject>> subsequence_of_prefix : subsequences_of_prefix) {
                List<List<NamedTextobject>> prefixes_as_list = combinations_of_single_elements_in_lists(subsequence_of_prefix);

                for (List<NamedTextobject> prefix_as_list : prefixes_as_list) {
                    for (NamedTextobject name : names) {
                    	LocationRecognizeResult result;
                        if ((result = queryForLocationNameList(prefix_as_list, name)).hasResults()) {
                            return result;
                        }
                    }
                }
            }
        }    
	    return LocationRecognizeResult.EMPTY;
	}

	/**
	 * Ex.:
	 * List<List<T>> lists = {
	 *     {Polski, Polska},
	 *     {dolnośląskie, dolnośląski},
	 *     {Wrocławia, Wrocław}
	 * }
	 * List<List<T>> resultList = {{}}; - important: LIST WITH ONE EMPTY LIST
	 *
	**/
	// for:
//	     {
//	         {Polski, Polska}
//	     }
	// after resultList = {
	//  {Polski},
	//  {Polski}
	// }
	//
	// for:
//	     {
//	         {Polski, Polska},
//	         {dolnośląskie, dolnośląski}
//	     }
	// after resultList = {
	//  {Polski, dolnośląskie},
	//  {Polski, dolnośląski},
	//  {Polska, dolnośląskie},
	//  {Polska, dolnośląski}
	// }
	//
	// for:
//	     {
//	         {Polski, Polska},
//	         {dolnośląskie, dolnośląski},
//	         {Wrocławia, Wrocław}
//	     }
	// after resultList = {
	//  {Polski, dolnośląskie, Wrocławia},
	//  {Polski, dolnośląskie, Wrocław},
	//  {Polski, dolnośląski, Wrocławia},
	//  {Polski, dolnośląski, Wrocław},
	//  {Polska, dolnośląskie, Wrocławia},
	//  {Polska, dolnośląskie, Wrocław},
	//  {Polska, dolnośląski, Wrocławia},
	//  {Polska, dolnośląski, Wrocław}
	// }
	//
		public static <T> List<List<T>> combinations_of_single_elements_in_lists(List<List<T>> lists) {
		    List<List<T>> resultList = new ArrayList<>();
		    resultList.add(new ArrayList<T>(lists.size()));

		    for (int i=lists.size()-1; i>=0; i--) {
		        List<List<T>> newLists = new ArrayList<>();
		        for (T el : lists.get(i)) {
		            for (List<T> list : resultList) {
		                List<T> newList = new ArrayList<>(list);
		                newList.add(0, el);
		                newLists.add(newList);
		            }
		        }
		        resultList = newLists;
		    }

		    return resultList;
		}

	// w tym algorytmie T = List<NamedTextobject>
	// prefix = {{Polski, Polska}, {dolnośląskie, dolnośląski}, {Wrocławia, Wrocław}} 
	// i=3
	// prefixes = {
//	     {
//	         {Polski, Polska},
//	         {dolnośląskie, dolnośląski},
//	         {Wrocławia, Wrocław}
//	     }
	// }
	//
	// i=2
	// prefixes = {
//	     {
//	         {Polski, Polska},
//	         {dolnośląskie, dolnośląski}
//	     },
//	     {
//	         {Polski, Polska},
//	         {Wrocławia, Wrocław}
//	     },
//	     {
//	         {dolnośląskie, dolnośląski},
//	         {Wrocławia, Wrocław}
//	     }
	// }
	//
	// i=1
	// prefixes = {
//	     {
//	         {Polski, Polska}
//	     },
//	     {
//	         {dolnośląskie, dolnośląski}
//	     },
//	     {
//	         {Wrocławia, Wrocław}
//	     }
	// }
	//
	// i=0
	// prefixes = {
	// }
	//
	public static <T> List<List<T>> subsequences_k_elem(List<T> list, int k) {
	    List<List<T>> ps = new ArrayList<List<T>>();
	    ps.add(new ArrayList<T>());   // add the empty set
	 
	    // for every item in the original list
	    for (T item : list) {
	        List<List<T>> newPs = new ArrayList<List<T>>();
	 
	        for (List<T> subset : ps) {
	            // copy all of the current powerset's subsets
	            newPs.add(subset);
	 
	            // plus the subsets appended with the current item
	            List<T> newSubset = new ArrayList<T>(subset);
	            newSubset.add(item);
	            newPs.add(newSubset);
	        }
	 
	        // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
	        ps = newPs;
	    }

	    // TODO: zoptymalizować! (ograniczyć powyższy sposób dodawania podciągów do k-elementówych podciągów)
	    Iterator<List<T>> it = ps.iterator();
	    while (it.hasNext()) {
	         List<T> el = it.next(); 
	         if (el.size() != k) {
	             it.remove();
	         }
	    }

	    return ps;
	}
	
	/**
	 * ... for use cache -> ex:
	 * query: "Polska, dolnośląski, Wrocław, Czekoladowej"
	 * run queries:
	 *  1. "Polska"
	 *  2. "Polska, dolnośląski"
	 *  3. "Polska, dolnośląski, Wrocław"
	 *  4. "Polska, dolnośląski, Wrocław, Czekoladowej"
	 *  Stop, when query has 0 results, and return 0 result.
	 * @param prefixes
	 * @param nto
	 * @return
	 * @throws OverQueryLimitException
	 * @throws UnrecognizedWebserviceResponseException
	 */
	private static LocationRecognizeResult queryForLocationNameList(List<NamedTextobject> prefixes, NamedTextobject nto) throws IOException, OverQueryLimitException, UnrecognizedWebserviceResponseException {
		StringBuilder sb = new StringBuilder();
		List<NamedTextobject> textobjects = new ArrayList<>(prefixes.size()+1);
		for (NamedTextobject prefix : prefixes) {
			if (sb.size() > 0) {
				sb.append(LOCATION_TEXT_SEARCH_SEPARATOR);
			}
			sb.append(prefix.getName());
			textobjects.add(prefix);
			LocationRecognizeResult partResult;
			if (!(partResult = LocationUtil.getFilteredResults(sb.toString(), prefix.getTextobject().getNameClass(), textobjects)).hasResults()) {
				return partResult;
			}
		}
		if (sb.size() > 0) {
			sb.append(LOCATION_TEXT_SEARCH_SEPARATOR);
		}
		sb.append(nto.getName());
		textobjects.add(nto);
		return LocationUtil.getFilteredResults(sb.toString(), nto.getTextobject().getNameClass(), textobjects);
	}
	
//	private LocationRecognizeResult processSearch_OLD() throws OverQueryLimitException, UnrecognizedWebserviceResponseException {
//		ArrayList<Textobject> tos = locationName.getResultTextobjects();
//		List<GeocoderResult> partResult = new ArrayList<>(0);
//		
//		StringBuilder sb = new StringBuilder();
//		
//		
//		// add main object name
//		if (tos.size() == 0) {
//			return LocationRecognizeResult.EMPTY;
//		}
//		Textobject mainTextobject = tos.get(tos.size()-1);
//		partResult = LocationUtil.getFilteredResults(mainTextobject.getBaseName(), mainTextobject.getNameClass());
//		if (partResult.isEmpty()) {
//			partResult = LocationUtil.getFilteredResults(mainTextobject.getForceBaseName(), mainTextobject.getNameClass());
//			if (!partResult.isEmpty()) {
//				sb.append(mainTextobject.getForceBaseName());
//			}
//		} else {
//			sb.append(mainTextobject.getBaseName());
//		}
//		
//		if (sb.size() > 1 && tos.size() > 1) {
//			for (ListIterator<Textobject> iterator = tos.listIterator(tos.size()-1); iterator.hasPrevious();) {
//				final Textobject to = iterator.previous();
//				
//				String partToAdd = null;
//				partResult = LocationUtil.getFilteredResults(to.getBaseName() + (sb.length() > 0 ? LocationRecognize.LOCATION_TEXT_SEARCH_SEPARATOR : "") + sb.toString(), mainTextobject.getNameClass());
//				if (partResult.isEmpty()) {
//					partResult = LocationUtil.getFilteredResults(to.getForceBaseName() + (sb.length() > 0 ? LocationRecognize.LOCATION_TEXT_SEARCH_SEPARATOR : "") + sb.toString(), mainTextobject.getNameClass());
//					if (!partResult.isEmpty()) {
//						partToAdd = to.getForceBaseName();
//					}
//				} else {
//					partToAdd = to.getBaseName();
//				}
//				if (partToAdd == null) {
//	//				sb.append(to.getBaseName()); // to add to LocationRecognizeResult
//	//				break;
//				} else {
//					if (sb.length() > 0) {
//						sb.insert(0, LocationRecognize.LOCATION_TEXT_SEARCH_SEPARATOR);
//					}
//					sb.insert(0, partToAdd);
//					if (partResult.size() == 1) {
//						break;
//					}
//				}
//			}
//		}
//		
//		LocationRecognizeResult locationRecognizeResult = new LocationRecognizeResult(sb.toString(), partResult);
//		
//		return locationRecognizeResult;
//	}
	
	public LocationRecognizeResult getResults() throws IOException, OverQueryLimitException, UnrecognizedWebserviceResponseException {
		if (result == null) {
			if (this.locationName.getTextobjectsLayersList().isEmpty()) {
				return LocationRecognizeResult.EMPTY;
			}
			List<NamedTextobject> names = this.locationName.getTextobjectsLayersList().get(this.locationName.getTextobjectsLayersList().size()-1);
			List<List<NamedTextobject>> prefix = new ArrayList<>(this.locationName.getTextobjectsLayersList());
			prefix.remove(names);
			this.result = searchLocations(names, prefix);
		}
		return result;
	}
	
	public static LocationRecognizeResult process(LocationNameHeuristicResult locationName) throws IOException, OverQueryLimitException, UnrecognizedWebserviceResponseException {
		LocationRecognize lr = new LocationRecognize(locationName);
		return lr.getResults();
	}
	
}
