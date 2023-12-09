package experiment6.Segment;

/**
 * @Author scinloop
 * @Date 2023/12/8 10:11
 */
public class SegmentTable {
    private int segmentNumber;
    private int segmentLength;
    private int segmentAddress;

    public SegmentTable(int segmentNumber, int segmentLength, int segmentAddress) {
        this.segmentNumber = segmentNumber;
        this.segmentLength = segmentLength;
        this.segmentAddress = segmentAddress;
    }

    void changeLogicToPhysical(int segmentNumber, int segmentOffset) {
        if (segmentNumber == this.segmentNumber) {
            if (segmentOffset <= this.segmentLength) {
                System.out.println("段号匹配,段内偏移量在段内,可以访问");
                System.out.println("物理地址为:" + (this.segmentAddress + segmentOffset));
            } else {
                System.out.println("段号匹配,段内偏移量超出段内,无法访问");
            }
        } else {
            System.out.println("段号不匹配");
        }
    }
}
