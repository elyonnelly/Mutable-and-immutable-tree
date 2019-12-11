package tree;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MutableTree<T extends Number> extends AbstractTree<T> {

    public MutableTree(MutableNode<T> root, BinaryOperator<T> adder, Comparator<T> comparator, T zero) {
        super(adder, comparator, zero);
        this.root = root; //чтобы MutableTree можно было создать только с MutableNode
    }

    @Override
    AbstractTree<T> removeSubtree(Node<T> rootSubTree) {
        if (!(rootSubTree instanceof MutableNode)) {
            throw new IllegalArgumentException("rootSubTree is not MutableTree");
        }
        if (!isNodeFound(rootSubTree)) {
            throw new IllegalArgumentException("tree does not contain such a subtree");
        }

        MutableNode<T> parent = (MutableNode<T>) rootSubTree.getParent();
        parent.removeChild((MutableNode<T>) rootSubTree);
        return this;
    }

    @Override
    AbstractTree<T> maximize(int k) {
        return null;
    }

    @Override
    AbstractTree<T> maximize() {
        return null;
    }

}
