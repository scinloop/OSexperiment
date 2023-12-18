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
		ss[i].begin_time = 0;			//����ʵʱ������ؼ����ڵ���ʱ��������ֹʱ��Ĳ��ϱ仯
		ss[i].least_end_time = ss[i].periodic_time + ss[i].begin_time;
		ss[i].laxity_time = ss[i].least_end_time - ss[i].run_time - temp_time;
	}
	sort(ss, ss + number, cmparr);
	jieshutime = ss[0].run_time;		//��ʼ����������ĵ���ʱ�䶼Ϊ��
	int temp_jieshutime = 0;
	int flag = 0;
	int fflag = 1;
	while (true) {
		if (temp_time == 160) break;
		temp_time++;
		ss[fflag].laxity_time = ss[fflag].least_end_time - ss[fflag].run_time - temp_time;	//������һ��������ɳڶ�		
		if (ss[fflag].laxity_time == 0 && ss[flag].run_time - temp_time + temp_jieshutime != 0) {		//A2��ռB1	��ǰ����û�н�����������һ�������ɳڶ�Ϊ0
			int a = temp_time - temp_jieshutime;			//�ж�Ϊ0��continue�޷��˳�
			cout << ss[flag].id << "ִ����" << a << "msʱ��" << endl;
			ss[flag].setrun_time(a);		//��ǰ�������
			sort(ss, ss + number, cmparr);
			jieshutime = temp_time;		//����
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
		cout << ss[flag].id << "ִ����" << a << "msʱ��" << endl;
		ss[flag].setrun_time(a);
		if (ss[flag].run_time == 0) {		//�жϵ�ǰ�����Ƿ����
			ss[flag].run_time = findRunTime(ss[flag], ss_runtime,number);
			ss[flag].begin_time += ss[flag].periodic_time;
			ss[flag].least_end_time = ss[flag].periodic_time + ss[flag].begin_time;
			ss[flag].laxity_time = ss[flag].least_end_time - ss[flag].run_time - temp_time;
			cout << ss[flag].id << "��" << temp_time << "msʱ����" << endl;
		}
		sort(ss, ss + number, cmparr);		//s[flag]B1
		temp_jieshutime = jieshutime;
		if (temp_time < ss[flag].begin_time) {		
			//jieshutime = ss[flag].begin_time;		�жϱ�־û���½��̵Ľ���
			flag = 1;
			fflag = 0;			//Ϊʲô������jieshutime����A4�ɳڶ�Ϊ0ʱ�Զ��˳�
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
