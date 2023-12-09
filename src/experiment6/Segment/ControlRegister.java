package experiment6.Segment;

public class    ControlRegister {
    private int SegmentTableBaseAddress;
    private int SegmentTableLength;

    public ControlRegister(int SegmentTableBaseAddress, int SegmentTableLength) {
        this.SegmentTableBaseAddress = SegmentTableBaseAddress;
        this.SegmentTableLength = SegmentTableLength;
    }

    public int getSegmentTableBaseAddress() {
        return SegmentTableBaseAddress;
    }

    public int getSegmentTableLength() {
        return SegmentTableLength;
    }

    Boolean compare(int SegmentTableLength, int SegmentNumber) {
        if (SegmentTableLength <= SegmentNumber) {
            System.out.println("段号超出段表长度,无法访问");
            return false;
        } else {
            System.out.println("段号在段表长度内,可以访问");
            return true;
        }
    }
}
