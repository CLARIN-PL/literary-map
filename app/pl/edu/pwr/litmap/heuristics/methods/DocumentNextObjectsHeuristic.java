package pl.edu.pwr.litmap.heuristics.methods;

import java.util.ArrayList;

import pl.edu.pwr.litmap.ccl.Sentence;
import pl.edu.pwr.litmap.heuristics.LocationNameHeuristicResult;
import pl.edu.pwr.litmap.heuristics.LocationNameRecognizeHeuristic;
import pl.edu.pwr.litmap.textobjects.NameClass;
import pl.edu.pwr.litmap.textobjects.Text;
import pl.edu.pwr.litmap.textobjects.Textobject;

public class DocumentNextObjectsHeuristic implements LocationNameRecognizeHeuristic {

	private NameClass[] searchFor = NameClass.values();
//	{ 
//			NameClass.CITY_NAM,
//			NameClass.COUNTRY_NAM
//	};
	
	@Override
	public void doRecognize(Textobject to, LocationNameHeuristicResult result) {
		
		for (NameClass nameclass : searchFor) {
			if (nameclass.isHigherInTopologicalHierachyThan(to.getNameClass()) & !result.containtsTextobjectWithNameClass(nameclass)) {
				Text text = to.getTextContainingThisTextobject();
				int index = text.getIndexOfSentence(to.getSentence())+1;
				while (index < text.getNumberOfSenteces()) {
					Sentence sentence = text.getSentenceByIndex(index);
					ArrayList<Textobject> textobjects = text.getTextobjectBySentence(sentence);
					for (Textobject textobject : textobjects) {
						if (textobject.getNameClass().equals(nameclass)) {
							result.addTextobject(textobject);
						}
					}
					index++;
				}
			}
		}
//		if (to.getBaseName().equals("Krakowskiego Przedmieścia")) {
//			System.out.println(" >> result:\n"+result.getResultString());
//		}
	}

}
