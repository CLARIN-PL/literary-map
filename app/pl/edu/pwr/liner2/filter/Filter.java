package pl.edu.pwr.liner2.filter;

import java.util.ArrayList;
import pl.edu.pwr.litmap.ccl.Annotation;
import pl.edu.pwr.litmap.ccl.Token;

public abstract class Filter {

	// List of annotation types to which the filter can be applied
	protected ArrayList<String> appliesTo = new ArrayList<String>(); 
	
	/**
	 * Check if chunk passes a filter condition.
	 * @param chunk
	 * @param charSeq
	 * @return
	 */
	protected abstract Annotation pass(Annotation chunk, CharSequence charSeq);
	
	public abstract String getDescription();
	
	public Annotation run(Annotation chunk, CharSequence charSeq)
	{
		if (appliesTo.contains(chunk.getType()))
			return this.pass(chunk, charSeq);
		else
			return chunk;
	}
			
	/**
	 * Pass chunk through set of filters.
	 * @param chunk
	 * @param sentence
	 * @param filters
	 * @return
	 */
	static public Annotation filter(Annotation chunk, ArrayList<Filter> filters) {
    	StringBuilder sb = new StringBuilder();
    	ArrayList<Token> tokens = chunk.getSentence().getTokens();
    	for (int i = chunk.getBegin(); i <= chunk.getEnd(); i++) {
    		Token token = tokens.get(i);
    		sb.append(token.getFirstValue() + (token.getNoSpaceAfter() ? "" : " "));
    	}
    	return Filter.filter(chunk, sb.toString().trim(), filters);
	}
	
	static private Annotation filter(Annotation chunk, CharSequence cSeq, ArrayList<Filter> filters) {
		Annotation chunkMod = chunk;        	
    	for (Filter filter : filters){    		
    		Annotation chunkFiltered = filter.run(chunkMod, cSeq);

    		if (chunkFiltered == null)
    			return null;
    		
    		chunkMod = chunkFiltered;        		
    	}
    	return chunkMod;
	}	
}
