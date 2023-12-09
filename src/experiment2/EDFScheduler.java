package experiment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Task {
    String name;
    int period;
    int executionTime;
    int deadline;

    public Task(String name, int arrivalTime, int period, int executionTime) {
        this.name = name;
        this.period = period;
        this.executionTime = executionTime;
        this.deadline = arrivalTime + period;
    }
}

public class EDFScheduler {
    public static void main(String[] args) {
        // 创建任务实例
        Task taskA1 = new Task("taskA1", 0, 20, 10);
        Task taskA2 = new Task("taskA2", 20, 20, 10);
        Task taskA3 = new Task("taskA3", 40, 20, 10);
        Task taskA4 = new Task("taskA4", 60, 20, 10);
        Task taskA5 = new Task("taskA5", 80, 20, 10);
        Task taskB1 = new Task("taskB1", 0, 50, 25);
        Task taskB2 = new Task("taskB2", 50, 50, 25);



        // 创建任务列表
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskA1);
        tasks.add(taskA2);
        tasks.add(taskA3);
        tasks.add(taskA4);
        tasks.add(taskA5);
        tasks.add(taskB1);
        tasks.add(taskB2);
        // 调度任务
        scheduleTasks(new ArrayList<>(tasks), 100);
    }

    public static void scheduleTasks(List<Task> tasks, int totalTime) {
        int currentTime = 0;

        // 按照最早截止时间排序任务
        List<Task> sortedTasks = new ArrayList<>(tasks);
        Collections.sort(sortedTasks, (task1, task2) -> Integer.compare(task1.deadline, task2.deadline));

        System.out.println("任务调度顺序:");

        // 调度任务
        for (Task task : sortedTasks) {
            if (currentTime < task.deadline) {
                int remainingTime = Math.min(task.executionTime, task.deadline - currentTime);
                System.out.println("任务 " + task.name + " 在时间 " + currentTime + "ms 开始执行，执行时间 " + remainingTime + "ms");
                currentTime += remainingTime;

                // 如果任务未完成，重新加入任务列表
                if (currentTime < task.deadline) {
                    tasks.add(task);
                }
            }
        }
    }
}

