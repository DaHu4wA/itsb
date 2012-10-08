#include "CQElement.h"
#include <stddef.h>
#include <iostream>

using namespace std;

/**
 * Represents a queue element that contains the job
 */

CQElement::CQElement(CPJob *cpJob) {
	job = cpJob;
	next = NULL;
	prev = NULL;
}

CQElement* CQElement::getNext() {
	return next;
}
CQElement* CQElement::getPrev() {
	return prev;
}
void CQElement::setNext(CQElement *qNext) {
	next = qNext;
}
void CQElement::setPrev(CQElement *qPrev) {
	prev = qPrev;
}
CPJob* CQElement::getData() {
	return job;
}

CQElement::~CQElement(){
	delete job;
	cout << "Queue Element geloescht" << endl;
}
