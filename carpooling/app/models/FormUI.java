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
    public ArrayList<String> otherError;
	private List<Field> fields;

    public FormUI(String action, String meth){
        this.action = action;
        this.meth = meth;
		this.fields = new ArrayList<Field>();
        this.otherError = new ArrayList<String>();
    }
	
	public FormUI(String action) {
		this(action, "post");
	}

    public void addField(Field field){
		this.fields.add(field);
    }
	
	public Field getField(String id) {
		for (Field f : this.fields)
			if (f.id.equals(id))
				return f;
		return null;
	}

    public int getIntField(String id){
    	String val = this.getField(id).value;
    	if (val == null || val.equals(""))
    		return 0;
    	else return Integer.parseInt(this.getField(id).value);
    }
    public float getFloatField(String id){
    	String val = this.getField(id).value;
    	if (val == null || val.equals(""))
    		return (float) 0.0;
        return Float.parseFloat(this.getField(id).value);
    }
	public String getStringField(String id){
        return this.getField(id).value;
    }
	
	public void completeForm(DynamicForm data) {
		for (Field f : this.fields) {
			if (!f.typeinput.equals("submit") && !f.typeinput.equals("button")) {
				if (f.regex == null || (data.get(f.id) != null && data.get(f.id).matches(f.regex)))
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
        if (!otherError.isEmpty())
            return true;
		return false;
	}
}
