package tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class ImmutableNode<T extends Number> implements Node<T> {

    private T value;
    private ImmutableNode<T> parent;
    private Collection<Node<T>> children;

    public ImmutableNode(T value,
                         ImmutableNode<T> parent,
                         //принимает ноду, отдает коллекцию. Присваивает ноде коллекцию в ее конструкторе и отдает эту самую
                         Function<ImmutableNode<T>, Collection<? extends Node<T>>> childrenConstructor) {
        this.value = value;
        this.parent = parent;
        //TODO возможно переделать
        this.children = (Collection<Node<T>>)childrenConstructor.apply(this);
        for (var child : this.children) {
            ((ImmutableNode)child).parent = this;
        }
    }

    @Override
    public Node<T> getParent() {
        return parent;
    }

    @Override
    public Collection<Node<T>> getChildren() {
        return children;
    }

    @Override
    public void print(int indents) {
        String space = "";
        for (int i = 0; i < indents; i++) {
            space += " ";
        }
        System.out.printf(space + value.toString());
    }

    @Override
    public T getValue() {
        return value;
    }
}
