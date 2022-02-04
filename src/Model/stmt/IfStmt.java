package Model.stmt;
import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.*;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.*;

public class IfStmt implements IStmt{
    Exp condition;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp cond,IStmt st1, IStmt st2){
        this.condition = cond;
        this.thenS = st1;
        this.elseS = st2;
    }

    public PrgState execute(PrgState state){
        IStack<IStmt> stack = state.getExeStack();
        System.out.println("??????????????????????????");
        System.out.println(this.condition);
        IValue value = this.condition.eval(state.getSymTable(), state.getHeapTable());
        System.out.println("Pana aici e bine!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(value.getType());
        if (value.getType().equals(new BoolType())){
            BoolValue bool = (BoolValue) value;
            if (bool.getValue()){
                stack.push(thenS);
            }
            else{
                stack.push(elseS);
            }
        }
        else{
            throw new MyException("Condition is not BoolType!");
        }
        return null;
    }

    public String toString(){
        return "If "+ this.condition.toString() +" then "+ this.thenS.toString() +" else "+ this.elseS.toString();
    }

    public IDict<String,IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typexp=condition.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
