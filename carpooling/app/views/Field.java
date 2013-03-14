package views;

/**
 * 
 */
class Field {
    String type;
    String name;
    String value;
    boolean required;
    String placeholder;
	String id;
	
	String regex;
	
	boolean isError;
	String error;
	
	String attr;
	
	public Field(String type, String name) {
		this.type = type;
		this.name = name;
	}
}