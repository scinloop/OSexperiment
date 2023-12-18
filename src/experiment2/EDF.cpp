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

	bool is_arrive;//�Ƿ񵽴��ʶ
	double last_service_time;//ʣ�����ʱ��
	vector<pair<double, double>> service_times;//����ʱ���
};

class TASK {
public:
	string name;
	int num;//��������
	double cycle_time;
	double service_time;
};

int n_task;//������������
vector<PCB> input_pcb;
vector<TASK> task;//����
deque<PCB> deque_pcb;//pcb����

void Pri() {
	cout << endl << "����:     ";
	for (int i = 0; i < input_pcb.size(); i++) {
		cout << input_pcb.at(i).name << " ";
	}
	cout << endl << "����ʱ��:  ";
	for (int i = 0; i < input_pcb.size(); i++) {
		cout << input_pcb.at(i).arrive_time << " ";
	}
	cout << endl << "����ʱ��: ";
	for (int i = 0; i < input_pcb.size(); i++) {
		cout << input_pcb.at(i).service_time << " ";
	}
	cout << endl << "��ֹʱ��: ";
	for (int i = 0; i < input_pcb.size(); i++) {
		cout << input_pcb.at(i).end_time << " ";
	}
}

void Pri(vector<PCB> arr) {
	cout << endl << "���н��" << endl;
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

	cout << "��������������: ";
	cin >> n_task;
	cout << "��������������,����ʱ��,����ʱ��,��������: " << endl;
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

	sort(input_pcb.begin(), input_pcb.end(), mysort);//�����а��ս�ֹʱ���С��������(���ٺ�����������)
	Pri();
	for (int i = 0; i < input_pcb.size(); i++) {
		deque_pcb.push_back(input_pcb.at(i));
	}
	input_pcb.clear();//��ո��ý���洢

	double run_time = 0;//����ʱ��
	while (!deque_pcb.empty()) {
		PCB t_pcb;
		PCB t_pcb_next;//��һ������������pcb����һ��������ʼ��pcb
		int n_t_pcb;//pcb����
		int n_t_pcb_next;//next pcb����
		bool is_first = true;
		//ɸѡҪ���е�����
		for (int i = 0; i < deque_pcb.size(); i++) {
			if (deque_pcb.at(i).arrive_time <= run_time) {
				if (is_first) {
					t_pcb = deque_pcb.at(i);
					n_t_pcb = i;
					t_pcb.is_arrive = true;
					if (deque_pcb.size() != 1) {
						t_pcb_next = deque_pcb.at(i + 1);
						n_t_pcb_next = i + 1;
					}//�ų���һ��
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
					deque_pcb.at(n_t_pcb_next - 1) = t_pcb_next;//ǰһ������ɾ����λ��ǰ��1λ
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
