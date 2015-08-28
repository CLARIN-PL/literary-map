package pl.edu.pwr.litmap.tasksManager;

public class Token {
	
	private final String id;

	public Token(String id) {
		this.id = id;
	}

	public boolean equals(Object o) {
		return (o instanceof Token) && (((Token) o).id == this.id);
	}
}
