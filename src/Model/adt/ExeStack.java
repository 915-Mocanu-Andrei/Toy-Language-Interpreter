package Model.adt;
import java.util.Stack;

public class ExeStack<T> implements IStack<T> {
    Stack<T> stack;

    public ExeStack(){
        this.stack = new Stack<>();
    }

    @Override
    public T pop() {
        return this.stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public String toString(){
        StringBuilder str = new StringBuilder("ExeStack:\n");
        for(int j = stack.size()-1; j >= 0; j--){
            str.append(this.stack.get(j).toString());
            str.append("\n");
        }
        return str.toString();
    }
}
