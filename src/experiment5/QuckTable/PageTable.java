package experiment5.QuckTable;

/**
 * @Author scinloop
 * @Date 2023/12/12 21:50
 */
public class PageTable {
    private int Pagenum;//页号
    private int PageFrameNum;//块号

    public PageTable(int pagenum, int pageFrameNum) {
        Pagenum = pagenum;
        PageFrameNum = pageFrameNum;
    }
    public int getPagenum() {
        return Pagenum;
    }
    public int getPageFrameNum() {
        return PageFrameNum;
    }

    public void setPagenum(int pagenum) {
        Pagenum = pagenum;
    }


}
