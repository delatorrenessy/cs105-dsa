// A self-contained B-Tree (Order 3 / 2-3 Tree) implementation with zero imports
class BTree {

    // Internal node structure
    static class Node {
        int[] keys;     // Array of keys
        Node[] children; // Array of child pointers
        int numKeys;    // Current number of keys in node
        boolean isLeaf; // True if node has no children

        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            // Order 3 means max 2 keys, but we allocate size 3 to temporarily hold an extra key before splitting
            this.keys = new int[3];
            this.children = new Node[4];
            this.numKeys = 0;
        }
    }

    private Node root;
    private final int T = 2; // Minimum degree for Order 3 (Max keys = 2*T - 1 = 3-1 = 2)

    public BTree() {
        this.root = new Node(true);
    }

    // Public wrapper for insertion
    public void insert(int key) {
        Node r = root;

        // If root is full, the tree grows in height
        if (r.numKeys == 2) {
            System.out.println("  💥 [SPLIT] Root is full! Splitting root node...");
            Node s = new Node(false);
            root = s;
            s.children[0] = r;
            splitChild(s, 0, r);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    // Helper to insert into a node that is guaranteed not to be full
    private void insertNonFull(Node node, int key) {
        int i = node.numKeys - 1;

        if (node.isLeaf) {
            // Find location to insert new key and move greater keys ahead
            while (i >= 0 && node.keys[i] > key) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.numKeys++;
        } else {
            // Find the child that will get the new key
            while (i >= 0 && node.keys[i] > key) {
                i--;
            }
            i++;

            // If the target child is full, split it first
            if (node.children[i].numKeys == 2) {
                System.out.println("  💥 [SPLIT] Child node full! Splitting child under key boundary index " + i);
                splitChild(node, i, node.children[i]);
                if (node.keys[i] < key) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

       // Splits a full child node (2 keys) into two balanced halves around its internal values
    private void splitChild(Node parent, int i, Node child) {
        // Create a new node to hold the upper keys of the full child
        Node z = new Node(child.isLeaf);
        
        // In an Order 3 Tree split: 
        // child.keys[0] stays with 'child'
        // child.keys[1] moves up to the 'parent'
        // z starts empty, and the subsequent insertNonFull will populate the right side
        z.numKeys = 0; 

        // If it's an internal node, pass the rightmost child pointers over to z
        if (!child.isLeaf) {
            z.children[0] = child.children[2];
            child.children[2] = null; // Clean old pointer
        }

        // Extract the correct median key to push up before altering child's key count
        int medianKey = child.keys[1];
        child.numKeys = 1; // 'child' now only retains keys[0]

        // Shift parent children links to make space for the new child pointer z
        for (int j = parent.numKeys; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = z;

        // Shift parent keys up to make room for the incoming medianKey
        for (int j = parent.numKeys - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        
        // Place the median key into the parent and increment key count
        parent.keys[i] = medianKey;
        parent.numKeys++;
    }

    // Prints the B-Tree level by level using a zero-import queue array
    public void printLevelOrder() {
        if (root == null || root.numKeys == 0) {
            System.out.println("Tree is empty.");
            return;
        }

        Node[] queue = new Node[100];
        int head = 0;
        int tail = 0;

        queue[tail++] = root;

        while (head < tail) {
            int levelSize = tail - head;

            for (int i = 0; i < levelSize; i++) {
                Node current = queue[head++];

                // Format node keys cleanly as [key1, key2]
                System.out.print("[");
                for (int j = 0; j < current.numKeys; j++) {
                    System.out.print(current.keys[j]);
                    if (j < current.numKeys - 1) System.out.print(",");
                }
                System.out.print("] ");

                // Enqueue children links if they exist
                if (!current.isLeaf) {
                    for (int j = 0; j <= current.numKeys; j++) {
                        if (current.children[j] != null) {
                            queue[tail++] = current.children[j];
                        }
                    }
                }
            }
            System.out.println(); // Next line for next tree level depth
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BTree tree = new BTree();

        // High-activity practice sequence engineered to trigger multiple node splits
        int[] practiceSet = {10, 20, 30, 40, 50, 60, 70, 80};

        System.out.println("Inserting elements into B-Tree sequentially:");
        for (int num : practiceSet) {
            System.out.println("Inserting: " + num);
            tree.insert(num);
        }

        System.out.println("\nFinal Level-Order traversal of the balanced B-Tree:");
        System.out.println("(Each bracket [] wraps elements clustered together inside the same structural node)");
        tree.printLevelOrder();
    }
}
