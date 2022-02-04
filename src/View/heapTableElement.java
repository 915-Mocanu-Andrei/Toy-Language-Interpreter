package View;

import Model.value.IValue;

public class heapTableElement {
    private int address;
    private String value;

    public heapTableElement(int address, String value) {
        this.address = address;
        this.value = value;
    }

    public int getAddress() {
        return address;
    }

    public String getValue() {
        return value;
    }
//public heapTableElement(int address,IValue value){
    //    this.address = address;
    //    this.value = value;
    //}
}
