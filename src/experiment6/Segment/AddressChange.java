package experiment6.Segment;

public class AddressChange {

    public static void main(String[] args) {
        LogicalAdress logicalAdress = new LogicalAdress(1, 100);

        SegmentTable[] segmentTableArray = new SegmentTable[4]; // You can change the size as needed

        // Initialize the SegmentTable objects in the array
        segmentTableArray[0] = new SegmentTable(0, 1024, 6144);
        segmentTableArray[1] = new SegmentTable(1, 600, 4096);
        segmentTableArray[2] = new SegmentTable(2, 500, 8192);
        segmentTableArray[3] = new SegmentTable(3, 200, 9200);

        ControlRegister controlRegister = new ControlRegister  (0, 4);

        if(controlRegister.compare(controlRegister.getSegmentTableLength(), logicalAdress.getSegmentNumber())){
            segmentTableArray[logicalAdress.getSegmentNumber()].changeLogicToPhysical(logicalAdress.getSegmentNumber(), logicalAdress.getSegmentOffset());
        }
        else{
            System.out.println("段号超出段表长度,无法访问");
        }
    }

}
