#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>

const int n = 5;  // ��������С
int in = 0, out = 0;
int buffer[n];
std::mutex mutex;
std::condition_variable empty, full;

void producer() {
    while (true) {
        int nextp = rand() % 100;  // ����һ�������������Ʒ
        {
            std::unique_lock<std::mutex> lock(mutex);
            // �����ߵȴ�����������
            while (in - out == n) {
                empty.wait(lock);
            }
            buffer[in] = nextp;
            in = (in + 1) % n;
        }
        // ���ӻ������е���Ʒ����
        full.notify_one();
        std::cout << "����: " << nextp << " (������: " << (in - out + n) % n << "/" << n << ")" << std::endl;
        // ģ�������߹���ʱ��
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }
}

void consumer() {
    while (true) {
        int nextc;
        {
            std::unique_lock<std::mutex> lock(mutex);
            // �����ߵȴ��������ǿ�
            while (in == out) {
                full.wait(lock);
            }
            nextc = buffer[out];
            out = (out + 1) % n;
        }
        // ���ӻ������Ŀ���λ��
        empty.notify_one();
        std::cout << "����: " << nextc << " (������: " << (in - out + n) % n << "/" << n << ")" << std::endl;
        // ģ�������߹���ʱ��
        std::this_thread::sleep_for(std::chrono::milliseconds(2000));
    }
}

int main() {
    std::thread producerThread(producer);
    std::thread consumerThread(consumer);

    producerThread.join();
    consumerThread.join();

    return 0;
}
