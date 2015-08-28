/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pwr.litmap.textobjects;



/**
 *
 * @author Wojciech Gaweł
 */
public class FakeTextobject extends Textobject {

    protected NameClass nameClass;
    protected String textFromSentence;
    protected String baseName;

    public FakeTextobject(Text text, NameClass nameClass, String textFromSentence, String baseName) {
        super(text, null);
    	this.textFromSentence = textFromSentence;
        this.baseName = baseName;
        this.nameClass = nameClass;
    }
    
    /**
     * @return Text (fragment of sentence)
     */
    public String getTextFromSentence() {
        return this.textFromSentence;
    }

    /**
     * @return the baseName
     */
    public String getBaseName() {
        return this.baseName;
    }

    /**
     * @return the nameClass
     */
    public NameClass getNameClass() {
        return this.nameClass;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("baseName: ");
        sb.append(getBaseName());
        sb.append("; nameClass: ");
        sb.append(getNameClass().toString());
        sb.append("; textFromSentence: ");
        sb.append(getTextFromSentence());
        sb.append("; locationText: ");
		sb.append(getLocationText());
        
        return sb.toString();
    }
    
    
}
