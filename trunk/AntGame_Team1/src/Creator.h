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
    int getInitialAntCount();
    void setInitialAntCount(int count);

	char* getAntName();

	Ant* createAnt(Field* currentField);
	Food* createFood(Field* currentField);
	AntHill* createAntHill(Field* currentField);

private:
	Creator();

	static Creator* pCreator;
	int initialAntCount;
	int randomFoodCount();
};

#endif
