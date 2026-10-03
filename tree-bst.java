import java.util.LinkedList;
import java.util.Queue;

// 1. Definition of a single Node
class Node {
    int data;
    Node left, right;

    public Node(int value) {
        data = value;
        left = right = null;
    }
}

// 2. BST Class with basic Insert and Level Display
class BinarySearchTree {
    Node root;

    // Fundamental Insertion
    void insert(int data) {
        root = insertRec(root, data);
    }

    Node insertRec(Node root, int data) {
        if (root == null) {
            return new Node(data);
        }
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    // Basic Level-by-Level Display using a null marker
    void displayLevelByLevel() {
        if (root == null) return;

        Queue<Node> queue = new LinkedList<>();
        
        // Step 1: Add the root and the first level marker (null)
        queue.add(root);
        queue.add(null); 

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Step 2: If we hit a null marker, the current level is finished
            if (current == null) {
                System.out.println(); // Jump to the next line

                // If the queue isn't empty, add a marker for the next level
                if (!queue.isEmpty()) {
                    queue.add(null);
                }
            } 
            // Step 3: Otherwise, print data and enqueue existing children
            else {
                System.out.print(current.data + " ");
                
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
        }
    }
}

// 3. Main Execution
public class Main {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        // Build a basic tree
        bst.insert(45);
        bst.insert(25);
        bst.insert(65);
        bst.insert(15);
        bst.insert(35);
        bst.insert(85);

        System.out.println("BST Level-Order:");
        bst.displayLevelByLevel();
    }
}
