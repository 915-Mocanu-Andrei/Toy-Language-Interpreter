package Model.stmt;

//import Model.Exception.DivByZeroException;
//import Model.Exception.EvaluationException;
//import Model.Exception.InexistentSymbolException;
import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;

import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import Model.exp.*;

public class NewLatchStmt implements IStmt{
    private static int newFreeLocation = -1;
    private String var;
    private Exp exp;

    public NewLatchStmt(String var,Exp exp) {
        this.var=var;
        this.exp=exp;
    }

    @Override
    public PrgState execute(PrgState state) {
        try {
            IValue numberR = this.exp.eval(state.getSymTable(), state.getHeap());//aici era int
            IntValue number = (IntValue) numberR;
            synchronized (state.getLatchTable()) {
                ++newFreeLocation;
                state.getLatchTable().put(newFreeLocation,number);
                state.getSymTable().add(this.var,new IntValue(newFreeLocation));
                return null;
            }

        }
        catch(MyException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "newLatch(" + this.var + ", " + this.exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            if(exp.typecheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("New Latch:exp not an Int!");
        else
            throw new MyException("New Latch:var not an Int!");
    }
}

