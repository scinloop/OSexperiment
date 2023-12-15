package experiment5;

/**
 * @Author scinloop
 * @Date 2023/12/12 21:53
 */
public class AdressChange {
    public static void main(String[] args) {

        LogicalAddress logicalAddress = new LogicalAddress(3, 1024);

        PageController pageController = new PageController(0, 6);

        PageTable[] pageTables = new PageTable[6];
        pageTables[0] = new PageTable(0, 1);
        pageTables[1] = new PageTable(1, 2);
        pageTables[2] = new PageTable(2, 3);
        pageTables[3] = new PageTable(3, 4);
        pageTables[4] = new PageTable(4, 5);
        pageTables[5] = new PageTable(5, 6);

        if(pageController.isSegmentExist(logicalAddress.getPagenum())) {

            System.out.println("段存在");
            System.out.println("逻辑地址：" +logicalAddress.getPagenum() + " " + logicalAddress.getOffset());
            System.out.println("物理地址：" + pageTables[logicalAddress.getPagenum()].getPageFrameNum() + " " + logicalAddress.getOffset());
        } else {
            System.out.println("段不存在");
        }


    }

}
