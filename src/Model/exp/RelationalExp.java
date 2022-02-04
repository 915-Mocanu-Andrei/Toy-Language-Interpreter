package Model.exp;
import Model.Exception.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.*;

import java.util.Objects;

public class RelationalExp extends Exp{
    String op;
    Exp e1, e2;

    public RelationalExp( String op,Exp e1, Exp e2){
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer,IValue> heapTable) throws MyException {
        IValue val1 = e1.eval(symTable,heapTable);
        IntType type = new IntType();
        if (val1.getType().equals(type)) {
            IValue val2 = e2.eval(symTable,heapTable);
            if (val2.getType().equals(type)) {
                IntValue intVal1 = (IntValue) val1;
                IntValue intVal2 = (IntValue) val2;
                int nr1 = intVal1.getValue();
                int nr2 = intVal2.getValue();
                boolean result = false;
                if (op.equals("<")){
                    if (nr1<nr2){
                        result = true;
                    }
                }
                if (op.equals("<=")){
                    if (nr1<=nr2){
                        result = true;
                    }
           }
                if (op.equals(">")){
                    if (nr1>nr2){
                        result = true;
                    }
           }
                if (op.equals(">=")){
                    if (nr1>=nr2){
                        result = true;
                    }
               }
                if (op.equals("==")){
                    if (nr1==nr2){
                        result = true;
                    }
              }
                if (op.equals("!=")){
                    if (nr1!=nr2){
                        result = true;
                    }
              }
                return new BoolValue(result);            }
            else throw new MyException("Second operand is not an Integer");
        }
        else throw new MyException("First operand is not an Integer");
    }

    @Override
    public String toString() { return e1.toString() + " " + op + " " + e2.toString(); }

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }

}

