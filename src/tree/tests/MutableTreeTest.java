package tree.tests;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tree.AbstractTree;
import tree.MutableNode;
import tree.MutableTree;
import tree.Node;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class MutableTreeTest {

    MutableTree<Integer>[] testTrees;

    @BeforeEach
    void setUp() throws IOException {
        testTrees = new MutableTree[5];
        for (int i = 1; i <= 5; i++) {
            testTrees[i - 1] = buildMutableTree(i);
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
    void removeSubtree() {
        var subtree = ((ArrayList<Node<Integer>>)testTrees[0].getRoot().getChildren()).get(2);
        testTrees[0].removeSubtree(subtree);
        assert (testTrees[0].getSize() == 3);
        assert(testTrees[0].getSum().equals(Integer.valueOf(3)));

        subtree = ((ArrayList<Node<Integer>>)testTrees[1].getRoot().getChildren()).get(0);
        subtree = ((ArrayList<Node<Integer>>)subtree.getChildren()).get(0);
        testTrees[1].removeSubtree(subtree);
        assert (testTrees[1].getSize() == 3);
        assert(testTrees[1].getSum().equals(Integer.valueOf(-3)));

        subtree = ((ArrayList<Node<Integer>>)testTrees[3].getRoot().getChildren()).get(1);
        testTrees[3].removeSubtree(subtree);
        assert (testTrees[3].getSize() == 4);
        assert(testTrees[3].getSum().equals(Integer.valueOf(220)));
    }

    @Test
    void maximize() {
        int[] actual = new int[] {8, 2, 5, 243, 10};
        for (int i = 0; i < 5; i++) {
            testTrees[i] = (MutableTree)testTrees[i].maximize();
            //System.out.printf(testTrees[i].getSum().toString() + " ");
            assert (testTrees[i].getSum().equals(actual[i]));
        }
    }

    private MutableTree<Integer> buildMutableTree(int test) throws IOException {
        FileReader fr;
        fr = new FileReader(System.getProperty("user.dir") + "/res/" + test + ".txt");

        Scanner in = new Scanner(fr);
        int sizeOfTree = in.nextInt();
        MutableNode[] nodes = new MutableNode[sizeOfTree];
        for (int i = 0; i < sizeOfTree; i++) {
            nodes[i] = new MutableNode<>(in.nextInt());
        }

        for (int i = 0; i < sizeOfTree; i++) {
            readChildren(i, nodes, in);
        }

        MutableTree<Integer> tree = new MutableTree<>(nodes[0], Integer::sum, Comparator.naturalOrder(), 0);

        fr.close();
        return tree;
    }

    private void readChildren(int parent, MutableNode<Integer>[] nodes, Scanner in) {
        int amountOfChildren = in.nextInt();
        for (int i = 0; i < amountOfChildren; i++) {
            int childNumber = in.nextInt();
            nodes[parent].addChild(nodes[childNumber]);
        }
    }


}