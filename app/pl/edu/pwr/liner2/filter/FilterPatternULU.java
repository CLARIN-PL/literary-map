package pl.edu.pwr.liner2.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.pwr.litmap.ccl.Annotation;

public class FilterPatternULU extends Filter {

	static String word = "^\\p{Lu}+\\p{Ll}+\\p{Lu}+$";
	static Pattern pattern = null;
	
	public FilterPatternULU(){
		this.appliesTo.add("PERSON_FIRST_NAM");
		this.appliesTo.add("PERSON_LAST_NAM");
		this.appliesTo.add("CITY_NAM");
		this.appliesTo.add("COUNTRY_NAM");
		this.appliesTo.add("ROAD_NAM");
		
		pattern = Pattern.compile(word);
	}
	
	
	@Override
	public String getDescription() {
		return "No mix case";
	}

	@Override
	public Annotation pass(Annotation chunk, CharSequence charSeq) {		
		Matcher m = pattern.matcher(charSeq);
		if (!m.matches()){
			return chunk;
		}else
			return null;
	}

}
