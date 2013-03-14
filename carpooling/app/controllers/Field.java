package controllers;

/**
 * 
 */
class Field {
    public String typeinput;
    public String name;
    public String value;
    public boolean required;
    public String placeholder;
	public String id;
	
	public String regex;
	
	public boolean isError;
	public String error;
	
	public String attr;



    public Field(String typeinput,
                 String name,
                 String id,
                 boolean required,
                 String error,
                 String regex) {
        if(typeinput.equals("address")){
            this.typeinput = "input";
            this.placeholder = "Address";
        }else{
            this.typeinput = typeinput;
        }
        this.name = name;
        this.required = required;
        this.id = id;
        this.regex = regex;
        this.error = error;
    }
	
	public Field(String type, String name) {
		this.typeinput = type;
		this.name = name;
	}
}