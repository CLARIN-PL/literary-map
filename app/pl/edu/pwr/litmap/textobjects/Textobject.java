/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pwr.litmap.textobjects;

import java.io.IOException;
import java.util.ArrayList;

import pl.edu.pwr.litmap.ccl.Annotation;
import pl.edu.pwr.litmap.ccl.Sentence;
import pl.edu.pwr.litmap.ccl.Tag;
import pl.edu.pwr.litmap.ccl.Token;
import pl.edu.pwr.litmap.exceptions.OverQueryLimitException;
import pl.edu.pwr.litmap.exceptions.UnrecognizedWebserviceResponseException;
import pl.edu.pwr.litmap.heuristics.LocationNameHeuristicResult;
import pl.edu.pwr.litmap.heuristics.LocationNameRecognize;
import pl.edu.pwr.litmap.objectrecognize.LocationRecognize;
import pl.edu.pwr.litmap.objectrecognize.LocationRecognizeResult;
import pl.edu.pwr.litmap.relations.SerelRelation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.code.geocoder.model.LatLng;


/**
 *
 * @author Wojciech Gaweł
 */
public class Textobject {

	/**
	 * Text containing this textobject
	 */
    protected final Text text;
    protected final Annotation chunk;

    private LocationRecognizeResult locationRecognizeResult = LocationRecognizeResult.EMPTY;
    private LocationGeocodeStatus locationGeocodeStatus = LocationGeocodeStatus.UNCHECKED;

    public Textobject(Text text, Annotation chunk) {
        this.text = text;
        this.chunk = chunk;
    }
    
    /**
     * @return Text (fragment of sentence)
     */
    public String getRawText() {
        return chunk.getRawText();
    }
    
    public String getRawSentenceText() {
        return chunk.getSentence().getRawText();
    }
    
    @JsonIgnore
    public Sentence getSentence() {
    	return chunk.getSentence();
    }

    /*
     * @return Text of sentence with this texobject 
     * and textobject from relations surrounded by tag <span>
     * with appropriate classes
     */
    public String getSentenceTextWithSpan() {
    	String openTagThisText = "<span class=\"litmap-textobject\">";
    	String closeTagThisText = "</span>";
    	String openTagRelFromText = "<span class=\"litmap-textobject-rel-from\">";
    	String closeTagRelFromText = "</span>";
    	String openTagRelToText = "<span class=\"litmap-textobject-rel-to\">";
    	String closeTagRelToText = "</span>";
        StringBuilder sb = new StringBuilder();
        boolean tagThisOpen = false;
        boolean tagRelFromOpen = false;
        boolean tagRelToOpen = false;
        int token_index = 0;
        for (Token token : chunk.getSentence().getTokens()) {
        	Annotation chunkFrom = null;
        	Annotation chunkTo = null;

        	if (chunk.getTokens().contains(token_index)) {
        		if (!tagThisOpen) {
        			sb.append(openTagThisText);
        			tagThisOpen = true;
        		}
        	}
        	for(SerelRelation sr : chunk.getRelationsFrom()) {
        		if (sr.getFrom().getTokens().contains(token_index)) {
        			chunkFrom = sr.getFrom();
	        		if (!tagRelFromOpen) {
	        			sb.append(openTagRelFromText);
	        			tagRelFromOpen = true;
	        		}
	        		break;
        		}
        	}
        	for(SerelRelation sr : chunk.getRelationsTo()) {
        		if (sr.getTo().getTokens().contains(token_index)) {
        			chunkTo = sr.getTo();
	        		if (!tagRelToOpen) {
	        			sb.append(openTagRelToText);
	        			tagRelToOpen = true;
	        		}
	        		break;
        		}
        	}
        	
            sb.append(token.toStringSimple());

        	if (tagThisOpen && this.chunk.getEnd() == token_index) {
    			sb.append(closeTagThisText);
    			tagThisOpen = false;
        	}
        	if (tagRelFromOpen && chunkFrom.getEnd() == token_index) {
    			sb.append(closeTagRelFromText);
    			tagRelFromOpen = false;
        	}
        	if (tagRelToOpen && chunkTo.getEnd() == token_index) {
    			sb.append(closeTagRelToText);
    			tagRelToOpen = false;
        	}
        	
            if (!token.getNoSpaceAfter()) {
                sb.append(' ');
            }
            token_index++;
        }
        return sb.toString();
    }

    /**
     * W obiektach, których nazwa składa się z wielu wyrazów czasami sam .getBase() daje błędne wyniki, np 
     * "Dawid Podsiadło" -> "Dawid podsiąść"
     * "Syryjskiego Obserwatorium Praw Człowieka" -> "syryjski obserwatorium prawo człowiek"
     * ale
     * "Sebastian Nowak: -> "Sebastian Nowak"
     * 
     * Błędy również w jednowyrazowych:
     * "[w] Opolu" -> "opole" (brak pierwszej dużej litery)
     * Dlatego jeżeli wykryje zmianę wielkości pierwszej litery (lub inną cechę wskazującą - np. nazwa ulicy, któej forma podstawowa zazwyczaj jest błędna)
     * na możliwość wystąpienia błedu pozostawia wersje z tekstu (zamiast nieprawidłowej formy podstawowej)
     * @return the baseName
     */
    public String getBaseName() {
    	String baseName;
    	StringBuilder sb = new StringBuilder();
    	boolean possibleErrorInRecognize = false;
    	
		if (this.getNameClass().equals(NameClass.ROAD_NAM) || this.getNameClass().equals(NameClass.ADDRESS_STREET_NAM)) {
			possibleErrorInRecognize = true;
		} else if (chunk.getTokens().size() > 1) {
        	possibleErrorInRecognize = true;
        } else {
	        
	        
	        for (Integer token_index : chunk.getTokens()) {
	            Token token = chunk.getSentence().getTokens().get(token_index);
	            Tag tag = token.getTags().get(0);
	            
	            if (Character.isUpperCase(token.getFirstValue().charAt(0)) && !Character.isUpperCase(tag.getBase().charAt(0))) {
	            	possibleErrorInRecognize = true;
	            	break;
	            }
	            
	            sb.append(tag.getBase());
	            if (!token.getNoSpaceAfter()) {
	                sb.append(' ');
	            }
	        }
        }
	        
        baseName = possibleErrorInRecognize
        		   ? getRawText()
        	       : sb.toString().trim();

		return baseName;
    }
    
    public String getForceBaseName() {
        StringBuilder sb = new StringBuilder();
        for (Integer token_index : chunk.getTokens()) {
            Token token = chunk.getSentence().getTokens().get(token_index);
            Tag tag = token.getTags().get(0);
            sb.append(tag.getBase());
            if (!token.getNoSpaceAfter()) {
                sb.append(' ');
            }
        }
        return sb.toString().trim();
    }

    /**
     * @return the nameClass
     */
    public NameClass getNameClass() {
    	NameClass result = null;
    	try {
    		result = NameClass.valueOf(chunk.getType().toUpperCase());
    	} catch (IllegalArgumentException e) {
    		System.out.println("Class "+NameClass.class+" does not contain \""+chunk.getType().toUpperCase()+"\". Textobject raw-text = \""+this.getRawText()+"\".");
    		throw e;
    	}
        return result;
    }
    
    /**
     * 
     * @return location lat and lng text | empty String when cannot get location
     * @throws IOException 
     * @throws OverQueryLimitException 
     */
    public String getLocationText() {
    	String locationText = "";
		if (getLocation() != null) {
			locationText = getLocation().toUrlValue(6);
		}
    	return locationText;
    }
    
    public double getLocationLat() throws OverQueryLimitException, IOException {
    	double locationLat = 0.0;
    	if (getLocation() != null) {
    		String[] arr = getLocationText().split(",");
    		if (arr.length == 2)
    			locationLat = Double.parseDouble(arr[0]);
    	}
    	return locationLat;
    }
    
    public double getLocationLng() throws OverQueryLimitException, IOException {
    	double locationLat = 0.0;
    	if (getLocation() != null) {
    		String[] arr = getLocationText().split(",");
    		if (arr.length == 2)
    			locationLat = Double.parseDouble(arr[1]);
    	}
    	return locationLat;
    }
    
    public LocationRecognizeResult getLocationRecognizeResult() {
    	if (locationGeocodeStatus.equals(LocationGeocodeStatus.OVER_QUERY_LIMIT)) { // if over req/sec
			locationGeocodeStatus = LocationGeocodeStatus.UNCHECKED;
    	}
    	if (locationGeocodeStatus.equals(LocationGeocodeStatus.UNCHECKED)) {
    		try {
				LocationNameHeuristicResult locationNameHeuristicResult = LocationNameRecognize.getInstance().doHeuristic(this);
				locationRecognizeResult = LocationRecognize.process(locationNameHeuristicResult);    			
				if (locationRecognizeResult.getList().isEmpty()) {
    				if (locationRecognizeResult.getSearchString().isEmpty()) {
        				locationGeocodeStatus = LocationGeocodeStatus.EMPTY_INPUT;
    				} else {
    					locationGeocodeStatus = LocationGeocodeStatus.ZERO_RESULTS;
    				}
    			} else {
    				locationGeocodeStatus = LocationGeocodeStatus.SUCCESS;
    			}
			} catch (OverQueryLimitException e) {
				locationGeocodeStatus = LocationGeocodeStatus.OVER_QUERY_LIMIT;
			} catch (UnrecognizedWebserviceResponseException e) {
				locationGeocodeStatus = LocationGeocodeStatus.WEBSERVICE_UNKNOWN_ERROR;
			} catch (IOException e) {
				locationGeocodeStatus = LocationGeocodeStatus.IO_EXCEPTION;
			}
    	}
    	if (locationRecognizeResult == null) {
    		locationRecognizeResult = LocationRecognizeResult.EMPTY;
    	}
    	return locationRecognizeResult;
    }
    
    /**
     * 
     * @return LatLng | null when cannot get location
     */
    public LatLng getLocation() {
    	return getLocationRecognizeResult().getList().isEmpty()
    			? null
    			: locationRecognizeResult.getList().get(0).getGeometry().getLocation();
    }
    
    public boolean hasChunk(Annotation chunk) {
    	return chunk == this.chunk;
    }
    
    @JsonIgnore
    public Text getTextContainingThisTextobject() {
    	return this.text;
    }
    
    @JsonIgnore
    public ArrayList<Textobject> getRelationFromTextobjects() {
    	ArrayList<Textobject> relFromTextobjects = new ArrayList<>();
		for (SerelRelation sr : this.chunk.getRelationsFrom()) {
			relFromTextobjects.add(text.getTextobjectByChunk(sr.getFrom()));
		}
		return relFromTextobjects;
    }
    
    @JsonIgnore
    public ArrayList<Textobject> getRelationToTextobjects() {
    	ArrayList<Textobject> relToTextobjects = new ArrayList<>();
		for (SerelRelation sr : this.chunk.getRelationsTo()) {
			relToTextobjects.add(text.getTextobjectByChunk(sr.getTo()));
		}
		return relToTextobjects;
    }
    
    public LocationGeocodeStatus getLocationGeocodeStatus() {
    	return this.locationGeocodeStatus;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("baseName: ");
        sb.append(getBaseName());
        sb.append("; nameClass: ");
        sb.append(getNameClass().toString());
        sb.append("; text: ");
        sb.append(getRawText());
        sb.append("; locationText: ");
		sb.append(getLocationText());
        
        return sb.toString();
    }
    
    
}
