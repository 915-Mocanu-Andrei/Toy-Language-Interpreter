package View;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.stmt.heap.HeapAllocStmt;
import Model.stmt.heap.HeapWriteStmt;
import Model.types.*;
import Model.value.*;
import Repo.Repo;
import Controller.Controller;
import Model.PrgState;

import javax.management.ListenerNotFoundException;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {


    //static Repo myRepository = new Repo();
    //static Controller myController = new Controller(myRepository);

    public static void main(String[] args) {

        //Runnable a = () -> System.out.println(2);
        //a.run();
        // ex 1:  int v; v = 2; Print(v)
        //IStmt simple = new PrintStmt(new ConstExp(67));
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                        new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));

        //IStmt originalProgram = new IfStmt(new ConstExp(true),
        //        new CompStmt(new AssignStmt("v", new ConstExp(5)),
        //                new PrintStmt(new ArithExp('/',
        //                        new VarExp("v"), new ConstExp(3)))),
       //         new PrintStmt(new ConstExp(100)));

        IStmt filesp1 = new CompStmt(new VarDeclStmt("varf",new StringType()),new AssignStmt("varf",
                new ValueExp(new StringValue("D:\\Interpretor\\Interpretor Implementation\\src\\test.in"))));
        IStmt filesp2 = new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                new VarDeclStmt("varc",new IntType()));
        IStmt filesp3 = new CompStmt(new ReadFileStmt(new VarExp("varf"),"varc"),new PrintStmt(new VarExp("varc")));
        IStmt filesp4 = filesp3;
        IStmt filesp5 = new CloseRFileStmt(new VarExp("varf"));
        IStmt files = new CompStmt(new CompStmt(new CompStmt(filesp1,filesp2),new CompStmt(filesp3,filesp4)),filesp5);

        IStmt ex5 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new RelationalExp("<",
                new ValueExp(new IntValue(9)),new ValueExp(new IntValue(123)))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));


        IStack<IStmt> exe1 = new ExeStack<IStmt>();
        exe1.push(ex1);
        ex1.typecheck(new Dict<String,IType>());
        PrgState prg1 = new PrgState(exe1,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex1);
        Repo repo1 = new Repo(prg1,"Log1.txt");
        Controller ctr1 = new Controller(repo1);
        IStack<IStmt> exe2 = new ExeStack<IStmt>();
        exe2.push(ex2);
        ex2.typecheck(new Dict<String,IType>());
        PrgState prg2 = new PrgState(exe2,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex2);
        Repo repo2 = new Repo(prg2,"Log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStack<IStmt> exe3 = new ExeStack<IStmt>();
        exe3.push(ex3);
        ex3.typecheck(new Dict<String,IType>());
        PrgState prg3 = new PrgState(exe3,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex3);
        Repo repo3 = new Repo(prg3,"Log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStack<IStmt> exe4 = new ExeStack<IStmt>();
        exe4.push(files);
        files.typecheck(new Dict<String,IType>());
        PrgState prg4 = new PrgState(exe4,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),files);
        Repo repo4 = new Repo(prg4,"Log4.txt");
        Controller ctr4 = new Controller(repo4);

        IStack<IStmt> exe5 = new ExeStack<IStmt>();
        exe5.push(ex5);
        ex5.typecheck(new Dict<String,IType>());
        PrgState prg5 = new PrgState(exe5,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex5);
        Repo repo5 = new Repo(prg5,"Log5.txt");
        Controller ctr5 = new Controller(repo5);

        IStack<IStmt> exe6 = new ExeStack<IStmt>();

        IStmt exHeap = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),new CompStmt(
                new HeapAllocStmt("v",new ValueExp(new IntValue(20))),new CompStmt(
                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),new CompStmt(
                new HeapAllocStmt("a",new VarExp("v")),new CompStmt(
                new PrintStmt(new VarExp("v")),new CompStmt(
                new PrintStmt(new VarExp("a")),new CompStmt(
                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))))));

        IStmt exHeap2 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),new CompStmt(
                new HeapAllocStmt("v",new ValueExp(new IntValue(20))),new CompStmt(
                new PrintStmt(new ReadHeapExp(new VarExp("v"))),new CompStmt(
                new HeapWriteStmt("v",new ValueExp(new IntValue(30))),
                new PrintStmt(new ArithExp('+',new ReadHeapExp(new VarExp("v")),new ValueExp(new IntValue(5))))))));

        IStmt exWhile = new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(
                new AssignStmt("v",new ValueExp(new IntValue(5))),new CompStmt(
                new WhileStmt(new RelationalExp(">",new VarExp("v"),new ValueExp(new IntValue(0))),
                new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",
                new ArithExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),
                new PrintStmt(new VarExp("v")))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt exaaaaaaaaa = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
                                        new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                        )))));

        //int v; Ref int a; v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a))
        IStmt forkEx = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),
                                new CompStmt(new HeapAllocStmt("a",new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a",new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        //exe6.push(new PrintStmt(new ReadHeapExp(new VarExp("a"))));
        //exe6.push(new PrintStmt(new ReadHeapExp(new VarExp("v"))));
        //exe6.push(new PrintStmt(new VarExp("a")));
        //exe6.push(new PrintStmt(new VarExp("v")));
        //exe6.push(new HeapAllocStmt("a",new VarExp("v")));
        //exe6.push(new VarDeclStmt("a",new RefType(new RefType(new IntType()))));
        //exe6.push(new HeapAllocStmt("v",new ValueExp(new IntValue(20))));
        //exe6.push(new VarDeclStmt("v",new RefType(new IntType())));
        exe6.push(exHeap);//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIIIIIIIIIIIIIIIIIIIIIIIICCCCCCCCCCCCCCIIIIIIIIIIIIIIAAAAAAAAAAAAAA
        exHeap.typecheck(new Dict<String,IType>());
        PrgState prg6 = new PrgState(exe6,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex5);
        Repo repo6 = new Repo(prg6,"Log6.txt");
        Controller ctr6 = new Controller(repo6);

        IStack<IStmt> exe7 = new ExeStack<IStmt>();
        exe7.push(exHeap2);
        exHeap2.typecheck(new Dict<String,IType>());
        PrgState prg7 = new PrgState(exe7,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex5);
        Repo repo7 = new Repo(prg7,"Log7.txt");
        Controller ctr7 = new Controller(repo7);

        IStack<IStmt> exe8 = new ExeStack<IStmt>();
        exe8.push(exWhile);
        exWhile.typecheck(new Dict<String,IType>());
        PrgState prg8 = new PrgState(exe8,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex5);
        Repo repo8 = new Repo(prg8,"Log8.txt");
        Controller ctr8 = new Controller(repo8);

        IStack<IStmt> exe9 = new ExeStack<IStmt>();
        exe9.push(exaaaaaaaaa);
        exaaaaaaaaa.typecheck(new Dict<String,IType>());
        PrgState prg9 = new PrgState(exe9,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex5);
        Repo repo9 = new Repo(prg9,"Log9.txt");
        Controller ctr9 = new Controller(repo9);

        IStack<IStmt> exe10 = new ExeStack<IStmt>();
        exe10.push(forkEx);
        forkEx.typecheck(new Dict<String,IType>());
        PrgState prg10 = new PrgState(exe10,new Dict<String, IValue>(),
                new Dict<StringValue, BufferedReader>(),new Heap<Integer,IValue>(),new List<String>(),new LatchTable<>(),ex5);
        Repo repo10 = new Repo(prg10,"Log10.txt");
        Controller ctr10 = new Controller(repo10);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExampleCommand("2",ex2.toString(),ctr2));
        menu.addCommand(new RunExampleCommand("3",ex3.toString(),ctr3));
        menu.addCommand(new RunExampleCommand("4",files.toString(),ctr4));
        menu.addCommand(new RunExampleCommand("5",ex5.toString(),ctr5));
        menu.addCommand(new RunExampleCommand("6",exHeap.toString(),ctr6));
        menu.addCommand(new RunExampleCommand("7",exHeap2.toString(),ctr7));
        menu.addCommand(new RunExampleCommand("8",exWhile.toString(),ctr8));
        menu.addCommand(new RunExampleCommand("9",exaaaaaaaaa.toString(),ctr9));
        menu.addCommand(new RunExampleCommand("10",forkEx.toString(),ctr10));
        menu.show();



    }
}
//myController.addProgram(myPrgState);
//myController.allStep();
/*
    Example1:
    int v;
    v=2;
    Print(v)
    Example2:
    int a;
    a=2+3*5;
    int b;
    b=a-4/2 + 7;
    Print(b)
    Example3:
    bool a;
    a=false;
    int v;
    If a Then v=2 Else v=3;
    Print(v)
    System.out.println("Which program do you want to run?");
        System.out.println("ex 1:  int v; v = 2; Print(v)");
        System.out.println("ex 2: a=2+3*5;b=a+1;Print(b)");
        System.out.println("ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
    */
