package experiment5;

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

//    将物理地址转变为物理地址

    public void changeLogicalAdressToPhycialAdress(int PageNum,int offset) {
        System.out.println("逻辑地址为："+PageNum+" "+offset);
        System.out.println("物理地址为："+PageFrameNum+" "+offset);
    }
}
