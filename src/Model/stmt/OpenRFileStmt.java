package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.*;

public class OpenRFileStmt implements IStmt{

    Exp expression;

    public OpenRFileStmt(Exp exp){
        this.expression =exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        System.out.println("Doing open");
        IValue val = this.expression.eval(state.getSymTable(), state.getHeapTable());
        if (val.getType().equals(new StringType())){
            StringValue valS = (StringValue) val;
            if (!state.getFileTable().isDefined(valS)){
                try{
                state.getFileTable().add(valS,new BufferedReader(new FileReader(valS.getValue())));
                }
                catch (IOException e){
                    throw new MyException(e.toString());
                }
            }
            else{
                throw new MyException("StringValue already in the FileTable");
            }
        }
        else{
            throw new MyException("Expression is not of StringType");
        }
        return null;
    }
    public String toString(){
        return "OpenRFile " + this.expression.toString();
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException {
        if (expression.typecheck(typeEnv).equals(new StringType())){
            return typeEnv;}
        else {
            throw new MyException("TypeCheck: Open File: Expression is not of StringType");
        }
    }
}
