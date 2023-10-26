import java.util.ArrayList;
import java.util.Scanner;

class MemoryBlock {
    private int id;
    private int size;
    private boolean allocated;

    public MemoryBlock(int id, int size) {
        this.id = id;
        this.size = size;
        this.allocated = false;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void allocate() {
        allocated = true;
    }

    public void deallocate() {
        allocated = false;
    }

    @Override
    public String toString() {
        return "Block ID: " + id + ", Size: " + size + ", Allocated: " + (allocated ? "Yes" : "No");
    }
}

class MemoryManager {
    private ArrayList<MemoryBlock> memoryBlocks;
    private int nextFitIndex;

    public MemoryManager() {
        memoryBlocks = new ArrayList<>();
        nextFitIndex = 0;
    }

    public void addMemoryBlock(int id, int size) {
        MemoryBlock block = new MemoryBlock(id, size);
        memoryBlocks.add(block);
    }

    public void displayMemoryStatus() {
        System.out.println("Memory Status:");
        for (MemoryBlock block : memoryBlocks) {
            System.out.println(block);
        }
        System.out.println();
    }

    public void allocateMemoryBestFit(int processSize) {
        int bestFitBlockIndex = -1;
        int bestFitSize = Integer.MAX_VALUE;

        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);

            if (!block.isAllocated() && block.getSize() >= processSize) {
                if (block.getSize() < bestFitSize) {
                    bestFitBlockIndex = i;
                    bestFitSize = block.getSize();
                }
            }
        }

        if (bestFitBlockIndex != -1) {
            memoryBlocks.get(bestFitBlockIndex).allocate();
            System.out.println("Memory allocated using Best Fit for Process Size " + processSize + " to Block ID " + memoryBlocks.get(bestFitBlockIndex).getId());
        } else {
            System.out.println("Memory allocation using Best Fit failed for Process Size " + processSize);
        }
    }

    public void allocateMemoryNextFit(int processSize) {
        for (int i = 0; i < memoryBlocks.size(); i++) {
            int index = (nextFitIndex + i) % memoryBlocks.size();
            MemoryBlock block = memoryBlocks.get(index);

            if (!block.isAllocated() && block.getSize() >= processSize) {
                block.allocate();
                nextFitIndex = (index + 1) % memoryBlocks.size();
                System.out.println("Memory allocated using Next Fit for Process Size " + processSize + " to Block ID " + block.getId());
                return;
            }
        }
        System.out.println("Memory allocation using Next Fit failed for Process Size " + processSize);
    }

    public void deallocateMemory(int blockId) {
        for (MemoryBlock block : memoryBlocks) {
            if (block.getId() == blockId && block.isAllocated()) {
                block.deallocate();
                System.out.println("Block ID " + blockId + " deallocated.");
                return;
            }
        }
        System.out.println("Block ID " + blockId + " not found or already deallocated.");
    }
}

public class MemoryAllocationSystem {
    public static void main(String[] args) {
        MemoryManager memoryManager = new MemoryManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Memory Allocation System");
            System.out.println("1. Add Memory Block");
            System.out.println("2. Allocate Memory (Best Fit)");
            System.out.println("3. Allocate Memory (Next Fit)");
            System.out.println("4. Deallocate Memory");
            System.out.println("5. Display Memory Status");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Block ID: ");
                    int blockId = scanner.nextInt();
                    System.out.print("Enter Block Size: ");
                    int blockSize = scanner.nextInt();
                    memoryManager.addMemoryBlock(blockId, blockSize);
                    System.out.println("Memory Block added.");
                    break;

                case 2:
                    System.out.print("Enter Process Size for Best Fit Allocation: ");
                    int processSize1 = scanner.nextInt();
                    memoryManager.allocateMemoryBestFit(processSize1);
                    break;

                case 3:
                    System.out.print("Enter Process Size for Next Fit Allocation: ");
                    int processSize2 = scanner.nextInt();
                    memoryManager.allocateMemoryNextFit(processSize2);
                    break;

                case 4:
                    System.out.print("Enter Block ID to deallocate: ");
                    int deallocateId = scanner.nextInt();
                    memoryManager.deallocateMemory(deallocateId);
                    break;

                case 5:
                    memoryManager.displayMemoryStatus();
                    break;

                case 6:
                    System.out.println("Exiting Memory Allocation System.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
