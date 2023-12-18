#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>

const int n = 5;  // 缓冲区大小
int in = 0, out = 0;
int buffer[n];
std::mutex mutex;
std::condition_variable empty, full;

void producer() {
    while (true) {
        int nextp = rand() % 100;  // 生成一个随机生产的物品
        {
            std::unique_lock<std::mutex> lock(mutex);
            // 生产者等待缓冲区非满
            while (in - out == n) {
                empty.wait(lock);
            }
            buffer[in] = nextp;
            in = (in + 1) % n;
        }
        // 增加缓冲区中的物品数量
        full.notify_one();
        std::cout << "生产: " << nextp << " (缓冲区: " << (in - out + n) % n << "/" << n << ")" << std::endl;
        // 模拟生产者工作时间
        std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    }
}

void consumer() {
    while (true) {
        int nextc;
        {
            std::unique_lock<std::mutex> lock(mutex);
            // 消费者等待缓冲区非空
            while (in == out) {
                full.wait(lock);
            }
            nextc = buffer[out];
            out = (out + 1) % n;
        }
        // 增加缓冲区的空闲位置
        empty.notify_one();
        std::cout << "消费: " << nextc << " (缓冲区: " << (in - out + n) % n << "/" << n << ")" << std::endl;
        // 模拟消费者工作时间
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
