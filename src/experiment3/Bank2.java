package experiment3;


import java.util.Scanner;

public class Bank2 {
    static int pcb_nums;
    static int res_nums;
    static int[][] max;
    static int[][] alloc;
    static int[][] need;
    static int[] ava;
    static int[] request;
    static int[] safe_seq;

    static void bank_init() {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入进程的数量：");
        pcb_nums = in.nextInt();
        System.out.println("请输入资源的数量：");
        res_nums = in.nextInt();
        safe_seq = new int[pcb_nums + 1];
        max = new int[pcb_nums][res_nums];
        alloc = new int[pcb_nums][res_nums];
        need = new int[pcb_nums][res_nums];
        ava = new int[res_nums];

        // 输入最大资源
        System.out.println("请输入Max资源：");
        for (int i = 0; i < pcb_nums; i++) {
            for (int j = 0; j < res_nums; j++) {
                max[i][j] = in.nextInt();
            }
        }

        // 输入分配资源
        System.out.println("请输入分配资源(Allocation)：");
        for (int i = 0; i < pcb_nums; i++) {
            for (int j = 0; j < res_nums; j++) {
                alloc[i][j] = in.nextInt();
            }
        }

        // 计算需求矩阵
        for (int i = 0; i < pcb_nums; i++) {
            for (int j = 0; j < res_nums; j++) {
                need[i][j] = max[i][j] - alloc[i][j];
            }
        }

        // 输入可用资源
        System.out.println("请输入可用资源(available):");
        for (int j = 0; j < res_nums; j++) {
            ava[j] = in.nextInt();
        }
    }

    static boolean compare(int[] m, int[] n) {
        for (int i = 0; i < res_nums; i++) {
            if (m[i] < n[i]) {
                return false;
            }
        }
        return true;
    }

    static boolean safe() {
        boolean[] finish = new boolean[pcb_nums];
        int[] work = new int[res_nums];
        int num = 0;

        for (int i = 0; i < res_nums; i++) {
            work[i] = ava[i];
        }

        for (int i = 0; i < pcb_nums; i++) {
            if (num == pcb_nums)
                break;
            for (int j = 0; j < pcb_nums; j++) {
                if (finish[j])
                    continue;
                else {
                    if (compare(work, need[j])) {
                        finish[j] = true;
                        safe_seq[num] = j;
                        num++;

                        // 释放进程资源
                        for (int k = 0; k < res_nums; k++) {
                            work[k] = work[k] + alloc[j][k];
                        }
                    }
                }
            }
        }

        for (int i = 0; i < pcb_nums; i++) {
            if (!finish[i])
                return false;
        }

        // 输出安全序列
        for (int i = 0; i < pcb_nums; i++) {
            System.out.print(safe_seq[i] + " ");
        }

        return true;
    }

    static void resafe(int n) {
        if (compare(ava, request) && compare(need[n - 1], request)) {
            for (int i = 0; i < res_nums; i++) {
                alloc[n - 1][i] = alloc[n - 1][i] + request[i];
                need[n - 1][i] = need[n - 1][i] - request[i];
                ava[i] = ava[i] - request[i];
            }

            if (safe()) {
                System.out.println("允许进程" + (n - 1) + "申请资源");
            } else {
                System.out.println("不允许进程" + (n - 1) + "申请资源");
                for (int i = 0; i < res_nums; i++) {
                    alloc[n - 1][i] = alloc[n - 1][i] - request[i];
                    need[n - 1][i] = need[n - 1][i] + request[i];
                    ava[i] = ava[i] + request[i];
                }
            }
        } else {
            System.out.println("申请资源量越界");
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n;
        bank_init();

        if (safe()) {
            System.out.println("存在安全序列，初始状态安全。");
        } else {
            System.out.println("不存在安全序列，初始状态不安全。");
        }

        System.out.println("请输入发出请求向量request的进程编号：");
        n = in.nextInt();
        request = new int[res_nums];

        System.out.println("请输入请求向量request:");
        for (int i = 0; i < res_nums; i++) {
            request[i] = in.nextInt();
        }
        resafe(n + 1);
    }
}

