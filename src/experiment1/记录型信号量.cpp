#include <iostream>
#include <thread>
#include <semaphore.h>

const int n = 5;  // 缓冲区大小
int in = 0, out = 0;
int buffer[n];
sem_t mutex, empty, full;

void producer() {
    while (true) {
        int nextp = rand() % 100;  // 生成一个随机生产的物品
        // 生产者等待缓冲区非满
        sem_wait(&empty);
        // 获取缓冲区访问权
        sem_wait(&mutex);
        buffer[in] = nextp;
        in = (in + 1) % n;
        // 释放缓冲区访问权
        sem_post(&mutex);
        // 增加缓冲区中的物品数量
        sem_post(&full);
        std::cout << "生产: " << nextp << " (缓冲区: " << (in - out + n) % n << "/" << n << ")" << std::endl;
        // 模拟生产者工作时间
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }
}

void consumer() {
    while (true) {
        // 消费者等待缓冲区非空
        sem_wait(&full);
        // 获取缓冲区访问权
        sem_wait(&mutex);
        int nextc = buffer[out];
        out = (out + 1) % n;
        // 释放缓冲区访问权
        sem_post(&mutex);
        // 增加缓冲区的空闲位置
        sem_post(&empty);
        std::cout << "消费: " << nextc << " (缓冲区: " << (in - out + n) % n << "/" << n << ")" << std::endl;
        // 模拟消费者工作时间
        std::this_thread::sleep_for(std::chrono::milliseconds(2000));
    }
}

int main() {
    // 初始化信号量
    sem_init(&mutex, 0, 1);
    sem_init(&empty, 0, n);
    sem_init(&full, 0, 0);

    std::thread producerThread(producer);
    std::thread consumerThread(consumer);

    producerThread.join();
    consumerThread.join();

    // 销毁信号量
    sem_destroy(&mutex);
    sem_destroy(&empty);
    sem_destroy(&full);

    return 0;
}
