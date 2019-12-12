package tree.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tree.AbstractTree;
import tree.MutableNode;
import tree.MutableTree;

import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

class MutableTreeTest {

    @BeforeEach
    void setUp() {
        //TODO
    }

    @Test
    void removeSubtree() {
        //TODO
    }

    @Test
    void maximize() {
        //TODO
    }

    private AbstractTree<Integer> buildMutableTree(int test) throws IOException {
        FileReader fr;
        fr = new FileReader(Integer.toString(test));

        Scanner in = new Scanner(fr);
        int sizeOfTree = in.nextInt();
        MutableNode[] nodes = new MutableNode[sizeOfTree];
        for (int i = 0; i < sizeOfTree; i++) {
            nodes[i] = new MutableNode<>(in.nextInt());
        }

        for (int i = 0; i < sizeOfTree; i++) {
            readChildren(i, nodes, in);
        }

        AbstractTree<Integer> tree = new MutableTree<>(nodes[0], Integer::sum, Comparator.naturalOrder(), 0);

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