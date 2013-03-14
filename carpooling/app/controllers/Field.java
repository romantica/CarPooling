package controllers;

import play.mvc.Controller;

/**
 * 
 */
class Field {
    public String type;
    public String name;
    public String value;
    public boolean required;
    public String placeholder;
	public String id;
	
	public String regex;
	
	public boolean isError;
	public String error;
	
	public String attr;
	
	public Field(String type, String name) {
		this.type = type;
		this.name = name;
	}
}