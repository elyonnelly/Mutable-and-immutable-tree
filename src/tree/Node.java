package tree;

import java.util.Collection;

/**
 * Interface representing the node in the tree
 * @param <T> Type of value stored at the node
 */
public interface Node<T extends Number> extends Wrapper<T> {
    Node<T> getParent();
    Collection<Node<T>> getChildren();

    /**
     * Print node to the console.
     * @param indents number of spaces before a node.
     */
    void print(int indents);
}
