package experiment4.hash;

/**
 * @Author scinloop
 * @Date 2023/12/19 13:56
 */
import java.util.HashMap;
import java.util.LinkedList;

public class HashBasedMemoryAllocator {
    private HashMap<Integer, LinkedList<Integer>> freeBlocks;

    public HashBasedMemoryAllocator() {
        this.freeBlocks = new HashMap<>();
    }

    // 分配内存
    public Integer allocate(int size) {
        // 查找合适大小的块
        if (freeBlocks.containsKey(size) && !freeBlocks.get(size).isEmpty()) {
            return freeBlocks.get(size).removeFirst();
        }
        // 没有合适的块，可以选择分割更大的块或返回null
        return null;
    }

    // 释放内存
    public void free(int size, int address) {
        freeBlocks.computeIfAbsent(size, k -> new LinkedList<>()).add(address);
    }

    // 添加一个空闲块
    public void addFreeBlock(int size, int address) {
        freeBlocks.computeIfAbsent(size, k -> new LinkedList<>()).add(address);
    }

    // 打印当前空闲块状态（用于调试）
    public void printFreeBlocks() {
        for (Integer size : freeBlocks.keySet()) {
            System.out.println("Block size " + size + ": " + freeBlocks.get(size));
        }
    }
}

