package pl.edu.pwr.litmap.heuristics;

import java.util.ArrayList;

import pl.edu.pwr.litmap.exceptions.OverQueryLimitException;
import pl.edu.pwr.litmap.exceptions.UnrecognizedWebserviceResponseException;
import pl.edu.pwr.litmap.heuristics.methods.DocumentNextObjectsHeuristic;
import pl.edu.pwr.litmap.heuristics.methods.DocumentPreviousObjectsHeuristic;
import pl.edu.pwr.litmap.heuristics.methods.SentenceSerelHeuristic;
import pl.edu.pwr.litmap.heuristics.methods.TextobjectNameHeuristic;
import pl.edu.pwr.litmap.objectrecognize.LocationRecognize;
import pl.edu.pwr.litmap.objectrecognize.LocationRecognizeResult;
import pl.edu.pwr.litmap.textobjects.Textobject;

public class LocationNameRecognize {
	
	private static LocationNameRecognize INSTANCE = new LocationNameRecognize();
	private final static boolean DEBUG = false;

	/*
	 * Metody heurystyczne powinny być dodawane w taki sposób, by najpierw
	 * dawały nazwy jak najbardziejsz szczegółowe (które mogą zwracać wiele wyników),
	 * a później dodawały bardziej ogólne, tzn,:
	 * 1. metoda: "Gliniana"
	 * 2. metoda: "Wrocław, Gliniana"
	 * 3. metoda: "Polska, Wrocław, "Gliniana"
	 * 
	 */
	private ArrayList<LocationNameRecognizeHeuristic> heuristics;
	
	public static LocationNameRecognize getInstance() {
		return INSTANCE;
	}
	private LocationNameRecognize() {
		heuristics = new ArrayList<>();
		heuristics.add(new TextobjectNameHeuristic());
		heuristics.add(new SentenceSerelHeuristic());
		heuristics.add(new DocumentPreviousObjectsHeuristic());
		heuristics.add(new DocumentNextObjectsHeuristic());
	}
	
	public LocationNameHeuristicResult doHeuristic(Textobject to) {
		if (DEBUG) {
			System.out.println("Start heuristic methods for "+to.getBaseName()+"...");
		}
		LocationNameHeuristicResult result = new LocationNameHeuristicResult();
		if (!TextobjectNameHeuristic.canGetLocationByNameClass(to.getNameClass())) {
			return result;
		}
//		LocationNameHeuristicResult lastNotEmptyResult = LocationNameHeuristicResult.EMPTY;
		for (LocationNameRecognizeHeuristic heuristic : heuristics) {
			heuristic.doRecognize(to, result);
			if (DEBUG) {
				System.out.print("After euristic method:  "+heuristic.getClass().getName()+" -> "+result.getTextobjectsLayersList().size()+" layers.");
				System.out.println(" rawNumberOfLayers: "+result.getNumberOfLayers());
			}
//			try {
//				LocationRecognizeResult lrr = LocationRecognize.process(result);
//				if (lrr.getList().size() > 0) {
//					lastNotEmptyResult = result.clone();
//					if (lrr.getList().size() == 1) {
//						break;
//					}
//				}
//			} catch (OverQueryLimitException | UnrecognizedWebserviceResponseException e) {
//			}
		}

		if (DEBUG) {
			System.out.println("Heuristic methods found results with "+result.getTextobjectsLayersList().size()+" layers.");
		}
		return result;
	}
}
