package views;

/**
 * User: sdefauw
 * Date: 14/03/13
 */
public class Form {

    private String action;
    private String meth;
    public String name;
    public String id;

    public Form (String action, String meth){
        this.action = action;
        this.meth = meth;
    }

    public void setField(String type, ){

    }

    public int getIntField(){
        return 0;
    }
}


class Field {
    String type;
    String name;
    String value;
    boolean empty;
    String placeholder;

}
