package Model.exp;
import Model.Exception.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class VarExp extends Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    public IValue eval(IDict<String,IValue> symTable, IHeap<Integer,IValue> heapTable) throws MyException {
        return symTable.lookup(id);
    }

    public String toString() {return id;}

    public IType typecheck(IDict<String, IType> typeEnv) throws MyException{
        return typeEnv.lookup(id);
    }
}
