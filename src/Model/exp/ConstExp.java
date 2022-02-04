package Model.exp;
import Model.Exception.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.types.*;
import Model.value.IntValue;
/* Redundant
public class ConstExp extends Exp{

    IValue value;
    IType type;

    public ConstExp(int value){
        this.value = new IntValue(value);
        this.type = new IntType();
    }
    public ConstExp(boolean value){
        this.value = new BoolValue(value);
        this.type = new BoolType();
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer,IValue> heapTable)throws MyException {
        //if (this.type.equals(new BoolType())){
            return this.value;
       // }
    }

    public String toString() {
        return this.value.toString();
    }
}*/
