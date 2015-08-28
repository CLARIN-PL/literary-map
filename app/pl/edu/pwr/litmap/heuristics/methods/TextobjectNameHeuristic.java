package pl.edu.pwr.litmap.heuristics.methods;

import pl.edu.pwr.litmap.heuristics.LocationNameHeuristicResult;
import pl.edu.pwr.litmap.heuristics.LocationNameRecognizeHeuristic;
import pl.edu.pwr.litmap.textobjects.NameClass;
import pl.edu.pwr.litmap.textobjects.Textobject;

public class TextobjectNameHeuristic implements LocationNameRecognizeHeuristic {
	
	public static final NameClass[] locationSearchEnabledClasses = {
	    NameClass.ADMIN1_NAM,
	    NameClass.ADMIN2_NAM,
	    NameClass.ADMIN3_NAM,
	    NameClass.CAPE_NAM,
	    NameClass.CITY_NAM,
	    NameClass.CONTINENT_NAM,
	    NameClass.CONURBATION_NAM,
	    NameClass.COUNTRY_NAM,
	    NameClass.COUNTRY_REGION_NAM,
	    NameClass.HISTORICAL_REGION_NAM,
	    NameClass.ISLAND_NAM,
	    NameClass.MOUNTAIN_NAM,
	    NameClass.PENINSULA_NAM,
	    NameClass.REGION_NAM,
	    NameClass.TOPONYM_NAM,
	    NameClass.ADDRESS_STREET_NAM,
	    NameClass.ROAD_NAM,
	    NameClass.RIVER_NAM,
	    NameClass.SEA_NAM,
	    NameClass.SQUARE_NAM,
//	    NameClass.ORGANIZATION_NAM,
//	    NameClass.FACILITY_NAM
	};
	
//	public static final NameClass[] SELF_SUFFICIENT_CLASSES = {
//		
//	};
	
	public static boolean canGetLocationByNameClass(NameClass nameClass) {

	    for (int i = 0; i<locationSearchEnabledClasses.length; i++) {
	        if (locationSearchEnabledClasses[i] == nameClass) {
	            return true;
	        }
	    }

	    return false;
	}
	
	@Override
	public void doRecognize(Textobject to, LocationNameHeuristicResult result) {
		// TODO: Można uzależnić od innych elementów w results, może zapytania do Google webservice?
		if (canGetLocationByNameClass(to.getNameClass())) {
			result.addTextobject(to);
		}
	}

}
