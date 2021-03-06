package pl.edu.pwr.liner2.filter;

import pl.edu.pwr.litmap.ccl.Annotation;

public class FilterFirstNotLower extends Filter {

	private static String firstLower = "([a-z]|ą|ż|ś|ę|ć|ń|ó|ł)";
	
	public FilterFirstNotLower(){
		this.appliesTo.add("PERSON_FIRST_NAM");
		this.appliesTo.add("PERSON_LAST_NAM");
		this.appliesTo.add("CITY_NAM");
		this.appliesTo.add("COUNTRY_NAM");
		this.appliesTo.add("ROAD_NAM");
	}
	
	@Override
	public String getDescription() {
		return "First not lower, does not match '"+firstLower+"'";
	}

	@Override
	public Annotation pass(Annotation chunk, CharSequence charSeq) {
		if ( !charSeq.toString().matches(FilterFirstNotLower.firstLower) )
			return chunk;
		else 
			return null;
	}

}
