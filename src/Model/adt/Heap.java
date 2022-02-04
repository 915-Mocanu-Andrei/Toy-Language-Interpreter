package Model.adt;

import Model.value.IValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Heap<T1,T2> implements IHeap<T1,T2> {
    Map<T1, T2> dictionary;
    int next;

    public Heap() {
        this.dictionary = new HashMap<T1,T2>();
        next = 0;
    }

    @Override
    public Heap<T1,T2> clone() {
        Heap<T1,T2> dictionaryClone = new Heap<T1, T2>();
        for (T1 x : this.dictionary.keySet()){
            dictionaryClone.add(x,this.lookup(x));
        }
        return dictionaryClone;
    }

    @Override
    public int getNext() {
        next =next + 1;
        return next;
    }

    @Override
    public void add(T1 v1, T2 v2) {
        this.dictionary.put(v1,v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        this.dictionary.replace(v1, v2);

    }

    @Override
    public void remove(T1 v1){
        this.dictionary.remove(v1);
    }

    @Override
    public T2 lookup(T1 id) {
        return dictionary.get(id);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);//Should it be String?
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("HeapTable: ");
        for (T1 key : dictionary.keySet()) {
            str.append(key).append("->").append(dictionary.get(key).toString()).append("; ");
        }
        return str.toString();
    }

    public void setContent(Map<T1, T2> map){
        this.dictionary = map;
            //for (T1 x: map.keySet()){
            //this.add(x,map.get(x));
        //}
    }

    @Override
    public Map<T1, T2> getContent() {
        return this.dictionary;
    }

    @Override
    public java.util.List<Integer> getHeapAdress() {
        return null;
    }

}
