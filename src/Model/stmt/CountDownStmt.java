package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.stmt.*;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;


public class CountDownStmt implements IStmt{

    private String var;

    public CountDownStmt(String var) {
        this.var = var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException{
        if (state.getSymTable().isDefined(this.var)) {
            IValue indexR = state.getSymTable().lookup(this.var);
            IntValue indexI = (IntValue) indexR;
            int index = indexI.getValue();
            synchronized (state.getLatchTable()) {
                if (!state.getLatchTable().contains(index))
                    throw new MyException("CountDown: Index not in the latch table!");
                else if (state.getLatchTable().get(index).getValue() > 0){
                    int count = state.getLatchTable().get(index).getValue();
                        state.getLatchTable().put(index, new IntValue(count - 1));
                        state.getOutput().add(Integer.toString(state.getId()));
                }
                else{
                    state.getOutput().add(Integer.toString(state.getId()));
                }
            }
        }
        else{
            throw new MyException("CountDown: var not defined!");
        }
        return null;
    }

    @Override
    public String toString() {
        return "countDown(" + this.var + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("CountDown var not an int");
    }
}

