#include <iostream>
#include <thread>
#include <semaphore.h>

const int n = 5;  // ��������С
int in = 0, out = 0;
int buffer[n];
sem_t mutex, empty, full;

void producer() {
    while (true) {
        int nextp = rand() % 100;  // ����һ�������������Ʒ
        // �����ߵȴ�����������
        sem_wait(&empty);
        // ��ȡ����������Ȩ
        sem_wait(&mutex);
        buffer[in] = nextp;
        in = (in + 1) % n;
        // �ͷŻ���������Ȩ
        sem_post(&mutex);
        // ���ӻ������е���Ʒ����
        sem_post(&full);
        std::cout << "����: " << nextp << " (������: " << (in - out + n) % n << "/" << n << ")" << std::endl;
        // ģ�������߹���ʱ��
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }
}

void consumer() {
    while (true) {
        // �����ߵȴ��������ǿ�
        sem_wait(&full);
        // ��ȡ����������Ȩ
        sem_wait(&mutex);
        int nextc = buffer[out];
        out = (out + 1) % n;
        // �ͷŻ���������Ȩ
        sem_post(&mutex);
        // ���ӻ������Ŀ���λ��
        sem_post(&empty);
        std::cout << "����: " << nextc << " (������: " << (in - out + n) % n << "/" << n << ")" << std::endl;
        // ģ�������߹���ʱ��
        std::this_thread::sleep_for(std::chrono::milliseconds(2000));
    }
}

int main() {
    // ��ʼ���ź���
    sem_init(&mutex, 0, 1);
    sem_init(&empty, 0, n);
    sem_init(&full, 0, 0);

    std::thread producerThread(producer);
    std::thread consumerThread(consumer);

    producerThread.join();
    consumerThread.join();

    // �����ź���
    sem_destroy(&mutex);
    sem_destroy(&empty);
    sem_destroy(&full);

    return 0;
}
