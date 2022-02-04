package Model.exp;

import Model.Exception.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.BoolValue;

public class LogicExp extends Exp{
    String op;
    Exp e1, e2;

    public LogicExp( String op,Exp e1, Exp e2){
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }


    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer,IValue> heapTable) throws MyException {
        IValue val1 = e1.eval(symTable,heapTable);
        BoolType type = new BoolType();
        if (val1.getType().equals(type)){
            IValue val2 = e2.eval(symTable,heapTable);
            if (val2.getType().equals(type)) {
                BoolValue bVal1 = (BoolValue) val1;
                BoolValue bVal2 = (BoolValue) val2;
                boolean b1 = bVal1.getValue();
                boolean b2 = bVal2.getValue();
                boolean result = false;
                if (op.equals("and")) {
                    if (b1 && b2) {
                        result = true;
                    }
                }
                if (op.equals("or")){
                    if (b1 || b2){
                        result = true;}
                    }
                return new BoolValue(result);
            }
            else throw new MyException("Second operand is not a Bool!");
        }
        else throw new MyException("First operand is not a Bool!");
    }
    /*
            if (op == '+') return (e1.eval(symTable)+e2.eval(symTable));
            //...
            else
                if(e2.eval(symTable)==0)
                 //throw new exception
        }
    */
    public String getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() { return e1.toString() + " " + op + " " + e2.toString(); }

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new MyException("second operand is not a bool");
        }
        else
            throw new MyException("first operand is not a bool");
    }
}
