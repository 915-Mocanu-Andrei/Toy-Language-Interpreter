package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;

public class CompStmt implements IStmt{
    IStmt s1;
    IStmt s2;

    public CompStmt( IStmt s1,IStmt s2){
        this.s1 = s1;
        this.s2 = s2;
    }

    public PrgState execute(PrgState state)  {
        System.out.println("Doing comp");
        IStack<IStmt> stack = state.getExeStack();
        stack.push(s2);
        stack.push(s1);
        return null;
    }

    public String toString(){ return this.s1.toString() +";"+ this.s2.toString() +"";}

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        return s2.typecheck(s1.typecheck(typeEnv));
    }
}
