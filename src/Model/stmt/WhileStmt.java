package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class WhileStmt implements IStmt{

    Exp condition;
    IStmt statement;

    public WhileStmt(Exp cond,IStmt s){
        this.condition = cond;
        this.statement = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue evaluated = this.condition.eval(state.getSymTable(),state.getHeapTable());
        System.out.println(evaluated.toString());
        if (evaluated.getType().equals(new BoolType())){
            BoolValue evaluatedB = (BoolValue) evaluated;
            if (evaluatedB.getValue()){
                state.getExeStack().push(this);
                state.getExeStack().push(this.statement);
            }
        }
        else{
            throw new MyException("Not a BoolType!");
        }
        return null;
    }

    public String toString(){ return "(while("+ this.condition + ")" + this.statement.toString()+")";}

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typexp=condition.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            statement.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
