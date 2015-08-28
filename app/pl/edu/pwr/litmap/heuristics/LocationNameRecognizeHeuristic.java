package pl.edu.pwr.litmap.heuristics;

import java.util.ArrayList;

import pl.edu.pwr.litmap.textobjects.Textobject;

public interface LocationNameRecognizeHeuristic {

	public void doRecognize(Textobject to, LocationNameHeuristicResult result);
}
