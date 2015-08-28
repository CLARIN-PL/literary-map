package pl.edu.pwr.litmap.relations;

import pl.edu.pwr.litmap.ccl.Annotation;
import pl.edu.pwr.litmap.ccl.Sentence;

public class SerelRelation {
	
	private final RelationType type;
	private final Annotation from;
	private final Annotation to;
	
	
	public SerelRelation(RelationType type, Annotation from, Annotation to) {
		super();
		this.type = type;
		this.from = from;
		this.to = to;
	}


	public RelationType getType() {
		return type;
	}


	public Annotation getFrom() {
		return from;
	}


	public Annotation getTo() {
		return to;
	}

}
