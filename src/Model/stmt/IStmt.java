package Model.stmt;
import Model.PrgState;//mine
import Model.Exception.MyException;
import Model.adt.IDict;
import Model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    String toString();
    IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException;
}
