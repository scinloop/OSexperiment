package experiment7.FIFO;

/**
 * @Author scinloop
 * @Date 2023/12/19 16:24
 */
import java.util.LinkedList;
import java.util.Queue;

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
}
