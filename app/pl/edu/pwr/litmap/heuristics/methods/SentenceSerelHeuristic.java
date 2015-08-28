package pl.edu.pwr.litmap.heuristics.methods;

import pl.edu.pwr.litmap.heuristics.LocationNameHeuristicResult;
import pl.edu.pwr.litmap.heuristics.LocationNameRecognizeHeuristic;
import pl.edu.pwr.litmap.textobjects.Textobject;

public class SentenceSerelHeuristic implements LocationNameRecognizeHeuristic {

	@Override
	public void doRecognize(Textobject to, LocationNameHeuristicResult result) {    	
		for (Textobject toItem : to.getRelationToTextobjects()) {
			if (TextobjectNameHeuristic.canGetLocationByNameClass(toItem.getNameClass())
					&& toItem.getNameClass().isHigherInTopologicalHierachyThan(to.getNameClass())) {
				result.addTextobject(toItem);
			}
		}
	}

}
