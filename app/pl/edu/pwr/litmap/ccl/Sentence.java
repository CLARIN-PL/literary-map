package pl.edu.pwr.litmap.ccl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

/**
 * Reprezentuje zdanie jako sekwencję tokenów i zbiór anotacji.
 *
 * @author czuk
 *
 */
public class Sentence {

    /* Indeks nazw atrybutów */
    TokenAttributeIndex attributeIndex = null;

    /* Sekwencja tokenów wchodzących w skład zdania */
    ArrayList<Token> tokens = new ArrayList<Token>();

    /* Zbiór anotacji */
    HashSet<Annotation> chunks = new HashSet<Annotation>();

    /* Identyfikator zdania */
    String id = null;

    public Sentence() {
    }

    public void addChunk(Annotation chunk) {
        chunks.add(chunk);
    }

    public void addChunking(AnnotationSet chunking) {
        if (chunking != null) {
            for (Annotation chunk : chunking.chunkSet()) {
                addChunk(chunk);
            }
        }
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public String getId() {
        return this.id;
    }

    /*
     * Zwraca chunk dla podanego indeksu tokenu.
     * TODO zmienić parametr na token?
     */
    public Annotation getChunkAt(int idx) {
        Annotation returning = null;
        Iterator<Annotation> i_chunk = chunks.iterator();
        while (i_chunk.hasNext()) {
            Annotation currentChunk = i_chunk.next();
            if ((currentChunk.getBegin() <= idx)
                    && (currentChunk.getEnd() >= idx)) {
                returning = currentChunk;
                break;
            }
        }
        return returning;
    }

    public HashSet<Annotation> getChunks() {
        return this.chunks;
    }

    public int getAttributeIndexLength() {
        return this.attributeIndex.getLength();
    }

    public TokenAttributeIndex getAttributeIndex() {
        return this.attributeIndex;
    }

    /*
     * Zwraca ilość tokenów.
     */
    public int getTokenNumber() {
        return tokens.size();
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setAttributeIndex(TokenAttributeIndex attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    public void setChunking(AnnotationSet chunking) {
        this.chunks = chunking.chunkSet();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRawText() {
        StringBuilder sb = new StringBuilder();
        for (Token token : this.tokens) {
            sb.append(token.toStringSimple());
            if (!token.noSpaceAfter) {
                sb.append(' ');
            }
        }
        
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sentence id#");
        sb.append(getId());
        sb.append("; attrIndex: ");
        sb.append(attributeIndex.toString());
        
        sb.append("\nchunks: ");
        for (Annotation chunk : this.chunks) {
            sb.append(chunk.toString());
            sb.append("\t|\t");
        }
                
        sb.append("\ntokens: ");
        for (Token token : this.tokens) {
            sb.append(token.toString());
            if (!token.noSpaceAfter) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        
        return sb.toString();
    }
    
    public String toStringTab() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sentence id#");
        sb.append(getId());
        sb.append("; attrIndex: ");
        sb.append(attributeIndex.toString());
        
        sb.append("\nchunks{");
        for (Annotation chunk : this.chunks) {
            sb.append("\t");
            sb.append(chunk.toString());
            sb.append("\n");
        }
        sb.append("}");
                
        sb.append("\ntokens: \n");
        for (Token token : this.tokens) {
            sb.append(token.toStringTab());
            if (!token.noSpaceAfter) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        
        return sb.toString();
    }

    public String toStringSimple() {
        StringBuilder sb = new StringBuilder();
        for (Token token : this.tokens) {
            sb.append(token.toStringSimple());
            if (!token.noSpaceAfter) {
                sb.append(' ');
            }
            sb.append('|');
        }
        
        return sb.toString();
    }
}
