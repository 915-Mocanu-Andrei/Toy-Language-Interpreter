package Model.stmt.heap;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapWriteStmt implements IStmt {

    String var_name;
    Exp expression;

    public HeapWriteStmt(String variable,Exp exp){
        this.var_name = variable;
        this.expression = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (state.getSymTable().isDefined(var_name)){
            if (state.getSymTable().lookup(var_name).getType() instanceof RefType){
                    RefValue val = (RefValue) state.getSymTable().lookup(var_name);
                    if (state.getHeapTable().isDefined(val.getAddr())){
                        IValue evaluated = this.expression.eval(state.getSymTable(),state.getHeapTable());
                        if (evaluated.getType().equals(val.getType().getInner())){
                            state.getHeapTable().add(((RefValue) state.getSymTable().lookup(var_name)).getAddr(),evaluated);
                        }
                        else{
                            throw new MyException("Different types!");
                        }
                    }
                    else{
                        throw new MyException("Key not present in heap table!");
                    }
            }
            else{
                throw new MyException("Not a Ref Type!");
            }
        }
        else{
            throw new MyException("Var_name not defined!");
        }
        return null;
    }

    public String toString(){
        return var_name +"->"+ expression.toString();
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(var_name);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("WRITE stmt: right hand side and left hand side have different types ");
    }
}
