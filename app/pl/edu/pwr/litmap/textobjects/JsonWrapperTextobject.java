package pl.edu.pwr.litmap.textobjects;

import java.util.ArrayList;

import pl.edu.pwr.litmap.ccl.Annotation;
import pl.edu.pwr.litmap.relations.RelationType;
import pl.edu.pwr.litmap.relations.SerelRelation;

public class JsonWrapperTextobject extends Textobject {

	private final Textobject textobject;
	
	public JsonWrapperTextobject(Text text, Textobject textobject) {
		super(text, textobject.chunk);
		this.textobject = textobject;
	}

	public JsonWrapperRelation[] getFromRelations() {
		int index = 0;
		JsonWrapperRelation[] jwr = new JsonWrapperRelation[this.chunk.getRelationsFrom().size()];
		for (SerelRelation relation : this.chunk.getRelationsFrom()) {
			jwr[index] = new JsonWrapperRelation(text.textobjects, relation);
			index++;
		}
		return jwr;
	}

	public JsonWrapperRelation[] getToRelations() {
		int index = 0;
		JsonWrapperRelation[] jwr = new JsonWrapperRelation[this.chunk.getRelationsTo().size()];
		for (SerelRelation relation : this.chunk.getRelationsTo()) {
			jwr[index] = new JsonWrapperRelation(text.textobjects, relation);
			index++;
		}
		return jwr;
	}
	
}
