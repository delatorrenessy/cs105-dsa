// A self-contained AVL Tree implementation with zero imports
class AVLTree {

    // Internal node structure
    static class Node {
        int key;
        int height;
        Node left;
        Node right;

        Node(int d) {
            key = d;
            height = 1; // New nodes are initially added at leaf level (height 1)
        }
    }

    private Node root;

    // Utility function to get the height of a node safely
    private int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    // Utility function to get the max of two integers
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Utility function to calculate the balance factor of a node
    private int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    // 1. Right Rotation (Used for Left-Left / LL Case)
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new local root
        return x;
    }

    // 2. Left Rotation (Used for Right-Right / RR Case)
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new local root
        return y;
    }

    // Public wrapper for insertion
    public void insert(int key) {
        root = insertNode(root, key);
    }

    // Recursive function to insert a key and rebalance the tree
    private Node insertNode(Node node, int key) {
        // Step 1: Perform standard BST insertion
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insertNode(node.left, key);
        } else if (key > node.key) {
            node.right = insertNode(node.right, key);
        } else {
            return node; // Duplicate keys are not allowed in this implementation
        }

        // Step 2: Update height of this ancestor node
        node.height = 1 + max(height(node.left), height(node.right));

        // Step 3: Get the balance factor to check if it became unbalanced
        int balance = getBalance(node);

        // --- Rebalancing Logic (The 4 Rotation Types) ---

        // Case A: Left-Left (LL) Case -> Requires Single Right Rotation
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // Case B: Right-Right (RR) Case -> Requires Single Left Rotation
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // Case C: Left-Right (LR) Case -> Requires Double Rotation (Left then Right)
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Case D: Right-Left (RL) Case -> Requires Double Rotation (Right then Left)
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // Return the (unchanged) node pointer
    }

    // Simple utility to print the tree in-order to check correctness
    public void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.key + " (H:" + node.height + ") ");
            printInOrder(node.right);
        }
    }

    public Node getRoot() {
        return root;
    }

    // Main method to test out practice arrays
        // Main method to test out an 8-node progressive rotation set
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        
        // This 8-node set triggers 3 progressive rotations as it builds
        int[] practiceSet = {40, 20, 10, 25, 30, 22, 21, 23}; 
        
        System.out.println("Inserting nodes sequentially:");
        for (int num : practiceSet) {
            System.out.println("Inserting: " + num);
            tree.insert(num);
        }

        System.out.println("\nFinal In-order traversal of the balanced 8-node AVL tree:");
        System.out.println("(Format: Value (H: Height of node))");
        tree.printInOrder(tree.getRoot());
        System.out.println();
    }

}
