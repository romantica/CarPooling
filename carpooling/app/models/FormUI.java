package models;

import java.util.*;
import play.data.DynamicForm;

/**
 * User: sdefauw
 * Date: 14/03/13
 */
public class FormUI {

    public String action;
    public String meth;
    public String name;
    public String id = "1";
	private List<Field> fields;

    public FormUI(String action, String meth){
        this.action = action;
        this.meth = meth;
		this.fields = new ArrayList<Field>();
    }
	
	public FormUI(String action) {
		this(action, "post");
	}

    public void addField(Field field){
		this.fields.add(field);
    }
	
	public Field getField(String name) {
		for (Field f : this.fields)
			if (f.name == name)
				return f;
		return null;
	}

    public int getIntField(String name){
        return Integer.parseInt(this.getField(name).value);
    }
    public float getFloatField(String name){
        return Float.parseFloat(this.getField(name).value);
    }
	
	public String getStringField(String name){
        return this.getField(name).value;
    }
	
	public void completeForm(DynamicForm data) {
		for (Field f : this.fields) {
			if (!f.typeinput.equals("submit")) {
				if (f.regex == null || data.get(f.id).matches(f.regex))
					f.value = data.get(f.id);
				else f.isError = true;
			}
        }
	}
	
	public List<Field> getFields() {
		return this.fields;
	}
	
	public boolean isError() {
		for (Field f : this.fields)
			if (f.isError)
				return true;
		return false;
	}
}
