#include  <iostream>
#include  "CPJob.h"
#include  "CQueue.h"

using namespace std;

void fillQueue(CQueue* pQueue, int num) {
	CPJob  *pPJob;
	for(int  i=0;  i<num;  i++){
		pPJob=  new  CPJob("text",i);
		pQueue->push(pPJob);
	}
}

int  main(int argc, char* argv[]) {
	CQueue *pQueue;
	pQueue =  new CQueue();
	cout << "---- Pushing -----" << endl;
	fillQueue(pQueue,3);
	cout << "---- Popping -----" << endl;
	pQueue->printJobs();
	cout << "----------------------------" << endl;
	cout << "---- Pushing -----" << endl;
	fillQueue(pQueue,3);
	pQueue->pop();
	cout << "-- one already popped ---" << endl;
	pQueue->printJobs();
	cout << "----------------------------" << endl;
	cout << "-- Popping again from empty queue (should fail) --" << endl;
	pQueue->pop();
	delete pQueue;
	return  0;
}
