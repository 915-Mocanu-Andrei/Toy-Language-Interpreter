package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.ExeStack;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;

public class ForkStmt implements IStmt{

    IStmt Stmt;

    public ForkStmt(IStmt Stmt){
        this.Stmt = Stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        PrgState state2 = new PrgState(new ExeStack<IStmt>(), state.getSymTable().clone(), state.getFileTable(),
                state.getHeapTable(), state.getOutput(), state.getLatchTable(), Stmt);
        state2.getExeStack().push(Stmt);
        return state2;
    }

    public String toString(){
        return "Fork( "+ this.Stmt.toString() + ")";
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException {
        Stmt.typecheck(typeEnv);
        return typeEnv;
    }
}
