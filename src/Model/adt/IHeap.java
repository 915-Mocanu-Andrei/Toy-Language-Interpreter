package Model.adt;

import java.util.Map;

public interface IHeap<T1,T2>{

    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    void remove(T1 v1);
    T2 lookup(T1 id);
    boolean isDefined(T1 id);
    String toString();
    public Heap<T1,T2> clone();
    public int getNext();
    public void setContent(Map<T1, T2> map);
    public Map<T1, T2> getContent();
    public java.util.List<Integer> getHeapAdress();
}
