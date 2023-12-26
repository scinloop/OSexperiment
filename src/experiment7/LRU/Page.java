package experiment7.LRU;

/**
 * @Author scinloop
 * @Date 2023/12/26 13:31
 */
public class Page {
    private int pageId;
    private int time;

    public Page(int pageId, int time) {
        this.pageId = pageId;
        this.time = time;
    }

    public int getPageId() {
        return pageId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int i) {
        this.time = i;
    }
}
