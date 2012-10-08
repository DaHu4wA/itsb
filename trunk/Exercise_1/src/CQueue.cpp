#include "CQueue.h"
#include "CQElement.h"
#include <iostream>

using namespace std;

CQueue::CQueue() {
	first = NULL;
	last = NULL;
}

/**
 * Printing all enqueued jobs
 */
void CQueue::printJobs() {
	if (isEmpty()) {
		cout << "ERROR: No jobs to print!" << endl;
		return;
	}

	while (!isEmpty()) {
		CPJob *job = pop();
		cout << "Popped: PID: " << job->getPid() << " - Name: "
				<< job->getText() << endl;
	}
}

/**
 * Get the first enqueued job (the oldest) and remove it from the list
 */
CPJob* CQueue::pop() {

	if (isEmpty()) {
		cout << "ERROR: List is empty!" << endl;
		return NULL;
	}

	CQElement* temp = first;

	if (temp->getNext() == NULL) {
		first = NULL;

	} else {
		temp->getNext()->setPrev(NULL);
		first = temp->getNext();
	}

    return  temp->getData();
}

/**
 * Insert a new Job into the Queue
 */
void CQueue::push(CPJob *cpJob) {

	cout << "PUSHED: " << cpJob->getPid() << " / " << cpJob->getText() << endl;

	CQElement *elem = new CQElement(cpJob);

	if (isEmpty()) {
		first = elem;
		last = elem;
		return;
	}
	elem->setPrev(last);
	last->setNext(elem);
	last = elem;
}

CQueue::~CQueue(void){
	cout << "Queue deleted" << endl;
}

/**
 * Quick check if the queue is empty
 */
bool CQueue::isEmpty() {
	return first == NULL;
}
