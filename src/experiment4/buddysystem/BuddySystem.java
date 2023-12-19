package experiment4.buddysystem;

public class BuddySystem {
    public static void main(String[] args) {
        BuddySystemAllocator allocator = new BuddySystemAllocator(1024);

        // 分配和释放内存的示例
//        Integer address1 = allocator.allocate(128);
        Integer address1 = allocator.allocate(10);
        System.out.println("Allocated 10 bytes at: " + address1);
        allocator.printFreeBlocks();

//        Integer address2 = allocator.allocate(256);
//        System.out.println("Allocated 256 bytes at: " + address2);
//        allocator.printFreeBlocks();

//        allocator.free(address1, 128);
//        System.out.println("Freed 128 bytes at: " + address1);
//        allocator.printFreeBlocks();
    }
}
