package experiment4.quickfit;

/**
 * @Author scinloop
 * @Date 2023/12/19 11:28
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class QuickFitMemoryAllocator {
    private HashMap<Integer, LinkedList<Integer>> freeBlocks;
    private final int[] blockSizes;

    public QuickFitMemoryAllocator(int[] sizes) {
        this.blockSizes = sizes;
        this.freeBlocks = new HashMap<>();
        for (int size : sizes) {
            freeBlocks.put(size, new LinkedList<>());
        }
    }

    // 分配内存
    public Integer allocate(int size) {
        // 检查是否有预定义大小匹配
        for (int blockSize : blockSizes) {
            if (size <= blockSize && !freeBlocks.get(blockSize).isEmpty()) {
                return freeBlocks.get(blockSize).removeFirst();
            }
        }
        // 没有找到合适的预定义块，返回null或执行其他内存分配逻辑
        return null;
    }

    // 释放内存
    public void free(int size, int address) {
        // 检查是否符合预定义的大小
        for (int blockSize : blockSizes) {
            if (size <= blockSize) {
                freeBlocks.get(blockSize).add(address);
                return;
            }
        }
        // 如果大小不匹配，执行其他释放逻辑
    }

    // 打印当前空闲块状态（用于调试）
    public void printFreeBlocks() {
        for (int size : blockSizes) {
            System.out.println("Size " + size + ": " + freeBlocks.get(size));
        }
    }
}

