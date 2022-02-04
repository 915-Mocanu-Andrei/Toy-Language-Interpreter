package GarbageCollector;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map;
import Model.adt.Dict;
import Model.adt.Heap;
//import Model.adt.List;
import Model.value.IValue;
import Model.value.RefValue;



public class GarbageCollector {

    public static Map<Integer, IValue> garbageCollector(java.util.List<Integer> symTableAddr, java.util.List<Integer> heapAddr, Map<Integer,IValue> heap){
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static java.util.List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream().filter(v-> v instanceof RefValue).map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();}).collect(Collectors.toList());
    }

    public static java.util.List<Integer> getAddrFromHeap(Collection<IValue> heapValues)
    {
        return heapValues.stream().filter(v-> v instanceof RefValue).map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();}).collect(Collectors.toList());
    }



    //Heap<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Heap<Integer,IValue> heap){
      //  return heap.entrySet().stream()
        //        .filter(e->symTableAddr.contains(e.getKey()))
          //              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}


    //List<Integer> getAddrFromSymTable(List<IValue> symTableValues){
      //  return symTableValues.stream()
        //        .filter(v-> v instanceof RefValue)
          //      .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
            //    .collect(Collectors.toList());
    }

