package pl.edu.pwr.litmap.ccl;

public class Tag {

	String base = null;
	String ctag = null;
	boolean disamb = false;
	
	public Tag()	{}
	
	public Tag(String base, String ctag, boolean disamb) {
		this.base = base;
		this.ctag = ctag;
		this.disamb = disamb;
	}
	
	public String getBase()		{ return base;	}
	public String getCtag()		{ return ctag;	}
	public boolean getDisamb()	{ return disamb; }
        
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("base: ");
            sb.append(base);
            sb.append(";ctag: ");
            sb.append(ctag);
            sb.append(";disamb: ");
            sb.append(disamb);
            
            return sb.toString();
        }
        
        public String toStringTab() {
            StringBuilder sb = new StringBuilder();
            sb.append(base);
            int spaces = 8 - base.length();
            while (spaces-- > 0) {
                sb.append(' ');
            }
            sb.append('\t');
            sb.append(ctag); 
            spaces = 20 - ctag.length();
            while (spaces-- > 0) {
                sb.append(' ');
            }
            sb.append(disamb);
            
            return sb.toString();
        }
}
