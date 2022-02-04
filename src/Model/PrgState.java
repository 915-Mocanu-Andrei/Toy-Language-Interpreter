package Model;
import Model.Exception.MyException;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.util.concurrent.Callable;

public class PrgState  {

    static int last_id = 0;
    int id;
    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IDict<StringValue, BufferedReader> fileTable;
    IHeap<Integer, IValue> heapTable;
    ILatchTable<Integer, IntValue> latchTable;
    IList<String> out;
    IStmt originalProgram; //optional field, but good to have

    public PrgState(IStack<IStmt> stack, IDict<String, IValue> table, IDict<StringValue, BufferedReader> fTable,
                    IHeap<Integer, IValue> hTable,IList<String> output,ILatchTable<Integer, IntValue> latchTable, IStmt original){
        this.exeStack = stack;
        this.symTable = table;
        this.fileTable = fTable;
        this.out = output;
        this.originalProgram = original;
        this.heapTable = hTable;
        this.id = PrgState.getNewId();
        this.latchTable = latchTable;
    }

    public IList<String> getOutput() {
        return this.out;
    }

    public IDict<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public ILatchTable<Integer, IntValue> getLatchTable() {
        return this.latchTable;
    }

    public IHeap<Integer, IValue> getHeapTable() {
        return this.heapTable;
    }

    public void setOutput(IList<String> output) {
        this.out = output;
    }

    public IStack<IStmt> getExeStack(){
        return this.exeStack;
    }

    public boolean isNotCompleted(){
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep()throws MyException {
        //IStack<IStmt> stk=this.exeStack;
        if(exeStack.isEmpty()) throw new MyException("Execution stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public void changeId(int new_id){
        id = new_id;
    }

    public int getId(){
        return id;
    }

    public static synchronized int getNewId(){
        last_id = last_id+1;
        return last_id;
    }

    public  IHeap<Integer, IValue> getHeap(){
        return this.heapTable;
    }

}
