package Model.adt;

import java.util.ArrayList;
import java.util.Locale;

public class List<T> implements IList<T> {
    ArrayList<T> list = new ArrayList<T>(10);

    @Override
    public void add(T v) {
        this.list.add(v);
    }

    @Override
    public T get_element(int index) {
        return list.get(index);
    }

    @Override
    public T getFirstElement() {
        return list.get(0);
    }

    @Override
    public ArrayList<T> getContent() {
        return this.list;
    }

    @Override
    public void setContent(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public int getLength() {
        return list.size();
    }

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear(){
        this.list.clear();
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Out: ");
        for(T x: list ){
            str.append(x).append(", ");
        }
        return str.toString();
    }

    public boolean contains(T element){
        return this.list.contains(element);
    }

}
