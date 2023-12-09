package experiment6.Page;

/**
 * @Author scinloop
 * @Date 2023/12/8 11:46
 */
public class PageTable {
    private int PageNumber;
    private int PageAddress;

    public PageTable(int PageNumber, int PageAddress){
        this.PageNumber = PageNumber;
        this.PageAddress = PageAddress;
    }

    public int getPageAddress() {
        return PageAddress;
    }
}
