package experiment6.Page;

public class AddressChange {

    public static void main(String[] args) {
        LogicalAdress logicalAdress = new LogicalAdress(2, 3,100);

        SegmentTable[] segmentTableArray = new SegmentTable[4]; // You can change the size as needed
        segmentTableArray[0] = new SegmentTable(0, 1024, 6144);
        segmentTableArray[1] = new SegmentTable(1, 600, 4096);
        segmentTableArray[2] = new SegmentTable(2, 500, 8192);
        segmentTableArray[3] = new SegmentTable(3, 400, 9200);

        ControlRegister controlRegister = new ControlRegister(0, 4);



        PageTable[] pageTableArray = new PageTable[4]; // You can change the size as needed
        pageTableArray[0] = new PageTable(0, 0);
        pageTableArray[1] = new PageTable(1, 1024);
        pageTableArray[2] = new PageTable(2, 2048);
        pageTableArray[3] = new PageTable(3, 3072);
        if (controlRegister.compare(controlRegister.getSegmentTableLength(), logicalAdress.getSegmentNumber())) {
            segmentTableArray[logicalAdress.getSegmentNumber()].changeLogicToPhysical(segmentTableArray[logicalAdress.getSegmentNumber()]
                            .getSegmentNumber(), logicalAdress.getPageNumber(), logicalAdress.getPageOffset(),
                    pageTableArray[logicalAdress.getPageNumber()]);
        } else {
            System.out.println("段号超出段表长度,无法访问");
        }


    }

}
