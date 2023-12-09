package experiment6.Page;

/**
 * @Author scinloop
 * @Date 2023/12/8 10:11
 */
public class SegmentTable {
    private int segmentNumber;
    private int PageSize;
    private int PageTableBaseAddress;

    public SegmentTable(int segmentNumber, int PageSize, int PageTableBaseAddress) {
        this.segmentNumber = segmentNumber;
        this.PageSize = PageSize;
        this.PageTableBaseAddress = PageTableBaseAddress;
    }

    public void changeLogicToPhysical(int SegmentNumber, int PageNumber, int PageOffset,PageTable pageTable) {
        if (SegmentNumber == this.segmentNumber) {
            if (PageNumber <= this.PageSize) {
                System.out.println("段号匹配,页号在页表内,可以访问");
                System.out.println("物理地址为:" + (this.PageTableBaseAddress + pageTable.getPageAddress() + PageOffset));
            } else {
                System.out.println("段号匹配,页号超出页表内,无法访问");
            }
        } else {
            System.out.println("段号不匹配");
        }
    }

    public int getSegmentNumber() {
        return segmentNumber;
    }
}
