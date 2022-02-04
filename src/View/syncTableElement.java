package View;

import Model.value.IValue;
import Model.value.IntValue;

public class syncTableElement {
    public String id;
    public String value;

    public syncTableElement(int id, IntValue value) {
        this.id = Integer.toString(id);
        this.value = value.toString();
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

}
