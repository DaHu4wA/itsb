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

	bool antsAreAlive();
	void incrementAntCount();
	void decrementAntCount();

	int randomLifetime();

	char* getAntName();

	Ant* createAnt(Field* currentField);
	Food* createFood(Field* currentField);
	AntHill* createAntHill(Field* currentField);

private:
	Creator();

	static Creator* pCreator;

	int randomFoodCount();
};

#endif
