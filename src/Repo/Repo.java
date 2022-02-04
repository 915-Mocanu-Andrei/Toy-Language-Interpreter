package Repo;
import Model.PrgState;
import Model.Exception.MyException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import java.lang.System;

public class Repo implements IRepo {

    List<PrgState> myPrgStates;
    String logFilePath;

    public Repo() {
        myPrgStates = new ArrayList<PrgState>();
        logFilePath = "C:\\Users\\Andrei\\Desktop\\CountDownLatch\\Interpretor Implementation\\src\\Repo\\Log";
   }

    public Repo(PrgState initial_state,String file) {
        myPrgStates = new ArrayList<PrgState>();
        logFilePath = "C:\\Users\\Andrei\\Desktop\\CountDownLatch\\Interpretor Implementation\\src\\Repo\\" + file;
        myPrgStates.add(initial_state);
    }

    @Override
    public PrgState getCrtPrg() {
        return myPrgStates.get(0);
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public void logPrgStateExec(PrgState p) throws MyException {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        }
        catch (IOException e){
            throw new MyException(e.toString());
        }
        logFile.println(p.getId());
        logFile.println(p.getExeStack().toString());
        logFile.println("SymTable:");
        logFile.println(p.getSymTable().toString());
        logFile.println(p.getHeapTable().toString());
        logFile.println("FileTable:");
        logFile.println(p.getFileTable().toString());
        logFile.println(p.getOutput().toString());
        logFile.println("LatchTable:");
        logFile.println(p.getLatchTable().toString());
        logFile.println();
        logFile.close();
    }


    @Override
    public void setLogFile(String file) {
        this.logFilePath = "C:\\Users\\Andrei\\Desktop\\CountDownLatch\\Interpretor Implementation\\src\\Repo\\" + file;
    }

    @Override
    public List<PrgState> getPrgList() {
        return myPrgStates;
    }

    public void setPrgList(List<PrgState> list){
        this.myPrgStates = list;
    }
}
