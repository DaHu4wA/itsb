#include "Item.h"
#include "Ant.h"
#include "AntHill.h"
#include "Food.h"

#ifndef CREATOR_H_
#define CREATOR_H_

class Creator {
public:

	static Creator* Instance();
	virtual ~Creator();

	Ant* createAnt(Field* currentField);
	Food* createFood(Field* currentField);
	AntHill* createAntHill(Field* currentField);

private:
	Creator();
	static Creator* pCreator;

	int randomFoodCount();
	int randomLifetime();
};

#endif
