#include "Item.h"

#ifndef CREATOR_H_
#define CREATOR_H_

class Creator {
public:

	static Creator* Instance();
	virtual ~Creator();

	Item* createAnt(Field* currentField);
	Item* createFood(Field* currentField);
	Item* createAntHill(Field* currentField);

private:
	Creator();
	static Creator* pCreator;

	int randomFoodCount();
	int randomLifetime();
};

#endif
