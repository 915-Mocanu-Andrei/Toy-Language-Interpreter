package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.*;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;


public class PrintStmt implements IStmt{

    Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    public PrgState execute(PrgState state){
        System.out.println("Doing print");
        System.out.println(expression.eval(state.getSymTable(), state.getHeapTable()).toString());
        IList<String> output = state.getOutput();
        output.add(expression.eval(state.getSymTable(), state.getHeapTable()).toString());
        state.setOutput(output);
        return null;
    }

    public String toString(){ return "print(" +expression.toString()+")";}

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }
}
