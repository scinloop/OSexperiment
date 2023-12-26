package experiment7.LRU;
import java.util.LinkedList;
import java.util.Queue;
/**
 * @Author scinloop
 * @Date 2023/12/26 13:28
 */
public class LRU {
    public static void main(String[] args) {
        int counttime = 0;
        Page[] pages = new Page[20];
        pages[0] = new Page(7, counttime++);
        pages[1] = new Page(0, counttime++);
        pages[2] = new Page(1, counttime++);
        pages[3] = new Page(2, counttime++);
        pages[4] = new Page(0, counttime++);
        pages[5] = new Page(3, counttime++);
        pages[6] = new Page(0, counttime++);
        pages[7] = new Page(4, counttime++);
        pages[8] = new Page(2, counttime++);
        pages[9] = new Page(3, counttime++);
        pages[10] = new Page(0, counttime++);
        pages[11] = new Page(3, counttime++);
        pages[12] = new Page(2, counttime++);
        pages[13] = new Page(1, counttime++);
        pages[14] = new Page(2, counttime++);
        pages[15] = new Page(0, counttime++);
        pages[16] = new Page(1, counttime++);
        pages[17] = new Page(7, counttime++);
        pages[18] = new Page(0, counttime++);
        pages[19] = new Page(1, counttime++);

        Queue<Page> queue = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            if (queue.size() < 3) {
                queue.add(pages[i]);
                System.out.println("栈未满，无需置换");
                //                输出栈中的所有页面
                for(Page page:queue){
                    System.out.print(page.getPageId()+" ");
                }
                System.out.println("\n");
            } else {
                int judge=0;
                for(Page page:queue){
                    if (pages[i].getPageId() == page.getPageId()) {
                        judge=1;
                    }
                }
                if (judge==1) {
//                   将queue中的与page[i]的pageid相同的页面的counttime改为与page[i].counttime相同
                    for (Page page : queue) {
                        if (page.getPageId() == pages[i].getPageId()) {
                            page.setTime(pages[i].getTime());
                        }
                    }
                    System.out.println("不需置换");
                    for(Page page:queue){
                        System.out.print(page.getPageId()+" ");
                    }
                    System.out.println("\n");
                }
                else {
                    System.out.println("页面置换");
                    //置换counttime最小的页面
                    int min = Integer.MAX_VALUE;
                    int index = 0;
                    for (int j = 0; j < queue.size(); j++) {
                        Page page = ((LinkedList<Page>) queue).get(j);
                        if (page.getTime() < min) {
                            min = page.getTime();
                            index = j;
                        }
                    }
                    ((LinkedList<Page>) queue).remove(index);
                    queue.add(pages[i]);
                    for(Page page:queue){
                        System.out.print(page.getPageId()+" ");
                    }
                    System.out.println("\n");
                }
            }
        }
    }
}
