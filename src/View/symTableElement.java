package View;

import Model.value.IValue;

public class symTableElement {
    public String id;
    public String value;

    public symTableElement(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

}
