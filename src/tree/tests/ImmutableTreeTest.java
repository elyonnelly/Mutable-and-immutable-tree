package tree.tests;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tree.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;

class ImmutableTreeTest {

    Integer[] values;
    Integer[][] children;
    ImmutableNode<Integer>[] nodes;

    ImmutableTree<Integer>[] testTrees;

    @BeforeEach
    void setUp() throws IOException {
        testTrees = new ImmutableTree[5];
        for (int i = 1; i <= 5; i++) {
            testTrees[i - 1] = buildImmutableTree(i);
        }
    }

    @Test
    void getSize() {
        int[] actual = new int[] {4, 4, 5, 8, 6};
        for (int i = 0; i < 5; i++) {
            assert (testTrees[i].getSize() == actual[i]);
        }
    }

    @Test
    void getSum() {
        Integer[] actual = new Integer[] {8, -8, -5, 198, 1};
        for (int i = 0; i < 5; i++) {
            assert (testTrees[i].getSum().equals(actual[i]));
        }
    }

    @Test
    void removeSubtree() throws FileNotFoundException {
        var subtree = ((ArrayList<Node<Integer>>)testTrees[0].getRoot().getChildren()).get(2);
        var newTree = testTrees[0].removeSubtree(subtree);
        assert (newTree.getSize() == 3);
        assert(newTree.getSum().equals(Integer.valueOf(3)));
        assert (newTree != testTrees[0]);
        assert (testTrees[0].getSize() == 4);
        assert (testTrees[0].getSum() == 8);

        subtree = ((ArrayList<Node<Integer>>)testTrees[1].getRoot().getChildren()).get(0);
        subtree = ((ArrayList<Node<Integer>>)subtree.getChildren()).get(0);
        newTree = testTrees[1].removeSubtree(subtree);
        assert (newTree.getSize() == 3);
        assert(newTree.getSum().equals(Integer.valueOf(-3)));
        assert (testTrees[1].getSize() == 4);
        assert (testTrees[1].getSum().equals(Integer.valueOf(-8)));

        subtree = ((ArrayList<Node<Integer>>)testTrees[3].getRoot().getChildren()).get(1);
        newTree = testTrees[3].removeSubtree(subtree);
        assert (newTree.getSize() == 4);
        assert(newTree.getSum().equals(Integer.valueOf(220)));
    }

    @Test
    void maximize() throws FileNotFoundException {

    }

    ImmutableTree<Integer> buildImmutableTree(int test) throws FileNotFoundException {
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
        nodes[0] = new ImmutableNode<Integer>(values[0], null, (tImmutableNode) -> childrenConstructor(0));
        ImmutableTree<Integer> tree = new ImmutableTree<Integer>(nodes[0], Integer::sum, Comparator.naturalOrder(), 0);

        return tree;

    }

    private Collection<? extends Node<Integer>> childrenConstructor(int parent) {
        Collection<ImmutableNode<Integer>> newChildren = new ArrayList<>();
        for (var child : children[parent]) {
            if (children[child].length == 0) {
                nodes[child] = new ImmutableNode<Integer>(values[child], nodes[parent], (tImmutableNode) -> new ArrayList<Node<Integer>>());
                newChildren.add(nodes[child]);
            }
            else {
                nodes[child] = new ImmutableNode<Integer>(values[child], nodes[parent], tImmutableNode -> childrenConstructor(child));
                newChildren.add(nodes[child]);
            }
        }
        return newChildren;
    }

}