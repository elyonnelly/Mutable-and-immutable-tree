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

    public ImmutableTree(ImmutableTree<T> oldTree, Node<T> extraSubTree) {
        super(oldTree.adder, oldTree.comparator, oldTree.zero);
        this.root = new ImmutableNode<>(oldTree.root.getValue(), null, (tImmutableNode) -> childrenConstructor((ImmutableNode<T>) oldTree.root, (ImmutableNode<T>)extraSubTree));
    }

    @Override
    public AbstractTree<T> removeSubtree(Node<T> rootSubTree) {
        return new ImmutableTree<T>(this, rootSubTree);
    }

    @Override
    public AbstractTree<T> maximize(int k) {
        return null;
    }

    @Override
    public AbstractTree<T> maximize() {
        return null;
    }

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
                newChildren.add(new ImmutableNode<T>(child.getValue(), oldNode, (tImmutableNode) -> childrenConstructor(tImmutableNode, extraSubTree)));
            }
        }
        return newChildren;
    }
}
