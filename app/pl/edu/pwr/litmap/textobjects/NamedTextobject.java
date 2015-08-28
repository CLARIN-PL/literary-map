package pl.edu.pwr.litmap.textobjects;

import java.util.ArrayList;
import java.util.List;

public class NamedTextobject {

	private final Textobject textobject;
	private final String name;
	
	private static List<NamedTextobject> namedTextobjects = new ArrayList<>();
	
	public static NamedTextobject create(Textobject textobject, String name) {
		for (NamedTextobject namedTextobject : namedTextobjects) {
			if (namedTextobject.name.equals(name) && namedTextobject.textobject.equals(textobject)) {
				return namedTextobject;
			}
		}
		return new NamedTextobject(textobject, name);
	}
	
	private NamedTextobject(Textobject textobject, String name) {
		this.textobject = textobject;
		this.name = name;
	}
	
	public Textobject getTextobject() {
		return textobject;
	}

	public String getName() {
		return name;
	}	
	
	public boolean equals(Object o) {
		return o instanceof NamedTextobject 
				&& ((NamedTextobject) o).name.equals(this.name)
				&& ((NamedTextobject) o).textobject.equals(this.textobject);
	}

}
