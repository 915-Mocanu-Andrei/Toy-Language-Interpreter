package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{

    Exp expression;

    public CloseRFileStmt(Exp exp){
        this.expression =exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        System.out.println("Doing close");
        IValue val = this.expression.eval(state.getSymTable(),state.getHeapTable());
        if (val.getType().equals(new StringType())){
            StringValue valS = (StringValue) val;
            if (state.getFileTable().isDefined(valS)){
                BufferedReader reader = state.getFileTable().lookup(valS);
                try{
                    reader.close();
                    state.getFileTable().remove(valS);}
                catch (IOException e) {
                    throw new MyException(e.toString());
                }
            }
            else{
                throw new MyException("StringValue is not in the FileTable!");
            }
        }
        else{
            throw new MyException("Expression is not of StringType");
        }
        return null;
    }

    public String toString(){
        return "CloseReadFile " + this.expression.toString();
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException {
        if (expression.typecheck(typeEnv).equals(new StringType())){
            return typeEnv;}
        else {
            throw new MyException("TypeCheck: Close File: Expression is not of StringType");
        }
    }
}
