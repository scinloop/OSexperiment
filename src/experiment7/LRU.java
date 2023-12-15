package experiment7;

/**
 * @Author scinloop
 * @Date 2023/12/9 20:20
 */
import java.util.Scanner;
import java.util.Stack;

public class LRU {
    int frameNum;    // 分配给该作业的物理页框数
    int[] pageFrame; // 物理页框
    int pageNum;     // 作业的页面走向总次数
    int[] pages;      // 作业的页面走向
    Stack<Integer> stack = new Stack<>(); // 存放淘汰页面的栈

    private void lruRun() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入分配给该作业的物理页框块数：");
        this.frameNum = scanner.nextInt();     // 物理页框数

        System.out.print("请输入该作业的页面走向：");
        scanner.nextLine(); // 控制输入格式
        String inputPages = scanner.nextLine();
        String[] split = inputPages.split("\\s+|,|，");
        this.pageNum = split.length;    // 作业的页面走向总次数

        pages = new int[pageNum];        // 作业的页面走向
        for (int i = 0; i < pageNum; i++) {
            this.pages[i] = Integer.parseInt(split[i]);
        }

        pageFrame = new int[frameNum];     // 物理页框

        int pageMissNum = 0;   // 缺页次数
        int count = 0;
        while (count < pageNum) {

            System.out.println("第" + (count+1) + "次：");

            boolean isMiss = true;    // 判断本次是否缺页
            boolean isEmpty = true;   // 判断物理页框中是否有空位
            boolean isExist = false; // 判断物理页框中是否存在本次页面走向

            // Queue<Integer> usedPage = new LinkedList<>();   // 保存之前的页面走向
            // usedPage.offer(pages[count]);   // 将页面走向进队

            // 判断物理页框中是否已经存在本次页面走向
            for (int i = 0; i < frameNum; i++) {
                if (pages[count] == pageFrame[i]) {
                    isExist = true;
                    break;
                }
            }
            // 若本次页面走向,物理页框中已存在，则直接进入下次页面走向
            if (isExist == true){
                System.out.println("本次页面走向，页框中已经存在！");
                System.out.print("目前物理页框中页面走向为：");
                for (int i : pageFrame) {
                    System.out.print(i + " ");
                }
                System.out.println();
                count++;
                continue;
            }

            // 判断物理页框有无空位
            for (int i = 0 ; i < frameNum ; i++){
                if (pageFrame[i] == 0){
                    isEmpty = true;
                    break;
                }else{
                    isEmpty = false;
                }
            }
            // 本次页面走向,物理页框中不存在，且有空位，按顺序放入
            if (isExist == false && isEmpty == true){
                for (int i = 0; i < frameNum; i++) {
                    if (pageFrame[i] == 0) {    // 物理页框中有空位则放入
                        pageFrame[i] = pages[count];
                        break;      // 从头开始找，找到一个空位即可
                    }
                }
            }

            // 本次页面走向，物理页框中不存在，且物理页框中没有空位了
            // 实现 LRU 算法
            if (!isExist && !isEmpty){
                for (int i = 0 ; i < frameNum ; i++){
                    if (pages[count-frameNum] == pageFrame[i]){
                        stack.push(pageFrame[i]);   // 淘汰页面入栈
                        pageFrame[i] = pages[count];
                    }
                }
            }

            if (isMiss == true){  // 计算缺页次数
                pageMissNum++;
            }

            System.out.print("目前物理页框中页面走向为：");
            for (int i : pageFrame) {
                System.out.print(i + " ");
            }
            System.out.println();
            count++;
        }
        System.out.println();

        System.out.println("缺页次数：" + pageMissNum + "次");
        System.out.println("一共调用: " + pageNum + "次");
        System.out.println("缺页中断率：" + pageMissNum*1.0/pageNum*100 + "%" );
        System.out.print("淘汰页面：");
        for (Integer integer : stack) {
            System.out.print(integer + " ");
        }
    }

    public static void main(String[] args) {
        LRU lru = new LRU();
        lru.lruRun();
    }

}
