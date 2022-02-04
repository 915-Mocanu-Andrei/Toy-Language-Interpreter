package Controller;
import Model.Exception.MyException;
import Model.PrgState;

import java.util.List;
import Repo.Repo;
import Repo.IRepo;
import Model.stmt.IStmt;
import Model.adt.IStack;
import GarbageCollector.GarbageCollector;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    IRepo repo;
    boolean flag;
    ExecutorService executor;
    //constructor

    public Controller(IRepo repo){
        this.repo = repo;
        this.flag = true;
    }

    public IRepo getRepo(){
        return this.repo;
    }

    public void addProgram(PrgState newPrg) throws MyException {
        this.repo.addPrg(newPrg);
    }

    PrgState oneStep(PrgState state) throws MyException{
        IStack<IStmt> stk=state.getExeStack();
        if(stk.isEmpty()){ throw new MyException("prgstate stack is empty");}
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
        }


    public void allStep() throws MyException{
        executor = Executors.newFixedThreadPool(10);
//remove the completed programs
        //System.out.println( "PrgListSize=" + repo.getPrgList().size());
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        //System.out.println("PrgListSize=" +prgList.size());
        prgList.forEach(prg ->repo.logPrgStateExec(prg));
        while(prgList.size() > 0){
            prgList.forEach(prg ->{GarbageCollector.garbageCollector(GarbageCollector.getAddrFromSymTable(prg.getSymTable().getContent().values()),
                    GarbageCollector.getAddrFromHeap(prg.getHeap().getContent().values()),prg.getHeap().getContent());});
            oneStepForAllPrg(prgList);
//remove the completed programs
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
//HERE the repository still contains at least one Completed Prg
// and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
//setPrgList of repository in order to change the repository
// update the repository state
        repo.setPrgList(prgList);
    }


    public void display(){
        System.out.println(this.repo.getCrtPrg().toString());
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException{
//before the execution, print the PrgState List into the log file
        //System.out.println( "PrgListSize=" + repo.getPrgList().size());
//RUN concurrently one step for each of the existing PrgStates
//-----------------------------------------------------------------------
//prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());
        List<PrgState> newPrgList;
        try{
            //System.out.println("STUFF");
            newPrgList = executor.invokeAll(callList). stream()
                . map(future ->{ try { return future.get();}// { try { return future.get();}
                catch(InterruptedException | ExecutionException e) {
                    throw new MyException("Interrupted!aaaaaaaaaaaaaaaa" + e.getCause());
}
                })
.filter(p -> p!=null)
                            .collect(Collectors.toList());}
        catch (InterruptedException e){
            throw new MyException("InterruptedException!");
        }
//add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
//------------------------------------------------------------------------------
//after the execution, print the PrgState List into the log file
        //prgList.forEach(prg ->repo.logPrgStateExec(prg));
        System.out.println( "PrgListSize=" + repo.getPrgList().size());
//Save the current programs in the repository
        repo.setPrgList(prgList);
        prgList.forEach(prg ->repo.logPrgStateExec(prg));//log
    }
}
/*
    void oneStepForAllPrg(IList<PrgState> prgList) {
//before the execution, print the PrgState List into the log file
        ArrayList<PrgState> aList = prgList.getContent();
        aList.forEach(prg ->repo.logPrgStateExec(prg));
        //RUN concurrently one step for each of the existing PrgStates
//-----------------------------------------------------------------------
//prepare the list of callables
        List<Callable<PrgState>> callList = new List<Callable<PrgState>>();
        callList.setContent( new ArrayList<Callable<PrgState>>(aList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList())));
        try{
            List<PrgState> newPrgList = new List<PrgState>();
        newPrgList.setContent(new ArrayList<PrgState>(executor.invokeAll(callList.getContent()). stream()
                . map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new MyException("Exception!");
                    }
                }).filter(p -> p!=null)
                .collect(Collectors.toList())));
        }
        catch (InterruptedException e){
            throw new MyException("Interrupted!");
        }
        prgList.addAll(newPrgList);
//------------------------------------------------------------------------------
//after the execution, print the PrgState List into the log file
        prgList.forEach(prg ->repo.logPrgStateExec(prg));
//Save the current programs in the repository
        repo.setPrgList(prgList);
    }*/
