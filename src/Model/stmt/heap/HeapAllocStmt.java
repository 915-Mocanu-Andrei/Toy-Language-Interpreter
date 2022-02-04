package Model.stmt.heap;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.stmt.IStmt;

import Model.exp.Exp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

import java.sql.Ref;

public class HeapAllocStmt implements IStmt {

    String var_name;
    Exp expression;

    public HeapAllocStmt(String variable,Exp exp){
        this.var_name = variable;
        this.expression = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        if (state.getSymTable().isDefined(var_name)){
            IValue evaluated = this.expression.eval(state.getSymTable(),state.getHeapTable());
            if (state.getSymTable().lookup(var_name).getType() instanceof RefType){
                //System.out.println(var_name);
                RefValue var = (RefValue) state.getSymTable().lookup(var_name);
                System.out.println("Var:"+var.getType());
                System.out.println("Var:"+var.getType().getInner());
                System.out.println("Evaluated:"+evaluated.getType());
                if(evaluated.getType().equals(var.getType().getInner())){
                    System.out.println(var_name);
                    int address = state.getHeapTable().getNext();
                    var.setAddress(address);
                    state.getHeapTable().add(address,evaluated.deepCopy());
                }

            }
            //if (evaluated.getType().equals(state.getSymTable().lookup(var_name).getType())){
            //    state.getHeapTable().add(state.getHeapTable().getNext(),evaluated);
            //    state.getSymTable().update(var_name,evaluated);
            //}
            else{
            throw new MyException("Var not a Ref Type!");
            }
        }
        else{
            throw new MyException("Var_name not defined!" + var_name);
        }
        return null;
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(var_name);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    public String toString(){
        return "new(" + this.var_name +","+ this.expression.toString() + ");";
    }
}
