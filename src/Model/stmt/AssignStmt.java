package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.List;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;
import Model.adt.Dict;

public class AssignStmt implements IStmt{

    String id;
    Exp expression;

    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.expression = exp;
    }

    @Override
    public String toString(){
        return this.id + "=" + this.expression.toString();
    }

    public PrgState execute(PrgState state){
        System.out.println("Doing assign");
        IDict<String,IValue> symtable = state.getSymTable();
        if (symtable.isDefined(id)){
            IValue val = this.expression.eval(symtable, state.getHeapTable());
            if (val.getType().equals(symtable.lookup(id).getType())){
                symtable.update(id,val);
            }
            else{
                throw new MyException("Variable and Expression are not of the same type!"+
                        val.getType().toString()+symtable.lookup(id).getType().toString());
            }
        }
        else{
            throw new MyException("Var is not defined!");
        }
        return null;
    }

    public IDict<String,IType> typecheck(IDict<String, IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(id);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
}
