// 1. Definition of a single Binary Tree Node
class Node {
    int data;
    Node left;
    Node right;

    public Node(int value) {
        data = value;
        left = null;
        right = null;
    }
}

// 2. Binary Tree class containing traversal algorithms
class BinaryTree {
    Node root;

    // Preorder Traversal (Root -> Left -> Right)
    void printPreorder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " "); // Visit Root
        printPreorder(node.left);          // Traverse Left
        printPreorder(node.right);         // Traverse Right
    }

    // Inorder Traversal (Left -> Root -> Right)
    void printInorder(Node node) {
        if (node == null) {
            return;
        }
        printInorder(node.left);           // Traverse Left
        System.out.print(node.data + " "); // Visit Root
        printInorder(node.right);          // Traverse Right
    }

    // Postorder Traversal (Left -> Right -> Root)
    void printPostorder(Node node) {
        if (node == null) {
            return;
        }
        printPostorder(node.left);          // Traverse Left
        printPostorder(node.right);         // Traverse Right
        System.out.print(node.data + " ");  // Visit Root
    }
}

// 3. Driver Code to execute and test the traversals
public class Main {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        /* Constructing a simple fundamental tree:
                 1
                / \
               2   3
              / \
             4   5
        */
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        System.out.print("Preorder Traversal: ");
        tree.printPreorder(tree.root);
        System.out.println();

        System.out.print("Inorder Traversal: ");
        tree.printInorder(tree.root);
        System.out.println();

        System.out.print("Postorder Traversal: ");
        tree.printPostorder(tree.root);
        System.out.println();
    }
}
