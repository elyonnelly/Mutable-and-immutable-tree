package tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;

public class ImmutableTree<T extends Number> extends AbstractTree<T> {

    public ImmutableTree(ImmutableNode<T> root, BinaryOperator<T> adder, Comparator<T> comparator, T zero) {
        super(adder, comparator, zero);
        this.root = root;
    }

    /**
     * Construct a new tree based on the oldTree one but without extraSubTree
     * @param oldTree
     * @param extraSubTree
     */
    public ImmutableTree(ImmutableTree<T> oldTree, Node<T> extraSubTree) {
        super(oldTree.adder, oldTree.comparator, oldTree.zero);
        this.root = new ImmutableNode<>(oldTree.root.getValue(), null, (tImmutableNode) -> childrenConstructor((ImmutableNode<T>) oldTree.root, (ImmutableNode<T>)extraSubTree));
    }

    @Override
    public AbstractTree<T> removeSubtree(Node<T> rootSubTree) {
        if (!(rootSubTree instanceof ImmutableNode)) {
            throw new IllegalArgumentException("rootSubTree is not MutableTree");
        }
        if (!isNodeFound(rootSubTree)) {
            throw new IllegalArgumentException("tree does not contain such a subtree");
        }

        if (rootSubTree == root) {
            return null;
        }

        return new ImmutableTree<T>(this, rootSubTree);
    }

    @Override
    public AbstractTree<T> maximize(int k) {
        return null;
    }

    @Override
    public AbstractTree<T> maximize() {
        var tempTree = new ImmutableTree<T>(this, new ImmutableNode<>());
        tempTree.removeNegativeSubtrees(root);
        return tempTree;
    }


    /**
     * Removes all subtrees with a negative amount
     * @param rootSubTree
     * @return
     */
    protected T removeNegativeSubtrees(Node<T> rootSubTree) {
        T sum = rootSubTree.getValue();
        var forDelete = new ArrayList<Node<T>>();
        for (var child : rootSubTree.getChildren()) {
            T sumChild = removeNegativeSubtrees(child);

            if (comparator.compare(sumChild, zero) < 0) {
                forDelete.add(child);
            } else {
                adder.apply(sum, sumChild);
            }
        }

        for (var child : forDelete) {
            removeSubtree(child);
        }

        return sum;
    }

    /**
     * Children Constructor which prohibits adding a extraSubTree to the tree
     * @param oldNode
     * @param extraSubTree
     * @return
     */
    private Collection<? extends Node<T>> childrenConstructor(ImmutableNode<T> oldNode, ImmutableNode<T> extraSubTree) {
        Collection<ImmutableNode<T>> newChildren = new ArrayList<>();
        for (var child : oldNode.getChildren()) {
            if (child == extraSubTree) {
                continue;
            }

            if (child.getChildren().size() == 0) {
                newChildren.add(new ImmutableNode<T>(child.getValue(), oldNode, (tImmutableNode) -> new ArrayList<>()));
            }
            else {
                newChildren.add(new ImmutableNode<T>(child.getValue(), oldNode, (tImmutableNode) -> childrenConstructor((ImmutableNode<T>) child, extraSubTree)));
            }
        }
        return newChildren;
    }

}
