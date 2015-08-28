package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.validation.*;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Document extends Model {
	
	@Id
	public long id;
	
    @Constraints.Required
    @Constraints.MinLength(value = 4)
	public String name;

    @Constraints.Required
    @Constraints.MinLength(value = 3)
    @Column(columnDefinition = "text")
	public String plainText;

    @Constraints.MinLength(value = 3)
	public String localization;
	
	@Formats.DateTime(pattern = "dd.MM.yyyy")
	@Column(name = "created_at")
    public Date createDate;
	
	public static Finder<Long,Document> find = new Finder<Long,Document>(
			Long.class, Document.class
	);
	
	public Document(String name, String plainText) {
		this.name = name;
		this.plainText = plainText;
	}
	
	public Document(String name, String plainText, String localization) {
		this(name, plainText);
		if (localization != null && !localization.isEmpty()) {
			this.localization = localization;
		}
	} 
	
	@Override
	public void save() {
		createdAt();
		super.save();
	}
	
	@PrePersist
	void createdAt() {
		this.createDate = new Date();
	}
}
