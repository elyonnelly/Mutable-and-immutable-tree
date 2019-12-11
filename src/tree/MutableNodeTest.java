package tree;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class MutableNodeTest {

    @Test
    void setChildren() {
        MutableNode<Integer> node = new MutableNode<Integer>();

        Collection<MutableNode<Integer>> newChildren = null;
        boolean throwed = false;
        try {
            node.setChildren(newChildren);
        }
        catch (IllegalArgumentException ex) {
            throwed = true;
        }

        Assert.assertEquals(true, throwed);

        newChildren = new ArrayList<>();
        node.setChildren(newChildren);
        Assert.assertEquals(0, node.getChildren().size());

        for (int i = 0; i < 5; i++) {
            newChildren.add(new MutableNode<>());
        }
        node.setChildren(newChildren);



        for (var child : newChildren) {
            Assert.assertEquals(node, child.getParent());
        }

    }

    @Test
    void addChild() {
    }

    @Test
    void removeChild() {
    }
}