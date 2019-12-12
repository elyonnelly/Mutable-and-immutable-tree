package tree.tests;

import org.junit.jupiter.api.Test;
import tree.AbstractTree;
import tree.ImmutableNode;
import tree.ImmutableTree;
import tree.Node;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;

class ImmutableTreeTest {

    Integer[] values;
    Integer[][] children;
    ImmutableNode<Integer>[] nodes;

    @Test
    void removeSubtree() throws FileNotFoundException {
        var tree = initializeTreeFromTestFile(1);

        var subtree = ((ArrayList<Node<Integer>>)tree.getRoot().getChildren()).get(1);

        var newTree = tree.removeSubtree(subtree);

        assert (newTree.getSize() == 2);
        assert (newTree != tree);
    }

    @Test
    void maximize() {
    }

    @Test
    void initialize() throws FileNotFoundException {
        var tree = initializeTreeFromTestFile(1);
        assert (tree.getSize() == 3);
    }

    AbstractTree<Integer> initializeTreeFromTestFile(int test) throws FileNotFoundException {
        FileReader fr;
        fr = new FileReader(System.getProperty("user.dir") + "/res/" + test + ".txt");

        Scanner in = new Scanner(fr);
        int numberOfNode = in.nextInt();
        values = new Integer[numberOfNode]; //значения
        nodes = new ImmutableNode[numberOfNode]; //вершины
        for (int i = 0; i < numberOfNode; i++) {
            values[i] = in.nextInt();
        }
        children = new Integer[numberOfNode][]; //список детей для конкретной вершины
        for (int i = 0; i < numberOfNode; i++) {
            int amountOfChildren = in.nextInt();
            children[i] = new Integer[amountOfChildren];
            for (int j = 0; j < amountOfChildren; j++) {
                children[i][j] = in.nextInt();
            }
        }
        nodes[0] = new ImmutableNode<Integer>(values[0], null, (tImmutableNode) -> childrenConstructorFromFile(0));
        AbstractTree<Integer> tree = new ImmutableTree<Integer>(nodes[0], Integer::sum, Comparator.naturalOrder(), 0);

        return tree;

    }

    private Collection<? extends Node<Integer>> childrenConstructorFromFile(int parent) {
        Collection<ImmutableNode<Integer>> newChildren = new ArrayList<>();
        for (var child : children[parent]) {
            if (children[child].length == 0) {
                nodes[child] = new ImmutableNode<Integer>(values[child], nodes[parent], (tImmutableNode) -> new ArrayList<Node<Integer>>());
                newChildren.add(nodes[child]);
            }
            else {
                nodes[child] = new ImmutableNode<Integer>(values[child], nodes[parent], tImmutableNode -> childrenConstructorFromFile(child));
                newChildren.add(nodes[child]);
            }
        }
        return newChildren;
    }

}