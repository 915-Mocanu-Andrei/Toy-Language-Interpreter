package Model.adt;

import java.util.ArrayList;
import java.util.Locale;

public interface IList<T> {
    void add(T v);
    T get_element(int index);
    String toString();
    boolean empty();
    void clear();
    public T getFirstElement();
    public ArrayList<T> getContent();
    public void setContent(ArrayList<T> list);
    public int getLength();
}
