// A self-contained Binary Heap implementation with zero imports
class BinaryHeap {
    private int[] heap;
    private int size;
    private int capacity;
    private boolean isMaxHeap; // True for Max-Heap, False for Min-Heap

    public BinaryHeap(int capacity, boolean isMaxHeap) {
        this.capacity = capacity;
        this.isMaxHeap = isMaxHeap;
        this.heap = new int[capacity];
        this.size = 0;
    }

    // Helper utilities to get parent and child indices
    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return (2 * i) + 1; }
    private int rightChild(int i) { return (2 * i) + 2; }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Comparison logic based on heap type
    private boolean compare(int childValue, int parentValue) {
        if (isMaxHeap) {
            return childValue > parentValue; // Max-Heap bubbles up larger values
        } else {
            return childValue < parentValue; // Min-Heap bubbles up smaller values
        }
    }

    // Inserts a element into the heap
    public void insert(int key) {
        if (size == capacity) {
            System.out.println("Heap Overflow!");
            return;
        }

        // Insert the new key at the end of the array
        int i = size;
        heap[i] = key;
        size++;

        // Fix the heap property by bubbling up (Heapify-Up)
        while (i != 0 && compare(heap[i], heap[parent(i)])) {
            System.out.println("  ↳ [SWAP] Bubbling up " + heap[i] + " with parent " + heap[parent(i)]);
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // Prints the heap level by level (Level-Order Array layout representation)
    public void printLevelOrder() {
        if (size == 0) {
            System.out.println("Heap is empty.");
            return;
        }

        int currentLevelCount = 1;
        int printedInLevel = 0;

        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
            printedInLevel++;

            if (printedInLevel == currentLevelCount) {
                System.out.println(); // Break line for next tree level depth
                currentLevelCount *= 2;
                printedInLevel = 0;
            }
        }
        if (printedInLevel > 0) {
            System.out.println(); // Final line break if the last level is partial
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int[] practiceSet = {40, 20, 10, 25, 30, 22, 21, 23};

        System.out.println("=== TESTING MAX-HEAP ===");
        BinaryHeap maxHeap = new BinaryHeap(20, true);
        for (int num : practiceSet) {
            System.out.println("Inserting: " + num);
            maxHeap.insert(num);
        }
        System.out.println("\nFinal Level-Order layout of Max-Heap array structure:");
        maxHeap.printLevelOrder();

        System.out.println("\n=== TESTING MIN-HEAP ===");
        BinaryHeap minHeap = new BinaryHeap(20, false);
        for (int num : practiceSet) {
            System.out.println("Inserting: " + num);
            minHeap.insert(num);
        }
        System.out.println("\nFinal Level-Order layout of Min-Heap array structure:");
        minHeap.printLevelOrder();
    }
}
