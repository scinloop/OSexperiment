int in=0,out=0;
item buffer[n];
semaphore mutex = 1,empty = n,full = 0;
void producer() {
	do{
		producer an item nextp;
		...
		wait(empty);
		wait(mutex);
		buffer[in] = nextp;
		in = (in+1)%n;
		signal(mutex);
		signal(full);
	}while(TRUE);
}
void consumer() {
	do {
		wait(full);
		wait(mutex);
		nextc = buffer[out];
		out =(out+1)%n;
		signal(mutex);
		signal(empty);
		consumer the item in nextc;
		...
	}while(TRUE);
}
void main() {
	cobegin
		producer();consumer();
		coend
}



int in=0,out=0;
item buffer[n];
semaphore mutex = 1,empty = n,full = 0;
void producer() {
	do{
		producer an item nextp;
		...
		Swait(empty,mutex);
		buffer[in] = nextp;
		in = (in+1)%n;
		Ssignal(mutex,full);
	}while(TRUE);
}
void consumer() {
	do {
		Swait(full,mutex);
		nextc = buffer[out];
		out =(out+1)%n;
		Ssignal(mutex,empty);
		consumer the item in nextc;
		...
	}while(TRUE);
}






