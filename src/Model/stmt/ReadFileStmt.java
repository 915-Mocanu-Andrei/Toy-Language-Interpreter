package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{

    Exp expression;
    String var_name;

    public ReadFileStmt(Exp exp,String var){
        this.expression = exp;
        this.var_name = var;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        System.out.println("Doing read");
        if (state.getSymTable().isDefined(this.var_name)){
            IValue val = expression.eval(state.getSymTable(), state.getHeapTable());
            if (val.getType().equals(new StringType())){
                StringValue valS = (StringValue) val;
                if (state.getFileTable().isDefined(valS)){
                        BufferedReader reader = state.getFileTable().lookup(valS);
                        try{
                            String line = reader.readLine();
                            IntValue intVal;
                            if (line != null){
                                intVal = new IntValue(Integer.parseInt(line));
                            }
                            else{
                                intVal = new IntValue();
                            }
                            state.getSymTable().add(var_name,intVal);
                        }
                        catch (IOException e){
                            throw new MyException(e.toString());
                        }
                }
                else{
                    throw new MyException("StringValue is not in the FileTable!");
                }
            }
            else{
                throw new MyException("Expression is not of StringType!");
            }
        }
        else {
            throw new MyException("Variable not defined!");
        }
        return null;
    }
    public String toString(){
        return "ReadFile " + this.expression.toString();
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException {

        if (expression.typecheck(typeEnv).equals(new StringType())){
            typeEnv.add(var_name,new StringType());
            return typeEnv;}
        else{
            throw new MyException("ReadFile: Expression Type not a String");
        }
    }
}
