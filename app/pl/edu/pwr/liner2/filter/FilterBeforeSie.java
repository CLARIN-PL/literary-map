package pl.edu.pwr.liner2.filter;

import pl.edu.pwr.litmap.ccl.Annotation;
import pl.edu.pwr.litmap.ccl.Token;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterBeforeSie extends Filter {
	
	public FilterBeforeSie(){
		this.appliesTo.add("PERSON_FIRST_NAM");
		this.appliesTo.add("PERSON_LAST_NAM");
		this.appliesTo.add("CITY_NAM");
		this.appliesTo.add("COUNTRY_NAM");
		this.appliesTo.add("ROAD_NAM");
	}
	
	@Override
	public String getDescription() {
		return "Remove if there is 'się' after annotation";
	}

	@Override
	public Annotation pass(Annotation chunk, CharSequence cSeq) {
		ArrayList<Token> tokens = chunk.getSentence().getTokens();
		// jeśli po chunku nic nie ma
		if (chunk.getEnd() == tokens.size()-1)
			return chunk;
		// jeśli po chunku jest "się"
		if (tokens.get(chunk.getEnd() + 1).getFirstValue().equals("się"))
			return null;
		else
			return chunk;
	}

}
