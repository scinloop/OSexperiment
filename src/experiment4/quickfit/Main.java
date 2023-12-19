package experiment4.quickfit;

public class Main {
    public static void main(String[] args) {
        // 初始化快速适应内存分配器，预定义一些块大小
        QuickFitMemoryAllocator allocator = new QuickFitMemoryAllocator(new int[]{64, 128, 256});

        // 假设有一些空闲块
        allocator.free(64, 0x1000);
        allocator.free(128, 0x2000);
        allocator.free(256, 0x3000);

        // 模拟内存分配
        Integer address = allocator.allocate(256);
        System.out.println("Allocated at: " +"0x" + Integer.toHexString(address));

        // 打印当前空闲块状态
        allocator.printFreeBlocks();
    }
}
