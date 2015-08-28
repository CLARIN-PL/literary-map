package pl.edu.pwr.litmap.textobjects;

import java.util.ArrayList;

import pl.edu.pwr.litmap.ccl.Annotation;
import pl.edu.pwr.litmap.relations.RelationType;
import pl.edu.pwr.litmap.relations.SerelRelation;

public class JsonWrapperRelation {

	private final SerelRelation relation;
	private final ArrayList<Textobject> textobjects;
	
	public JsonWrapperRelation(ArrayList<Textobject> textobjects, SerelRelation relation) {
		this.textobjects = textobjects;
		this.relation = relation;
	}

	public RelationType getType() {
		return relation.getType();
	}


	public int getFromIndex() {
		int index = 0;
		for (Textobject textobject : this.textobjects) {
			if (textobject.hasChunk(relation.getFrom())) {
				return index;
			}
			index++;
		}
		throw new RuntimeException("Inconsistency of data: cannot find textobject with chunk from relation.");
	}


	public int getToIndex() {
		int index = 0;
		for (Textobject textobject : this.textobjects) {
			if (textobject.hasChunk(relation.getTo())) {
				return index;
			}
			index++;
		}
		throw new RuntimeException("Inconsistency of data: cannot find textobject with chunk from relation.");
	}

	// TODO: Uprościć
	public Textobject getToTextobject() {
		Text text = textobjects.get(0).getTextContainingThisTextobject();
		Textobject textobject = text.getTextobjectByChunk(this.relation.getTo());
		return textobject;
	}

	// TODO: Uprościć
	public Textobject getFromTextobject() {
		Text text = textobjects.get(0).getTextContainingThisTextobject();
		Textobject textobject = text.getTextobjectByChunk(this.relation.getFrom());
		return textobject;
	}
	
}
