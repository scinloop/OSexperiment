package experiment5.PageTable;

/**
 * @Author scinloop
 * @Date 2023/12/12 21:49
 */
public class PageController {
    private int SegmentTableBaseAddress;
    private int SegmentTableLength;

    public PageController(int SegmentTableBaseAddress, int SegmentTableLength) {
        this.SegmentTableBaseAddress = SegmentTableBaseAddress;
        this.SegmentTableLength = SegmentTableLength;
    }

    public int getSegmentTableBaseAddress() {
        return SegmentTableBaseAddress;
    }
    public int getSegmentTableLength() {
        return SegmentTableLength;
    }

    Boolean isPageExist(int segmentNumber) {
        return segmentNumber < SegmentTableLength;
    }
}
