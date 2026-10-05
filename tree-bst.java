class Node {
    int data;
    Node left, right;

    public Node(int item) {
        data = item;
        left = right = null;
    }
}

public class Main {

    // 1. Calculate the total height of the tree
    static int getHeight(Node root) {
        if (root == null) return 0;
        
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        
        return (leftHeight > rightHeight) ? (leftHeight + 1) : (rightHeight + 1);
    }

    // 2. Print all nodes at a specific level (1-indexed)
    static void printLevel(Node root, int level) {
        if (root == null) return;
        
        if (level == 1) {
            System.out.print(root.data + " ");
        } else if (level > 1) {
            printLevel(root.left, level - 1);
            printLevel(root.right, level - 1);
        }
    }

    // 3. Loop through each level from top to bottom
    static void levelOrder(Node root) {
        int height = getHeight(root);
        for (int i = 1; i <= height; i++) {
            printLevel(root, i);
            System.out.println(); 
        }
    }

    public static void main(String[] args) {
        /* Creating sample tree:
                 4
               /   \
              2     5
             / \
            1   3
        */
        Node root = new Node(4);
        root.left = new Node(2);
        root.right = new Node(5);
        root.left.left = new Node(1);
        root.left.right = new Node(3);

        System.out.println("Recursive level-order traversal:");
        levelOrder(root); 
        // Output: 4 
        // 2 5
        // 1 3
    }
}
