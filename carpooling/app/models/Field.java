package models;

import java.util.List;

/**
 *
 */
public class Field {
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

    public String[] selection;

    public Field() {
    }

    public Field(String typeinput,
                 String name,
                 String id,
                 boolean required,
                 String error,
                 String regex) {
        if (typeinput.equals("address")) {
            this.typeinput = "text";
            this.placeholder = "Address";
        } else if (typeinput.equals("submit") || typeinput.equals("button")) {
            this.value = name;
            name = "";
            this.typeinput = typeinput;
        } else {
            this.typeinput = typeinput;
        }
        this.name = name;
        this.required = required;
        this.id = id;
        this.regex = regex;
        this.error = error;
    }


    public Field(String type, String name, String id) {
        this.typeinput = type;
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Field{" +
                "typeinput='" + typeinput + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", required=" + required +
                ", placeholder='" + placeholder + '\'' +
                ", id='" + id + '\'' +
                ", regex='" + regex + '\'' +
                ", isError=" + isError +
                ", error='" + error + '\'' +
                ", attr='" + attr + '\'' +
                '}';
    }
}