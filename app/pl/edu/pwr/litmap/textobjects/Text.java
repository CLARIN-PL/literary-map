/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pwr.litmap.textobjects;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.litmap.ccl.Annotation;
import pl.edu.pwr.litmap.ccl.Sentence;
import pl.edu.pwr.litmap.relations.RelationType;
import pl.edu.pwr.litmap.relations.SerelRelation;

/**
 *
 * @author Wojciech Gaweł
 */
public class Text {
    ArrayList<Sentence> sentences;
    ArrayList<Textobject> textobjects = new ArrayList<>();
    ArrayList<SerelRelation> relations =  new ArrayList<>();
    
    public Text() {
        this.sentences = new ArrayList<Sentence>();
    }
    
    public Text(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }
    
    public void addSentence(Sentence sentence) {
        this.sentences.add(sentence);
        addTextobjectsFromSentence(sentence);
    }

    public void addSentences(ArrayList<Sentence> sentences) {
        this.sentences.addAll(sentences);
        for (Sentence sentence : sentences) {
            addTextobjectsFromSentence(sentence);
        }
    }

    public void addRelationData(RelationType relationType, String fromSent, String fromChan, int fromNr, String toSent, String toChan, int toNr) {
        Annotation annFrom = getChannelBySentenceIdAndChanNameAndChanNr(fromSent, fromChan, fromNr);
        Annotation annTo = getChannelBySentenceIdAndChanNameAndChanNr(toSent, toChan, toNr);
    	SerelRelation relation = new SerelRelation(relationType, annFrom, annTo);
        this.relations.add(relation);
        annFrom.addRelationTo(relation);
        annTo.addRelationFrom(relation);
    }
    
    public Sentence getSentenceById(String sentenceId) {
        for (Sentence sentence : this.sentences) {
        	if (sentence.getId().equals(sentenceId)) {
        		return sentence;
        	}
        }
    	return null;
    }
    
    public Annotation getChannelBySentenceIdAndChanNameAndChanNr(String sentenceId, String chanName, int chanNr) {
        Sentence s = getSentenceById(sentenceId);

    	for (Annotation chunk : s.getChunks()) {
        	if (chunk.getType().equals(chanName)) {
        		if (--chanNr == 0) {
        			return chunk;
        		}
        	}
        }
    	return null;
    }
    
    public int getIndexOfSentence(Sentence sentence) {
    	return this.sentences.indexOf(sentence);
    }
    
    public Sentence getSentenceByIndex(int index) {
    	return this.sentences.get(index);
    }
    
    public int getNumberOfSenteces() {
    	return this.sentences.size();
    }
    
    protected Textobject[] getTextobjectAsArray() {
    	return textobjects.toArray(new Textobject[0]);
    }
    
    public Textobject[] getJsonWrapperTextobjectAsArray() {
    	JsonWrapperTextobject[] jwt = new JsonWrapperTextobject[this.textobjects.size()];
    	int index = 0;
    	for (Textobject t : this.textobjects) {
    		jwt[index++] = new JsonWrapperTextobject(this, t);
    	}
    	return jwt;
    }
    
    protected SerelRelation[] getRelationsAsArray() {
    	return this.relations.toArray(new SerelRelation[0]);
    }
    
    public JsonWrapperRelation[] getJsonWrapperRelationsAsArray() {
    	JsonWrapperRelation[] jwr = new JsonWrapperRelation[this.relations.size()];
    	int index = 0;
    	for (SerelRelation sr : this.relations) {
    		jwr[index++] = new JsonWrapperRelation(this.textobjects, sr);
    	}
    	return jwr;
    }
    
    private void addTextobjectsFromSentence(Sentence sentence) {
        for (Annotation chunk : sentence.getChunks()) {
            textobjects.add(new Textobject(this, chunk));
        }
    }
    
    public Textobject getTextobjectByChunk(Annotation chunk) {
    	for (Textobject to : this.textobjects) {
    		if (to.hasChunk(chunk)) {
    			return to;
    		}
    	}
    	return null;
    }
    
    public ArrayList<Textobject> getTextobjectBySentence(Sentence sentence) {
    	ArrayList<Textobject> result = new ArrayList<>();
    	for (Textobject to : this.textobjects) {
    		if (to.getSentence() == sentence) {
    			result.add(to);
    		}
    	}
    	return result;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence s : sentences) {
            sb.append(s.toString());
        }
        return sb.toString();
    }
    
    public String toStringTab() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tTokenId\tTags (base\tctag\t\tdisamb)\t\tAttributes\n");
        for (Sentence s : sentences) {
            sb.append(s.toStringTab());
        }
        return sb.toString();
    }
    
    public String toStringSimple() {
        StringBuilder sb = new StringBuilder();
        for (Sentence s : sentences) {
            sb.append("Sentence id#");
            sb.append(s.getId());
            sb.append(' ');
            sb.append(s.toStringSimple());
        }
        return sb.toString();
    }
    
    public String toStringTextObjects() {
        StringBuilder sb = new StringBuilder();
        for (Textobject to : this.textobjects) {
            sb.append(to.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
