package experiment2;

/**
 * @author cyt
 * @date 2023/11/3 10:17
 * @desc
 */
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

class RealTimeTask {
    String name;
    int period;//周期
    int executionTime;//运行时间
    int deadline;//截止时间
    int remainingTime;//剩余时间

    public RealTimeTask(String name, int period, int executionTime) {
        this.name = name;
        this.period = period;                // 周期时间
        this.executionTime = executionTime;  // 需要执行时间
        this.deadline = period;              // 截止时间
        this.remainingTime = executionTime;  // 剩余执行时间
    }
}

public class EDF {
    public static void main(String[] args) {
        List<RealTimeTask> tasks = new ArrayList<>();
        tasks.add(new RealTimeTask("A", 20, 10));
        tasks.add(new RealTimeTask("B", 50, 25));

        int currentTime = 0;

        while (true) {
            List<RealTimeTask> readyTasks = new ArrayList<>();
            for (RealTimeTask task : tasks) {
                if (currentTime % task.period == 0) {
                    task.remainingTime = task.executionTime;
                }
                if (task.remainingTime > 0 && currentTime >= task.deadline) {
                    System.out.println("任务截止时间过了 " + task.name);
                    return;
                }
                if (task.remainingTime > 0) {  // 还有剩余时间  加到就绪队列
                    readyTasks.add(task);
                }
            }

            if (readyTasks.isEmpty()) {
                currentTime++;
            } else {
                // 根据最早截至时间排序
                Collections.sort(readyTasks, (a, b) -> a.deadline - b.deadline);
                RealTimeTask taskToExecute = readyTasks.get(0);
                taskToExecute.remainingTime--;

                try {
                    System.out.println("执行任务中" + taskToExecute.name + " 当前时间为： " + currentTime);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                currentTime++;

                if (taskToExecute.remainingTime == 0) {
                    taskToExecute.deadline += taskToExecute.period;
                    System.out.println("任务执行完毕 " + taskToExecute.name + " 当前时间为： " + currentTime);
                }
            }
        }
    }
}
