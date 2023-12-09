//package experiment4;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
//
//class MemoryBlock {
//    int startAddress;
//    int size;
//    int processId;
//
//    public MemoryBlock(int startAddress, int size, int processId) {
//        this.startAddress = startAddress;
//        this.size = size;
//        this.processId = processId;
//    }
//}
//
//class MemoryManager {
//    private List<MemoryBlock> memory;
//    private int lastAllocatedIndex; // for NF
//
//    public MemoryManager(int size) {
//        memory = new ArrayList<>();
//        memory.add(new MemoryBlock(0, size, -1));
//        lastAllocatedIndex = 0;
//    }
//
//    public void allocate(int processId, int size, String algorithm) {
//        switch (algorithm) {
//            case "FF":
//                firstFit(processId, size);
//                break;
//            case "NF":
//                nextFit(processId, size);
//                break;
//            case "BF":
//                bestFit(processId, size);
//                break;
//            case "WF":
//                worstFit(processId, size);
//                break;
//            default:
//                System.out.println("Invalid algorithm");
//        }
//    }
//
//    private void firstFit(int processId, int size) {
//        for (MemoryBlock block : memory) {
//            if (block.processId == -1 && block.size >= size) {
//                block.processId = processId;
//                if (block.size > size) {
//                    // Split the block if it's larger than the requested size
//                    MemoryBlock newBlock = new MemoryBlock(block.startAddress + size, block.size - size, -1);
//                    memory.add(memory.indexOf(block) + 1, newBlock);
//                    block.size = size;
//                }
//                printMemoryStatus();
//                return;
//            }
//        }
//        System.out.println("Allocation failed for process " + processId);
//    }
//
//    private void nextFit(int processId, int size) {
//        for (int i = lastAllocatedIndex; i < memory.size(); i++) {
//            MemoryBlock block = memory.get(i);
//            if (block.processId == -1 && block.size >= size) {
//                block.processId = processId;
//                lastAllocatedIndex = i;
//                if (block.size > size) {
//                    MemoryBlock newBlock = new MemoryBlock(block.startAddress + size, block.size - size, -1);
//                    memory.add(i + 1, newBlock);
//                    block.size = size;
//                }
//                printMemoryStatus();
//                return;
//            }
//        }
//        System.out.println("Allocation failed for process " + processId);
//    }
//
//    private void bestFit(int processId, int size) {
//        List<MemoryBlock> freeBlocks = getFreeBlocks();
//        Collections.sort(freeBlocks, Comparator.comparingInt(block -> block.size));
//
//        for (MemoryBlock block : freeBlocks) {
//            if (block.size >= size) {
//                block.processId = processId;
//                if (block.size > size) {
//                    MemoryBlock newBlock = new MemoryBlock(block.startAddress + size, block.size - size, -1);
//                    memory.add(memory.indexOf(block) + 1, newBlock);
//                    block.size = size;
//                }
//                printMemoryStatus();
//                return;
//            }
//        }
//        System.out.println("Allocation failed for process " + processId);
//    }
//
//    private void worstFit(int processId, int size) {
//        List<MemoryBlock> freeBlocks = getFreeBlocks();
//        Collections.sort(freeBlocks, Comparator.comparingInt(block -> block.size).reversed());
//
//        for (MemoryBlock block : freeBlocks) {
//            if (block.size >= size) {
//                block.processId = processId;
//                if (block.size > size) {
//                    MemoryBlock newBlock = new MemoryBlock(block.startAddress + size, block.size - size, -1);
//                    memory.add(memory.indexOf(block) + 1, newBlock);
//                    block.size = size;
//                }
//                printMemoryStatus();
//                return;
//            }
//        }
//        System.out.println("Allocation failed for process " + processId);
//    }
//
//    private List<MemoryBlock> getFreeBlocks() {
//        List<MemoryBlock> freeBlocks = new ArrayList<>();
//        for (MemoryBlock block : memory) {
//            if (block.processId == -1) {
//                freeBlocks.add(block);
//            }
//        }
//        return freeBlocks;
//    }
//
//    public void deallocate(int processId) {
//        for (MemoryBlock block : memory) {
//            if (block.processId == processId) {
//                block.processId = -1;
//                // Merge adjacent free blocks
//                mergeFreeBlocks();
//                printMemoryStatus();
//                return;
//            }
//        }
//        System.out.println("Deallocation failed for process " + processId);
//    }
//
//    private void mergeFreeBlocks() {
//        for (int i = 0; i < memory.size() - 1; i++) {
//            MemoryBlock currentBlock = memory.get(i);
//            MemoryBlock nextBlock = memory.get(i + 1);
//            if (currentBlock.processId == -1 && nextBlock.processId == -1) {
//                currentBlock.size += nextBlock.size;
//                memory.remove(i + 1);
//                i--; // Check the merged block with the previous one
//            }
//        }
//    }
//
//    public void printMemoryStatus() {
//        System.out.println("Memory Status:");
//        for (MemoryBlock block : memory) {
//            System.out.println("[" + block.startAddress + "-" + (block.startAddress + block.size - 1) +
//                    "] ProcessID: " + block.processId + " Size: " + block.size);
//        }
//        System.out.println();
//    }
//}
//
//public class MemoryAllocationSimulation {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter the size of memory: ");
//        int memorySize = scanner.nextInt();
//
//        MemoryManager memoryManager = new MemoryManager(memorySize);
//
//        while (true) {
//            System.out.println("1. Allocate Process");
//            System.out.println("2. Deallocate Process");
//            System.out.println("3. Print Memory Status");
//            System.out.println("4. Exit");
//            System.out.print("Enter your choice: ");
//            int choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    System.out.print("Enter Process ID: ");
//                    int processId = scanner.nextInt();
//                    System.out.print("Enter Process Size: ");
//                    int size = scanner.nextInt();
//                    System.out.print("Enter Allocation Algorithm (FF, NF, BF, WF): ");
//                    String algorithm = scanner.next();
//                    memoryManager.allocate(processId, size, algorithm);
//                    break;
//                case 2:
//                    System.out.print("Enter Process ID to deallocate: ");
//                    int deallocProcessId = scanner.nextInt();
//                    memoryManager.deallocate(deallocProcessId);
//                    break;
//                case 3:
//                    memoryManager.printMemoryStatus();
//                    break;
//                case 4:
//                    System.out.println("Exiting...");
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        }
//    }
//}
