package Model.adt;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        this.dictionary = new HashMap<T1,T2>();
    }

    @Override
    public IDict<T1,T2> clone() {
        Dict<T1,T2> dictionaryClone = new Dict<T1, T2>();
        for (T1 x : this.dictionary.keySet()){
            dictionaryClone.add(x,this.lookup(x));
        }
        return dictionaryClone;
    }

    @Override
    public Map<T1,T2> getContent() {
        return this.dictionary;
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
        for (T1 key : dictionary.keySet()) {
            str.append(key).append("->").append(dictionary.get(key).toString()).append("; ");
        }
        return str.toString();
    }

}
