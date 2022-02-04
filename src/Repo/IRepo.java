package Repo;
import Model.PrgState;
import Model.Exception.MyException;

import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    PrgState getCrtPrg();
    void logPrgStateExec(PrgState p) throws MyException;
    void setLogFile(String file);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    }
