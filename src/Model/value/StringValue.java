package Model.value;

import Model.types.IType;
import Model.types.StringType;

public class StringValue implements IValue{

    String value;

    public StringValue(){
        this.value = "";
    }

    public StringValue(String str){
        this.value = str;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        StringValue o_value = (StringValue) o;
        return o_value.value.equals(this.value);
    }
    public String toString(){
        return this.value;
    }

}
