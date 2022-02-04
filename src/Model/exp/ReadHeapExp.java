package Model.exp;

import Model.Exception.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class ReadHeapExp extends Exp{

    Exp expression;

    public ReadHeapExp(Exp exp){
        this.expression = exp;
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer,IValue> heapTable) throws MyException{
        IValue evaluated = this.expression.eval(symTable,heapTable);//EXPRESSION
        if (evaluated.getType() instanceof RefType){
            RefValue evaluatedRef = (RefValue) evaluated;
            int address = evaluatedRef.getAddr();
            if(heapTable.isDefined(address)){
                return heapTable.lookup(address);
            }
            else{
                throw new MyException("Address not present in Heap!");
            }
        }
        else{
            throw new MyException("Not a ref type!Can't read!");
        }
    }

    @Override
    public String toString() {
        return "rH("+this.expression.toString()+")";
    }

    @Override
    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typ=expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

}
