package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.stmt.*;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class AwaitStmt implements IStmt {

    private String var;

    public AwaitStmt(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState p) {
        if (!p.getSymTable().isDefined(var))
            throw new MyException("Await var not defined!");
        //int foundIndex=p.getSymTable().lookup(var);
        IValue foundIndexR=p.getSymTable().lookup(var);
        IntValue foundIndexI = (IntValue) foundIndexR;
        int foundIndex = foundIndexI.getValue();
        if(!p.getLatchTable().contains(foundIndex))
            throw new MyException("Await Latch doesn't contain"+foundIndexI.toString());
        else {
            if (p.getLatchTable().get(foundIndex).getValue() != 0){
                p.getExeStack().push(this);
            }
            else{
                return null;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "await(" + var + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Await:var not an int!");
    }
}

