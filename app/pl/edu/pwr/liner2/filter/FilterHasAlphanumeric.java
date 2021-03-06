package pl.edu.pwr.liner2.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.pwr.litmap.ccl.Annotation;

public class FilterHasAlphanumeric extends Filter {

	private static String hasAlpanumeric = "(\\p{Lu}|\\p{Ll}|[0-9])";
	private static Pattern pattern = null;
	
	public FilterHasAlphanumeric(){
		this.appliesTo.add("PERSON_FIRST_NAM");
		this.appliesTo.add("PERSON_LAST_NAM");
		this.appliesTo.add("CITY_NAM"); 
		this.appliesTo.add("COUNTRY_NAM");
		this.appliesTo.add("ROAD_NAM");
		
		pattern = Pattern.compile(hasAlpanumeric);
	}
	
	@Override
	public String getDescription() {
		return "Has alphanumeric char: " + hasAlpanumeric;
	}

	@Override
	public Annotation pass(Annotation chunk, CharSequence charSeq) {
		Matcher m = pattern.matcher(charSeq);
		if ( m.find() )
			return chunk;
		else
			return null;
	}

}
