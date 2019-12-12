package tree.tests;

import org.junit.jupiter.api.Test;
import tree.ImmutableNode;
import tree.ImmutableTree;
import tree.Node;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

class ImmutableNodeTest {

    @Test
    void checkReInitializer() {
        ImmutableTree<Integer> oldTree = null;
        var root = new  ImmutableNode<Integer>(5, null,  (tImmutableNode) -> childrenConstructorFromExistTree(tImmutableNode));

    }

    private Collection<? extends Node<Integer>> childrenConstructorFromExistTree(ImmutableNode<Integer> parent) {
        Collection<ImmutableNode<Integer>> newChildren = new ArrayList<>();
        for (var child : parent.getChildren()) {
            if (child.getChildren().size() == 0) {
                newChildren.add(new ImmutableNode<Integer>(child.getValue(), parent, (tImmutableNode) -> new ArrayList<>()));
            }
            else {
                 newChildren.add(new ImmutableNode<Integer>(child.getValue(), parent, tImmutableNode -> childrenConstructorFromExistTree(tImmutableNode)));
            }
        }
        return newChildren;
    }



}