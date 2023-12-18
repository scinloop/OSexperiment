#include<iostream>
#include<string>
#include<vector>
#include<deque>
#include<map>
#include<algorithm>
using namespace std;

class PCB {
public:
	string name;
	double arrive_time;
	double service_time;
	double end_time;

	bool is_arrive;//是否到达标识
	double last_service_time;//剩余完成时间
	vector<pair<double, double>> service_times;//运行时间段
};

class TASK {
public:
	string name;
	int num;//周期数量
	double cycle_time;
	double service_time;
};

int n_task;//周期任务数量
vector<PCB> input_pcb;
vector<TASK> task;//任务
deque<PCB> deque_pcb;//pcb队列

void Pri() {
	cout << endl << "名称:     ";
	for (int i = 0; i < input_pcb.size(); i++) {
		cout << input_pcb.at(i).name << " ";
	}
	cout << endl << "到达时间:  ";
	for (int i = 0; i < input_pcb.size(); i++) {
		cout << input_pcb.at(i).arrive_time << " ";
	}
	cout << endl << "服务时间: ";
	for (int i = 0; i < input_pcb.size(); i++) {
		cout << input_pcb.at(i).service_time << " ";
	}
	cout << endl << "截止时间: ";
	for (int i = 0; i < input_pcb.size(); i++) {
		cout << input_pcb.at(i).end_time << " ";
	}
}

void Pri(vector<PCB> arr) {
	cout << endl << "运行结果" << endl;
	for (int i = 0; i < arr.size(); i++) {
		cout << arr.at(i).name << ": ";
		for (int j = 0; j < arr.at(i).service_times.size(); j++) {
			cout << arr.at(i).service_times.at(j).first << "-" << arr.at(i).service_times.at(j).second << "   ";
		}
		cout << endl;
	}
}

bool static mysort(PCB a, PCB b) {
	return a.arrive_time < b.arrive_time;
}

int main() {

	cout << "请输入任务数量: ";
	cin >> n_task;
	cout << "请输入任务名字,周期时间,处理时间,周期数量: " << endl;
	for (int i = 0; i < n_task; i++) {
		TASK t_task;
		cin >> t_task.name >> t_task.cycle_time >> t_task.service_time >> t_task.num;
		task.push_back(t_task);
	}

	for (int i = 0; i < task.size(); i++) {
		for (int j = 0; j < task.at(i).num; j++) {
			PCB t_pcb;
			t_pcb.arrive_time = j * task.at(i).cycle_time;
			t_pcb.service_time = task.at(i).service_time;
			t_pcb.last_service_time = t_pcb.service_time;
			t_pcb.end_time = t_pcb.arrive_time + task.at(i).cycle_time;
			t_pcb.name = task.at(i).name + to_string(j);
			t_pcb.is_arrive = false;
			input_pcb.push_back(t_pcb);
		}
	}

	sort(input_pcb.begin(), input_pcb.end(), mysort);//将队列按照截止时间从小到大排序(减少后续排序消耗)
	Pri();
	for (int i = 0; i < input_pcb.size(); i++) {
		deque_pcb.push_back(input_pcb.at(i));
	}
	input_pcb.clear();//清空复用结果存储

	double run_time = 0;//运行时间
	while (!deque_pcb.empty()) {
		PCB t_pcb;
		PCB t_pcb_next;//下一个符合条件的pcb或下一个即将开始的pcb
		int n_t_pcb;//pcb索引
		int n_t_pcb_next;//next pcb索引
		bool is_first = true;
		//筛选要运行的任务
		for (int i = 0; i < deque_pcb.size(); i++) {
			if (deque_pcb.at(i).arrive_time <= run_time) {
				if (is_first) {
					t_pcb = deque_pcb.at(i);
					n_t_pcb = i;
					t_pcb.is_arrive = true;
					if (deque_pcb.size() != 1) {
						t_pcb_next = deque_pcb.at(i + 1);
						n_t_pcb_next = i + 1;
					}//排除第一个
					else
						n_t_pcb_next = -1;
					if (t_pcb_next.arrive_time <= run_time)
						t_pcb_next.is_arrive = true;
					is_first = false;
				}
				else
					if (deque_pcb.at(i).end_time < t_pcb.end_time) {
						t_pcb_next = t_pcb;
						n_t_pcb_next = n_t_pcb;
						t_pcb_next.is_arrive = true;
						t_pcb = deque_pcb.at(i);
						n_t_pcb = i;
						t_pcb.is_arrive = true;
					}

			}
		}
		if (t_pcb_next.is_arrive == true) {
			t_pcb.service_times.push_back(make_pair(run_time, run_time + t_pcb.last_service_time));
			run_time += t_pcb.last_service_time;
			t_pcb.last_service_time = 0;
			cout << "1:run_time: " << run_time << endl;
			input_pcb.push_back(t_pcb);
			deque_pcb.erase(deque_pcb.begin() + n_t_pcb);
			if (n_t_pcb_next != -1) {
				if (n_t_pcb > n_t_pcb_next)
					deque_pcb.at(n_t_pcb_next) = t_pcb_next;
				else
					deque_pcb.at(n_t_pcb_next - 1) = t_pcb_next;//前一个任务删除，位置前移1位
			}

		}
		else {
			t_pcb.last_service_time -= t_pcb_next.arrive_time - run_time;
			t_pcb.service_times.push_back(make_pair(run_time, t_pcb_next.arrive_time));
			run_time = t_pcb_next.arrive_time;
			cout << "2:run_time: " << run_time << endl;
			deque_pcb.at(n_t_pcb) = t_pcb;
			if (n_t_pcb_next != -1) {
				deque_pcb.at(n_t_pcb_next) = t_pcb_next;
			}
		}

	}
	Pri(input_pcb);

	return 0;
}
