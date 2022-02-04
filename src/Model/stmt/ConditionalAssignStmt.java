package Model.stmt;

import Model.Exception.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.exp.ValueExp;
import Model.types.BoolType;
import Model.types.IType;

public class ConditionalAssignStmt implements IStmt{

    //v=exp1?exp2:exp3
    Exp exp1,exp2,exp3;
    String v;

    public ConditionalAssignStmt(String v,Exp exp1,Exp exp2, Exp exp3){
        this.v=v;
        this.exp1=exp1;
        this.exp2=exp2;
        this.exp3=exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        //if (exp1) then v=exp2 else v=exp3
        IStmt toPush = new IfStmt(exp1,new AssignStmt("v",exp2),new AssignStmt("v",exp3));
        state.getExeStack().push(toPush);
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        if (exp1.typecheck(typeEnv).equals(new BoolType())){
            if(exp2.typecheck(typeEnv).equals(exp3.typecheck(typeEnv)) &&
                    typeEnv.lookup("v").equals(exp3.typecheck(typeEnv))){
                return typeEnv;
            }
            else{
                throw new MyException("ConditionalAssignStmt: exp2,exp3,v not of the same type!");
            }
        }
        else{
            throw new MyException("ConditionalAssignStmt: exp1 not a Bool!");
        }
    }

    public String toString(){
        return v.toString() + "="+ exp1.toString() + "?" + exp2.toString()+":"+exp3.toString();
    }
}
