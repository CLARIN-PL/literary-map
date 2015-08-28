package pl.edu.pwr.liner2.filter;

import java.util.ArrayList;

import pl.edu.pwr.litmap.ccl.Annotation;
import pl.edu.pwr.litmap.ccl.Token;

public class FilterCutRoadPrefix extends Filter {

	public FilterCutRoadPrefix(){
		this.appliesTo.add("ROAD_NAM");
	}
	
	@Override
	public String getDescription() {
		return "Cut of road prefix 'ul.'";
	}

	@Override
	public Annotation pass(Annotation chunk, CharSequence charSeq) {
		ArrayList<Token> tokens = chunk.getSentence().getTokens();
		int begin = chunk.getBegin();
		int end = chunk.getEnd();
		if (end - begin > 2)
			if ((tokens.get(begin).getFirstValue().equals("ul")) &&
				(tokens.get(begin + 1).getFirstValue().equals(".")))
				return new Annotation(begin + 2, end, chunk.getType(), chunk.getSentence());
		return chunk;
	}

}
