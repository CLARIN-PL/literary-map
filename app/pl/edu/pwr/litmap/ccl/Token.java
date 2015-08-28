package pl.edu.pwr.litmap.ccl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Reprezentuje token, z których składa się zdanie (Sentence)
 *
 * @author czuk
 *
 */
public class Token {

    /* Uporządkowana lista atrybutów */
    ArrayList<String> attributes = new ArrayList<String>();

    /* Lista analiz morfologicznych, jeżeli dostępna. */
    ArrayList<Tag> tags = new ArrayList<Tag>();

    /* Oznaczenie, czy między bieżącym a następnym tokenem był biały znak. */
    boolean noSpaceAfter = false;

    private String id = null;

    public void clearAttributes() {
        this.attributes = new ArrayList<String>();
    }

    public void removeAttribute(int attrIdx) {
        this.attributes.remove(attrIdx);
    }

    /**
     * TODO Zwraca wartość atrybutu o podany indeksie.
     *
     * @param name
     * @return
     */
    public String getAttributeValue(int index) {
        return attributes.get(index);
    }

    public int getNumAttributes() {
        return attributes.size();
    }

    public String getId() {
        return this.id;
    }

    /**
     * TODO Funkcja pomocnicza zwraca wartość pierwszego atrybutu. Przeważnie
     * będzie to orth.
     *
     * @return
     */
    public String getFirstValue() {
        return attributes.get(0);
    }

    public boolean getNoSpaceAfter() {
        return this.noSpaceAfter;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        if (attributes.size() < 3) {
            this.setAttributeValue(1, tag.getBase());
            this.setAttributeValue(2, tag.getCtag());
        }
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void packAtributes(int size) {
        while (getNumAttributes() < size) {
            attributes.add(null);
        }
    }

    public void setAttributeValue(int index, String value) {
        if (index < attributes.size()) {
            attributes.set(index, value);
        } else if (index == attributes.size()) {
            attributes.add(value);
        }
    }

    public void setNoSpaceAfter(boolean noSpaceAfter) {
        this.noSpaceAfter = noSpaceAfter;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFirstValue());
        sb.append("(id#");
        sb.append(this.id);
        sb.append(", tags{");
        Iterator<Tag> it = this.tags.iterator();
        while (it.hasNext()) {
            sb.append("(");
            Tag tag = it.next();
            sb.append(tag.toString());
            if (it.hasNext()) {
                sb.append("), (");
            } else {
                sb.append(")");
            }
        }
        sb.append("}, attrs{");
        Iterator<String> ita = this.attributes.iterator();
        while (ita.hasNext()) {
            sb.append("(");
            String attribute = ita.next();
            sb.append(attribute);
            if (ita.hasNext()) {
                sb.append("), (");
            } else {
                sb.append(")");
            }
        }
        sb.append("}");
        return sb.toString();
    }
    
    public String toStringTab() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFirstValue());
        int spaces = 8 - getFirstValue().length();
        while (spaces-- > 0) {
            sb.append(' ');
        }
        sb.append("\t");
        sb.append(this.id);
        sb.append("\ttags{ ");
        Iterator<Tag> it = this.tags.iterator();
        while (it.hasNext()) {
            Tag tag = it.next();
            sb.append(tag.toStringTab());
            if (it.hasNext()) {
                sb.append("\t|\t");
            } 
        }
        sb.append(" }\tattrs{ ");
        Iterator<String> ita = this.attributes.iterator();
        while (ita.hasNext()) {
            String attribute = ita.next();
            sb.append(attribute);
            spaces = 10 - attribute.length();
            while (spaces-- > 0) {
                sb.append(' ');
            }
            if (ita.hasNext()) {
                sb.append("\t|\t");
            }
        }
        sb.append(" }");
        return sb.toString();
    }
    
    public String toStringSimple() {
        return getFirstValue();
    }
}
