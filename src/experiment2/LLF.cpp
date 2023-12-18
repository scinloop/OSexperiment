#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;


struct process {
	string id;
	int periodic_time;
	int begin_time;
	int run_time;
	int laxity_time;
	int least_end_time;

	void setrun_time(int run) {		
		run_time -= run;
	}

};
bool cmparr(process p1, process p2)
{
 return p1.laxity_time < p2.laxity_time;
}
istream& operator >>(ifstream& in, process& p) {
	if (!in) EXIT_FAILURE;
	in >> p.id >> p.periodic_time >> p.run_time;
	return in;
}

int findRunTime(process p1, process* ss, int number) {
	string temp = p1.id;
	for (int i = 0; i < number; i++) {
		if (temp == ss[i].id) {
			return ss[i].run_time;
		}
	}
}
int main() {
	process p;
	process p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
	ifstream in;
	in.open("d:\\process.txt");
	if (!in)	return 0;

	int temp_time = 1;
	int jieshutime = 0;
	int number = 0;
	process ss[] = { p1, p2, p3, p4, p5 };
	process ss_runtime[] = { p6,p7,p8,p9,p10 };
	for (int i = 0; in >> p; i++) {
		ss[i] = p;
		ss_runtime[i] = p;
		number++;
		ss[i].begin_time = 0;			//周期实时任务，最关键在于到达时间和最晚截止时间的不断变化
		ss[i].least_end_time = ss[i].periodic_time + ss[i].begin_time;
		ss[i].laxity_time = ss[i].least_end_time - ss[i].run_time - temp_time;
	}
	sort(ss, ss + number, cmparr);
	jieshutime = ss[0].run_time;		//初始化两个任务的到达时间都为零
	int temp_jieshutime = 0;
	int flag = 0;
	int fflag = 1;
	while (true) {
		if (temp_time == 160) break;
		temp_time++;
		ss[fflag].laxity_time = ss[fflag].least_end_time - ss[fflag].run_time - temp_time;	//考虑另一个任务的松弛度		
		if (ss[fflag].laxity_time == 0 && ss[flag].run_time - temp_time + temp_jieshutime != 0) {		//A2抢占B1	当前任务没有结束，但是另一个任务松弛度为0
			int a = temp_time - temp_jieshutime;			//判断为0后continue无法退出
			cout << ss[flag].id << "执行了" << a << "ms时间" << endl;
			ss[flag].setrun_time(a);		//当前任务结束
			sort(ss, ss + number, cmparr);
			jieshutime = temp_time;		//特判
			temp_jieshutime = jieshutime;
			if (temp_time < ss[flag].begin_time) {
				flag = 1;
				fflag = 0;
			}
			else {
				flag = 0;
				fflag = 1;
				jieshutime += ss[flag].run_time;
			}
		}
		if (flag == 1 && ss[flag].run_time - temp_time + temp_jieshutime == 0) {
			goto part1;
		}
		if (temp_time != jieshutime)continue;
	part1:
		int a = temp_time - temp_jieshutime;
		cout << ss[flag].id << "执行了" << a << "ms时间" << endl;
		ss[flag].setrun_time(a);
		if (ss[flag].run_time == 0) {		//判断当前进程是否结束
			ss[flag].run_time = findRunTime(ss[flag], ss_runtime,number);
			ss[flag].begin_time += ss[flag].periodic_time;
			ss[flag].least_end_time = ss[flag].periodic_time + ss[flag].begin_time;
			ss[flag].laxity_time = ss[flag].least_end_time - ss[flag].run_time - temp_time;
			cout << ss[flag].id << "在" << temp_time << "ms时结束" << endl;
		}
		sort(ss, ss + number, cmparr);		//s[flag]B1
		temp_jieshutime = jieshutime;
		if (temp_time < ss[flag].begin_time) {		
			//jieshutime = ss[flag].begin_time;		判断标志没有新进程的进入
			flag = 1;
			fflag = 0;			//为什么不设置jieshutime，当A4松弛度为0时自动退出
		}
		else {					
			flag = 0;
			fflag = 1;
			jieshutime += ss[flag].run_time;
		}

	}

	in.close();
	return 0;
}
