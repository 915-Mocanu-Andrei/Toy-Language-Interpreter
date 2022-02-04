package Model.exp;
import Model.Exception.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class ValueExp extends Exp{

    IValue value;
    public ValueExp(IValue value){
        this.value = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer,IValue> heapTable) throws MyException {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public IType typecheck(IDict<String, IType> typeEnv) throws MyException{
        return value.getType();
    }
}
