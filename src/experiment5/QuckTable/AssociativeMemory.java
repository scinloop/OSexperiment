package experiment5.QuckTable;

/**
 * @Author scinloop
 * @Date 2023/12/15 11:08
 */
public class AssociativeMemory {
    private int pagenum;
    private int pageFrameNum;

    public AssociativeMemory(int pagenum, int pageFrameNum) {
        this.pagenum = pagenum;
        this.pageFrameNum = pageFrameNum;
    }
    public int getPagenum() {
        return pagenum;
    }
    public int getPageFrameNum() {
        return pageFrameNum;
    }
    Boolean isPageExist(int pagenum) {
        return pagenum == this.pagenum;
    }
}
