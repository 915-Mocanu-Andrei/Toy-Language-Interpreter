package View;

import Controller.Controller;
import GarbageCollector.GarbageCollector;
import Model.Exception.MyException;
import Model.PrgState;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.RefValue;
import Repo.IRepo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Window2C implements Initializable {

    Controller current;
    ExecutorService executor;

    @FXML
    private Label RunningProgram;

    @FXML
    private Button OneStep;

    @FXML
    private Button LoadButton;

    @FXML
    private TextField nrPrgStates;

    @FXML
    private ListView<String> Out;

    @FXML
    private ListView<String> PrgStates;

    @FXML
    private ListView<String> FileTable;

    @FXML
    private ListView<String> ExeStack;

    @FXML
    private TableView<heapTableElement> HeapTable;

    @FXML
    private TableColumn<heapTableElement,Integer> addressColumn;

    @FXML
    private TableColumn<heapTableElement, String> valueHColumn;//SyncTable

    @FXML
    private TableView<syncTableElement> SyncTable;

    @FXML
    private TableColumn<syncTableElement,String> firstColumn;

    @FXML
    private TableColumn<syncTableElement, String> secondColumn;

    @FXML
    private TableView<symTableElement> SymTable;

    @FXML
    private TableColumn<symTableElement,String> variableNameColumn;

    @FXML
    private TableColumn<symTableElement, String> valueSColumn;

    private ObservableList<heapTableElement> heapList;

    private ObservableList<symTableElement> symList;

    private ObservableList<syncTableElement> syncList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addressColumn.setCellValueFactory(new PropertyValueFactory<heapTableElement,Integer>("address"));
        valueHColumn.setCellValueFactory(new PropertyValueFactory<heapTableElement,String>("value"));

        variableNameColumn.setCellValueFactory(new PropertyValueFactory<symTableElement,String>("id"));
        valueSColumn.setCellValueFactory(new PropertyValueFactory<symTableElement,String>("value"));

        firstColumn.setCellValueFactory(new PropertyValueFactory<syncTableElement,String>("id"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<syncTableElement,String>("value"));

        PrgStates.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //cod sa schimbi SymTable si ExeStack
                String item = PrgStates.getSelectionModel().getSelectedItem();
                if (item != null){
                    int id = Integer.parseInt(item);
                    current.getRepo().getPrgList().forEach(prg -> {
                        if (prg.getId() == id) {
                            ExeStack.getItems().clear();
                            ExeStack.getItems().addAll(prg.getExeStack().toString());
                            //
                            symList = FXCollections.observableArrayList();
                            prg.getSymTable().getContent().keySet().forEach(key -> {
                                symList.add(new symTableElement(key,
                                        prg.getSymTable().getContent().get(key).toString()));
                            System.out.println("VALUEEEEEE:"+ prg.getSymTable().getContent().get(key).toString());
                            });
                            SymTable.setItems(symList);
                    }
                });
            }
            }
    });}

    public void handleLoadButton(javafx.scene.input.MouseEvent event) throws IOException{
        Stage stage;
        Parent root;
        stage=(Stage) LoadButton.getScene().getWindow();
        current = (Controller) stage.getUserData();
        double v = 1000;
        stage.setMinHeight(v-350);
        stage.setMinWidth(v-50);
        LoadButton.setText(":)");
        executor = Executors.newFixedThreadPool(10);
        LoadButton.setDisable(true);
    }

    public void handleOneStepButton(javafx.scene.input.MouseEvent event)throws IOException{
        List<PrgState> prgList=removeCompletedPrg(current.getRepo().getPrgList());
        //prgList.forEach(prg ->current.getRepo().logPrgStateExec(prg));
            prgList.forEach(prg ->{
                GarbageCollector.garbageCollector(GarbageCollector.getAddrFromSymTable(prg.getSymTable().getContent().values()),
                        GarbageCollector.getAddrFromHeap(prg.getHeap().getContent().values()),prg.getHeap().getContent());});
            oneStepForAllPrg(prgList);
            nrPrgStates.clear();
            nrPrgStates.appendText(String.valueOf(prgList.size()));
            nrPrgStates.setEditable(false);
            Out.getItems().clear();
            Out.getItems().addAll(prgList.get(0).getOutput().toString());
            FileTable.getItems().clear();
            FileTable.getItems().addAll(prgList.get(0).getFileTable().toString());
            HeapTable.getItems().clear();
        heapList = FXCollections.observableArrayList();
        List<PrgState> finalPrgList = prgList;
        prgList.get(0).getHeapTable().getContent().keySet().forEach(key ->{heapList.add( new heapTableElement(key,
                    finalPrgList.get(0).getHeapTable().getContent().get(key).toString()));
            //System.out.println("VALUEEEEEE:"+ finalPrgList.get(0).getHeapTable().getContent().get(key).toString());
        });
        HeapTable.setItems(heapList);

        SyncTable.getItems().clear();
        syncList = FXCollections.observableArrayList();
        //List<PrgState> finalPrgList = prgList;
        prgList.get(0).getLatchTable().getContent().keySet().forEach(key ->{syncList.add( new syncTableElement(key,
                finalPrgList.get(0).getLatchTable().getContent().get(key)));
            //System.out.println("VALUEEEEEE:"+ finalPrgList.get(0).getHeapTable().getContent().get(key).toString());
        });
        SyncTable.setItems(syncList);
            prgList=removeCompletedPrg(current.getRepo().getPrgList());
            PrgStates.getItems().clear();//problem
            prgList.forEach(prg -> PrgStates.getItems().add(String.valueOf(prg.getId())));
        if(prgList.size() <= 0){
        executor.shutdownNow();
        current.getRepo().setPrgList(prgList);
        OneStep.setDisable(true);
        }
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
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
        System.out.println( "PrgListSize=" + current.getRepo().getPrgList().size());
//Save the current programs in the repository
        current.getRepo().setPrgList(prgList);
        prgList.forEach(prg ->current.getRepo().logPrgStateExec(prg));//log
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }
}