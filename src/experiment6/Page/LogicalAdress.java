package experiment6.Page;

public class LogicalAdress {
    private int SegmentNumber;
    private int PageNumber;
    private int PageOffset;

    public LogicalAdress(int SegmentNumber, int PageNumber, int PageOffset) {
        this.SegmentNumber = SegmentNumber;
        this.PageNumber = PageNumber;
        this.PageOffset = PageOffset;
    }
    public int getSegmentNumber(){
        return SegmentNumber;
    }


    public int getPageNumber() {
        return PageNumber;
    }

    public int getPageOffset() {
        return PageOffset;
    }
}
