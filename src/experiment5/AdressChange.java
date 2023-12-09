package experiment5;

import java.util.HashMap;
import java.util.Map;

class LogicalAdress {
    private int pagenum;
    private int pageinadress;
    public LogicalAdress(int pagenumber,int pageinadressin) {
        this.pagenum = pagenumber;
        this.pageinadress = pageinadressin;
    }

    public int getPagenum() {
        return pagenum;
    }

    // 比较页号和页面大小
    public boolean isPageSizeValid(PageMemory pageMemory) {
        int pageSize = pageMemory.getPagesize();
        return pagenum >= 0 && pagenum < pageSize;
    }
}

class PageMemory {
    private int pagetableinitadress;
    private int pagesize;
    public PageMemory(int ptia,int ps){
        this.pagetableinitadress=ptia;
        this.pagesize=ps;
    }
    public int getPagesize() {
        return pagesize;
    }


}
class PageTable {
    private Map<Integer, Integer> pageToBlockMap;

    public PageTable() {
        this.pageToBlockMap = new HashMap<>();
    }

    // 添加从页号到块号的映射
    public void addMapping(int pageNumber, int blockNumber) {
        pageToBlockMap.put(pageNumber, blockNumber);
    }

    // 获取给定页号对应的块号
    public int getBlockNumber(int pageNumber) {
        return pageToBlockMap.getOrDefault(pageNumber, -1); // -1 表示无效映射
    }
}



public class AdressChange {
    public static void main(String[] args){
        int ptia=0;
        int ps=10;
        int pagenumber = 32;
        int pageinadressin = 2;
        PageMemory PM = new PageMemory(ptia,ps);
        LogicalAdress LA =new LogicalAdress(pagenumber,pageinadressin);
        PageTable PT = new PageTable();
        for(int i=0;i<10;i++){
            PT.addMapping(i,i+5);
        }
        if(LA.isPageSizeValid(PM)){
            System.out.println("页号是："+pagenumber+"  "+"块号是："+PT.getBlockNumber(ptia+pagenumber*pageinadressin));
        }
        else {
            System.out.println("错误");
        }
    }
}