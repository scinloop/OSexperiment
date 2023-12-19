#include <stdio.h>
#define L 10
struct Free_node {
	int ID;//空闲区的序号
	int address;//空闲区的起始地址
	int size;//空闲区的大小
	int state;//分配状态：0表示已分配，1表示未分配
} Free_node[10] = {{1, 0, 50, 1}, {2, 150, 200, 1}, {3, 350, 20, 1}, {4, 450, 150, 1}, {5, 750, 100, 1}, {6, 900, 150, 1}};

int main() {
	void FirstFit();//首次适应算法
	void NextFit();//循环首次适应算法
	void BestFit();//最佳适应算法
	void WorstFit();//最差适应算法
	void recycle();//回收
	void print();//打印
	int sortaddress();//将空闲区按地址从小到大排列
	void sortrise();//将空闲区按容量从小到大排列
	int sortdown();//将空闲区按容量从大到小排列

	printf("模拟动态分区分配算法以下为：");

	printf("\n1、首次适应算法\n2、循环首次适应算法");
	printf("\n3、最坏适应算法\n4、最佳适应算法");
	printf("\n5、回收内存\n6、打印空闲分区表\n");
	printf("请选择算法1~6，按enter键开始：");

	while (getchar()) {
		int i;
		scanf("%d", &i);
		switch (i) {
			case 1:
				FirstFit();
				break;
			case 2:
				NextFit();
				break;
			case 3:
				WorstFit();
				break;
			case 4:
				BestFit();
				break;
			case 5:
				recycle();
				break;
			case 6:
				print();
				break;
			default:
				printf("您的输入有误！\n");
		}
	}
}

int sortaddress() { //将空闲区按地址从小到大排
	int i, j;
	struct Free_node t;
	for (j = 0; j < 6; j++) {
		for (i = 0; i < 6 - j - 1; i++) {
			if (Free_node[i].address > Free_node[i + 1].address) {
				t = Free_node[i];
				Free_node[i] = Free_node[i + 1];
				Free_node[i + 1] = t;
			}
		}

	}
	for (i = 0; i < 7; i++) {
		if (Free_node[i].size == 0) {
			Free_node[i] = Free_node[i + 1];
		} else
			return 1;
	}
	return 0;
}


void sortrise() {
	int i, j;
	struct Free_node t;
	for (j = 0; j < 6; j++) {
		for (i = 0; i < 6 - j - 1; i++) {
			if (Free_node[i].size > Free_node[i + 1].size) {
				t = Free_node[i];
				Free_node[i] = Free_node[i + 1];
				Free_node[i + 1] = t;
			}
		}
	}
}


int sortdown() {
	int i, j;
	struct Free_node t;
	for (j = 0; j < 6; j++) {
		for (i = 0; i < 6 - j - 1; i++) {
			if (Free_node[i].size < Free_node[i + 1].size) {
				t = Free_node[i];
				Free_node[i] = Free_node[i + 1];
				Free_node[i + 1] = t;
			}
		}
	}
	for (i = 0; i < 7; i++) {
		if (Free_node[i].size == 0) {
			Free_node[i] = Free_node[i + 1];
		} else
			return 1;
	}
	return 0;
}


//首次适应算法

void FirstFit() {
	int applyarea;
	int i;
	printf("请输入作业申请量：\n");
	scanf("%d", &applyarea);
	for (i = 0; i < 6; i++) {
		if (Free_node[i].state == 1 && Free_node[i].size > applyarea) {
			printf("申请的作业的起始地址为：%d\n", Free_node[i].address);
			printf("内存分配成功！\n");
			Free_node[i].address = Free_node[i].address + applyarea;
			Free_node[i].size = Free_node[i].size - applyarea;
			break;
		} else if (Free_node[i].state == 1 && Free_node[i].size == applyarea) {
			Free_node[i].state = 0;
			printf("申请的作业的起始地址为：%d\n", Free_node[i].address);
			printf("内存分配成功！\n");
			break;
		}

	}
	if (i == 6) {
		printf("输入的作业量过大，内存分配失败！\n");
	}
}


//循环首次适应算法
void NextFit() {
	int s, i, applyarea;
	printf("请输入作业申请量：\n");
	scanf("%d", &applyarea);
	for (i = 0; i < 6; i++) {
		if ( Free_node[i].state == 0) {
			s = i;
			break;
		}
	}
	for (i = s + 1; i < 6; i++) { //从上一个分配后的内存找下一个空闲区
		if (Free_node[i].state == 1 && Free_node[i].size > applyarea) {
			printf("申请的作业的起始地址为：%d\n", Free_node[i].address);
			printf("内存分配成功！\n");
			Free_node[i].address = Free_node[i].address + applyarea;
			Free_node[i].size = Free_node[i].size - applyarea;
			break;
		} else if (Free_node[i].state == 1 && Free_node[i].size == applyarea) {
			Free_node[i].state = 0;
			printf("申请的作业的起始地址为：%d\n", Free_node[i].address);
			printf("内存分配成功！\n");
			break;
		}
	}
	if (i == 6) {
		printf("输入的作业量过大，内存分配失败！\n");
	}
}


//最佳适应算法
void BestFit() {
	sortrise();
	int applyarea;
	int i = 0;
	printf("请输入作业申请量：\n");
	scanf("%d", &applyarea);
	int final = 100000;
	int min = 0;
	if (!(Free_node[0].size >= applyarea || Free_node[1].size >= applyarea || Free_node[2].size >= applyarea || Free_node[3].size >= applyarea || Free_node[4].size >= applyarea || Free_node[5].size >= applyarea)) {
		printf("输入的作业量过大，内存分配失败！\n");
	} else {
		for (i = 0; i < 6; i++) {
			if (Free_node[i].state == 1 && Free_node[i].size >= applyarea) {
				if (final >= Free_node[i].size - applyarea) {
					final = Free_node[i].size - applyarea;
					min = i;
				}
			}
		}
		printf("申请的作业的起始地址为：%d\n", Free_node[min].address);
		printf("内存分配成功！\n");
	}
}



//最坏适应算法
void WorstFit() {
	sortrise();
	int applyarea;
	int i = 0;
	printf("请输入作业申请量：\n");
	scanf("%d", &applyarea);
	int final = 0;
	int max = 0;
	if (!(Free_node[0].size >= applyarea || Free_node[1].size >= applyarea || Free_node[2].size >= applyarea || Free_node[3].size >= applyarea || Free_node[4].size >= applyarea || Free_node[5].size >= applyarea)) {
		printf("输入的作业量过大，内存分配失败！\n");
	} else {
		for (i = 0; i < 6; i++) {
			if (Free_node[i].state == 1 && Free_node[i].size >= applyarea) {
				if (final <= Free_node[i].size - applyarea) {
					final = Free_node[i].size - applyarea;
					max = i;
				}
			}
		}
		printf("申请的作业的起始地址为：%d\n", Free_node[max].address);
		printf("内存分配成功！\n");
	}
}


void recycle() { //回收
	sortaddress();//先按地址大小给排序
	int a, s;
	int i, j;
	printf("请输入需要回收的内存起始地址：");
	scanf("%d", &a);
	printf("请输入需要回收的内存大小：");
	scanf("%d", &s);
	for (i = 0; i < 10; i++) {
		if ((Free_node[i].address == a + s) && (Free_node[i].state == 1)) { //回收区与下一个空闲区相邻
			if (Free_node[i - 1].address + Free_node[i - 1].size == a) { //回收区与上一个空闲区也相邻
				Free_node[i - 1].size = Free_node[i - 1].size + s + Free_node[i].size;
				for (j = i; j < 10; j++) {
					Free_node[j] = Free_node[j + 1];
				}
				break;

			} else {
				Free_node[i].address = a;
				Free_node[i].size = Free_node[i].size + s;
				break;
			}
		} else if (Free_node[i].address + Free_node[i].size == a && (Free_node[i].state == 1)) { //回收区与上一个空闲区相邻
			if (Free_node[i + 1].address == a + s) {
				Free_node[i].size = Free_node[i].size + s + Free_node[i + 1].size;
				for (j = i + 1; j < 10; j++) {
					Free_node[j] = Free_node[j + 1];
				}
				break;
			} else {
				Free_node[i].size = Free_node[i].size + s;
				break;
			}
		}

	}
	if (i == 10) { //回收区无相邻的空闲区
		for (i = 0; i < 10; i++) {
			if (Free_node[i].ID == 0) { //建立新表项
				Free_node[i].ID = i + 1;
				Free_node[i].address = a;
				Free_node[i].size = s;
				Free_node[i].state = 1;
				break;
			}
		}
	}
}


void print() { //打印
	printf("为作业分配内存后，空闲分区为：\n");

	printf("        序号        起始地址       空闲区大小\n");
	int i;
	sortaddress();
	for (i = 0; i < 7; i++) {
		if (Free_node[i].state == 1) {
			printf("         %d             %3d              %d\n", Free_node[i].ID, Free_node[i].address, Free_node[i].size);
		}
	}
}
