package experiment4.buddysystem;

/**
 * @Author scinloop
 * @Date 2023/12/19 13:22
 */
import java.util.HashMap;
import java.util.LinkedList;

public class BuddySystemAllocator {
    private final int maxBlockSize;
    private HashMap<Integer, LinkedList<Integer>> freeBlocks;

    public BuddySystemAllocator(int maxBlockSize) {
        this.maxBlockSize = maxBlockSize;
        this.freeBlocks = new HashMap<>();

        // 初始化时，整个内存块是空闲的
        LinkedList<Integer> initialList = new LinkedList<>();
        initialList.add(0); // 假设起始地址为 0
        this.freeBlocks.put(maxBlockSize, initialList);
    }

    // 分配内存
    public Integer allocate(int size) {
        int blockSize = this.maxBlockSize;

        // 找到合适大小的块
        while (blockSize >= size) {
            if (this.freeBlocks.containsKey(blockSize) && !this.freeBlocks.get(blockSize).isEmpty()) {
                int blockStart = this.freeBlocks.get(blockSize).removeFirst();

                // 如果块太大，则进行分割
                while (blockSize > size) {
                    blockSize /= 2;
                    int buddyAddress = blockStart + blockSize;
                    this.freeBlocks.computeIfAbsent(blockSize, k -> new LinkedList<>()).add(buddyAddress);
                }

                return blockStart;
            }

            blockSize /= 2;
        }

        return null; // 没有足够的空间
    }

    // 释放内存
    public void free(int address, int size) {
        int blockSize = size;

        while (blockSize <= this.maxBlockSize) {
            int buddyAddress = address ^ blockSize;
            LinkedList<Integer> blockList = this.freeBlocks.get(blockSize);

            if (blockList != null && blockList.remove((Integer) buddyAddress)) {
                address = Math.min(address, buddyAddress);
                blockSize *= 2;
            } else {
                this.freeBlocks.computeIfAbsent(blockSize, k -> new LinkedList<>()).add(address);
                break;
            }
        }
    }

    // 打印当前空闲块状态（用于调试）
    public void printFreeBlocks() {
        for (int blockSize : freeBlocks.keySet()) {
            System.out.println("Block size " + blockSize + ": " + freeBlocks.get(blockSize));
        }
    }
}

