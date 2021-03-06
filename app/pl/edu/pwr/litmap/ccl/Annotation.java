package pl.edu.pwr.litmap.ccl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import pl.edu.pwr.litmap.relations.SerelRelation;

/**
 * Klasa reprezentuje anotację jako ciągłą sekwencję tokenów w zdaniu.
 *
 * @author czuk
 *
 */
public class Annotation {

    /**
     * Typ oznakowania.
     */
    private String type = null;

    /**
     * Zdanie, do którego należy chunk.
     */
    private Sentence sentence = null;

    private String id = null;
    /**
     * Indeksy tokenów.
     */
    private ArrayList<Integer> tokens = new ArrayList<Integer>();

    /**
     * Indeks głowy anotacji.
     */
    private int head;

    /**
     * Indeks anotacji w kanale.
     */
    private int channelIdx;

    /**
     * Informacja czy anotacja ma oznaczoną głowę.
     */
    private boolean hasHead = false;
    
    protected ArrayList<SerelRelation> relationsFrom = new ArrayList<>();
	protected ArrayList<SerelRelation> relationsTo = new ArrayList<>();

    public Annotation(int begin, int end, String type, Sentence sentence) {
        for (int i = begin; i <= end; i++) {
            this.tokens.add(i);
        }
        this.type = type;
        this.sentence = sentence;
    }

    public Annotation(int begin, String type, Sentence sentence) {
        this.tokens.add(begin);
        this.type = type;
        this.sentence = sentence;
    }

    public Annotation(int begin, String type, int channelIdx, Sentence sentence) {
        this.tokens.add(begin);
        this.type = type;
        this.sentence = sentence;
        this.channelIdx = channelIdx;
    }

    public void setChannelIdx(int idx) {
        this.channelIdx = idx;
    }

    public int getChannelIdx() {
        return this.channelIdx;
    }

    public boolean hasHead() {
        return this.hasHead;
    }

    public int getHead() {
        return this.head;
    }

    public void setHead(int idx) {
        this.hasHead = true;
        this.head = idx;
    }

    public void addToken(int idx) {
        if (idx > getEnd()) {
            this.tokens.add(idx);
        } else {
            for (int i = 0; i < this.tokens.size(); i++) {
                if (this.tokens.get(i) < idx) {
                    this.tokens.add(i, idx);
                }
            }
        }
    }

    public void replaceTokens(int begin, int end) {
        this.tokens = new ArrayList<Integer>();
        for (int i = begin; i <= end; i++) {
            this.tokens.add(i);
        }
    }

    public boolean equals(Annotation chunk) {
        if (!this.tokens.equals(chunk.getTokens())) {
            return false;
        } else if (!this.sentence.equals(chunk.getSentence())) {
            return false;
        } else if (!this.type.equals(chunk.getType())) {
            return false;
        }
        return true;
    }

    public String getId() {
        return this.id;
    }

    public int getBegin() {
        return this.tokens.get(0);
    }

    public int getEnd() {
        return this.tokens.get(this.tokens.size() - 1);
    }

    public ArrayList<Integer> getTokens() {
        return this.tokens;
    }

    public Sentence getSentence() {
        return this.sentence;
    }

    public String getType() {
        return this.type;
    }

    /**
     * Zwraca treść chunku, jako konkatenację wartości pierwszych atrybutów.
     *
     * @return
     */
    public String getRawText() {
        ArrayList<Token> tokens = this.sentence.getTokens();
        StringBuilder text = new StringBuilder();
        for (int i : this.tokens) {
            Token token = tokens.get(i);
            text.append(token.getFirstValue());
            if ((!token.getNoSpaceAfter()) && (i < getEnd())) {
                text.append(" ");
            }
        }
        return text.toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Annotation[] sortChunks(HashSet<Annotation> chunkSet) {
        int size = chunkSet.size();
        Annotation[] sorted = new Annotation[size];
        int idx = 0;
        for (Annotation c : chunkSet) {
            sorted[idx++] = c;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if ((sorted[i].getBegin() > sorted[j].getBegin())
                        || ((sorted[i].getBegin() == sorted[j].getBegin())
                        && (sorted[i].getEnd() > sorted[j].getEnd()))) {
                    Annotation aux = sorted[i];
                    sorted[i] = sorted[j];
                    sorted[j] = aux;
                }
            }
        }
        return sorted;
    }
    
    public ArrayList<SerelRelation> getRelationsFrom() {
		return relationsFrom;
	}

	public void addRelationFrom(SerelRelation relationFrom) {
		this.relationsFrom.add(relationFrom);
	}

	public ArrayList<SerelRelation> getRelationsTo() {
		return relationsTo;
	}

	public void addRelationTo(SerelRelation relationTo) {
		this.relationsTo.add(relationTo);
	}
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(id);
        sb.append("; hasHead: ");
        sb.append(hasHead ? "true" : "false");
        sb.append("; head: ");
        sb.append(head);
        sb.append("; channelIdx: ");
        sb.append(channelIdx);
        sb.append("; type: ");
        sb.append(type);
        sb.append("; sentence_id: ");
        sb.append(sentence.id);
        sb.append("; tokens: ");
        for (int i= 0; i<tokens.size(); i++) {
            sb.append(tokens.get(i));
            if (i<tokens.size()-1) {
                sb.append(",");
            }
        }
        
        return sb.toString();
    }
}
