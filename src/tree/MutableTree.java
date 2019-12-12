package tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MutableTree<T extends Number> extends AbstractTree<T> {

    public MutableTree(MutableNode<T> root, BinaryOperator<T> adder, Comparator<T> comparator, T zero) {
        super(adder, comparator, zero);
        this.root = root; //чтобы MutableTree можно было создать только с MutableNode
    }

    /**
     * Returns the tree from which the subtree whose root is rootSubTree
     * @param rootSubTree
     * @return
     */
    @Override
    public AbstractTree<T> removeSubtree(Node<T> rootSubTree) {
        if (!(rootSubTree instanceof MutableNode)) {
            throw new IllegalArgumentException("rootSubTree is not MutableTree");
        }
        if (!isNodeFound(rootSubTree)) {
            throw new IllegalArgumentException("tree does not contain such a subtree");
        }

        if (rootSubTree == root) {
            return null;
        }

        MutableNode<T> parent = (MutableNode<T>) rootSubTree.getParent();
        parent.removeChild((MutableNode<T>) rootSubTree);
        return this;
    }

     /**
     * Returns a tree that has the maximum sum of all tree node values.
     * To do this, subtrees are removed from the tree in the quantity in which it is necessary.
     * @return
     */
    @Override
    public AbstractTree<T> maximize() {
        removeNegativeSubtrees(root);
        return this;
    }


    /**
     * Returns a tree that has the maximum sum of all values of
     * the tree nodes when removing no more than k subtrees from this tree.
     * @param k maximum number of subtrees to remove
     * @return
     */
    @Override
    public AbstractTree<T> maximize(int k) {
        return null;
    }


}
