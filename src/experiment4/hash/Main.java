package experiment4.hash;

public class Main {
    public static void main(String[] args) {
        HashBasedMemoryAllocator allocator = new HashBasedMemoryAllocator();

        // 初始化一些空闲块
        allocator.addFreeBlock(64, 0x1000);
        allocator.addFreeBlock(128, 0x2000);

        // 分配内存
        Integer address = allocator.allocate(64);
        System.out.println("Allocated 64 bytes at: " + Integer.toHexString(address));

        // 打印当前空闲块状态
        allocator.printFreeBlocks();
    }
}
