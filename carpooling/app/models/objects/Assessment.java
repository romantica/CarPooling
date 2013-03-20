package models.objects;

import javax.persistence.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;


@Entity
@Table(name="Assessment")
public class Assessment extends Model {
	
	@Id
	private int id;
	
	@Constraints.Required
	private int rating;
	
	private String comment;
	private boolean Type;
	
	public Assessment(int rating, String comment, boolean type) {
		super();
		this.rating = rating;
		this.comment = comment;
		Type = type;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isType() {
		return Type;
	}

	public void setType(boolean type) {
		Type = type;
	}
	
	public static Finder<Integer, Assessment> find = new Finder<Integer, Assessment>(Integer.class, Assessment.class);
	
}
