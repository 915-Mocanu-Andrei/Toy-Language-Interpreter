package Model.value;

import Model.types.RefType;
import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue{

    int address;
    IType locationType;

    public RefValue(int add,IType locT){
        address = add;
        locationType = locT;
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address,locationType);
    }

    public int getAddr() {return address;}

    @Override
    public IType getType() { return locationType;}

    public void setAddress(int add) {this.address = add;}

    @Override
    public String toString(){
        return "("+ address+", "+locationType.getInner().toString()  +") ";
    }

}
