package Model.stmt;

import Model.value.*;
import Model.Exception.MyException;
import Model.PrgState;
import Model.types.*;
import Model.types.IType;
import Model.adt.IDict;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;

    public VarDeclStmt(String name, IType type){
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        System.out.println("Doing declare");
        IDict<String, IValue> symtable = state.getSymTable();
        if (symtable.isDefined(this.name)){
                throw new MyException("Variable already declared");
        }
        else{
            if (this.type.equals(new BoolType())) {
                symtable.add(this.name, new BoolValue());
                return null;
            }
            if (this.type.equals(new StringType())) {
                symtable.add(this.name,new StringValue());
                return null;
            }
            if (this.type.equals(new IntType())) {
                symtable.add(this.name,new IntValue());
                return null;
            }
            symtable.add(this.name,new RefValue(0,this.type));
            System.out.println("Declarare type"+this.type.toString());
            return null;
        }
    }

    public IDict<String,IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        typeEnv.add(name,type);
        return typeEnv;
    }

    public String toString(){ return this.type + " " + this.name ;}

}
