package experiment5.QuckTable;

/**
 * @Author scinloop
 * @Date 2023/12/12 21:49
 */
public class PageController {
    private int PageTableBaseAddress;
    private int PageTableLength;

    public PageController(int PageTableBaseAddress, int PageTableLength) {
        this.PageTableBaseAddress = PageTableBaseAddress;
        this.PageTableLength = PageTableLength;
    }

    public int getSegmentTableBaseAddress() {
        return PageTableBaseAddress;
    }
    public int getSegmentTableLength() {
        return PageTableLength;
    }

    Boolean isPageExist(int PageNumber) {
        return PageNumber < PageTableLength;
    }
}
