#include "CPJob.h"
#include "CQElement.h"
#include <cstring>

#ifndef CQUEUE_H_	//damit verhindert man doppelte Einbindung im compiler
#define CQUEUE_H_

class CQueue {
private:
	CQElement* first;
	CQElement* last;
	bool isEmpty();

public:
	CQueue();
	~CQueue(void);
	void printJobs(void);
	CPJob* pop(void);
	void push(CPJob *cpJob);
};

#endif
