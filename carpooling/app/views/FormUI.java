package views;

import java.util.*;
import play.data.DynamicForm;

/**
 * User: sdefauw
 * Date: 14/03/13
 */
public class FormUI {

    private String action;
    private String meth;
    public String name;
    public String id;
	private Map<String, Field> fields;

    public Form (String action, String meth){
        this.action = action;
        this.meth = meth;
		this.fields = new HashMap<String, Field>();
    }

    public void setField(Field field){
		this.fields.put(field.name, field);
    }
	
	public Field getField(String name) {
		return this.fields.get(name);
	}

    public int getIntField(String name){
        return Integer.parseInt(this.getField(name).value);
    }
	
	public String getStringField(String name){
        return this.getField(name).value;
    }
	
	public void completeForm(DynamicForm data) {
		for (Field f : this.fields.values())
			f.value = data.get(f.name);
	}
}
