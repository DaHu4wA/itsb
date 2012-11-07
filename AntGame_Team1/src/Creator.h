#include "Item.h"

#ifndef CREATOR_H_
#define CREATOR_H_

class Creator {
public:

	static Creator* Instance();
	virtual ~Creator();

	Item* createAnt();
	Item* createFood();
	Item* createAntHill();

private:
	Creator();
	static Creator* pCreator;
};

#endif
