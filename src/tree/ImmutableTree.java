package tree;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class ImmutableTree<T extends Number> extends AbstractTree<T> {

    public ImmutableTree(ImmutableNode<T> root, BinaryOperator<T> adder, Comparator<T> comparator, T zero) {
        super(adder, comparator, zero);
        this.root = root;
    }

    public ImmutableTree(ImmutableTree<T> oldTree, Node<T> extraSubTree) {
        super(oldTree.adder, oldTree.comparator, oldTree.zero);
        this.root = recreateTreeWithRoot((ImmutableNode<T>)oldTree.root, extraSubTree);
    }

    @Override
    AbstractTree<T> removeSubtree(Node<T> rootSubTree) {
        return new ImmutableTree<T>(this, rootSubTree);
    }

    @Override
    AbstractTree<T> maximize(int k) {
        return null;
    }

    @Override
    AbstractTree<T> maximize() {
        return null;
    }

    private ImmutableNode<T> recreateTreeWithRoot(ImmutableNode<T> rootSubTree, Node<T> extraSubTree) {
        if (rootSubTree.getChildren().contains(extraSubTree)) {
            return new ImmutableNode<>(rootSubTree, extraSubTree);
        }

        for (var child : rootSubTree.getChildren()) {
            //ну как бы изменения происходят внутри класса... blya. no.

        }
        return rootSubTree;
    }
}
