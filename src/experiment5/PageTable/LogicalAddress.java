package experiment5.PageTable;

/**
 * @Author scinloop
 * @Date 2023/12/12 21:44
 */
public class LogicalAddress {
    private int Pagenum;
    private int Offset;

    public LogicalAddress(int pagenum, int offset) {
        Pagenum = pagenum;
        Offset = offset;
    }

    public int getPagenum() {
        return Pagenum;
    }

    public void setPagenum(int pagenum) {
        Pagenum = pagenum;
    }

    public int getOffset() {
        return Offset;
    }
}
