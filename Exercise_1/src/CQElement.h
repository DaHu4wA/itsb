#include "CPJob.h"

#ifndef CQElement_H_
#define CQElement_H_

class CQElement {
private:
	CPJob *job;
	CQElement *next;
	CQElement *prev;

public:
	CQElement(CPJob *cpJob);
	~CQElement(void);
	CQElement* getNext();
	CQElement* getPrev();
	void setNext(CQElement *next);
	void setPrev(CQElement *prev);
	CPJob* getData();
};

#endif /* CQElement_H_ */
