package tree.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tree.MutableNode;
import tree.Node;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MutableNodeTest {

MutableNode<Integer> node;

    @BeforeEach
    void setUp() {
        node = new MutableNode<Integer>(5);
        MutableNode<Integer> parent = new MutableNode<>(-1);
        Collection<Node<Integer>> children = new ArrayList<>();
        children.add(new MutableNode<>(4));
        children.add(new MutableNode<>(-1));
        children.add(new MutableNode<>(1));
        children.add(new MutableNode<>(0));
        node = new MutableNode<Integer>(5, parent, children);
    }


    @Test
    void setChildren() {

        assertThrows(IllegalArgumentException.class, () -> node.setChildren(null));

        Collection<MutableNode<Integer>> newChildren = new ArrayList<>();
        node.setChildren(newChildren);
        assertEquals(0, node.getChildren().size());

        for (int i = 0; i < 5; i++) {
            newChildren.add(new MutableNode<>(-1));
        }
        node.setChildren(newChildren);

        //equals осуществляет поэлементное сравнение
        assertEquals(true, newChildren.equals(node.getChildren()));

        for (var child : newChildren) {
            assertEquals(node, child.getParent());
        }

    }

    @Test
    void addChild() {
        ArrayList<Node<Integer>> oldChildren = new ArrayList<Node<Integer>>(node.getChildren());
        MutableNode<Integer> child = new MutableNode<Integer>(42);

        node.addChild(child);
        oldChildren.add(child);

        assertEquals(node, child.getParent());
        assertTrue(oldChildren.equals(node.getChildren()));
        assertThrows(IllegalArgumentException.class, () -> node.addChild(null));
        assertThrows(IllegalArgumentException.class, () -> node.addChild(child));
    }

    @Test
    void removeChild() {
        MutableNode<Integer> incorrectChild = new MutableNode<>(123);
        assertThrows(IllegalArgumentException.class, () -> node.removeChild(null));
        assertThrows(IllegalArgumentException.class, () -> node.removeChild(incorrectChild));

        ArrayList<Node<Integer>> oldChildren = new ArrayList<>(node.getChildren());

        MutableNode<Integer> child = (MutableNode<Integer>)oldChildren.get(0);

        node.removeChild(child);
        oldChildren.remove(child);

        assertTrue(child.getParent() == null);
        assertTrue(oldChildren.equals(node.getChildren()));
    }
}