package tree;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A generic class of a mutable tree node
 * @param <T> Type of value stored at the node
 */
public class MutableNode<T extends Number> implements Node<T> {
    private T value;
    private MutableNode<T> parent;
    private Collection<Node<T>> children;

    public MutableNode() {
        children = new ArrayList<>();
    }

    public MutableNode(T value) {
        this.value = value;
        children = new ArrayList<>(); // инициализация эррэйлистом для быстрого удаления-добавления
    }

    public MutableNode(T value, MutableNode<T> parent) {
        this.value = value;
        this.parent = parent;
        children = new ArrayList<>(); // в каждом конструкоре должна быть инициализация
    }

    @Override
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public Node<T> getParent() {
        return parent;
    }

    public void setParent(MutableNode<T> parent) {
        this.parent = parent;
    }

    @Override
    public Collection<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(Collection<MutableNode<T>> newChildren) {
        replaceChildren(newChildren);
    }

    /**
     * Completely replace the collection of children for node
     * @param newChildren new children
     */
    private void replaceChildren(Collection<MutableNode<T>> newChildren) {
        if (newChildren == null) {
            throw new IllegalArgumentException("Collection of children is null");
        }

        for(var child : this.children) {
            removeChild((MutableNode)child);
        }
        for(var child : newChildren) {
            addChild(child);
        }
    }

    /**
     * Add a new node to the node collection of children and set parent for new node
     * @param child node child
     */
    public void addChild(MutableNode<T> child) {
        if (child == null) {
            throw new IllegalArgumentException("new child is null");
        }

        MutableNode<T> odlParent = (MutableNode<T>)child.getParent();

        if (odlParent != null) {
            odlParent.removeChild(child);
        }

        child.setParent(this);
        children.add(child);
    }

    /**
     * Remove some node from collection of children
     * @param child
     */
    public void removeChild(MutableNode<T> child) {
        if (child == null) {
            throw new IllegalArgumentException("new child is null");
        }
        if (!children.contains(child)) {
            throw new IllegalArgumentException("argument is not a child for node");
        }

        children.remove(child);
        child.setParent(null);
    }

    @Override
    public void print(int indents) {
        String space = "";
        for (int i = 0; i < indents; i++) {
            space += " ";
        }
        System.out.printf(space + value.toString());
    }
}
