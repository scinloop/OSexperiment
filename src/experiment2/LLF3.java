package experiment2;

/**
 * @author cyt
 * @date 2023/11/3 14:26
 * @desc
 */
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class RealTimeTask4 {
    String name;
    int period;
    int executionTime; // 需要执行时间
    int deadline;      // 截止数据 1
    int remainingTime; // 剩余时间 1
    int laxity;        // 松弛度   1

    public RealTimeTask4(String name, int period, int executionTime) {
        this.name = name;
        this.period = period;
        this.executionTime = executionTime;
        this.deadline = period;
        this.remainingTime = executionTime;
        this.laxity = deadline - remainingTime;
    }

    public RealTimeTask4() {

    }

    @Override
    public String toString() {
        return "RealTimeTask4{" +
                "name='" + name + '\'' +
                ", period=" + period +
                ", executionTime=" + executionTime +
                ", deadline=" + deadline +
                ", remainingTime=" + remainingTime +
                ", laxity=" + laxity +
                '}';
    }
}

public class LLF3 {
    public static void main(String[] args) {
        List<RealTimeTask4> tasks = new ArrayList<>();
        tasks.add(new RealTimeTask4("A", 20, 10));
        tasks.add(new RealTimeTask4("B", 50, 25));

        int currentTime = 0;

        while (true) {
            List<RealTimeTask4> readyTasks = new ArrayList<>();
            RealTimeTask4 temp = new RealTimeTask4();
            for (RealTimeTask4 task : tasks) {
                if (currentTime % task.period == 0) {
                    task.remainingTime = task.executionTime;
                    task.deadline = currentTime + task.period;  // 更新截止时间
                    task.laxity = task.deadline - currentTime - task.remainingTime;
                }
                if (task.remainingTime > 0 && currentTime >= task.deadline) {
                    System.out.println("Deadline Missed for task " + task.name);
                    return;
                }
                if (task.remainingTime > 0) {
                    readyTasks.add(task);
                }
            }

            if (readyTasks.isEmpty()) {
                currentTime++;
            } else {
                readyTasks.sort((a, b) -> a.laxity - b.laxity);
                RealTimeTask4 taskToExecute = readyTasks.get(0);

                taskToExecute.remainingTime--;


                try {
                    System.out.println("执行任务 ---- " + taskToExecute.name + " 当前时间： " + currentTime);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime++;

                taskToExecute.laxity = taskToExecute.deadline - (currentTime + taskToExecute.remainingTime);

                if (taskToExecute.remainingTime == 0) {
                    // taskToExecute.deadline += taskToExecute.period;
                    taskToExecute.laxity = 100;
                    System.out.println("任务执行完毕 ******* " + taskToExecute.name + " 当前时间： " + currentTime);
                }
            }
        }
    }
}



