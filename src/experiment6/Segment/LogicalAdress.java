package experiment6.Segment;

public class LogicalAdress {
    private int SegmentNumber;
    private int SegmentOffset;

    public LogicalAdress(int SegmentNumber, int SegmentOffset) {
        this.SegmentNumber = SegmentNumber;
        this.SegmentOffset = SegmentOffset;
    }

    public int getSegmentNumber() {
        return SegmentNumber;
    }

    public int getSegmentOffset() {
        return SegmentOffset;
    }
}
